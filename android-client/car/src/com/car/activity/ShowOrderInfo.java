package com.car.activity;

import java.util.ArrayList;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.alibaba.fastjson.JSONObject;
import com.car.R;
import com.car.adapter.ShowGoodsAdapter;
import com.car.application.LocalApplication;
import com.car.entity.Order;
import com.car.util.ConstantsUtil;
import com.car.util.SharedUtils;
import com.car.util.StringUtil;
import com.car.view.ToastMaker;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 这是查询的页面
 * @author shiran 
 *
 */
public class ShowOrderInfo extends  BaseActivity{

	//声明适配器
	private  ShowGoodsAdapter showGoodsAdapter;
	//order列表
	private List<Order> orders = new ArrayList<Order>();
	//适配器列表
	@ViewInject(R.id.all_order_show) 
	private ListView  allOrderShow;
	@ViewInject(R.id.car_order_goback)
	private TextView car_order_goback;
	
	//分页参数定义
	private int page = 0;
	private int size = 20;
	
	
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.showorderinfo;
	}

	@Override
	protected void initParams() {
		
		//加载订单数据
		loadOrderData();
		
		/*Log.i("sunjob", orders + " 这是订单数据");
		showGoodsAdapter = new ShowGoodsAdapter(getBaseContext(), orders, allOrderShow);
		allOrderShow.setAdapter(showGoodsAdapter);*/
		/*pw.stopSpinning();  下载之后完成刷新
		pw.setVisibility(View.GONE);*/
	}

	/**
	 * 下载订单数据
	 */
	private void loadOrderData() {
		page++;
		//得到用户登录名  
		String username = SharedUtils.getUserName(getApplicationContext());
		if ("点击登录".equals(username) || StringUtil.isNull(username)) {
			ToastMaker.showShortToast("请进行登录之后再进行查看订单!");
			finish();
			return;
		}
		
		//点击删除的时候获取orderId 并在查询之前进行删除
		String orderId = "0";
		Bundle  bundle = getIntent().getExtras();
		if (bundle != null) {
			//bundle根据键进行值的获取
			orderId = (String) bundle.get("orderId");
		}
		
/*		RequestParams params = new RequestParams();
		params.addBodyParameter("page", page + "");
		params.addBodyParameter("size", size + "");
		params.addBodyParameter("uname", username);*/
		//ToastMaker.showLongToast("--" + username);
		LocalApplication.getInstance().httpUtils.send(HttpMethod.GET, ConstantsUtil.SERVER_URL + "order.do?method=getOrderByfenye&uname=" + username + "&page=" + page + "&size=" +size + "&orderId=" + orderId, 
				new RequestCallBack<String>() {
                    //成功的方法
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						//ToastMaker.showShortToast(responseInfo.result);
						String listStr = JSONObject.parseObject(responseInfo.result).getString("datas");
						String  count = JSONObject.parseObject(responseInfo.result).getString("count");
						List<Order> orderList = JSONObject.parseArray(listStr, Order.class);
						if (orderList != null && orderList.size() > 0) {
							orders.addAll(orderList);
						}
						showGoodsAdapter = new ShowGoodsAdapter(ShowOrderInfo.this, orders, allOrderShow);
						allOrderShow.setAdapter(showGoodsAdapter);
					}
                    //失败的方法
					@Override
					public void onFailure(HttpException error, String msg) {
						 ToastMaker.showShortToast("可能网络中断，请进行重试");
					}
		});
		
	}
	
	/**
	 * 点击事件处理
	 * 这里可以对时间进行初始化
	 * @param v
	 */
	@OnClick({R.id.car_order_goback})
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.car_order_goback: //这里表示进行返回
			finish();   //将这个activity进行销毁
			break;
		default:
			break;
		}
	}
	
	
	/**
	 * 当滚动的状态改变的时候 滚动到底部的时候状态就发生了改变
	 * 
	 * @param view
	 * @param scrollState
	 
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
	}*/
	

}
