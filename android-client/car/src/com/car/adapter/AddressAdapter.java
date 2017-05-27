package com.car.adapter;

import java.util.List;

import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.car.R;
import com.car.activity.AddressUpdateActivity;
import com.car.cache.AsyncImageLoader;
import com.car.entity.AddressEntity;
import com.car.util.ConstantsUtil;
import com.car.view.ToastMaker;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class AddressAdapter extends SimpleBaseAdapter<AddressEntity>{

	private Context  c;
	public AddressAdapter(Context c, List<AddressEntity> datas) {
		super(c, datas);
		this.c = c;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		EntityHolder entityHolder = null;
		final AddressEntity address = datas.get(position);
		if (convertView == null)
		{
			entityHolder = new EntityHolder();

			convertView = layoutInflater.inflate(R.layout.add_person_order_address_item, null);

			ViewUtils.inject(entityHolder, convertView);

			convertView.setTag(entityHolder);
		} else
		{
			entityHolder = (EntityHolder) convertView.getTag();
		}
		
		entityHolder.address_name.setText(datas.get(position).addressName);
		entityHolder.address_phone.setText(datas.get(position).addressTel);
		String cityAndDetailAddressInfo = datas.get(position).getAddressCity() + "—" + datas.get(position).addressDetail;
		entityHolder.order_detail_address.setText(cityAndDetailAddressInfo);
		//得到性别
		String  peopleSex = datas.get(position).peopleSex;
		if (peopleSex.equals("1")) {  //表示男的
			entityHolder.is_boy_girl.setText("先生");
		}else {
			entityHolder.is_boy_girl.setText("女士");
		}
		entityHolder.address_update_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//ToastMaker.showShortToast(address.getAddressName());   
				Intent  intent = new Intent(c, AddressUpdateActivity.class);
				intent.putExtra("address", address);
			    c.startActivity(intent);
			}
		});
		return convertView;
	}	
	
	
	
	private class EntityHolder
	{
		@ViewInject(R.id.address_name)
		TextView address_name;
		@ViewInject(R.id.address_phone)
		TextView address_phone;
		@ViewInject(R.id.order_detail_address)
		TextView order_detail_address;
		@ViewInject(R.id.address_update_image)
		ImageView address_update_image;
		@ViewInject(R.id.is_boy_girl)  //判断是男的还是女的
		TextView  is_boy_girl;
	}
	

	

}
