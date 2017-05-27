package com.car.adapter;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.car.R;
import com.car.cache.AsyncImageLoader;
import com.car.entity.Carseries;
import com.car.util.ConstantsUtil;
import com.car.view.PinnedHeaderListView;
import com.car.view.PinnedHeaderListView.PinnedHeaderAdapter;

/**
 * 车系找车适配器
 * 
 * @author blue
 * 
 */
public class CarSeriesAdapter extends SimpleBaseAdapter<Carseries> implements PinnedHeaderAdapter, OnScrollListener
{
	// 数据源
	private View view;

	private int mLocationPosition = -1;

	public CarSeriesAdapter(Context c, List<Carseries> datas, View view)
	{
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

			convertView = layoutInflater.inflate(R.layout.fragment_find_cover_listview_item, null);
			entityHolder.cover_list_item_header = (LinearLayout) convertView.findViewById(R.id.cover_list_item_header);
			entityHolder.cover_list_item_header_text = (TextView) convertView.findViewById(R.id.cover_list_item_header_text);
			entityHolder.cover_list_item_content_img = (ImageView) convertView.findViewById(R.id.cover_list_item_content_img);
			entityHolder.cover_list_item_content_name = (TextView) convertView.findViewById(R.id.cover_list_item_content_name);
			entityHolder.cover_list_item_content_price = (TextView) convertView.findViewById(R.id.cover_list_item_content_price);

			convertView.setTag(entityHolder);
		} else
		{
			entityHolder = (EntityHolder) convertView.getTag();
		}

		//这里就根据以下两个方法进行匹配  然后将第一次出现的name显示   后面出现的影藏
		String prefix = getSectionForPosition(position);
		if (getPositionForSection(prefix) == position)
		{
			entityHolder.cover_list_item_header.setVisibility(View.VISIBLE);
			entityHolder.cover_list_item_header_text.setText(datas.get(position).name);
		} else
		{
			entityHolder.cover_list_item_header.setVisibility(View.GONE);
		}
		entityHolder.cover_list_item_content_name.setText(datas.get(position).aliasName);
		entityHolder.cover_list_item_content_price.setText(datas.get(position).dealerPrice);

		// 给imageview设置一个tag，保证异步加载图片时不会乱序h
		entityHolder.cover_list_item_content_img.setTag(ConstantsUtil.IMAGE_URL + datas.get(position).aliasImg);
		// 开启异步加载图片
		AsyncImageLoader.getInstance(c).loadBitmaps(view, entityHolder.cover_list_item_content_img, ConstantsUtil.CARERIES__URL + datas.get(position).aliasImg);

		return convertView;
	}

	private class EntityHolder
	{
		// 列表头布局
		public LinearLayout cover_list_item_header;
		// 列表头文字
		public TextView cover_list_item_header_text;
		// 列表车系图片
		public ImageView cover_list_item_content_img;
		// 列表内容名字
		public TextView cover_list_item_content_name;
		// 列表内容价格
		public TextView cover_list_item_content_price;
	}

	/**
	 * 返回当前前缀出现的第一个位置
	 */
	public int getPositionForSection(String prefix)
	{
		for (int i = 0; i < getCount(); i++)
		{
			String tmp = datas.get(i).name;
			if (tmp.equals(prefix))
			{
				return i;
			}
		}
		return -1;
	}

	/**
	 * 返回当前位置的前缀
	 */
	public String getSectionForPosition(int position)
	{
		return datas.get(position).name;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
	{
		if (view instanceof PinnedHeaderListView)
		{
			((PinnedHeaderListView) view).configureHeaderView(firstVisibleItem);
		}
	}

	/**
	 * 列表头挤压状态判断
	 */
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
			String nextPrefix = getSectionForPosition(realPosition + 1);
			int nextSectionPosition = getPositionForSection(nextPrefix);
			if (nextSectionPosition != -1 && realPosition == nextSectionPosition - 1)
			{
				return PINNED_HEADER_PUSHED_UP;
			}
		}
		return PINNED_HEADER_VISIBLE;
	}

	/**
	 * 设置列表头文字
	 */
	@Override
	public void configurePinnedHeader(View header, int position, int alpha)
	{
		if (datas.size() > 0)
		{
			((TextView) header.findViewById(R.id.cover_list_item_header_text)).setText(datas.get(position).name);
		}
	}

}
