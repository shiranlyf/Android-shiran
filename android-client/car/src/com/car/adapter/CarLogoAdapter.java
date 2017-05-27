package com.car.adapter;

import java.util.List;

import java.util.Locale;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.car.R;
import com.car.cache.AsyncImageLoader;
import com.car.entity.Carlogo;
import com.car.util.ConstantsUtil;
import com.car.view.PinnedHeaderListView;
import com.car.view.PinnedHeaderListView.PinnedHeaderAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 品牌找车适配器
 * 
 * @author blue
 * 
 */
public class CarLogoAdapter extends SimpleBaseAdapter<Carlogo> implements SectionIndexer, PinnedHeaderAdapter, OnScrollListener
{
	private View view;

	private int mLocationPosition = -1;

	public CarLogoAdapter(Context c, List<Carlogo> datas,View view) {
		super(c, datas);
		this.view = view;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		EntityHolder entityHolder = null;

		if (convertView == null)
		{
			entityHolder = new EntityHolder();

			convertView = layoutInflater.inflate(R.layout.fragment_find_brand_listview_item, null);

			ViewUtils.inject(entityHolder, convertView);

			convertView.setTag(entityHolder);
		} else
		{
			entityHolder = (EntityHolder) convertView.getTag();
		}

		int section = getSectionForPosition(position);
		if (getPositionForSection(section) == position)
		{
			entityHolder.list_item_header.setVisibility(View.VISIBLE);
			entityHolder.list_item_header_text.setText(datas.get(position).namespell.toUpperCase(Locale.CHINA).charAt(0) + "");
		} else
		{
			entityHolder.list_item_header.setVisibility(View.GONE);
		}
		entityHolder.list_item_content_text.setText(datas.get(position).name);
		// 给imageview设置一个tag，保证异步加载图片时不会乱序
		//Log.i("sunjob", ConstantsUtil.CARLOGO__URL + datas.get(position).carimg+"这是img");
		entityHolder.list_item_content_logo.setTag(ConstantsUtil.CARLOGO__URL + datas.get(position).carimg);
		// 开启异步加载图片
		AsyncImageLoader.getInstance(c).loadBitmaps(view, entityHolder.list_item_content_logo, ConstantsUtil.CARLOGO__URL + datas.get(position).carimg);

		return convertView;
	}

	private class EntityHolder
	{
		// 列表头布局
		@ViewInject(R.id.list_item_header)
		LinearLayout list_item_header;
		// 列表头文字
		@ViewInject(R.id.list_item_header_text)
		TextView list_item_header_text;
		// 列表内容文字
		@ViewInject(R.id.list_item_content_text)
		TextView list_item_content_text;
		// 汽车logo
		@ViewInject(R.id.list_item_content_logo)
		ImageView list_item_content_logo;
	}
	
	
	
	/********************以下是SectionIndex的三个实现方法**********/
	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 返回当前首字母出现的第一个位置
	 */
	@Override
	public int getPositionForSection(int section)
	{
		for (int i = 0; i < getCount(); i++)
		{
			char firstChar = datas.get(i).namespell.toUpperCase(Locale.CHINA).charAt(0);
			if (firstChar == section)
			{
				return i;
			}
		}
		return -1;
	}

	/**
	 * 返回当前位置的首字母
	 */
	@Override
	public int getSectionForPosition(int position)
	{
		return datas.get(position).namespell.toUpperCase(Locale.CHINA).charAt(0);
	}
	/********************以上是SectionIndex的三个实现方法**********/

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
	{
		if (view instanceof PinnedHeaderListView)
		{
			((PinnedHeaderListView) view).configureHeaderView(firstVisibleItem);
		}
	}
	

	@Override
	public int getPinnedHeaderState(int position)
	{
		int realPosition = position;
		if (realPosition < 0 || (mLocationPosition != -1 && mLocationPosition == realPosition))
		{
			return PINNED_HEADER_GONE;
		}
		mLocationPosition = -1;
		if (datas.size() > 1)
		{
			int nextSection = getSectionForPosition(realPosition + 1);
			int nextSectionPosition = getPositionForSection(nextSection);
			if (nextSectionPosition != -1 && realPosition == nextSectionPosition - 1)
			{
				return PINNED_HEADER_PUSHED_UP;
			}
		}
		return PINNED_HEADER_VISIBLE;
	}

	@Override
	public void configurePinnedHeader(View header, int position, int alpha)
	{
		((TextView) header.findViewById(R.id.list_item_header_text)).setText(datas.get(position).namespell.toUpperCase(Locale.CHINA).charAt(0) + "");
	}

	

	
}
