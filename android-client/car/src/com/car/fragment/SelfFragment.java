package com.car.fragment;

import java.net.ResponseCache;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import cn.sharesdk.framework.i;

import com.car.R;
import com.car.activity.AddressActivity;
import com.car.activity.MyloginActivity;
import com.car.activity.SellerManageActivity;
import com.car.activity.ShowOrderInfo;
import com.car.util.RandomCodeUtil;
import com.car.util.SharedUtils;
import com.car.util.StringUtil;
import com.car.view.ToastMaker;
import com.lidroid.xutils.view.annotation.ViewInject;



/**
 * 我的
 * 
 * @author blue
 */
public class SelfFragment extends BaseFragment implements OnClickListener
{

	//声明一个TextView 
	@ViewInject(R.id.my_index_login_text)
	private  TextView loginText;
	//头像的位置
	@ViewInject(R.id.my_index_login_image)
	private  ImageView  loginImage;
	@ViewInject(R.id.my_address_item)
	private  TextView my_address_item;
	@ViewInject(R.id.my_index_item_order)
	private  TextView my_index_item_order;
	//销售订单统计管理
	@ViewInject(R.id.my_index_item_seller_order_management)
	private  TextView my_index_item_seller_order_management;
	
	@Override
	protected int getLayoutId()
	{
		return R.layout.fragment_self_main;
	}

	@Override
	protected void initParams()
	{
	     loginText.setOnClickListener(this);
	     my_address_item.setOnClickListener(this);
	     my_index_item_order.setOnClickListener(this);
	     my_index_item_seller_order_management.setOnClickListener(this);
	     showUserName();
	}
	
	/**
	 * 显示用户名
	 */
	private void showUserName() {
		 String userName = SharedUtils.getUserName(context);
	     if(!"点击登录".equals(userName)){
	    	 loginText.setText(userName);
		     loginImage.setImageResource(R.drawable.profile_default);
	     }
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.my_index_login_text:
		case R.id.my_index_login_image:
			//Log.i("sunjob", "--------------");
			login();//登录
			break;
		case R.id.my_address_item:
			String username = SharedUtils.getUserName(getActivity());
			if("点击登录".equals(username)){
				ToastMaker.showShortToast("亲   您还没有登录!");
				return;
			}
			Intent  intent = new Intent(getActivity(), AddressActivity.class);
			startActivity(intent);
			break;
		case R.id.my_index_item_order:
			Intent  orderIntent = new Intent(getActivity(), ShowOrderInfo.class);
			startActivity(orderIntent);
		case R.id.my_index_item_seller_order_management:
			sellerOrderManagement();
			break;
		default:
			break;
		}
	}
	
	
	//订单销售管理
	private void sellerOrderManagement() {
		 String userName = SharedUtils.getUserName(context); //get a username 
		 //ToastMaker.showShortToast("=============" + userName);
		 if (userName != null && !userName.equals("点击登录")) {
			 if (userName == "admin" || userName.equals("admin")) {
				Intent intent = new Intent(getActivity(), SellerManageActivity.class);
				startActivity(intent);
			 }else {
				 ToastMaker.showShortToast("没有权限进行管理！");
				 return;
			}
		 }else {
			ToastMaker.showShortToast("没有进行登录！");
		}
	}

	//登录的方法
	public void login(){
		Intent  intent = new Intent(getActivity(), MyloginActivity.class);
		//带有返回值的跳转  //第二个参数指的请求码
		startActivityForResult(intent, RandomCodeUtil.RequestLoginCode);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//这里请求码和响应码是相同的 
		if (requestCode == RandomCodeUtil.RequestLoginCode && resultCode == RandomCodeUtil.RequestLoginCode) {
			loginText.setText(data.getStringExtra("login_name"));
			loginImage.setImageResource(R.drawable.profile_default);
		}
	}

}
