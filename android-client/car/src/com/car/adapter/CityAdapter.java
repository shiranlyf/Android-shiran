package com.car.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.car.R;
import com.car.entity.City;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


public class CityAdapter extends  SimpleBaseAdapter<City>{

	private Context  context;
	private List<City>   listCityDatas;
	
	public CityAdapter(Context c, List<City> datas) {
		super(c, datas);
		context = c;
		listCityDatas  = datas;
	}

	//用来第一次保存首字母的索引
	private  StringBuffer  buffer = new StringBuffer();
	//用来保存索引值对应的城市名称
	private  List<String>  firstList = new ArrayList<String>();
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		MyHolder  holder = null;
		if (convertView == null) {
			holder = new MyHolder();
			//布局加载器生成view对象
			convertView = LayoutInflater.from(context).inflate(R.layout.address_city_list_item, null);
			//进行注解的注册
			ViewUtils.inject(holder, convertView);
			//给holder大一个tag
			convertView.setTag(holder);
		}else {
			holder = (MyHolder) convertView.getTag();
		}
		//数据显示的处理
		City city = listCityDatas.get(position);
		String  sort = city.getCitySortkey();
		String  name = city.getCityName();
		//第一次字母索引不存在的时候
		if (buffer.indexOf(sort) == -1) {
			buffer.append(sort);
			firstList.add(name);
		}
		//索引已经存在就让索引进行影藏
		if (firstList.contains(name)) {
			holder.keySort.setText(sort);
			//包含对应的城市就让其索引进行显示
			holder.keySort.setVisibility(View.VISIBLE); 
		} else {
			holder.keySort.setVisibility(View.GONE);
		}
		//名称都需要显示
		holder.cityName.setText(name);
		return convertView;
	}
	
	
	/**
	 * 定义一个holder对象
	 * @author Administrator
	 */
	public class MyHolder{
		@ViewInject(R.id.city_list_item_sort)
		public TextView  keySort;  
		@ViewInject(R.id.city_list_item_name)
		public TextView  cityName;
	}

}
