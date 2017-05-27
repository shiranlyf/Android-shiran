package com.car.activity;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.sina.weibo.SinaWeibo.ShareParams;

import com.alibaba.fastjson.JSONArray;
import com.car.R;
import com.car.application.LocalApplication;
import com.car.cache.AsyncImageLoader;
import com.car.entity.Newsimageitem;
import com.car.util.ConstantsUtil;
import com.car.util.DisplayUtil;
import com.car.view.ToastMaker;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 资讯图解详情
 * 
 * @author blue
 * 
 */
public class NewsPictureActivity extends BaseActivity {

	// 获得数据
	public List<Newsimageitem> dataList;

	@ViewInject(R.id.car_picture_iv_back)
	ImageView car_picture_iv_back;
	@ViewInject(R.id.car_picture_iv_like)
	ImageView car_picture_iv_like;
	@ViewInject(R.id.car_picture_iv_share)
	ImageView car_picture_iv_share;
	@ViewInject(R.id.car_picture_tv_index)
	TextView car_picture_tv_index;
	@ViewInject(R.id.car_picture_vp)
	ViewPager car_picture_vp;

	// 分享对话框
	private Dialog shareDialog;

	private static final int MSG_AUTH_CANCEL = 2;
	private static final int MSG_AUTH_ERROR = 3;
	private static final int MSG_AUTH_COMPLETE = 4;

	// 动画图层
	private ViewGroup animlayout;
	// +1数字动画
	private TextView numTv;

	// 静态方法启动activity datas是json数据
	public static void startActivity(Context context, String datas) {
		Intent intent = new Intent(context, NewsPictureActivity.class);
		intent.putExtra("datas", datas);
		context.startActivity(intent);
	}

