package com.car.activity;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

public class NearbySearchActivity extends Activity implements LocationSource, AMapLocationListener{

	@ViewInject(R.id.search_mymap)
	private MapView mapView;
	private AMap aMap;
	private double longitude;// 经度
	private double latitude;// 纬度
	private LocationManagerProxy mAmapLocationManagerger;
	private OnLocationChangedListener  mListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_map_act);
		ViewUtils.inject(this);
		mapView.onCreate(savedInstanceState);
		if (aMap == null) {
			aMap = mapView.getMap();
			//设置定位的监听
			aMap.setLocationSource(this);
			//显示定位层并且可以触发定位
			aMap.setMyLocationEnabled(true);
		}
	}
	 
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mapView.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mapView.onPause();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mapView.onDestroy();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}


	@Override
	public void activate(OnLocationChangedListener listener) {
		if (mAmapLocationManagerger == null) {
			mListener = listener;
			mAmapLocationManagerger = LocationManagerProxy.getInstance(this);
			mAmapLocationManagerger.requestLocationData(LocationProviderProxy.AMapNetwork, 5000, 10, this);
			Log.i("sunjob", "当前经纬度:"+longitude + "," +latitude);
		}
	}


	@Override
	public void deactivate() {}


	//地址改变的时候回传location对象
	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location != null) {
			// 初始化经纬度
	        longitude = location.getLongitude();
	        latitude = location.getLatitude();
	        //定位自己的小蓝点
	        mListener.onLocationChanged(location);
		}
	}


	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
