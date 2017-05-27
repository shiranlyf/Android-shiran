package com.car.adapter;

import java.util.List;
import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.car.R;
import com.car.activity.NewsPictureActivity;
import com.car.application.LocalApplication;
import com.car.cache.AsyncImageLoader;
import com.car.entity.Newshead;
import com.car.entity.Newsimportant;
import com.car.util.ConstantsUtil;
import com.car.util.DisplayUtil;
import com.car.util.JStringKit;
import com.car.util.StringUtil;
import com.car.view.ToastMaker;

/**
 * 推荐新闻头适配器
 * 
 * @author blue
 * 
 */
public class NewsHeadAdapter extends PagerAdapter
{
	private List<Newshead> datas;

	private Context context;
	private LayoutInflater layoutInflater;

	public NewsHeadAdapter(Context context, List<Newshead> datas)
	{
		this.datas = datas;
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
	}

	/**
	 * 刷新适配器数据源
	 * 只有条数改变的时候才触发刷新机制
	 * @author andrew
	 * @param datas
	 */
	public void refreshDatas(List<Newshead> datas)
	{
		this.datas = datas;
		this.notifyDataSetChanged();
	}
   

	/**
	 * 虚拟告诉适配器 
	 * 需要适配的数据很多
	 */
	@Override
	public int getCount()
	{
		//return datas.size();
	    return  Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		((ViewPager) container).removeView((View) object);
	}

	/**
	 * @SuppressLint("InflateParams")
	 * 设定
	 */
	@SuppressLint("InflateParams")
	@Override
	public Object instantiateItem(ViewGroup container, final int position)
	{
		View layout = layoutInflater.inflate(R.layout.news_head_item, null);
		ImageView viewpager_item_img = (ImageView) layout.findViewById(R.id.viewpager_item_img);
        
		//加入传过来的数据为空的话    就返回    否则datas为null不能调用get()方法   就会报错
		if (datas==null) {
			return false;
		}
		//datas.get(position).converimage
		viewpager_item_img.setTag(ConstantsUtil.IMAGE_URL + datas.get(position % datas.size()).converimage);
		// 开启异步加载图片,显示图片比例为screenW*200dp    取余数实现循环
     	AsyncImageLoader.getInstance(context).loadBitmaps(layout, viewpager_item_img, ConstantsUtil.NEWS_HEAD_URL +datas.get(position % datas.size()).converimage, LocalApplication.getInstance().screenW, DisplayUtil.dip2px(context, 200));
        
     	viewpager_item_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				if (JStringKit.isNotEmpty(datas.get(position % datas.size()).image_list)) 
				{
				    NewsPictureActivity.startActivity(context, datas.get(position % datas.size()).image_list);
				}else
				{
					ToastMaker.showShortToast("新闻详情");
				}
			}
		});
     	//Log.i("sunjob", "我来了");
		((ViewPager) container).addView(layout);
		return layout;
	}
	
	
	// 配合notifyDataSetChanged方法刷新viewpager
	@Override
	public int getItemPosition(Object object)
	{
		return POSITION_NONE;
	}
}
