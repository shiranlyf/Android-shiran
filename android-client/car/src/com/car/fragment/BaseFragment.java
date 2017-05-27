package com.car.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;

/**
 * basefragment
 * 
 * @author blue
 */
public abstract class BaseFragment extends Fragment
{   
	//将这里的private改成public 子类可以继承
	public Context context;
	protected Dialog dialog;
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		context = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		//将一个布局转化成一个view对象
		View view = inflater.inflate(getLayoutId(), container, false);
		ViewUtils.inject(this, view);
		initParams();

		return view;
	}

	/**
	 * 初始化布局
	 * 
	 * @author blue
	 */
	protected abstract int getLayoutId();
	
	/**
	 * 参数设置
	 * 
	 * @author blue
	 */
	protected abstract void initParams();

	


	/**
	 * 关闭对话框
	 * 
	 * @author blue
	 */
	public void dismissDialog()
	{
		if (null != dialog && dialog.isShowing())
		{
			dialog.dismiss();
		}
	}
}
