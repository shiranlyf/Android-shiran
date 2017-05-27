package com.car.activity;

import java.io.IOException;

import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.car.R;
import com.car.application.LocalApplication;
import com.car.entity.AddressEntity;
import com.car.util.ConstantsUtil;
import com.car.util.MyUtiils;
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
 * 地址的修改添加页面 实现LocationListener定位的监听器 gps定位权限：<uses-permission
 * android:name="android.permission.ACCESS_FINE_LOCATION"/>
 * gsp定位
 * @author shiran
 */
@SuppressLint("ServiceCast")
public class AddressUpdateActivity extends BaseActivity implements
		LocationListener {

	private AddressEntity address;

	@ViewInject(R.id.address_update_goback)
	private TextView address_update_goback;
	@ViewInject(R.id.address_name)
	private EditText address_name;
	@ViewInject(R.id.address_boy_girl_radio)
	private RadioGroup address_boy_girl_radio;
	@ViewInject(R.id.male)
	private RadioButton male;
	@ViewInject(R.id.female)
	private RadioButton female;
	@ViewInject(R.id.address_phone)
	private EditText address_phone;
	@ViewInject(R.id.prepare_phone)
	private EditText prepare_phone;
	@ViewInject(R.id.address_detail)
	private EditText address_detail;
	@ViewInject(R.id.confirm_address)
	private Button confirm_address;

	@ViewInject(R.id.address_add_goback)
	private TextView address_add_goback;

	private String RadioButtonValue = "男";

	private String isAddOrUpdate = null;

	@ViewInject(R.id.btn_addres_delete)
	private ImageView btn_addres_delete;

	// 这里表示获取到uid的值
	private String uname = null ;

	// 城市定位按钮
	@ViewInject(R.id.choose_location_city)
	private TextView topCity;
	// 当前显示的城市名称
	private String cityName;
	// 定位的管理器类
	private LocationManager locationManager;

	@ViewInject(R.id.get_address_city_location)
	private TextView locationCity;
	
	
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.add_person_address_update;
	}

	@Override
	protected void initParams() {
		// 获取传过来的地址
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			address = (AddressEntity) bundle.get("address");
			// 这是添加是获取判断的数据
			isAddOrUpdate = (String) bundle.get("isAddOrUpdate");
		}

		if ("1".equals(isAddOrUpdate)) { // 表示添加
			address_update_goback.setVisibility(View.GONE);
			address_add_goback.setVisibility(View.VISIBLE);
		} else {
			btn_addres_delete.setVisibility(View.VISIBLE);
		}

		// 不为空回显地址信息
		if (address != null) {
			// ToastMaker.showShortToast(address.getAddressName());
			backShowAddressInfo(address);
		}

		// 获取数据并且显示
		topCity.setText(SharedUtils.getCityName(getBaseContext()));
	}

	/**
	 * 重写onStart方法 检查gps是否打开
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// 检查当前的gps模块
		checkGPSIsOpen();
	}

	/**
	 * 检查是否打开gps
	 */
	private void checkGPSIsOpen() {
		// 获取当前LocationManager对象 获取服务的形式进行调用
		locationManager = (LocationManager) getBaseContext().getSystemService(
				Context.LOCATION_SERVICE);
		// 参数为定位的模式
		boolean isOpen = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!isOpen) {
			// gps页面的设置
			Intent intent = new Intent();
			intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			// 标记为一个新的任务
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivityForResult(intent, 0);
		}
		// 开始定位
		startLocation();
	}

	/**
	 * 使用gps定位的方法
	 */
	private void startLocation() {
		// 选第二个方法 定位模式 位置更改的时间 触发定位的最短距离 监听对象(这里实现了接口 这里用this)
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				2000, 10, this);
	}

	/**
	 * 回显个人信息
	 * 
	 * @param address2
	 */
	private void backShowAddressInfo(AddressEntity address2) {
		address_name.setText(address.getAddressName());

		if (StringUtil.isNotNull(address.getPeopleSex())) {
			if ("1".equals(address.getPeopleSex().trim())) {
				male.setChecked(true);
			} else {
				female.setChecked(true);
			}
		}
		address_phone.setText(address.getAddressTel());
		prepare_phone.setText(address.getPreparePhone());
		address_detail.setText(address.getAddressDetail());
		locationCity.setText(address.getAddressCity());

		address_boy_girl_radio.setOnCheckedChangeListener(mChangeRadio);
	}

	private RadioGroup.OnCheckedChangeListener mChangeRadio = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			if (checkedId == male.getId()) {
				RadioButtonValue = male.getText().toString();
			} else if (checkedId == female.getId()) {
				RadioButtonValue = female.getText().toString();
			}
		}
	};

	/**
	 * 控件的点击事件
	 * 
	 * @param v
	 */
	@OnClick({ R.id.address_update_goback, R.id.confirm_address,
			R.id.btn_addres_delete, R.id.address_add_goback,
			R.id.choose_location_city })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.address_update_goback:
			finish();
			break;
		case R.id.confirm_address:
			saveAddressUpdate();
			break;
		case R.id.btn_addres_delete:
			// ToastMaker.showShortToast(address.getAddressId().toString());
			deleteAddressById(address.getAddressId());
			break;
		case R.id.address_add_goback:
			finish();
			break;
		case R.id.choose_location_city: // 定位监听
			// 带有返回值的跳转 第二个参数指的响应码
			startActivityForResult(new Intent(getBaseContext(),
					CityActivity.class), MyUtiils.RequestCityCode);
			break;
		default:
			break;
		}

	}

	/**
	 * 处理城市返回值的方法
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == MyUtiils.RequestCityCode
				&& resultCode == Activity.RESULT_OK) {
			cityName = data.getStringExtra("cityName");
			// Log.i("sunjob", cityName + "得到城市");
			locationCity.setText(cityName);
		}

	}

	/**
	 * 删除地址
	 * 
	 * @param addressId
	 */
	private void deleteAddressById(Integer addressId) {
		LocalApplication.getInstance().httpUtils.send(HttpMethod.GET,
				ConstantsUtil.SERVER_URL
						+ "address.do?method=deletePersonAddress&addressId="
						+ addressId + "&timerandom=" + new Date().getTime(),
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// Log.i("sunjob", responseInfo.result.toString() +
						// "删除地址===");
						if (responseInfo.result.toString().equals("1")) {
							ToastMaker.showShortToast("删除地址成功");
						} else {
							ToastMaker.showShortToast("删除地址失败，请重试");
						}
						Intent intent = new Intent(AddressUpdateActivity.this,
								AddressActivity.class);
						startActivity(intent);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						ToastMaker.showShortToast("网络不好请重试，请重试");
					}
				});
	}

	/**
	 * 提交修改
	 */
	private void saveAddressUpdate() {
		int addressId = 0;
		if (!"1".equals(isAddOrUpdate)) {
			// 这里得到地址id
			addressId = address.getAddressId();
		}
		String addressNameValue = address_name.getText().toString();
		String peopleSex = "1";
		if (address_boy_girl_radio.getCheckedRadioButtonId() == male.getId()) {
			peopleSex = "1";
		} else {
			peopleSex = "0";
		}
		String addressPhoneValue = address_phone.getText().toString();
		String preparePhoneValue = prepare_phone.getText().toString();
		String addressDetailValue = address_detail.getText().toString();
		String locationCityValue = locationCity.getText().toString();
		//在保存的shared中取出已经登陆的uname
		String username = SharedUtils.getUserName(getBaseContext());
		if("点击登录".equals(username)){ //表示没有进行登录
		   ToastMaker.showShortToast("亲   您还没有登录   请先登录!");
		   return;
		}
		uname = username;
		LocalApplication.getInstance().httpUtils.send(
				HttpMethod.GET,
				ConstantsUtil.SERVER_URL
						+ "address.do?method=updatePersonAddress&addressId="
						+ addressId + "&addressNameValue=" + addressNameValue
						+ "&peopleSex=" + peopleSex + "&addressPhoneValue="
						+ addressPhoneValue + "&preparePhoneValue="
						+ preparePhoneValue + "&addressDetailValue="
						+ addressDetailValue + "&isaddaddres=" + isAddOrUpdate
						+ "&uname=" + uname + "&locationCityValue="
						+ locationCityValue + "&timerandom="
						+ new Date().getTime(), new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						if (responseInfo.result.toString().equals("1")) {
							if (!"1".equals(isAddOrUpdate)) {
								ToastMaker.showShortToast("修改地址成功");
							} else {
								ToastMaker.showShortToast("添加成功");
							}
							// 跳转地址列表页
							Intent intent = new Intent(
									AddressUpdateActivity.this,
									AddressActivity.class);
							startActivity(intent);
						} else {
							ToastMaker.showShortToast("修改地址失败，请重试");
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						ToastMaker.showShortToast("网络不好请重试，请重试");
					}
				});
	}

	/**
	 * 接收并且处理消息
	 */
	private Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// 进行what值的匹配
			if (msg.what == 1) {
				topCity.setText(cityName);
			}
			return false;
		}
	});

	/**
	 * 获取对应位置的经纬度
	 * 
	 * @param location1
	 */
	private void updateWithNewLocation(Location location) {
		// 设置默认的初始值
		double lat = 0; // 经度
		double lng = 0; // 纬度
		if (location != null) {
			lat = location.getLatitude();
			lng = location.getLongitude();
			// Log.i("sunjob", "经度:" + lat + "纬度:" +lng);
		} else {
			cityName = "无法获取城市信息";
		}
		// 通过经纬度得到地址 由于地址有多个 这个与经纬度的精确度有关 这里定义为2 即在集合对象中有两个值
		List<Address> list = null;
		// 经纬度与地址信息的相互转换 传入上下文对象
		Geocoder ge = new Geocoder(getBaseContext());
		// 第三个参数指的返回数 返回值是list集合
		try {
			list = ge.getFromLocation(lat, lng, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list != null && list.size() > 0) {
			// 遍历address对象
			for (int i = 0; i < list.size(); i++) {
				Address ad = list.get(i);
				// 获取城市
				cityName = ad.getLocality();
			}
		}
		// 发送一条空消息 what=1
		handler.sendEmptyMessage(1);

	}

	/** 定位的实现方法开始 ***/

	/**
	 * 位置信息更改执行的方法
	 */
	@Override
	public void onLocationChanged(Location location) {
		updateWithNewLocation(location);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	/*** 定位的实现方法结束 ****/

	/**
	 * 接触定位
	 */
	@Override
	protected synchronized void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 保存定位
		SharedUtils.putCityName(getBaseContext(), cityName);

		// 停止定位
		stopLocation();
	}

	/**
	 * 停止定位
	 */
	private void stopLocation() {
		locationManager.removeUpdates(this);
	}

}
