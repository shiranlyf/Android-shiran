package com.car.activity;

import java.util.HashMap;


import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import com.alibaba.fastjson.JSONObject;
import com.car.R;
import com.car.application.LocalApplication;
import com.car.util.ConstantsUtil;
import com.car.util.RandomCodeUtil;
import com.car.util.SharedUtils;
import com.car.util.StringUtil;
import com.car.view.ToastMaker;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Application;
import android.content.Intent;
import android.os.UserManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyloginActivity extends BaseActivity implements OnClickListener,
		PlatformActionListener {

	@ViewInject(R.id.login_check_random)
	private Button checkRandom;
	@ViewInject(R.id.login_btn)
	private Button loginBtn;
	@ViewInject(R.id.login_uname)
	private EditText loginName;
	@ViewInject(R.id.login_register)
	private TextView register;
	@ViewInject(R.id.login_check_num)
	private EditText login_check_num_value;
	@ViewInject(R.id.login_pass)
	private EditText loginPass;
	@ViewInject(R.id.login_by_qq)
	private TextView loginByQQ;
	@ViewInject(R.id.login_by_weixin)
	private TextView loginByWinxin;
	@ViewInject(R.id.login_back)
	private ImageButton login_back;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_self_login;
	}

	@Override
	protected void initParams() {
		// 第三方登录的使用的初始化
		ShareSDK.initSDK(this); // 初始化sharesdk
		checkRandom.setOnClickListener(this);
		loginBtn.setOnClickListener(this);
		register.setOnClickListener(this);
		login_back.setOnClickListener(this);
		loginByQQ.setOnClickListener(this);
		loginByWinxin.setOnClickListener(this);

		// 初始化验证码 对checkRandom进行内容的填充
		setRandomView(checkRandom);
	}

	/**
	 * 页面不存在的时候 就进行销毁
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ShareSDK.stopSDK();// 销毁sharesdk
	}

	// 设置随机验证码的方法
	public void setRandomView(TextView textView) {
		// 产生4个数的随机数
		textView.setText(RandomCodeUtil.getRandom(4));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_check_random:// 设置验证码
			setRandomView(checkRandom);
			break;
		case R.id.login_btn:// 点击登录
			String check_value = login_check_num_value.getText().toString();
			if (check_value == "" || check_value.length() == 0) {
				Toast.makeText(getApplicationContext(), "验证码不能为空",
						Toast.LENGTH_SHORT).show();
			} else if (!checkRandom.getText().toString()
					.equalsIgnoreCase(check_value)) {
				Toast.makeText(getBaseContext(), "验证码不正确", Toast.LENGTH_SHORT)
						.show();
			} else {
				// 首先进行验证码的验证
				handleLogin();
			}
			break;
		case R.id.login_register:// 点击注册
			startActivity(new  Intent(this, MyRegisterActivity.class));
			break;
		case R.id.login_by_qq: // 第三方QQ登录
			loginByQQ();
			break;
		case R.id.login_by_weixin: // 第三方微信登录
			loginByWinxin();
			break;
		case R.id.login_back: //返回按钮
			finish();
		default:
			break;
		}
	}

	/**
	 * 验证是否登录成功的方法
	 */
	public void handleLogin() {
		final String userName = loginName.getText().toString();
		String passWard = loginPass.getText().toString();
		RequestParams params = new RequestParams();
		// 传参数过去的情况
		params.addBodyParameter("userName", userName);
		params.addBodyParameter("passWord", passWard);
		LocalApplication.getInstance().httpUtils.send(HttpMethod.GET,
				ConstantsUtil.SERVER_URL + "user.do?method=checkUser&userName="
						+ userName + "&passWord=" + passWard, params,
				new RequestCallBack<String>() {
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String stateStr = JSONObject.parseObject(
								responseInfo.result).getString("state");
						int state = Integer.parseInt(stateStr);
						//ToastMaker.showShortToast(responseInfo.result);
						if (state == 1) {
							// 保存登录名称
							SharedUtils.putUserName(MyloginActivity.this,
									userName);
							loginSuccess(userName);
						} else if (state == 0) {
							ToastMaker.showShortToast("用户名或则密码不正确，请重新登录");
						}
					}

					// 登录失败的情况
					@Override
					public void onFailure(HttpException error, String msg) {
						ToastMaker.showShortToast("登录失败，请重新登录");
						// Toast.makeText(MyloginActivity.this, "登录失败，请重新登录",
						// Toast.LENGTH_SHORT).show();
					}
				});
	}

	/**
	 * 登录成功的方法
	 * 这里讲用户名保存起来   用户名唯一
	 */
	public void loginSuccess(String uname) {
		// String uname = loginName.getText().toString();
		Intent intent = new Intent(this, MainActivity.class);
		//保存用户名
		SharedUtils.putUserName(getApplicationContext() ,uname);
		
		//从保存中进行取出数据    这里用于测试
		//String   username = SharedUtils.getUserName(getApplicationContext());
		//ToastMaker.showShortToast("myUsername:" + username);

		// 传值
		intent.putExtra("login_name", uname);
		// 第一个参数对应的是响应
		setResult(RandomCodeUtil.RequestLoginCode, intent);
		finish();
	}

	// qq的三方登录
	public void loginByQQ() {
		// 1、得到QQ的登录平台
		Platform platform = ShareSDK.getPlatform(this, QQ.NAME);
		// 2、增加监听监听事件 (授权) 在本类实现
		platform.setPlatformActionListener(this);
		// 3、判断授权是否已经验证（是否正常登录）
		if (platform.isValid()) {
			// 获取三方的显示名称
			String uname = platform.getDb().getUserName();
			String pid = platform.getDb().getUserId(); //可以作为本平台登录密码
			Log.i("sunjob", "验证通过======" + uname);
			// 返回我们的登录平台
			loginSuccess(uname);
		} else {
			// 如果没有授权成功 呈现空对象
			platform.showUser(null);
		}
	}

	// 微信的三方登录
	public void loginByWinxin() {
		// 1、得到QQ的登录平台
		Platform platform = ShareSDK.getPlatform(this, Wechat.NAME);
		// 2、增加监听监听事件 (授权) 在本类实现
		platform.setPlatformActionListener(this);
		// 3、判断授权是否已经验证（是否正常登录）
		if (platform.isValid()) {
			// 获取三方的显示名称
			String uname = platform.getDb().getUserName();
			Log.i("sunjob", "验证通过======" + uname);
			// 返回我们的登录平台
			loginSuccess(uname);
		} else {
			// 如果没有授权成功 呈现空对象
			platform.showUser(null);
		}
	}

	/********************** PlatformActionListener接口中的方法 *****************************/
	@Override
	public void onCancel(Platform arg0, int arg1) {
		// arg0.getName()得到平台名称
		ToastMaker.showLongToast(arg0.getName() + "授权已取消");
	}

	@Override
	public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
		// 获得第三方平台显示的名称
		String uname = arg0.getDb().getUserName();
		System.out.println(uname + "授权已成功");
		// 返回我的页面
		loginSuccess(uname);
	}

	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2) {

		ToastMaker.showLongToast(arg0.getName() + "授权已失败，请重试");
	}

	/********************** PlatformActionListener接口中的方法 *****************************/

}
