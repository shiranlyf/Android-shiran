package com.car.fragment;

import java.util.List;
import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.car.R;
import com.car.adapter.FindFilterViewPagerAdapter;
import com.car.util.JListKit;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 找车-品牌找车
 * 
 * @author blue
 */
public class FindFilterFragment extends BaseFragment
{
	// 已选条件栏
	@ViewInject(R.id.find_filter_hscv_choosed)
	HorizontalScrollView find_filter_hscv_choosed;
	@ViewInject(R.id.find_filter_llyt_choosed)
	LinearLayout find_filter_llyt_choosed;
	// 价格
	@ViewInject(R.id.find_filter_tv_price)
	TextView find_filter_tv_price;
	// 级别
	@ViewInject(R.id.find_filter_tv_level)
	TextView find_filter_tv_level;
	// 排量
	@ViewInject(R.id.find_filter_tv_pai)
	TextView find_filter_tv_pai;
	// 变速箱
	@ViewInject(R.id.find_filter_tv_bian)
	TextView find_filter_tv_bian;
	// 游标
	@ViewInject(R.id.find_filter_iv_cursor)
	ImageView find_filter_iv_cursor;
	// 筛选条件pager
	@ViewInject(R.id.find_filter_vp)
	ViewPager find_filter_vp;

	// 价格选项
	private Button fragment_find_price_btn_price1;
	private Button fragment_find_price_btn_price2;
	private Button fragment_find_price_btn_price3;

	// 级别选项
	private Button fragment_find_level_btn_level1;
	private Button fragment_find_level_btn_level2;
	private Button fragment_find_level_btn_level3;

	// 排量选项
	private Button fragment_find_pai_btn_pai1;
	private Button fragment_find_pai_btn_pai2;
	private Button fragment_find_pai_btn_pai3;

	// 变速箱选项
	private Button fragment_find_bian_btn_bian1;
	private Button fragment_find_bian_btn_bian2;
	private Button fragment_find_bian_btn_bian3;

	// viewpager数据源
	private List<View> viewList = JListKit.newArrayList();
	// 当前选中项
	private int currIndex = 0;
	// 图片居中位移
	private int offset;
	// 游标图片宽度
	private int bmpW;

	// 价格是否已经筛选
	private boolean priceAdd = false;
	// 级别是否已经筛选
	private boolean levelAdd = false;
	// 排量是否已经筛选
	private boolean paiAdd = false;
	// 变速箱是否已经筛选
	private boolean bianAdd = false;

	@Override
	protected int getLayoutId()
	{
		return R.layout.fragment_find_filter_main;
	}

	@Override
	protected void initParams()
	{
		// 游标初始化
		initCursor();
		// 页卡初始化
		initViewPager();
	}

	// 控件点击事件
	@OnClick({ R.id.find_filter_tv_price, R.id.find_filter_tv_level, R.id.find_filter_tv_pai, R.id.find_filter_tv_bian })
	public void viewOnClick(View view)
	{
		switch (view.getId()) {
		case R.id.find_filter_tv_price:

			find_filter_vp.setCurrentItem(0);

			break;
		case R.id.find_filter_tv_level:

			find_filter_vp.setCurrentItem(1);

			break;
		case R.id.find_filter_tv_pai:

			find_filter_vp.setCurrentItem(2);

			break;
		case R.id.find_filter_tv_bian:

			find_filter_vp.setCurrentItem(3);

			break;

		default:
			break;
		}
	}

