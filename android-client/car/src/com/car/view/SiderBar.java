package com.car.view;

import com.amap.api.mapcore.util.ca;
import com.car.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 城市列表侧边栏
 * @author shiran
 * 绘制对应的英文字母
 */
public class SiderBar extends  View{

	
	//xml文件创建控件对象时调用
	public SiderBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	//new对象的时候调用
	public SiderBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	private Paint  paint = new Paint(); //创建一个画笔
	/**
	 * 绘制26个字母
	 */
	public static String[] 
			        sideBar={"热门","A","B","C","D","E","F","J","H","I",
		                          "J","K","L","M","N","O","P","Q","R","S","T",
		                          "U","V","W","X","Y","Z"};
	//定义选中位置
	private int choose ;
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		//设置画笔为灰色
		paint.setColor(Color.GRAY);
		//设置字体为粗体
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		paint.setTextSize(28);
		//获取自定义view的宽  高
		int  height = getHeight();
		int width = getWidth();
		//设置每个字母所在控件的高度
		int each_height = height/sideBar.length;
		for (int i = 0; i < sideBar.length; i++) {
			//位子内容的偏移量
			float x = width/2 - paint.measureText(sideBar[i])/2;
			//中轴线对半划分
			float y = (i + 1)*each_height;
			canvas.drawText(sideBar[i], x, y, paint);
		}
	}
	
	private OnTouchingLetterChangedListener letterChangedListener;
	
	/**
	 * 配置监听
	 */
	public interface OnTouchingLetterChangedListener{
		public void onTouchingLetterChangedListener(String s);
	}

	/**
	 * 设置监方法
	 * @param shiran
	 */
	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener){
		this.letterChangedListener = onTouchingLetterChangedListener;
	}
	
	/**
	 * 事件的分发
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int  action = event.getAction();
		final float y  = event.getY();  //点击y坐标
		final  OnTouchingLetterChangedListener listener = letterChangedListener;
		final int c = (int)(y/getHeight()*sideBar.length);
		switch (action) {
		case MotionEvent.ACTION_UP: //起
			setBackgroundResource(android.R.color.transparent);
			//应用生效
			invalidate();
			break;

		default:
			setBackgroundResource(R.drawable.sidebar_background);
			if (c > 0 && c < sideBar.length) {
				if (listener != null) {
					listener.onTouchingLetterChangedListener(sideBar[c]);
				}
				choose = c;
				invalidate();
			}
			break;
		}
		
		return true;
	}
}
