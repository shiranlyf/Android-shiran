package com.car.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.car.R;
import com.car.util.MyUtiils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 全部分类页面的activity
 * @author shiran
 *
 */
public class AllCategoryActivity extends BaseActivity{
    @ViewInject(R.id.home_nav_all_categary)
	public ListView  categoryList;
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.home_index_nav_all;
	}

	@Override
	protected void initParams() {
		//适配数据
		categoryList.setAdapter(new MyAdapter());
	}

	/**
	 * 进行数据的适配
	 * @author shiran
	 */
	public  class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return MyUtiils.allCategray.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		    MyHolder  myHolder = null;
		    if (convertView == null) {
				myHolder = new MyHolder();
				//xml---view
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_index_nav_all_item, null);
			    //进行初始化
				ViewUtils.inject(myHolder, convertView);
				//view打标签
				convertView.setTag(myHolder);
		    } else {
                //从标签中进行取值
		    	myHolder = (MyHolder) convertView.getTag();
			}
		    //赋值
		    myHolder.textDesc.setText(MyUtiils.allCategray[position]);
		    myHolder.imageView.setImageResource(MyUtiils.allCategrayImages[position]);
		    //对每一个分类的商品数量进行显示
		    myHolder.textNumber.setText(MyUtiils.allCategoryNumber[position] + "");
			return convertView;
		}
		
	}
	
	public class MyHolder{
		@ViewInject(R.id.home_nav_all_item_number)
		public TextView  textNumber;
		@ViewInject(R.id.home_nav_all_item_desc)
		public TextView  textDesc;
		@ViewInject(R.id.home_nav_all_item_image)
		public ImageView  imageView;
	}
}
