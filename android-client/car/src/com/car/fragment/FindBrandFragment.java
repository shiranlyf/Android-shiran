package com.car.fragment;

import java.util.List;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.car.R;
import com.car.activity.MainActivity;
import com.car.activity.MainActivity.FragmentOnTouchListener;
import com.car.adapter.CarLogoAdapter;
import com.car.adapter.CarSeriesAdapter;
import com.car.application.LocalApplication;
import com.car.entity.Carlogo;
import com.car.entity.Carseries;
import com.car.util.ConstantsUtil;
import com.car.util.JListKit;
import com.car.view.PinnedHeaderListView;
import com.car.view.ProgressWheel;
import com.car.view.SideBar;
import com.car.view.ToastMaker;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

/**
 * 找车-品牌找车
 * 
 * @author blue
 */
public class FindBrandFragment extends BaseFragment  implements FragmentOnTouchListener {

	@ViewInject(R.id.find_brand_pw)
	ProgressWheel find_brand_pw;
	@ViewInject(R.id.find_brand_lv)
	PinnedHeaderListView find_brand_lv;

	// 自定义的字母控件
	@ViewInject(R.id.find_brand_sb)
	SideBar find_brand_sb;
	
	
	// 二级菜单对应的车系找车
	@ViewInject(R.id.find_brand_llyt_content)
	LinearLayout find_brand_llyt_content;
	@ViewInject(R.id.find_cover_pw)
	ProgressWheel find_cover_pw;
	@ViewInject(R.id.find_cover_lv)
	PinnedHeaderListView find_cover_lv;
	


	// 列表数据源
	private List<Carlogo> datas = JListKit.newArrayList();
	// 适配器
	private CarLogoAdapter carLogoAdapter;

	// 将Textview加到WindowManager中就可以 显示到中间
	private WindowManager windowManager;
	// 提示对话框
	private TextView dialogText;
	
	
	// 是否显示二级菜单
	private boolean isShow = false;
	// 车系列表数据源
	private List<Carseries> seriesList = JListKit.newArrayList();
	// 车系找车适配器
	private CarSeriesAdapter carSeriesAdapter;

