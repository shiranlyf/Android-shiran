package com.car.activity;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.car.R;
import com.car.application.LocalApplication;
import com.car.entity.Newsimportant;
import com.car.entity.User;
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
 * 生成订单的页面
 * 
 * @author shiran
 * 
 */
public class GetMyOrderActivity extends BaseActivity implements OnClickListener {

	// 初始化变量
	@ViewInject(R.id.index_to_order_back)
	private TextView indexToOrderBack;
	@ViewInject(R.id.address_username)
	private TextView addressUsername;
	@ViewInject(R.id.username_phone)
	private TextView usernamePhone;
	@ViewInject(R.id.wait_for_pay)
	private TextView waiForPay; // 待支付按钮
	@ViewInject(R.id.for_pay)
	private TextView forPay; // 应支付按钮
	@ViewInject(R.id.goods_name)
	private TextView goodsName; // 商品名称
	@ViewInject(R.id.confirm_order)
	private Button confirmOrder; // 确认提交按钮
	@ViewInject(R.id.order_address_name)
	private TextView  orderAddress; //地址信息
	private Newsimportant newsimportant; // 用来保存goods的信息
	private String userAddressId;   //这是用户下单时的地址id号   用于写入到订单
	// 产生一个user
	private User user = new User();

	@Override
	protected int getLayoutId() {
		return R.layout.get_myorder;
	}

	@Override
	protected void initParams() {
		// TODO Auto-generated method stub
		indexToOrderBack.setOnClickListener(this);
		// 进行用户名和电话号码的显示
		showOrderUsername();
		//进行商品信息的显示
		showGoodsInfo();
	}

	// 在页面上进行商品信息的显示
	private void showGoodsInfo() {
		//得到intent跳转得到goods信息
		Bundle  bundle = getIntent().getExtras();
		if (bundle != null) {
			//通过key进行值额获取
			newsimportant = (Newsimportant) bundle.get("goods");
		}
		String title = newsimportant.getTitle();  //得到商品的名称
		String car_price = newsimportant.getCar_price();
		if (StringUtil.isNull(car_price)) {
			car_price = "0";
		}
		waiForPay.setText(car_price);
		forPay.setText(car_price);
		goodsName.setText(title);
	}

	// 展示用户名
	private void showOrderUsername() {
		// TODO Auto-generated method stub
		// 根据用户名得到用户对象
		/*
		 * 
		 * User user = getUserByUsername(username);
		 * addressUsername.setText(username);
		 */
		String username = SharedUtils.getUserName(getApplicationContext());
		//ToastMaker.showShortToast(username);
		getUserByUsername(username);
		
	}

	// 根据用户名得到用户对象
	private User getUserByUsername(String username) {
		// 发送http请求进行user对象的获取 第一个参数代表的是get请求 第二参数表示url请求的路径
		LocalApplication.getInstance().httpUtils.send(HttpMethod.GET,
				ConstantsUtil.SERVER_URL
						+ "user.do?method=getUserByUsername&username="
						+ username, new RequestCallBack<String>() {
					// http成功的时候返回的对象
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						/*
						 * String uid =
						 * JSONObject.parseObject(responseInfo.result
						 * ).getString("uid"); String pwd =
						 * JSONObject.parseObject() String username =
						 * JSONObject.
						 * parseObject(responseInfo.result).getString("name");
						 * String userTel =
						 * JSONObject.parseObject(responseInfo.result
						 * ).getString("tel"); User user
						 */
						// 进行用户的封装
						JSONObject jb = JSONObject
								.parseObject(responseInfo.result);
						String uid = jb.getString("uid");
						String username = jb.getString("name"); // 得到用户名
						String userpassword = jb.getString("loginPwd"); // 得到密码
						String userTel = jb.getString("tel"); // 得到用户的电话
						String userPayPassword = jb.getString("payPwd"); // 得到用户的支付密码
						String userAddress = jb.getString("addressName");
						userAddressId = jb.getString("addressId");
						//ToastMaker.showShortToast("地址信息：" + userAddress);
						//这里进行是否填写了地址
						if (StringUtil.isNull(userAddress) ||  StringUtil.isNull(userAddressId)) {
							Intent  intent = new Intent(GetMyOrderActivity.this, AddressActivity.class);
							//这里不传递信息
					        startActivity(intent);
					        ToastMaker.showShortToast("用户地址没有填写完整  或则没有用户地址");
						}
						user.setUid(Integer.parseInt(uid));
						user.setName(username);
						user.setLoginPwd(userpassword);
						user.setTel(userTel);
						user.setPayPwd(userPayPassword);
						addressUsername.setText(user.getName());
						usernamePhone.setText(user.getTel());
						orderAddress.setText(userAddress);
					}

					// 失败的返回的对象
					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						ToastMaker.showShortToast("订单提交失败   请重试  可能是网络不正确!");
					}
				});
		return user;
	}

	@OnClick({R.id.index_to_order_back, R.id.confirm_order})
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.index_to_order_back:
			finish();
			break;
		case R.id.confirm_order:
			confirmOrder();
			break;
		default:
			break;
		}
	}

	/**
	 * 确认提交订单
	 */
	private void confirmOrder() {
		//得到提交订单的参数     这里的地址id默认是该用户的第一个地址
		int uid = user.getUid();
		int goodsId = newsimportant.getId();
		//这里默认设置时货到付款1
		LocalApplication.getInstance().httpUtils.send(HttpMethod.GET,
				ConstantsUtil.SERVER_URL + "order.do?method=confirmOrder&uid=" + uid +"&goodsId=" + goodsId + "&addressId=" + userAddressId + "&payway=" + 1,
				new RequestCallBack<String>() {
					// 请求成功
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						//ToastMaker.showShortToast(responseInfo.result);
                        //根据json字符串的值进行值的获取
						String isSuccess = JSONObject.parseObject(responseInfo.result).getString("isSuccess");
						if ("1".equals(isSuccess) || "1" == isSuccess) {  //表示下单成功
							   ToastMaker.showShortToast("下单成功，请等待收货");
							   //跳转到订单查询也面
							   Intent intent = new Intent(GetMyOrderActivity.this, ShowOrderInfo.class);
							   startActivity(intent);
						}else {  //表示下单失败
							 ToastMaker.showShortToast("下载失败     请重试");
						}
					}

					// 请求失败
					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
                        ToastMaker.showShortToast("下单失败! 请进行重试");
					}
				});
	}

}
