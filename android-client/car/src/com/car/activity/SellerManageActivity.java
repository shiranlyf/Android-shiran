package com.car.activity;
import java.util.ArrayList;
import java.util.List;

import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.car.R;
import com.car.adapter.SellerManageAdapter;
import com.car.application.LocalApplication;
import com.car.entity.Order;
import com.car.util.ConstantsUtil;
import com.car.view.ToastMaker;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 销售管理分析管理
 * @author shiran 
 */
public class SellerManageActivity extends BaseActivity{
	
	//适配器主键
	@ViewInject(R.id.person_order_manage_listview)
	private ListView person_order_manage_listview;
	
	
	private List<Order>  orderList = new ArrayList<Order>();
	
	//适配器变量
	private SellerManageAdapter  sellerManageAdapter;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.car_order_seller_manage;
	}

	@Override
	protected void initParams() {
	    getAllOrderInfo();
	}

	/**
	 * get all order information 
	 */
	private void getAllOrderInfo() {
		LocalApplication.getInstance().httpUtils.send(HttpMethod.GET, ConstantsUtil.SERVER_URL + "order.do?method=getAllSellerOrder", new RequestCallBack<String>() {

			//http success method
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				String dataList = JSONObject.parseObject(responseInfo.result).getString("data");
			    orderList = JSONObject.parseArray(dataList, Order.class);
			    sellerManageAdapter = new SellerManageAdapter(SellerManageActivity.this, orderList);
			    person_order_manage_listview.setAdapter(sellerManageAdapter);
			}
			
			//http failure method
			@Override
			public void onFailure(HttpException error, String msg) {
				ToastMaker.showShortToast("订单信息加载失败，请重新再试！");
			}
		});
	}

}
