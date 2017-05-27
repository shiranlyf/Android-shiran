package com.car.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.car.R;
import com.car.cache.AsyncImageLoader;
import com.car.entity.Newsimportant;
import com.car.util.ConstantsUtil;
import com.car.util.DisplayUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NewsItemAdapter extends SimpleBaseAdapter<Newsimportant>{
	
	//显示的列表视图
	private  ListView   listView;
	
    /**
     * 这里对应的构造方法
     * 对listView进行初始哈
     * @param c
     * @param datas
     */
	public NewsItemAdapter(Context c, List<Newsimportant> datas, ListView listView) {
		super(c, datas);
		this.listView = listView;
	}
    
	/**
	 * 核心的绘制视图
	 * 的函数
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    EntityHolder  entityHolder=null;
		if (convertView==null) {
			entityHolder=new EntityHolder();
			//将布局文件转换成view对象  根节点社为空
			convertView=layoutInflater.inflate(R.layout.news_item, null);
			//对entityHolder里面的控件注解进行初始化
		    ViewUtils.inject(entityHolder, convertView);
		    //给convertView打一个标签
		    convertView.setTag(entityHolder);
		}else {
			//根据标签得到entityHolder对象
			entityHolder=(EntityHolder) convertView.getTag();
		}
	    //给索引position对应的item位置设置值     这里显示的title
		entityHolder.item_tv_title.setText(datas.get(position).title);
		// 给imageview设置一个tag，保证异步加载图片时不会乱序
		entityHolder.item_iv_img.setTag(ConstantsUtil.IMAGE_URL +  datas.get(position).coverImage);
		// 开启异步加载图片
        AsyncImageLoader.getInstance(c).loadBitmaps(listView, entityHolder.item_iv_img, ConstantsUtil.IMAGE_URL +  datas.get(position).coverImage, DisplayUtil.dip2px(c, 105), DisplayUtil.dip2px(c, 75));
		
		
		return convertView;
	}

	private  class EntityHolder{
		//imageView进行初始化
		@ViewInject(R.id.item_iv_img)
		ImageView item_iv_img;
		//标题的初始化
		@ViewInject(R.id.item_tv_title)
		TextView item_tv_title;
	}
}