	// 手势监听器声明
	private GestureDetector gestureDetector;

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_find_brand_main;
	}

	@Override
	protected void initParams() {
		// 设置显示文字信息
		find_brand_pw.setText("loading");
		// 开始旋转加载
		find_brand_pw.spin();

		// 初始化适配器
		carLogoAdapter = new CarLogoAdapter(context, datas, find_brand_lv);
		find_brand_lv.setAdapter(carLogoAdapter);
		// 设置列表滚动事件
		find_brand_lv.setOnScrollListener(carLogoAdapter);
		// 设置列表挤压头部
		find_brand_lv.setPinnedHeaderView(LayoutInflater.from(context).inflate(
				R.layout.fragment_find_brand_listview_head, find_brand_lv,
				false));

		// 初始化提示对话框
		dialogText = (TextView) LayoutInflater.from(context).inflate(R.layout.fragment_find_brand_listview_remind, null);
		dialogText.setVisibility(View.INVISIBLE);
		
		// 初始化窗口管理器
		windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.TYPE_APPLICATION,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);
		// 将提示对话框添加到窗口中
		windowManager.addView(dialogText, lp);

		// 设置侧边栏信息
		find_brand_sb.setTextView(dialogText);
		//这里注意  在setListView()中将adapter转化成了SectionIndex 
		//所以在设置find_brand_lv的是适配中实现这个类的方法
		find_brand_sb.setListView(find_brand_lv);
		
		// 二级菜单初始化车系找车适配器
		carSeriesAdapter = new CarSeriesAdapter(context, seriesList, find_cover_lv);
		find_cover_lv.setAdapter(carSeriesAdapter);
		// 设置列表滚动事件
		find_cover_lv.setOnScrollListener(carSeriesAdapter);
		// 设置列表挤压头部
		find_cover_lv.setPinnedHeaderView(LayoutInflater.from(context).inflate(R.layout.fragment_find_cover_listview_head, find_cover_lv, false));

		//这里是二级菜单滑动关闭的效果(实现事件的注册  将mainactivity中的事件分发到fragment中)
		((MainActivity) getActivity()).registerFragmentOnTouchListener(this);
		//将事件传给手势监听器
		gestureDetector = new GestureDetector(context, onGestureListener);

		
		
		
		// 加载品牌数据
		loadCarLogo();
	}
	
	/****** 一级菜单的子项绑定***********/
	@OnItemClick(R.id.find_brand_lv)
	public void onItemClick(AdapterView<?> adapterView, View itemView, int position, long itemId)
	{
		//判断是否显示二级菜单
		if (!isShow)
		{
			// 显示二级菜单
			showContent();
		}
		
		//刷新或则加载数据
		loadCarSeries(datas.get(position).id);
	}
	/****** 一级菜单的子项绑定结束***********/
	
	
	// 加载品牌数据
	private void loadCarLogo() {
		LocalApplication.getInstance().httpUtils.send(HttpMethod.GET,
				ConstantsUtil.SERVER_URL
						+ "carlogo.do?method=getAllCarLogoJson",
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException error, String msg) {
						find_brand_pw.stopSpinning();
						find_brand_pw.setVisibility(View.GONE);
						ToastMaker.showShortToast("请求失败，请检查网络后重试");
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						List<Carlogo> tmp = JSONObject.parseArray(arg0.result,
								Carlogo.class);

						find_brand_pw.stopSpinning();
						find_brand_pw.setVisibility(View.GONE);
						find_brand_lv.setVisibility(View.VISIBLE);
						//字母列表页一起显示
						find_brand_sb.setVisibility(View.VISIBLE);

						if (JListKit.isNotEmpty(tmp)) {
							datas.addAll(tmp);
							carLogoAdapter.refreshDatas(datas);
						}
					}

				});

	}
	

 /*************车系二级菜单的数据加载**************************/
	// 加载车系数据
	private void loadCarSeries(int id)
	{
		// 显示loading
		find_cover_pw.spin();
		find_cover_pw.setVisibility(View.VISIBLE);
		// 隐藏列表
		find_cover_lv.setVisibility(View.GONE);

		RequestParams params = new RequestParams();
		params.addBodyParameter("id", id + "");
		LocalApplication.getInstance().httpUtils.send(HttpMethod.POST, ConstantsUtil.SERVER_URL + "careries.do?method=getCareriesJson", params, new RequestCallBack<String>()
		{

			@Override
			public void onFailure(HttpException arg0, String arg1)
			{
				find_cover_pw.stopSpinning();
				find_cover_pw.setVisibility(View.GONE);
				ToastMaker.showShortToast("请求失败，请检查网络后重试");
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0)
			{ 
				//反序列化数据
				List<Carseries> tmp = JSONObject.parseArray(arg0.result, Carseries.class);

				find_cover_pw.stopSpinning();
				find_cover_pw.setVisibility(View.GONE);
				find_cover_lv.setVisibility(View.VISIBLE);

				if (JListKit.isNotEmpty(tmp))
				{
					seriesList.clear();
					seriesList.addAll(tmp);
					carSeriesAdapter.refreshDatas(seriesList);
					find_cover_lv.setSelection(0);
				} else
				{
					find_cover_lv.setVisibility(View.GONE);
					ToastMaker.showShortToast("暂无数据");
				}
			}
		});
	}

 /*************车系二级菜单的数据加载**************************/
	
 /**********显示和影藏二级菜单从右侧进来 从右侧出去的动画****************/
	// 显示二级菜单
	public void showContent()
	{
		find_brand_sb.setVisibility(View.GONE);
		find_brand_llyt_content.setVisibility(View.VISIBLE);
		find_brand_llyt_content.startAnimation(AnimationUtils.loadAnimation(context, R.anim.in_from_right));
		isShow = true;
	}

	// 隐藏二级菜单
	public void closeContent()
	{
		find_brand_sb.setVisibility(View.VISIBLE);
		find_brand_llyt_content.startAnimation(AnimationUtils.loadAnimation(context, R.anim.out_to_right));
		find_brand_llyt_content.setVisibility(View.GONE);
		isShow = false;
	}
	
 /**********显示和影藏二级菜单从右侧进来 从右侧出去的动画****************/
	
 /**********FragmentOnTouchListener接口实现方法(将事件传给手势事件)****************/
	@Override
	public boolean onTouch(MotionEvent ev) {
		// TODO Auto-generated method stub
		return gestureDetector.onTouchEvent(ev);
	}
	
/**********FragmentOnTouchListener接口实现方法****************/
	
	
	// 手势滑动监听器
	private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener()
	{
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
		{
			// 手势滑动时失去焦点(滑动的时候也会调用点击事件   所以点击等事件屏蔽掉)
			find_cover_lv.setPressed(false);
			find_cover_lv.setFocusable(false);
			find_cover_lv.setFocusableInTouchMode(false);

			return super.onScroll(e1, e2, distanceX, distanceY);
		}

		//滑动的时候调用的方法e1和e2分别表示 起点和终点
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
		{
			float x = e2.getX() - e1.getX();

			// 向右滑动到一定距离时隐藏内容
			if (x > 100)
			{
				closeContent();
			}
			return true;
		}
	};
	
}