	// 初始化游标
	private void initCursor()
	{
		// 得到游标图片的宽度
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.fragment_find_filter_ic_select).getWidth();
		// 得到屏幕的宽度
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		// 计算图片居中需要的位移
		offset = (screenW / 4 - bmpW) / 2;
		// 设置初始化位置
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		find_filter_iv_cursor.setImageMatrix(matrix);
	}

	// 初始化viewpager
	@SuppressLint("InflateParams")
	private void initViewPager()
	{
		// 切换的四个界面初始化
		LinearLayout price_layout = (LinearLayout) getLayoutInflater(null).inflate(R.layout.fragment_find_filter_price_main, null);
		LinearLayout level_layout = (LinearLayout) getLayoutInflater(null).inflate(R.layout.fragment_find_filter_level_main, null);
		LinearLayout pai_layout = (LinearLayout) getLayoutInflater(null).inflate(R.layout.fragment_find_filter_pai_main, null);
		LinearLayout bian_layout = (LinearLayout) getLayoutInflater(null).inflate(R.layout.fragment_find_filter_bian_main, null);

		// 初始化价格布局
		initPriceLayout(price_layout);
		// 初始化级别布局
		initLevelLayout(level_layout);
		// 初始化排量布局
		initPaiLayout(pai_layout);
		// 初始化变速箱布局
		initBianLayout(bian_layout);

		// 将四个界面的视图添加到viewpager的数据源中
		viewList.add(price_layout);
		viewList.add(level_layout);
		viewList.add(pai_layout);
		viewList.add(bian_layout);

		// viewpager绑定适配器
		find_filter_vp.setAdapter(new FindFilterViewPagerAdapter(viewList));
		// viewpager初始选中第一视图
		find_filter_vp.setCurrentItem(currIndex);
		// viewpager绑定切换监听器
		find_filter_vp.setOnPageChangeListener(new DefineOnPageChangeListener());
	}

	// 初始化价格布局
	private void initPriceLayout(LinearLayout layout)
	{
		fragment_find_price_btn_price1 = (Button) layout.findViewById(R.id.fragment_find_price_btn_price1);
		fragment_find_price_btn_price2 = (Button) layout.findViewById(R.id.fragment_find_price_btn_price2);
		fragment_find_price_btn_price3 = (Button) layout.findViewById(R.id.fragment_find_price_btn_price3);

		fragment_find_price_btn_price1.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (!priceAdd)
				{
					priceAdd = true;
					addChoosedItem("price", fragment_find_price_btn_price1.getText());
				} else
				{
					changeChoosedItem("price", fragment_find_price_btn_price1.getText());
				}
				fragment_find_price_btn_price1.setEnabled(false);
				fragment_find_price_btn_price2.setEnabled(true);
				fragment_find_price_btn_price3.setEnabled(true);
			}
		});
		fragment_find_price_btn_price2.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (!priceAdd)
				{
					priceAdd = true;
					addChoosedItem("price", fragment_find_price_btn_price2.getText());
				} else
				{
					changeChoosedItem("price", fragment_find_price_btn_price2.getText());
				}
				fragment_find_price_btn_price1.setEnabled(true);
				fragment_find_price_btn_price2.setEnabled(false);
				fragment_find_price_btn_price3.setEnabled(true);
			}
		});
		fragment_find_price_btn_price3.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (!priceAdd)
				{
					priceAdd = true;
					addChoosedItem("price", fragment_find_price_btn_price3.getText());
				} else
				{
					changeChoosedItem("price", fragment_find_price_btn_price3.getText());
				}
				fragment_find_price_btn_price1.setEnabled(true);
				fragment_find_price_btn_price2.setEnabled(true);
				fragment_find_price_btn_price3.setEnabled(false);
			}
		});
	}

	// 初始化级别布局
	private void initLevelLayout(LinearLayout layout)
	{
		fragment_find_level_btn_level1 = (Button) layout.findViewById(R.id.fragment_find_level_btn_level1);
		fragment_find_level_btn_level2 = (Button) layout.findViewById(R.id.fragment_find_level_btn_level2);
		fragment_find_level_btn_level3 = (Button) layout.findViewById(R.id.fragment_find_level_btn_level3);

		fragment_find_level_btn_level1.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (!levelAdd)
				{
					levelAdd = true;
					addChoosedItem("level", fragment_find_level_btn_level1.getText());
				} else
				{
					changeChoosedItem("level", fragment_find_level_btn_level1.getText());
				}
				fragment_find_level_btn_level1.setEnabled(false);
				fragment_find_level_btn_level2.setEnabled(true);
				fragment_find_level_btn_level3.setEnabled(true);
			}
		});
		fragment_find_level_btn_level2.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (!levelAdd)
				{
					levelAdd = true;
					addChoosedItem("level", fragment_find_level_btn_level2.getText());
				} else
				{
					changeChoosedItem("level", fragment_find_level_btn_level2.getText());
				}
				fragment_find_level_btn_level1.setEnabled(true);
				fragment_find_level_btn_level2.setEnabled(false);
				fragment_find_level_btn_level3.setEnabled(true);
			}
		});
		fragment_find_level_btn_level3.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (!levelAdd)
				{
					levelAdd = true;
					addChoosedItem("level", fragment_find_level_btn_level3.getText());
				} else
				{
					changeChoosedItem("level", fragment_find_level_btn_level3.getText());
				}
				fragment_find_level_btn_level1.setEnabled(true);
				fragment_find_level_btn_level2.setEnabled(true);
				fragment_find_level_btn_level3.setEnabled(false);
			}
		});
	}

	// 初始化排量布局
	private void initPaiLayout(LinearLayout layout)
	{
		fragment_find_pai_btn_pai1 = (Button) layout.findViewById(R.id.fragment_find_pai_btn_pai1);
		fragment_find_pai_btn_pai2 = (Button) layout.findViewById(R.id.fragment_find_pai_btn_pai2);
		fragment_find_pai_btn_pai3 = (Button) layout.findViewById(R.id.fragment_find_pai_btn_pai3);

		fragment_find_pai_btn_pai1.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (!paiAdd)
				{
					paiAdd = true;
					addChoosedItem("pai", fragment_find_pai_btn_pai1.getText());
				} else
				{
					changeChoosedItem("pai", fragment_find_pai_btn_pai1.getText());
				}
				fragment_find_pai_btn_pai1.setEnabled(false);
				fragment_find_pai_btn_pai2.setEnabled(true);
				fragment_find_pai_btn_pai3.setEnabled(true);
			}
		});
		fragment_find_pai_btn_pai2.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (!paiAdd)
				{
					paiAdd = true;
					addChoosedItem("pai", fragment_find_pai_btn_pai2.getText());
				} else
				{
					changeChoosedItem("pai", fragment_find_pai_btn_pai2.getText());
				}
				fragment_find_pai_btn_pai1.setEnabled(true);
				fragment_find_pai_btn_pai2.setEnabled(false);
				fragment_find_pai_btn_pai3.setEnabled(true);
			}
		});
		fragment_find_pai_btn_pai3.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (!paiAdd)
				{
					paiAdd = true;
					addChoosedItem("pai", fragment_find_pai_btn_pai3.getText());
				} else
				{
					changeChoosedItem("pai", fragment_find_pai_btn_pai3.getText());
				}
				fragment_find_pai_btn_pai1.setEnabled(true);
				fragment_find_pai_btn_pai2.setEnabled(true);
				fragment_find_pai_btn_pai3.setEnabled(false);
			}
		});
	}

	// 初始化变速箱布局
	private void initBianLayout(LinearLayout layout)
	{
		fragment_find_bian_btn_bian1 = (Button) layout.findViewById(R.id.fragment_find_bian_btn_bian1);
		fragment_find_bian_btn_bian2 = (Button) layout.findViewById(R.id.fragment_find_bian_btn_bian2);
		fragment_find_bian_btn_bian3 = (Button) layout.findViewById(R.id.fragment_find_bian_btn_bian3);

		fragment_find_bian_btn_bian1.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (!bianAdd)
				{
					bianAdd = true;
					addChoosedItem("bian", fragment_find_bian_btn_bian1.getText());
				} else
				{
					changeChoosedItem("bian", fragment_find_bian_btn_bian1.getText());
				}
				fragment_find_bian_btn_bian1.setEnabled(false);
				fragment_find_bian_btn_bian2.setEnabled(true);
				fragment_find_bian_btn_bian3.setEnabled(true);
			}
		});
		fragment_find_bian_btn_bian2.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (!bianAdd)
				{
					bianAdd = true;
					addChoosedItem("bian", fragment_find_bian_btn_bian2.getText());
				} else
				{
					changeChoosedItem("bian", fragment_find_bian_btn_bian2.getText());
				}
				fragment_find_bian_btn_bian1.setEnabled(true);
				fragment_find_bian_btn_bian2.setEnabled(false);
				fragment_find_bian_btn_bian3.setEnabled(true);
			}
		});
		fragment_find_bian_btn_bian3.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (!bianAdd)
				{
					bianAdd = true;
					addChoosedItem("bian", fragment_find_bian_btn_bian3.getText());
				} else
				{
					changeChoosedItem("bian", fragment_find_bian_btn_bian3.getText());
				}
				fragment_find_bian_btn_bian1.setEnabled(true);
				fragment_find_bian_btn_bian2.setEnabled(true);
				fragment_find_bian_btn_bian3.setEnabled(false);
			}
		});
	}

	// viewpager视图切换监听器
	public class DefineOnPageChangeListener implements OnPageChangeListener
	{
		// 游标移动一个单位长度
		int one = offset * 2 + bmpW;
		// 两个单位长度
		int two = one * 2;
		// 三个单位长度
		int three = one * 3;

		@Override
		public void onPageScrollStateChanged(int arg0)
		{
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
		{
		}

		@Override
		public void onPageSelected(int arg0)
		{
			Animation animation = null;
			switch (arg0) {
			// 价格
			case 0:

				// 如果当前在第二页卡
				if (currIndex == 1)
				{
					// 初始化切换页卡动画
					animation = new TranslateAnimation(one, 0, 0, 0);
				}
				// 如果当前在第三页卡
				else if (currIndex == 2)
				{
					// 初始化切换页卡动画
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
				// 如果当前在第四页卡
				else if (currIndex == 3)
				{
					// 初始化切换页卡动画
					animation = new TranslateAnimation(three, 0, 0, 0);
				}

				break;
			// 级别
			case 1:

				// 如果当前在第一页卡
				if (currIndex == 0)
				{
					// 初始化切换页卡动画
					animation = new TranslateAnimation(0, one, 0, 0);
				}
				// 如果当前在第三页卡
				else if (currIndex == 2)
				{
					// 初始化切换页卡动画
					animation = new TranslateAnimation(two, one, 0, 0);
				}
				// 如果当前在第四页卡
				else if (currIndex == 3)
				{
					// 初始化切换页卡动画
					animation = new TranslateAnimation(three, one, 0, 0);
				}

				break;
			// 排量
			case 2:

				// 如果当前在第一页卡
				if (currIndex == 0)
				{
					// 初始化切换页卡动画
					animation = new TranslateAnimation(0, two, 0, 0);
				}
				// 如果当前在第二页卡
				else if (currIndex == 1)
				{
					// 初始化切换页卡动画
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				// 如果当前在第四页卡
				else if (currIndex == 3)
				{
					// 初始化切换页卡动画
					animation = new TranslateAnimation(three, two, 0, 0);
				}

				break;
			// 变速箱
			case 3:

				// 如果当前在第一页卡
				if (currIndex == 0)
				{
					// 初始化切换页卡动画
					animation = new TranslateAnimation(0, three, 0, 0);
				}
				// 如果当前在第二页卡
				else if (currIndex == 1)
				{
					// 初始化切换页卡动画
					animation = new TranslateAnimation(one, three, 0, 0);
				}
				// 如果当前在第三页卡
				else if (currIndex == 2)
				{
					// 初始化切换页卡动画
					animation = new TranslateAnimation(two, three, 0, 0);
				}

				break;
			}
			currIndex = arg0;
			// 切换页卡动画
			animation.setFillAfter(true);
			animation.setDuration(300);
			find_filter_iv_cursor.startAnimation(animation);
		}

	}

	// 新增筛选条件
	@SuppressLint("InflateParams")
	private void addChoosedItem(final String tag, CharSequence text)
	{
		final LinearLayout choosedItem = (LinearLayout) getLayoutInflater(null).inflate(R.layout.fragment_find_filter_add_main, null);
		LinearLayout fragment_find_filter_add_llyt = (LinearLayout) choosedItem.findViewById(R.id.fragment_find_filter_add_llyt);
		TextView fragment_find_filter_add_txt = (TextView) choosedItem.findViewById(R.id.fragment_find_filter_add_txt);
		fragment_find_filter_add_llyt.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 清空价格条件
				if (tag.equals("price"))
				{
					priceAdd = false;
					clearPrice();
				}
				// 清空级别条件
				else if (tag.equals("level"))
				{
					levelAdd = false;
					clearLevel();
				}
				// 清空排量条件
				else if (tag.equals("pai"))
				{
					paiAdd = false;
					clearPai();
				}
				// 清空变速箱条件
				else if (tag.equals("bian"))
				{
					bianAdd = false;
					clearBian();
				}
				find_filter_llyt_choosed.removeView(choosedItem);
			}
		});

		fragment_find_filter_add_txt.setText(text);
		choosedItem.setTag(tag);
		find_filter_llyt_choosed.addView(choosedItem);
		// 动态滚动已选条件栏
		find_filter_hscv_choosed.post(new Runnable()
		{

			@Override
			public void run()
			{
				find_filter_hscv_choosed.smoothScrollTo(find_filter_hscv_choosed.getWidth(), 0);
			}
		});
	}

	// 修改筛选条件
	private void changeChoosedItem(String tag, CharSequence text)
	{
		for (int i = 0; i < find_filter_llyt_choosed.getChildCount(); i++)
		{
			LinearLayout tmp = (LinearLayout) find_filter_llyt_choosed.getChildAt(i);
			String tmpTag = (String) tmp.getTag();
			if (tag.equals(tmpTag))
			{
				find_filter_llyt_choosed.removeView(tmp);
				addChoosedItem(tag, text);
				break;
			}
		}
	}

	// 清空价格条件
	private void clearPrice()
	{
		fragment_find_price_btn_price1.setEnabled(true);
		fragment_find_price_btn_price2.setEnabled(true);
		fragment_find_price_btn_price3.setEnabled(true);
	}

	// 清空级别条件
	private void clearLevel()
	{
		fragment_find_level_btn_level1.setEnabled(true);
		fragment_find_level_btn_level2.setEnabled(true);
		fragment_find_level_btn_level3.setEnabled(true);
	}

	// 清空排量条件
	private void clearPai()
	{
		fragment_find_pai_btn_pai1.setEnabled(true);
		fragment_find_pai_btn_pai2.setEnabled(true);
		fragment_find_pai_btn_pai3.setEnabled(true);
	}

	// 清空变速箱条件
	private void clearBian()
	{
		fragment_find_bian_btn_bian1.setEnabled(true);
		fragment_find_bian_btn_bian2.setEnabled(true);
		fragment_find_bian_btn_bian3.setEnabled(true);
	}

}
