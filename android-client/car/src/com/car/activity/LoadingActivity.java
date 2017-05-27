package com.car.activity;

import android.view.animation.Animation;

import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.car.R;
import com.car.util.AppPreferences;
import com.car.util.AppPreferences.PreferenceKey;
import com.car.util.VersionUtil;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * loading界面
 * 
 * @author blue
 */
public class LoadingActivity extends BaseActivity
{
	@ViewInject(R.id.loading_iv_ad)
	ImageView loading_iv_ad;

	// 透明度补间动画
	private Animation layoutAnimation;

	@Override
	protected int getLayoutId()
	{
		return R.layout.loading_main;
	}

	@Override
	protected void initParams()
	{
		// 透明度补间动画
		layoutAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_loading);
		layoutAnimation.setAnimationListener(new AlphaLayoutListener());
		loading_iv_ad.startAnimation(layoutAnimation);
	}

	// 透明度补间动画监听器
	class AlphaLayoutListener implements AnimationListener
	{

		@Override
		public void onAnimationEnd(Animation animation)
		{
			loading_iv_ad.postDelayed(new Runnable()
			{

				@Override
				public void run()
				{
					// 得到启动时的版本号,此时没有存储过数据所以为空字符串
					String start_version = AppPreferences.instance().getString(PreferenceKey.START_VERSION);
					if (!start_version.equals(VersionUtil.getAppVersionName()))
					{
						GuideActivity.startActivity(LoadingActivity.this);
						AppPreferences.instance().putString(PreferenceKey.START_VERSION, VersionUtil.getAppVersionName());
					} else
					{
						MainActivity.startActivity(LoadingActivity.this);
					}
					finish();
				}
			}, 2 * 1000);
		}

		@Override
		public void onAnimationRepeat(Animation animation)
		{

		}

		@Override
		public void onAnimationStart(Animation animation)
		{

		}
	}

}
