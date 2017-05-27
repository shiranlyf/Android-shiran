package com.car.activity;

import java.io.InputStream;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.car.R;
import com.car.util.JListKit;
import com.car.view.indicator.CirclePageIndicator;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 引导界面
 * 
 * @author blue
 */
public class GuideActivity extends BaseActivity
{
	// viewpager
	@ViewInject(R.id.guide_vp)
	ViewPager guide_vp;
	// viewpage indicator
	@ViewInject(R.id.guide_vp_indicator)
	CirclePageIndicator guide_vp_indicator;

	// 数据源
	private List<View> dataList = JListKit.newArrayList();

	private LinearLayout layou1;
	private LinearLayout layou2;
	private LinearLayout layou3;
	private LinearLayout layou4;
	private FrameLayout layou5;
	private Button guide_btn_start;
	private ImageView guide_iv1;
	private ImageView guide_iv2;
	private ImageView guide_iv3;
	private ImageView guide_iv4;
	private ImageView guide_iv5;
	private Bitmap bitmap1;
	private Bitmap bitmap2;
	private Bitmap bitmap3;
	private Bitmap bitmap4;
	private Bitmap bitmap5;

	// 静态方法启动activity
	public static void startActivity(Context context)
	{
		Intent intent = new Intent(context, GuideActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected int getLayoutId()
	{
		return R.layout.guide_main;
	}

	@SuppressLint("InflateParams")
	@Override
	protected void initParams()
	{
		layou1 = (LinearLayout) getLayoutInflater().inflate(R.layout.guide_item1, null);
		layou2 = (LinearLayout) getLayoutInflater().inflate(R.layout.guide_item2, null);
		layou3 = (LinearLayout) getLayoutInflater().inflate(R.layout.guide_item3, null);
		layou4 = (LinearLayout) getLayoutInflater().inflate(R.layout.guide_item4, null);
		layou5 = (FrameLayout) getLayoutInflater().inflate(R.layout.guide_item5, null);

		guide_iv1 = (ImageView) layou1.findViewById(R.id.guide_iv1);
		guide_iv2 = (ImageView) layou2.findViewById(R.id.guide_iv2);
		guide_iv3 = (ImageView) layou3.findViewById(R.id.guide_iv3);
		guide_iv4 = (ImageView) layou4.findViewById(R.id.guide_iv4);
		guide_iv5 = (ImageView) layou5.findViewById(R.id.guide_iv5);

		//动态读取图片  以免内存溢出
		bitmap1 = readBitMap(this, R.drawable.guide_bg1);
		bitmap2 = readBitMap(this, R.drawable.guide_bg2);
		bitmap3 = readBitMap(this, R.drawable.guide_bg3);
		bitmap4 = readBitMap(this, R.drawable.guide_bg4);
		bitmap5 = readBitMap(this, R.drawable.guide_bg5);

		guide_iv1.setImageBitmap(bitmap1);
		guide_iv2.setImageBitmap(bitmap2);
		guide_iv3.setImageBitmap(bitmap3);
		guide_iv4.setImageBitmap(bitmap4);
		guide_iv5.setImageBitmap(bitmap5);

		// 立即体验
		guide_btn_start = (Button) layou5.findViewById(R.id.guide_btn_start);
		guide_btn_start.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				MainActivity.startActivity(GuideActivity.this);
				finish();
			}
		});

		dataList.add(layou1);
		dataList.add(layou2);
		dataList.add(layou3);
		dataList.add(layou4);
		dataList.add(layou5);

		guide_vp.setAdapter(new GuideViewPagerAdapter());
		guide_vp_indicator.setViewPager(guide_vp);
	}

	// viewpager适配器
	public class GuideViewPagerAdapter extends PagerAdapter
	{

		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			((ViewPager) container).addView(dataList.get(position), 0);
			return dataList.get(position);
		}

		@Override
		public int getCount()
		{
			return dataList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1)
		{
			return arg0 == (arg1);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			((ViewPager) container).removeView(dataList.get(position));
		}
	}

	// 以最省内存的方式读取本地资源图片
	public Bitmap readBitMap(Context context, int resId)
	{
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	@Override
	protected synchronized void onDestroy()
	{
		if (bitmap1 != null && !bitmap1.isRecycled())
		{
			bitmap1.recycle();
		}
		if (bitmap2 != null && !bitmap2.isRecycled())
		{
			bitmap2.recycle();
		}
		if (bitmap3 != null && !bitmap3.isRecycled())
		{
			bitmap3.recycle();
		}
		if (bitmap4 != null && !bitmap4.isRecycled())
		{
			bitmap4.recycle();
		}
		if (bitmap5 != null && !bitmap5.isRecycled())
		{
			bitmap5.recycle();
		}
		super.onDestroy();
	}

}