	/**
	 * 将json反序列化为一个list集合
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		dataList = JSONArray.parseArray(getIntent().getStringExtra("datas"),
				Newsimageitem.class);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.news_picture_main;
	}

	@Override
	protected void initParams() {
		// 初始化当前的索引为1
		car_picture_tv_index.setText("1/" + dataList.size());
		// 给viewpager绑定适配器
		car_picture_vp.setAdapter(new ViewPagerAdapter());
		// 也卡切换的监听器
		car_picture_vp.setOnPageChangeListener(new ViewPagerChangeListener());
		// 设置默认选中项
		car_picture_vp.setCurrentItem(0);
	}

	// 查看大图viewpager适配器
	private class ViewPagerAdapter extends PagerAdapter {
		@SuppressLint("InflateParams")
		@Override
		public Object instantiateItem(android.view.ViewGroup container,
				int position) {
			View view = getLayoutInflater().inflate(R.layout.news_picture_item,
					null);
			ImageView picture_iv_item = (ImageView) view
					.findViewById(R.id.picture_iv_item);
			// 给imageview设置一个tag，保证异步加载图片时不会乱序
			picture_iv_item.setTag(ConstantsUtil.IMAGE_URL
					+ dataList.get(position).url);
			// 开启异步加载图片,显示图片宽度为screenW
			AsyncImageLoader.getInstance(NewsPictureActivity.this).loadBitmaps(
					view,
					picture_iv_item,
					ConstantsUtil.NEWS_IMAGEITEM_URL
							+ dataList.get(position).url,
					LocalApplication.getInstance().screenW, 0);
			container.addView(view);

			return view;
		};

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = (View) object;
			container.removeView(view);
		}

	}

	// viewpager切换监听器
	private class ViewPagerChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			car_picture_tv_index.setText((arg0 + 1) + "/" + dataList.size());
		}

	}

	// 控件点击事件
	@OnClick({ R.id.car_picture_iv_back, R.id.car_picture_iv_like,
			R.id.car_picture_iv_share })
	public void viewOnClick(View view) {
		switch (view.getId()) {
		case R.id.car_picture_iv_back:

			finish();

			break;

		case R.id.car_picture_iv_like:

			int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
			view.getLocationInWindow(start_location);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
			numTv = new TextView(this);
			numTv.setText("+1");
			numTv.setTextColor(getResources().getColor(android.R.color.white));
			numTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			setAnim(numTv, start_location);// 开始执行动画

			break;

		case R.id.car_picture_iv_share:

			// 弹出分享对话框
			showDialog();

			break;

		default:
			break;
		}
	}

	// 创建动画层
	private ViewGroup createAnimLayout() {
		ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
		LinearLayout animLayout = new LinearLayout(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
		animLayout.setLayoutParams(lp);
		animLayout.setId(Integer.MAX_VALUE);
		animLayout.setBackgroundResource(android.R.color.transparent);
		rootView.addView(animLayout);
		return animLayout;
	}

	private View addViewToAnimLayout(final ViewGroup vg, final View view,
			int[] location) {
		int x = location[0];
		int y = location[1];
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = x + DisplayUtil.dip2px(this, 10);
		lp.topMargin = y;
		view.setLayoutParams(lp);
		return view;
	}

	private void setAnim(final View v, int[] start_location) {
		animlayout = null;
		animlayout = createAnimLayout();
		animlayout.addView(v);// 把动画添加到动画层
		final View view = addViewToAnimLayout(animlayout, v, start_location);

		// 计算位移
		int endY = -DisplayUtil.dip2px(this, 30);// 动画位移的y坐标

		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
				0, endY);
		translateAnimationY.setInterpolator(new AccelerateInterpolator());
		translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationY.setFillAfter(true);

		AnimationSet set = new AnimationSet(false);
		set.setFillAfter(false);
		set.addAnimation(translateAnimationY);
		set.setDuration(800);// 动画的执行时间
		view.startAnimation(set);
		// 动画监听事件
		set.setAnimationListener(new AnimationListener() {
			// 动画的开始
			@Override
			public void onAnimationStart(Animation animation) {
				v.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			// 动画的结束
			@Override
			public void onAnimationEnd(Animation animation) {
				v.setVisibility(View.GONE);
			}
		});

	}

	@SuppressLint("InflateParams")
	private void showDialog() {
		if (shareDialog == null) {
			View view = getLayoutInflater().inflate(R.layout.share_dialog_main,
					null);
			// 新浪微博
			LinearLayout share_llyt_sina = (LinearLayout) view
					.findViewById(R.id.share_llyt_sina);

			// 新浪微博
			share_llyt_sina.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ShareParams sp = new ShareParams();
					sp.setText("我分享了一张图片，大家快来看！");
					sp.setImageUrl(ConstantsUtil.IMAGE_URL
							+ dataList.get(0).url);

					Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
					// 设置分享事件回调
					weibo.setPlatformActionListener(new ShareListener());
					// 执行图文分享
					weibo.share(sp);

					shareDialog.dismiss();
					ToastMaker.showShortToast("正在分享，请稍候");
				}
			});

			shareDialog = new Dialog(this,
					R.style.DialogNoTitleStyleTranslucentBg);
			shareDialog.setContentView(view, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			// 设置动画
			Window window = shareDialog.getWindow();
			// 设置显示动画
			window.setWindowAnimations(R.style.BottomMenuAnim);
			WindowManager.LayoutParams wl = window.getAttributes();
			wl.x = 0;
			wl.y = getWindowManager().getDefaultDisplay().getHeight();
			// 以下这两句是为了保证可以水平满屏
			wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
			wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

			// 设置显示位置
			shareDialog.onWindowAttributesChanged(wl);
			// 设置点击外面关闭对话框
			shareDialog.setCanceledOnTouchOutside(true);
		}
		shareDialog.show();
	}

	// 分享回调接口
	private class ShareListener implements PlatformActionListener {

		@Override
		public void onCancel(Platform arg0, int arg1) {
			// 判断当前是否是分享结果
			if (arg1 == Platform.ACTION_SHARE) {
				shareHandler.sendEmptyMessage(MSG_AUTH_CANCEL);
			}
		}

		@Override
		public void onComplete(Platform arg0, int arg1,
				HashMap<String, Object> arg2) {
			// 判断当前是否是分享结果
			if (arg1 == Platform.ACTION_SHARE) {
				shareHandler.sendEmptyMessage(MSG_AUTH_COMPLETE);
			}
		}

		@Override
		public void onError(Platform arg0, int arg1, Throwable arg2) {
			// 判断当前是否是分享结果
			if (arg1 == Platform.ACTION_SHARE) {
				shareHandler.sendEmptyMessage(MSG_AUTH_ERROR);
			}
		}

	}

	/**
	 * 回调到ui线程进行提示
	 */
	@SuppressLint("HandlerLeak")
	private Handler shareHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 授权取消
			case MSG_AUTH_CANCEL:

				ToastMaker.showShortToast("分享取消");

				break;
			// 授权成功
			case MSG_AUTH_COMPLETE:

				ToastMaker.showShortToast("分享成功");

				break;
			// 授权失败
			case MSG_AUTH_ERROR:

				ToastMaker.showShortToast("分享失败，请先安装第三方客户端");

				break;

			default:
				break;
			}

		}
	};

}
