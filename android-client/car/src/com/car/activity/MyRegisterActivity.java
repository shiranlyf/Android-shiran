package com.car.activity;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.alibaba.fastjson.JSONObject;
import com.car.R;
import com.car.application.LocalApplication;
import com.car.util.ConstantsUtil;
import com.car.util.StringUtil;
import com.car.view.ToastMaker;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.R.string;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * 会员注册的activity
 */
public class MyRegisterActivity extends BaseActivity implements OnClickListener {

	private Button checkPassBtn, registerBtn;
	private ImageView backImage;
	private CountTimer countTimer;
	private EditText password;
	private EditText phone, phoneRandom;
	// 监听事件的对象
	private EventHandler eh;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_self_register;
	}

	@Override
	protected void initParams() {
		checkPassBtn = (Button) findViewById(R.id.register_get_check_pass);
		registerBtn = (Button) findViewById(R.id.register_btn);
		backImage = (ImageView) findViewById(R.id.register_back);
		// 对倒计时对象进行初始化
		countTimer = new CountTimer(60000, 1000);
		phone = (EditText) findViewById(R.id.register_phone);
		phoneRandom = (EditText) findViewById(R.id.register_check_upass);
		password = (EditText) findViewById(R.id.register_upass);
		checkPassBtn.setOnClickListener(this);
		backImage.setOnClickListener(this);
		registerBtn.setOnClickListener(this);
		// 初始化短信验证
		SMSSDK.initSDK(this, "11a437bf68f0c",
				"7f60823138d95d5996c1a20914488714");
	}

	// 接触注册的短信回调
	@Override
	protected synchronized void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		SMSSDK.unregisterEventHandler(eh);// 接触注册注册的短信回调
	}

	// 定义一个范松短信验证码的方法(完成SMS的初始化与回调)
	public void sendSMSRandom() {
//		// 初始化短信验证
//		SMSSDK.initSDK(this, "11a437bf68f0c",
//				"7f60823138d95d5996c1a20914488714");
		// 监听事件
		eh = new EventHandler() {
			@Override
			public void afterEvent(int event, int result, Object data) {
				if (result == SMSSDK.RESULT_COMPLETE) {
					// 回调完成
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
						Log.i("sunjob", "回调完成");
						// 提交验证码成功 
						 registerUser() ;//进行注册

					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {// 短信校验成功的话就会调用这个方法
						Log.i("sunjob", "提交验证码成功");
						// 获取验证码成功
					} else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
						// 返回支持发送验证码的国家列表
					}
				} else {
					((Throwable) data).printStackTrace();
				}
			}
		};
		SMSSDK.registerEventHandler(eh); // 注册短信回调
		String phoneName = phone.getText().toString();
		// 请求获取短信验证码 在监听中返回
		SMSSDK.getVerificationCode("86", phoneName.toString());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register_get_check_pass:// 点击了获取验证码
			// 首先判断手机号不能为空
			String phoneText = phone.getText().toString();
			if (StringUtil.isNull(phoneText)) {
				ToastMaker.showLongToast("填写的手机号不能为空");
			} else {
				// 开始倒计时 开启倒计时 里面封装的是一个线程
				countTimer.start();
				// 发送验证码
				sendSMSRandom();
			}

			break;
		case R.id.register_back: // 点击了返回按钮
			finish();
			break;
		case R.id.register_btn: // 点击了提交按钮
			String phoneNumber = phone.getText().toString();
			String phoneRandomContent = phoneRandom.getText().toString();
			String passwordContent = password.getText().toString();
			if(StringUtil.isNull(phoneNumber)||StringUtil.isNull(phoneRandomContent)||StringUtil.isNull(passwordContent)){
				ToastMaker.showShortToast("填写信息不能为空");
			}else {
				// 去服务器验证输入的验证码 国家id中国为86 电话号码
				//Log.i("sunjob", phoneNumber+"--"+phoneRandomContent);
				SMSSDK.submitVerificationCode("86", phoneNumber,phoneRandomContent);
				Log.i("sunjob", "sys---9");
			}
			
			break;
		default:
			break;
		}
	}

	// 每个一分钟可点击验证码
	public class CountTimer extends CountDownTimer {
		/**
		 * @param millisInFuture
		 *            时间间隔是多长时间
		 * @param countDownInterval
		 *            回调onTick()方法 每个多久执行一次
		 */
		public CountTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		// 间隔时间结束的时候才会调用
		@Override
		public void onFinish() {
			// 更新页面的控件
			checkPassBtn.setText(R.string.register_get_check_num);
			checkPassBtn
					.setBackgroundResource(R.drawable.my_register_get_check_pass);
			checkPassBtn.setClickable(true);
		}

		// 间隔是后内执行的操作
		@Override
		public void onTick(long millisUtilFinished) {
			// 更新页面主键
			checkPassBtn.setText(millisUtilFinished / 1000 + "秒后发送");
			// 设置背景资源
			checkPassBtn.setBackgroundResource(R.drawable.btn_light_press);
			// 设置为不能点击
			checkPassBtn.setClickable(false);
		}

	}

	// 注册 
	public void registerUser() {
//		if (phone.getText().toString().trim().length() <= 0) {
//			phone.setError(Html.fromHtml("<font color=red>用户名不能为空！</font>"));
//			return;
//		}
//		if (password.getText().toString().trim().length() <= 0) {
//			password.setError(Html.fromHtml("<font color=red>密码不能为空！</font>"));
//			return;
//		}
		String  phoneName =phone.getText().toString();
		String  passwordName = password.getText().toString();
		Log.i("sunjob", phoneName+"---ok"+passwordName);
		// 向服务器发送信息
        LocalApplication.getInstance().httpUtils.send(HttpMethod.GET, ConstantsUtil.SERVER_URL+"user.do?method=userRegister&userPhone="+phoneName+"&password="+passwordName, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String stateStr = JSONObject.parseObject(responseInfo.result).getString("state");
				//消息提示   msg是对应的注册返回的
				String  msg  = JSONObject.parseObject(responseInfo.result).getString("msg");
				if (Integer.parseInt(stateStr) == 1) {
					//注册成功
					ToastMaker.showShortToast("注册成功");
					Intent  intent = new Intent(MyRegisterActivity.this, MyloginActivity.class);
					intent.putExtra("login_name", phone.getText().toString());
					startActivity(intent);
					//关闭本页
					finish();
				}else {
					ToastMaker.showShortToast(msg);
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				ToastMaker.showShortToast("注册失败，重新注册");
			}
		});
	}

}
