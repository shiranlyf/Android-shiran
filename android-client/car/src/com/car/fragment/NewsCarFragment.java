package com.car.fragment;

import com.car.R;
import com.car.view.ProgressWheel;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 * 资讯-新车
 * 新车对应的瀑布流
 * @author blue
 */
public class NewsCarFragment extends BaseFragment
{
    //初始化环的控件
	@ViewInject(R.id.pw)
	ProgressWheel pw;
	
	@Override
	protected int getLayoutId()
	{
		return R.layout.fragment_car_main;
	}

	@Override
	protected void initParams()
	{
		//设置显示文字信息
		pw.setText("loading");
		//开始旋转加载
		pw.spin();
		
	}

}
