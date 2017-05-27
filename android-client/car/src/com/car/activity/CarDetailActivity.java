package com.car.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.sharesdk.framework.authorize.b;

import com.car.R;
import com.car.cache.AsyncImageLoader;
import com.car.entity.Newsimportant;
import com.car.entity.Shop;
import com.car.util.ConstantsUtil;
import com.car.util.DisplayUtil;
import com.car.util.SharedUtils;
import com.car.util.StringUtil;
import com.car.view.ToastMaker;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso;

public class CarDetailActivity extends BaseActivity {

	// 使用注解的形式进行初始化
	@ViewInject(R.id.goods_image)
	private ImageView goods_image;
	@ViewInject(R.id.goods_title)
	private TextView goods_title;
	@ViewInject(R.id.goods_desc)
	private TextView goods_desc;
	@ViewInject(R.id.goods_title)
	private TextView shop_title;
	@ViewInject(R.id.shop_phone)
	private TextView shop_phone;
	@ViewInject(R.id.goods_price)
	private TextView goods_price;
	@ViewInject(R.id.goods_old_price)
	private TextView goods_old_price;
	@ViewInject(R.id.btn_buy_now)
	private Button   btn_buy_now;  //现在购买按钮
	@ViewInject(R.id.tv_more_details_web_view)
	private WebView tv_more_details_web_view;
	@ViewInject(R.id.wv_gn_warm_prompt)
	private WebView wv_gn_warm_prompt;
	private Newsimportant newsimportant;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.car_item_detail;
	}

	@Override
	protected void initParams() {
		// 给textView设置中划线
		goods_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		// 让网页自适应屏幕
		WebSettings webSettings = tv_more_details_web_view.getSettings();
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		WebSettings webSettings1 = wv_gn_warm_prompt.getSettings();
		webSettings1.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// 得到intent传过来的值
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			// 通过key值进行获取
			newsimportant = (Newsimportant) bundle.get("goods");
		}
		if (newsimportant != null) {
			// 更新详情页的内容
			updateTitleImage();
			updateNewsImportantInfo();
			updateShopInfo();
			updateMoreDetails();
		}

	}

	/**
	 * 拨打商家电话的点击事件
	 * @param v
	 */
	@OnClick({R.id.shop_call, R.id.goods_detail_goback, R.id.btn_buy_now})
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shop_call:
			Intent callin = new Intent(Intent.ACTION_DIAL);
			callin.setData(Uri.parse("tel:"
					+ newsimportant.getShop().getShopTel()));
			startActivity(callin);
			break;
		case R.id.goods_detail_goback:
			finish();
			break;
		case R.id.btn_buy_now: //点击现在购买按钮
			String username = SharedUtils.getUserName(getBaseContext());
			if("点击登录".equals(username)){
				ToastMaker.showShortToast("亲   您还没有登录");
				return;
			}
			Intent  intent = new Intent(getBaseContext(), GetMyOrderActivity.class);
			//将商品信息传递过去
			intent.putExtra("goods", newsimportant);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	
	// 更新商品的标题图片
	private void updateTitleImage() {
		Picasso.with(this)
				.load(ConstantsUtil.IMAGE_URL + newsimportant.getCoverImage())
				.placeholder(R.drawable.ic_empty_dish).into(goods_image);
		// 图片的压缩加载
		// AsyncImageLoader.getInstance(getApplicationContext()).loadBitmaps(null,
		// goods_image, ConstantsUtil.IMAGE_URL + newsimportant.getCoverImage(),
		// DisplayUtil.dip2px(getApplicationContext(), 105),
		// DisplayUtil.dip2px(getApplicationContext(), 75));
	}

	// 更新商品信息
	private void updateNewsImportantInfo() {
		goods_title.setText(newsimportant.getTitle());
		// 添加商品详情
		goods_desc.setText(newsimportant.getCardesc());
		// 设置现在的价格
		goods_price.setText("￥" + newsimportant.getCar_price());
		goods_old_price.setText("￥" + newsimportant.getCar_old_price());
	}

	// 更新商家信息
	private void updateShopInfo() {
		// 得到shop对象
		Shop shop = newsimportant.getShop();
		if (shop != null) {
			// 设置商家标题
			if (StringUtil.isNotNull(shop.getShopName())) {
				shop_title.setText(shop.getShopName());
			}
			// 设置商家电话
			if (StringUtil.isNotNull(shop.getShopTel())) {
				shop_title.setText(shop.getShopName());
			}
			shop_phone.setText(shop.getShopTel());
		}
	}
	
	
	public void updateMoreDetails() {
        String[] data = htmlSub(newsimportant.getCar_detail());
        tv_more_details_web_view.loadDataWithBaseURL("", data[1], "text/html", "utf-8", "");
        wv_gn_warm_prompt.loadDataWithBaseURL("", data[0], "text/html", "utf-8", "");
	}

	/**
	 * 解析html类型的字符串 <div class="prodetail-sp"><h4 style="background:#720c00">
	 * 【本单详情】</h4> </div>
	 */
	public String[] htmlSub(String html) {
		String[] data = new String[3];
		if (StringUtil.isNotNull(html)) {
			char[] str = html.toCharArray();
			int len = str.length;
			Log.i("sunjob", len + "");
			int n = 0;
			int oneIndex = 0;
			int secIndex = 1;
			int thiIndex = 2;
			for (int i = 0; i < len; i++) {
				if (str[i] == '【') {
					n++;
					if (n == 1)
						oneIndex = i;
					if (n == 2)
						secIndex = i;
					if (n == 3)
						thiIndex = i;
				}
			}
			if (oneIndex > 0 && secIndex > 1 && thiIndex > 2) {
				data[0] = html.substring(oneIndex, secIndex);
				data[1] = html.substring(secIndex, thiIndex);
				// </div>占六个字符 减掉
				data[2] = html.substring(thiIndex, html.length() - 6);
			}
		}

		return data;
	}

}
