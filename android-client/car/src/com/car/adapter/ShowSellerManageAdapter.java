package com.car.adapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.car.R;
import com.car.activity.GetMyOrderActivity;
import com.car.activity.MainActivity;
import com.car.activity.ShowOrderInfo;
import com.car.cache.AsyncImageLoader;
import com.car.entity.Order;
import com.car.util.ConstantsUtil;
import com.car.util.DisplayUtil;
import com.car.view.ToastMaker;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ShowSellerManageAdapter extends SimpleBaseAdapter<Order>{
	
	private ListView  listView;
	private List<Order> data;
	private Context  c;

	public ShowSellerManageAdapter(Context c, List<Order> datas, ListView listView) {
		super(c, datas);
		this.listView = listView;
		this.data = datas;
		this.c = c;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		EntityHolder  entityHolder = null;
		//将用户的信息进拆分 
		String order_show_Info = datas.get(position).getOrder_show_info();
		//Log.i("sunjob", order_show_Info + "这是在是配置中");
		//goodsImg  goodsName   goodsPrice
		String[] infos = order_show_Info.split("_");
		String goodsImg = infos[0];
		String goodsName = infos[1];
		String goodsPrice = infos[2];
		if (convertView == null) {
			entityHolder = new EntityHolder();
			//将xml文件换成view对象    root节点设置为空
			convertView=layoutInflater.inflate(R.layout.showorseller_manage, null);
			//对EntityHolder中注解的控件进行初始化
		    ViewUtils.inject(entityHolder, convertView);
		    //给convertView打一个标签
		    convertView.setTag(entityHolder);
		}else {
			entityHolder = (EntityHolder) convertView.getTag();
		}
		
		/* //给索引position对应的item位置设置值     这里显示的title
		entityHolder.item_tv_title.setText(datas.get(position).title);
		// 给imageview设置一个tag，保证异步加载图片时不会乱序
		entityHolder.item_iv_img.setTag(ConstantsUtil.IMAGE_URL +  datas.get(position).coverImage);
		*/
		
		//进行其它控件的值的适配
		entityHolder.item_tv_title.setText(goodsName);
		//显示时间
		String orderTime = datas.get(position).getOrderTime().toString();
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date  date = new Date(orderTime);
		String realOrderTime = null;
		//将时间进行格式化
		entityHolder.item_tv_time.setText(sdf.format(date));
		//显示订单的总价 这里显示的是商品的单价      这里要进行价格的判断
		entityHolder.item_tv_count.setText(goodsPrice + "/" + "元");
		//给imageView设置一个tag  保证异步尽在图片时不会乱序
		entityHolder.item_iv_img.setTag(ConstantsUtil.IMAGE_URL + goodsImg);
        entityHolder.forquxiaoOrder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//int orderId = datas.get(position).getOid();
				//这里得到是订单Id  注意：这里传递过来的是本activity的对象
				Intent  intent = new Intent(c, ShowOrderInfo.class);
			    intent.putExtra("orderId", datas.get(position).getOid().toString());
				c.startActivity(intent);
			}
		});
		//开启异步加载图片
		AsyncImageLoader.getInstance(c).loadBitmaps(listView, entityHolder.item_iv_img, ConstantsUtil.IMAGE_URL +  goodsImg, DisplayUtil.dip2px(c, 70), DisplayUtil.dip2px(c, 40));
	    //给取消按钮添加事件
		return convertView;
	}
	
	private class EntityHolder{
		@ViewInject(R.id.item_iv_img)
		private ImageView item_iv_img;
		@ViewInject(R.id.item_tv_title)
		private TextView item_tv_title;
		@ViewInject(R.id.item_tv_time)
		private TextView item_tv_time;
		@ViewInject(R.id.item_tv_count) //显示的价格
		private TextView item_tv_count;
		@ViewInject(R.id.forquxiaoOrder)
		private ImageView forquxiaoOrder;
	}

}
