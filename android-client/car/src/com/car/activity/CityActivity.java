package com.car.activity;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.R.string;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.car.R;
import com.car.adapter.CityAdapter;
import com.car.entity.City;
import com.car.entity.ResponseObject;
import com.car.util.ConstantsUtil;
import com.car.view.SiderBar;
import com.car.view.SiderBar.OnTouchingLetterChangedListener;
import com.car.view.ToastMaker;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.google.gson.reflect.TypeToken;

/**
 * 获取所有城市的activity
 * 
 * @author shiran
 * 
 */
public class CityActivity extends BaseActivity implements
		OnTouchingLetterChangedListener {

	@ViewInject(R.id.city_list)
	private ListView listdatas;

	private List<City> citylist;

	@ViewInject(R.id.city_side_bar)
	private SiderBar siderBar;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.address_city_list;
	}

	@Override
	protected void initParams() {
		// 给列表添加一个长在搜索的头
		View view = LayoutInflater.from(this).inflate(
				R.layout.address_city_search, null);
		listdatas.addHeaderView(view);

		// 执行异步任务
		new CityDataTask().execute();

		// 给siderBar设置监听 给自定义view siderbar设置touch监听
		siderBar.setOnTouchingLetterChangedListener(this);
	}

	/**
	 * 点击事件
	 * 
	 * @param v
	 */
	@OnClick({ R.id.index_city_back, R.id.index_city_flushcity })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.index_city_back: // 返回
			finish();
			break;
		case R.id.index_city_flushcity: // 刷新
			// 开启创建的异步任务
			new CityDataTask().execute();
			break;
		default:
			break;
		}
	}

	/**
	 * citylist中item的点击事件
	 * 
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */
	@OnItemClick({ R.id.city_list })
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent();
		// 注意要view显示城市名称对应的item 所以这里要用view(我的理解是里面包含了两个listView所以要进行筛选)
		TextView textView = (TextView) view
				.findViewById(R.id.city_list_item_name);
		intent.putExtra("cityName", textView.getText().toString());
		setResult(RESULT_OK, intent);
		finish();
	}

	/**
	 * 使用异步任务获取json字符串 Params 表示传值 第三个参数指的是返回类型
	 */
	public class CityDataTask extends AsyncTask<Void, Void, List<City>> {

		@Override
		protected List<City> doInBackground(Void... params) {
			// 得到http客户端
			HttpClient client = new DefaultHttpClient();
			// 获取请求对象 封装了url
			HttpPost httpPost = new HttpPost(ConstantsUtil.SERVER_URL
					+ "city.do?method=getAllCity");
			// 执行请求
			try {
				HttpResponse httpResponse = client.execute(httpPost);
				// 返回200表示正常返回
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					String cityJsonString = EntityUtils.toString(httpResponse
							.getEntity());
					return parseCityDatasJson(cityJsonString);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 进行提交
			return null;
		}

		// doInBackground中进行提交的方法
		@Override
		protected void onPostExecute(List<City> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// 将获取的result集合给citylist进行赋值
			citylist = result;
			// 适配显示
			CityAdapter cityAdapter = new CityAdapter(getBaseContext(),
					citylist);
			listdatas.setAdapter(cityAdapter);
		}

	}

	/**
	 * 解析城市数据的json
	 * 
	 * @param json
	 * @return
	 */
	private List<City> parseCityDatasJson(String json) {
		Gson gson = new Gson();
		// 根据自己的格式将json转换
		ResponseObject<List<City>> responseObject = gson.fromJson(json,
				new TypeToken<ResponseObject<List<City>>>() {
				}.getType());
		// 返回list集合
		return responseObject.getDatas();
	}

	/** OnTouchingLetterChangedListener 在siderbar中方法 **/
	@Override
	public void onTouchingLetterChangedListener(String s) {
		 listdatas.setSelection(findIndex(citylist, s));
	}

	/** OnTouchingLetterChangedListener 在siderbar中方法 **/

	/**
	 * 根据s找到s的位置
	 * 
	 * @param s
	 * @return
	 */
	public int findIndex(List<City> list, String s) {
		if (list != null) {
            for (int i = 0; i < list.size(); i++) {
				City city = list.get(i);
				//根据city中的sortKey进行比较
				if (s.equals(city.getCitySortkey())) {
					return i;
				}
			}
		} else {
			Toast.makeText(this, "暂无信息", Toast.LENGTH_SHORT).show();
		}
		return -1;
	}

}
