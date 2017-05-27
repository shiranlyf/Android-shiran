package com.car.fragment;

import in.srain.cube.views.ptr.PtrDefaultHandler;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.R.color;
import android.R.integer;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.car.R;
import com.car.activity.CarDetailActivity;
import com.car.adapter.NewsHeadAdapter;
import com.car.adapter.NewsItemAdapter;
import com.car.application.LocalApplication;
import com.car.entity.Newshead;
import com.car.entity.Newsimportant;
import com.car.util.ConstantsUtil;
import com.car.util.DisplayUtil;
import com.car.util.JListKit;
import com.car.view.ProgressWheel;
import com.car.view.ToastMaker;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.lidroid.xutils.view.annotation.event.OnScroll;
import com.lidroid.xutils.view.annotation.event.OnScrollStateChanged;

/**
 * 资讯-要闻 ctrl+shift+r：打开资源
 * 
 * 刷新部分
 * 1、PtrHandler 业务逻辑层
 * 2、PtrUiHandler  执行ui层 特效
 * @author blue
 */
public class NewsImportantFragment extends BaseFragment {
	//上啦刷新
	@ViewInject(R.id.ptr)
	PtrFrameLayout ptr;
	
	@ViewInject(R.id.pw)
	ProgressWheel pw;
	@ViewInject(R.id.news_important_lv)
	ListView news_important_lv;

	// 对控件进行初始化 将数据源显示到listView中
	@ViewInject(R.id.news_important_lv)
	ListView listview_item_bg;
	// 数据源 用适配器进行数据的渲染
	List<Newsimportant> datas = JListKit.newArrayList();
	// 适配器的声明
	private NewsItemAdapter adapter;
	// 加载布局
	private LinearLayout loading_llyt;
	// 是否是最后一行
	private boolean islastRow = false;
	// 是否还有更多数据
	private boolean isMore = true;
	// 是否正在加载数据
	private boolean isLoading = false;

	private int page = 0;
	private int size = 20;

	// 新闻组图
	private FrameLayout news_head_view;
	// viewpager
	private ViewPager news_head_vp;
	//标题
	private TextView news_head_tv;
	//5个指示器
	private TextView news_head_tv1;
	private TextView news_head_tv2;
	private TextView news_head_tv3;
	private TextView news_head_tv4;
	private TextView news_head_tv5;
	//装载5个指示器
	private List<TextView> textViewList = JListKit.newArrayList();
	
	// 推荐新闻数据源
	private List<Newshead> headList = JListKit.newArrayList();
	// 推荐新闻适配器
	private NewsHeadAdapter headAdapter;
	
	// 自动轮播定时器
	private ScheduledExecutorService scheduledExecutorService;
	// 当前图片的索引号
	private int currentItem = 0;
	
	private boolean  isUpdate = false;
	
