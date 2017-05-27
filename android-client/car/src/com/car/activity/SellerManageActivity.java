package com.car.activity;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import android.view.InputEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.car.R;
import com.car.adapter.SellerManageAdapter;
import com.car.adapter.ShowGoodsAdapter;
import com.car.adapter.ShowSellerManageAdapter;
import com.car.application.LocalApplication;
import com.car.entity.AddressEntity;
import com.car.entity.Order;
import com.car.util.ConstantsUtil;
import com.car.util.DateTimePickDialogUtil;
import com.car.view.ToastMaker;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 销售管理分析管理
 * @author shiran 
 */
public class SellerManageActivity extends BaseActivity{
	
	@ViewInject(R.id.car_address_goback)
    private  TextView car_address_goback;
	@ViewInject(R.id.inputDate)
	private EditText inputDate;
	
	@ViewInject(R.id.goods_name)
	private EditText  goodsName;

	
	@ViewInject(R.id.person_order_manage_listview)
	private ListView person_order_manage_listview;
	
	@ViewInject(R.id.submit_order_manage)
	private Button  submit_order_manage;
	
    private List  orderList = new ArrayList();	
	
    private ShowSellerManageAdapter sellAdapter = null;
    
	
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.car_order_seller_manage;
	}

	@Override
	protected void initParams() {
        //进行时间插件的格式	
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyy年MM月");
		String dateTimenow = sdf.format(new Date());
		inputDate.setText(dateTimenow+"-"+dateTimenow);
		
		//数据展示
		showAllOrderInfo();
	}
	
	@OnClick({R.id.inputDate, R.id.submit_order_manage, R.id.car_address_goback})
	public void Onclick(View v) {
		switch (v.getId()) {
		case R.id.inputDate:
			DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(SellerManageActivity.this, inputDate.getText().toString());
			dateTimePicKDialog.dateTimePicKDialog(inputDate);
			break;
		case R.id.submit_order_manage:
			//添加查询
			showAllOrderInfo();
			break;
		case R.id.car_address_goback: //返回
			finish();
			break;
		default:
			break;
		}
	}

    /**
     * 初始化查询所有的订单信息
     */
	private void showAllOrderInfo() {
		String inputDateContent = inputDate.getText().toString();  //这个是购买时间 
		String goodsNameContent = goodsName.getText().toString(); //得到的是商品的名称或者购买用户名
		//ToastMaker.showShortToast(inputDateContent + "=======" + goodsNameContent);
		
		//ToastMaker.showShortToast("laile" + inputDateContent + "------------" + goodsNameContent);
		LocalApplication.getInstance().httpUtils.send(HttpMethod.GET, ConstantsUtil.SERVER_URL + "order.do?method=getAllManageOrder&time=" + inputDateContent + "&goodNameOrUser=" + goodsNameContent, new RequestCallBack<String>() {

			//success   
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String orderListStr = JSONObject.parseObject(
						responseInfo.result).getString("data");
				orderList = JSONObject
						.parseArray(orderListStr, Order.class);
				sellAdapter =  new  ShowSellerManageAdapter(SellerManageActivity.this, orderList, person_order_manage_listview);
			    //进行数据的适配
				person_order_manage_listview.setAdapter(sellAdapter);
			}

			//failure
			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				ToastMaker.showShortToast("数据下载失败，请重试！");
			}
		
		});
		
	}
	
	
}
