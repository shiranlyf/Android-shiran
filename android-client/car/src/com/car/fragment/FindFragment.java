package com.car.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.car.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 找车
 * 
 * @author blue
 */
public class FindFragment extends BaseFragment {
	// 选择开关
	@ViewInject(R.id.fragment_find_llyt_switch)
	LinearLayout fragment_find_llyt_switch;
	// 品牌找车
	@ViewInject(R.id.fragment_find_tv_brand)
	TextView fragment_find_tv_brand;
	// 精准找车
	@ViewInject(R.id.fragment_find_tv_filter)
	TextView fragment_find_tv_filter;

	// fragment事务
	private FragmentTransaction ft;
	// 品牌找车
	private FindBrandFragment findBrandFragment;
	// 精准找车
	private FindFilterFragment findFilterFragment;

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_find_main;
	}

	@Override
	protected void initParams() {
		//默认选择品牌选车
		viewOnClick(fragment_find_tv_brand);
	}


	// 控件点击事件
	@OnClick({ R.id.fragment_find_tv_brand, R.id.fragment_find_tv_filter })
	public void viewOnClick(View view) {
		ft = getChildFragmentManager().beginTransaction();

		switch (view.getId()) {
		// 品牌找车
		case R.id.fragment_find_tv_brand:

			fragment_find_llyt_switch
					.setBackgroundResource(R.drawable.fragment_find_ic_left);
			fragment_find_tv_brand.setTextColor(getResources().getColor(
					R.color.find_cl_choose));
			fragment_find_tv_filter.setTextColor(getResources().getColor(
					R.color.find_cl_unchoose));

			if (findBrandFragment == null) {
				findBrandFragment = new FindBrandFragment();
			}
			ft.replace(R.id.fragment_find_flyt_content, findBrandFragment);

			break;
		// 精准找车
		case R.id.fragment_find_tv_filter:

			fragment_find_llyt_switch
					.setBackgroundResource(R.drawable.fragment_find_ic_right);
			fragment_find_tv_filter.setTextColor(getResources().getColor(
					R.color.find_cl_choose));
			fragment_find_tv_brand.setTextColor(getResources().getColor(
					R.color.find_cl_unchoose));

			if (findFilterFragment == null) {
				findFilterFragment = new FindFilterFragment();
			}
			ft.replace(R.id.fragment_find_flyt_content, findFilterFragment);

			break;

		default:
			break;
		}
		ft.commit();
	}

}
