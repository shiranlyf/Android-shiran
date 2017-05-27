package com.car.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.car.R;
import com.car.activity.AllCategoryActivity;
import com.car.util.MyUtiils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 * 资讯-导购
 * 
 * @author blue
 */
public class NewsBuyFragment extends BaseFragment
{
	@ViewInject(R.id.guide_nav_home)
    private GridView  guideSort;
	
	@Override
	protected int getLayoutId()
	{
		return R.layout.fragment_buy_main;
	}

	@Override
	protected void initParams()
	{
		guideSort.setAdapter(new GuideNavAdapter());
	}
	
	
	//设置一个设配器
	public class GuideNavAdapter extends BaseAdapter{

		//计算需要设配的数据总量
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return MyUtiils.navsSort.length;
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
			//convertView为空的时候将布局转换为view
		    MyHolder myHolder = null;
		    if (convertView == null) {
			     myHolder = new MyHolder();
			     //将xml--view  root设置为空
			     convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_index_nav_item, null);
			     //注解的形式进行初始化
			     ViewUtils.inject(myHolder, convertView);
			     //给view打上一个标签   第二次可以根据标签进行取值myHolder
			     convertView.setTag(myHolder);
			}else {
				myHolder = (MyHolder) convertView.getTag();
			}
		    //将值传入到对应的参数中
		    myHolder.textView.setText(MyUtiils.navsSort[position]);
		    myHolder.imageView.setImageResource(MyUtiils.navsSortImages[position]); 
		    //如果当前选中的是全部的话
		    if (position == MyUtiils.navsSort.length - 1) {
				//给图片控件设置一个点击事件
		    	myHolder.imageView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						startActivity(new Intent(getActivity(), AllCategoryActivity.class));
					}
				});
			}
			return convertView;
		}
		
	}
	
	
	public class MyHolder{
		@ViewInject(R.id.home_nav_item_desc)
		public TextView textView;
		@ViewInject(R.id.home_nav_item_image)
		public ImageView imageView;
	}

}
