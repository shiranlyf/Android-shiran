package com.car.activity;

import java.text.BreakIterator;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.car.R;
import com.car.adapter.AddressAdapter;
import com.car.application.LocalApplication;
import com.car.entity.AddressEntity;
import com.car.util.ConstantsUtil;
import com.car.util.SharedUtils;
import com.car.view.ToastMaker;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class AddressActivity extends BaseActivity {

	private List<AddressEntity> addressList = new ArrayList<AddressEntity>();
	@ViewInject(R.id.person_address_listview)
	private ListView person_address_listview;
	private AddressAdapter addressAdapter;

	@ViewInject(R.id.car_address_goback)
	private TextView car_address_goback;
	
	@ViewInject(R.id.is_boy_girl)
	private TextView  is_boy_girl;
	
	@ViewInject(R.id.btn_add_order_address)
	private TextView  btn_add_order_address;
	

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.car_order_address;
	}

	@Override
	protected void initParams() {
		loadAddressByUser();
	}

	@OnClick({R.id.car_address_goback, R.id.btn_add_order_address})
	public void Onclick(View v) {
		switch (v.getId()) {
		case R.id.car_address_goback:
			finish();
			break;

		case R.id.btn_add_order_address:
		    //跳转到添加页面
			Intent  intent = new Intent(AddressActivity.this, AddressUpdateActivity.class);
			intent.putExtra("isAddOrUpdate", "1");
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	/**
	 * 得到用户的所有地址
	 */
	private void loadAddressByUser() {
		
		String username = SharedUtils.getUserName(getBaseContext());
		if("点击登录".equals(username)){
			ToastMaker.showShortToast("亲   您还没有登录!");
			return;
		}

		LocalApplication.getInstance().httpUtils.send(HttpMethod.GET,
				ConstantsUtil.SERVER_URL
						+ "address.do?method=getPersonAddress&username=" + username
						+ "&timerandom=" + new Date().getTime(),
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String list = JSONObject.parseObject(
								responseInfo.result).getString("data");
						addressList = JSONObject
								.parseArray(list, AddressEntity.class);
						addressAdapter = new AddressAdapter(
								AddressActivity.this, addressList);
						person_address_listview.setAdapter(addressAdapter);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						ToastMaker.showShortToast("地址加载失败，请重试");
					}
				});
	}

}