	//给listview中每一项进行添加点击事件
	@OnItemClick(R.id.news_important_lv)
	public void OnItemClick(AdapterView<?> parent,View view, int position, long id){
		//intent对象进行跳转  跳转到详情页
		Intent  intent = new Intent(getActivity(), CarDetailActivity.class);
		//将商品信息传递过去
		intent.putExtra("goods", datas.get(position - 1));
		startActivity(intent);
	}
	
	

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_important_main;
	}

	@Override
	protected void initParams() {
		// 1、设置显示文字信息
		pw.setText("...");
		// 开始旋转加载
		pw.spin();

		// 2、动态的加载了底部布局 首先得到布局加载器
		loading_llyt = (LinearLayout) getLayoutInflater(null).inflate(
				R.layout.listview_loading_view, null);

		// 头部布局
		news_head_view = (FrameLayout) getLayoutInflater(null).inflate(R.layout.news_head_view, null);
		news_head_vp = (ViewPager) news_head_view.findViewById(R.id.news_head_vp);
		//滑动背景对应的标题
		news_head_tv = (TextView) news_head_view.findViewById(R.id.news_head_tv);
		// 初始化指示器
		news_head_tv1 = (TextView) news_head_view.findViewById(R.id.news_head_tv1);
		news_head_tv2 = (TextView) news_head_view.findViewById(R.id.news_head_tv2);
		news_head_tv3 = (TextView) news_head_view.findViewById(R.id.news_head_tv3);
		news_head_tv4 = (TextView) news_head_view.findViewById(R.id.news_head_tv4);
		news_head_tv5 = (TextView) news_head_view.findViewById(R.id.news_head_tv5);
		textViewList.add(news_head_tv1);
		textViewList.add(news_head_tv2);
		textViewList.add(news_head_tv3);
		textViewList.add(news_head_tv4);
		textViewList.add(news_head_tv5);

		// 初始化推荐新闻
		headAdapter = new NewsHeadAdapter(context, headList);
		news_head_vp.setAdapter(headAdapter);
		//viewPager的事件绑定
		news_head_vp.setOnPageChangeListener(new NewsPageChangeListener());

		// 初始化列表
		adapter = new NewsItemAdapter(context, datas, news_important_lv);
		// 增加头部显示布局
		news_important_lv.addHeaderView(news_head_view);
		// 增加底部加载布局
		news_important_lv.addFooterView(loading_llyt);
		// 绑定适配器
		news_important_lv.setAdapter(adapter);
		
		//设置刷新头
		initPtr();

		// 3、加载数据  两个同事请求
		loadHeadData();
		loadListData();

		// // 适配器进行初始化 context是继承过来的参数
		// adapter = new NewsItemAdapter(context, datas);
		// // 给listView绑定一个尾部 必须卸载listview_item_bg.setAdapter(adapter)的前面
		// listview_item_bg.addFooterView(loading_llyt);
		// // 将adapter绑定到listView上面
		// listview_item_bg.setAdapter(adapter);

		/**
		 * 给listView添加滚动监听 listview_item_bg.setOnScrollListener(new
		 * OnScrollListener() { //状态发生改变的时候就调用这个方法
		 * 
		 * @Override public void onScrollStateChanged(AbsListView arg0, int
		 *           arg1) {} //滚动的时候就调用的这个方法
		 * @Override public void onScroll(AbsListView arg0, int arg1, int arg2,
		 *           int arg3) { // TODO Auto-generated method stub
		 * 
		 *           } });
		 */
	}
	
	//下拉刷新
	public void initPtr(){
		//初始化刷新头
		StoreHouseHeader  header = new StoreHouseHeader(context);
		header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
		header.setPadding(0, DisplayUtil.dip2px(context, 15), 0, DisplayUtil.dip2px(context, 10));
		//初始化文字内容
		header.initWithString("MY CAR");
		//设置字体的颜色
		//header.setTextColor(getResources().getColor(color.black));
		header.setTextColor(getResources().getColor(android.R.color.black));
		
		//设置头部布局
		ptr.setHeaderView(header);
		//header 其实也是一个PtrUIHandler子类实现的是咧
 		ptr.addPtrUIHandler(header);
 		
 		//业务逻辑层
 		ptr.setPtrHandler(new PtrHandler() {
			
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
//				 //实现1.8秒结束刷新
//				frame.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						//结束刷新的方法
//						ptr.refreshComplete();
//					}
//				}, 1800);
				//加载下一页数据
				isUpdate = true;
				page ++;
				loadListData();
				
			}
			
			//检查是否能够刷新
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content,
					View header) {
				//content指的是framelayout   我们需要刷新的是listview
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, news_important_lv, header);
			}
		});
		
		
		
		
	}

	// 加载头部数据
	private void loadHeadData() 
	{
		LocalApplication.getInstance().httpUtils.send(HttpMethod.GET,ConstantsUtil.SERVER_URL + "newshead.do?method=getNewsHead",
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String list = JSONObject.parseObject(arg0.result).getString("datas");
						List<Newshead> tmp = JSONObject.parseArray(list,Newshead.class);
						
						if (JListKit.isNotEmpty(tmp)) {
							headList.addAll(tmp);
							//刷新数据
							headAdapter.refreshDatas(headList);
							//初始选中项  默认选中第一项   headList.size() * 1000可以向左右滑动
							news_head_vp.setCurrentItem(headList.size() * 1000);
							news_head_tv.setText(headList.get(0).getTitle());
							//要闻推荐不能超过5个  超过5个就要做相应的调整
							if (tmp.size() < 5) 
							{
								for (int i = 0; i < 5 - tmp.size(); i++) 
								{
									 textViewList.get(textViewList.size() - 1 - i).setVisibility(View.GONE);
								}
							}
						}
					}
				});
	}

	/**
	 * 向服务器发出请求 加载数据
	 */
	private void loadListData() {
		// 开始进来就开始加载数据
		isLoading = true;
		// 开始为0 从第一页开始加载
		page++;
		RequestParams params = new RequestParams();
		params.addBodyParameter("page", page + "");
		params.addBodyParameter("size", size + "");
		LocalApplication.getInstance().httpUtils.send(HttpMethod.POST,
				ConstantsUtil.SERVER_URL
						+ "newsImportant.do?method=getNewsImportant", params,
				new RequestCallBack<String>() {
					// 请求失败对应的方法
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						if (isLoading) {  //下拉刷新失败
							//完成刷新
							ptr.refreshComplete();
							
						} else { //正常请求失败
							// 停止进度条
							pw.stopSpinning();
							// 影藏进度条
							pw.setVisibility(View.GONE);
							// 失败提示
						}
						ToastMaker.showShortToast("请求失败，请检查网络后重试");
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String list = JSONObject.parseObject(arg0.result)
								.getString("datas");
						List<Newsimportant> tmp = JSONObject.parseArray(list,
								Newsimportant.class);
						
						//得到总的页数
						String  count = JSONObject.parseObject(arg0.result).getString("count");
						int amountPage = Integer.parseInt(count);
						
						List<Newsimportant> refreshdatas = null;
						
						pw.stopSpinning();
						pw.setVisibility(View.GONE);
						news_important_lv.setVisibility(View.VISIBLE);
						if (JListKit.isNotEmpty(tmp)) {
							if (page == 1) {
								// 移除底部加载布局
								if (tmp.size() < size) {
									news_important_lv
											.removeFooterView(loading_llyt);
								}
							}
							//如果下拉刷新 清空以前的数据   并将请求数据职位第一页
							if (isUpdate) {
								isUpdate = false;
								//完成刷新
								ptr.refreshComplete();
								//清空以前数据
								refreshdatas = datas;
								datas.clear();
								if (page >= amountPage) {
									page =1;
								}
							}
							
							
							datas.addAll(tmp);
							adapter.refreshDatas(datas);
						} else {
								isMore = false;
								news_important_lv.removeFooterView(loading_llyt);
								ToastMaker.showShortToast("已没有更多数据");
								
								//下拉刷新完成
								ptr.refreshComplete();
						}
						isLoading = false;
					}
				});
	}

	/**
	 * 判断是否滚动到了底部
	 * 
	 * @param firstVisibleItem当前可以看到的条目的索引
	 * @param visibleItemCount指的是当前可见的条目的数量
	 * @param totalItemCount指的是listview的总数量
	 */
	@OnScroll(R.id.news_important_lv)
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (firstVisibleItem + visibleItemCount == totalItemCount
				&& totalItemCount > 0) {
			islastRow = true;
		}
	}

	/**
	 * 当滚动的状态改变的时候 滚动到底部的时候状态就发生了改变
	 * 
	 * @param view
	 * @param scrollState
	 */
	@OnScrollStateChanged(R.id.news_important_lv)
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// 这里判断滚动到最后一条且已经停止 SCROLL_STATE_IDLE这个标识的就是已经停止了
		if (islastRow && scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			// 没有加载 有更多的数据
			if (!isLoading && isMore) {
				loadListData();
			}
			islastRow = false;
		}
	}
	
	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author blue
	 */
	private class NewsPageChangeListener implements OnPageChangeListener
	{
       
		@Override
		public void onPageScrollStateChanged(int arg0)
		{
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
		{
		}

		@Override
		public void onPageSelected(int arg0)
		{
			//在滑动选择之后 要重新改变索引
			currentItem = arg0;
			news_head_tv.setText(headList.get(arg0 % headList.size()).title);
			for (int i = 0; i < headList.size(); i++)
			{
				//对应的指示器对应的选中与未选中颜色
				if (i == arg0 % headList.size())
				{
					textViewList.get(i).setBackgroundColor(context.getResources().getColor(R.color.news_head_cl_choose));
				} else
				{
					textViewList.get(i).setBackgroundColor(context.getResources().getColor(R.color.news_head_cl_unchoose));
				}
			}
		}
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		//初始化  scheduledExecutorService
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每五秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 5, 5, TimeUnit.SECONDS);
	}

	@Override
	public void onStop()
	{
		super.onStop();
		// 当Activity不可见的时候停止切换
		if (scheduledExecutorService != null)
		{
			scheduledExecutorService.shutdown();
		}
	}
	
	/**
	 * 换行切换任务
	 * 
	 * @author Administrator
	 * 
	 */
	private class ScrollTask implements Runnable
	{

		public void run()
		{
			synchronized (news_head_vp)
			{
				currentItem++;
				// 通过Handler切换图片
				handler.sendEmptyMessage(1);
			}
		}

	}
	
	//主线程接收消息
	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what) {
			// 切换图片
			case 1:

				if (JListKit.isNotEmpty(headList))
				{
					news_head_vp.setCurrentItem(currentItem);
				}

				break;

			default:
				break;
			}
		}

	};


}
