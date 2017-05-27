package com.car.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.car.R;
import com.car.util.DisplayUtil;

/**
 * 自定义字母表侧边栏
 * @author blue
 * 
 */
public class SideBar extends View
{
	// 字母表
	private char[] alphabet;
	// 列表
	private PinnedHeaderListView listView;
	private SectionIndexer sectionIndexer;
	// 提示对话框
	private TextView dialogText;

	
	/**
	 * 自定义控件时的三个方法
	 * @param context
	 */
	public SideBar(Context context)
	{
		super(context);
		init();
	}

	public SideBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	/**
	 * 初始化字母表
	 * 单个构造方法中都条用了这个方法
	 * 这里是绘制的内容
	 * @author blue
	 */
	private void init()
	{
		alphabet = new char[] { '#', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '*' };
	}

	public void setListView(PinnedHeaderListView listView)
	{
		this.listView = listView;
		//将列表强转成SectionIndexer接口  所有要是小SectionIndexer里面的接口
		sectionIndexer = (SectionIndexer) listView.getAdapter();
	}

	//将这个textview传到我们本地来  就是在中央的textview
	public void setTextView(TextView dialogText)
	{
		this.dialogText = dialogText;
	}

	/**
	 * 这就是触摸的时候对应的方法
	 */
	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent event)

	{
		super.onTouchEvent(event);

		// 得到当前触摸y值
		int i = (int) event.getY();
		// 计算当前触摸位置属于哪个字母
		int idx = i / (getMeasuredHeight() / alphabet.length);
		
		//解决触摸时 的索引异常
		if (idx >= alphabet.length)
		{
			idx = alphabet.length - 1;
		} else if (idx < 0)
		{
			idx = 0;
		}
		
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE)
		{
			// 触摸时设置字母表背景
			setBackgroundResource(R.drawable.fragment_find_brand_bg_sb);
			// 设置显示信息
			dialogText.setVisibility(View.VISIBLE);
			//设置显示的文字
			dialogText.setText(String.valueOf(alphabet[idx]));
			//设置显示的大小
			dialogText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 34);

			// 列表选中触摸字母对应项
			if (alphabet[idx] == '#')
			{
				listView.setSelection(0);
			} else
			{
				//根据字母得到它的位置  得到的是字母首次出现的位置
				int position = sectionIndexer.getPositionForSection(alphabet[idx]);
				if (position == -1)
				{
					return true;
				}
				//葛军position进行选中
				listView.setSelection(position);
			}
		} else  
		{   
			// 松手的时候  延迟1秒隐藏
			dialogText.postDelayed(new Runnable()
			{

				@Override
				public void run()
				{
					dialogText.setVisibility(View.INVISIBLE);
				}
			}, 1 * 1000);

		}
		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			// 松开时设置字母表背景
			setBackgroundColor(getResources().getColor(android.R.color.transparent));
		}
		return true;
	}

	/**
	 * 话view
	 */
	@SuppressLint("DrawAllocation")
	protected void onDraw(Canvas canvas)
	{
		//这是就完成画笔的初始化
		Paint paint = new Paint();
		// 画笔颜色     
		paint.setColor(getResources().getColor(R.color.sidebar_cl));
		// 绘制文字尺寸
		paint.setTextSize(DisplayUtil.sp2px(getContext(), 15));
		// 绘制位置居中
		paint.setTextAlign(Paint.Align.CENTER);
		// 绘制风格：粗体
		Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
		paint.setTypeface(font);

		
		
		
		float widthCenter = getMeasuredWidth() / 2;
		if (alphabet.length > 0)
		{
			//每一个字母对应的高度
			float height = getMeasuredHeight() / alphabet.length;
			//for循环字母表   绘制每一个字母
			for (int i = 0; i < alphabet.length; i++)
			{
				canvas.drawText(String.valueOf(alphabet[i]), widthCenter, (i + 1) * height, paint);
			}
		}
		this.invalidate();
		super.onDraw(canvas);
	}
}
