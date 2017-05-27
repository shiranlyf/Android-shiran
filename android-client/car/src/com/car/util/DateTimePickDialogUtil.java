package com.car.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.car.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

/**
 * 

 * @ClassName:     DateTimePickDialogUtil

 * @Description:TODO(DateTimePickDialogUtil)

 * @author:    FLX

 * @date:        2015-4-2 下午5:26:12
 */
public class DateTimePickDialogUtil extends Activity implements OnDateChangedListener,OnTimeChangedListener {
		
	private DatePicker datePickerstart,datePickerend;
	private AlertDialog ad;
	private String dateTime;
	private String initDateTime;
	private Activity activity;

	public DateTimePickDialogUtil(Activity activity, String initDateTime) {
		this.activity = activity;
		this.initDateTime = initDateTime;

	}

	public void init(DatePicker datePickerstart,DatePicker datePickerstartend) {
		
		
		//截取开始时间和结束时间
		String[] tiemString = new String[2];
		tiemString = initDateTime.split("-");
		
		//设置允许选择的最大时间为今天
		datePickerstart.setMaxDate(new Date().getTime());
		datePickerstartend.setMaxDate(new Date().getTime());
		
		//TODO 设置允许选择的最小时间
		
		if(tiemString.length==2){
			Calendar startcalendar = Calendar.getInstance();
			startcalendar= this.getCalendarByInintData(tiemString).get(0);
			datePickerstart.init(startcalendar.get(Calendar.YEAR),startcalendar.get(Calendar.MONTH),0, this);
			
			Calendar endcalendar = Calendar.getInstance();
			endcalendar= this.getCalendarByInintData(tiemString).get(1);
			datePickerstartend.init(endcalendar.get(Calendar.YEAR),endcalendar.get(Calendar.MONTH),0, this);
		}
	}

	/**
	 * 
	
	 * @Title: dateTimePicKDialog
	
	 * @Description: TODO(弹出日期选择框)
	
	 * @param: @param inputDate
	 * @param: @return   
	
	 * @return: AlertDialog   
	
	 * @throws
	 */
	public AlertDialog dateTimePicKDialog(final EditText inputDate) {
		LinearLayout dateTimeLayout = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.common_datetime, null);
				
		datePickerstart = (DatePicker) dateTimeLayout.findViewById(R.id.datepickerstart);
		datePickerend= (DatePicker) dateTimeLayout.findViewById(R.id.datepickerend);
		
		/**
		 * 让DatePicker只显示年月
		 */
		((LinearLayout) ((ViewGroup)datePickerstart.getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
		((LinearLayout) ((ViewGroup)datePickerend.getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
		
		//进行初始化
		init(datePickerstart,datePickerend);

		ad = new AlertDialog.Builder(activity)
				.setTitle(initDateTime)
				.setView(dateTimeLayout)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
						Calendar calendarstart =getCalendar(datePickerstart,datePickerend).get(0);
						Calendar calendarend = getCalendar(datePickerstart,datePickerend).get(1);
						
						//判断时间是否合法
						if(compare_date(calendarstart.getTime(),calendarend.getTime())==1){
							
							Toast.makeText(activity, "开始时间不能大于结束时间", Toast.LENGTH_SHORT).show();
							setAlertDialogcanel(false);
							
						}else{
							inputDate.setText(dateTime);
							setAlertDialogcanel(true);
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				}).show();

		onDateChanged(null, 0, 0, 0);
		return ad;
	}

	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		onDateChanged(null, 0, 0, 0);
	}

	/**
	 * 
	
	 * <p>Title: onDateChanged 监听日期变化</p>
	
	 * <p>Description: </p>
	
	 * @param view
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @see android.widget.DatePicker.OnDateChangedListener#onDateChanged(android.widget.DatePicker, int, int, int)
	 */
	public void onDateChanged(DatePicker view, int year, int monthOfYear, 
			int dayOfMonth) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");

		String dateTimestart = sdf.format(getCalendar(datePickerstart,datePickerend).get(0).getTime());
		String dataTimeend = sdf.format(getCalendar(datePickerstart,datePickerend).get(1).getTime());
		
		dateTime = dateTimestart+"-"+dataTimeend;
		
		ad.setTitle(dateTime);
	}

	/**
	 * 
	
	 * @Title: setAlertDialogcanel
	
	 * @Description: TODO(设置Dialog确认按钮可控)
	
	 * @param: @param canCancel   
	
	 * @return: void   
	
	 * @throws
	 */
	private void setAlertDialogcanel( boolean canCancel){
		Field field;
		try {
		field = ad.getClass().getSuperclass()
		.getDeclaredField("mShowing");
		field.setAccessible(true);
		// 设置mShowing值，欺骗android系统
		field.set(ad, canCancel);//如果为true则会推出
		} catch (NoSuchFieldException e) {
		e.printStackTrace();
		} catch (IllegalArgumentException e) {
		e.printStackTrace();
		} catch (IllegalAccessException e) {
		e.printStackTrace();
		}
	}
	
	/**
	 * 
	
	 * @Title: getCalendar
	
	 * @Description: TODO(通过当前DatePicker返回两个Calendar实例)
	
	 * @param: @param datePickerstart
	 * @param: @param datePickerend
	 * @param: @return   
	
	 * @return: List<Calendar>   
	
	 * @throws
	 */
	private List<Calendar> getCalendar(DatePicker datePickerstart,DatePicker datePickerend){
		
		List<Calendar> calendarlist = new ArrayList<Calendar>();
		
		Calendar calendarstart = Calendar.getInstance();
		Calendar calendarend = Calendar.getInstance();

		calendarstart.set(datePickerstart.getYear(), datePickerstart.getMonth()+1,0);
		calendarend.set(datePickerend.getYear(), datePickerend.getMonth()+1,0);		
		
		calendarlist.add(calendarstart);
		calendarlist.add(calendarend);
		
		return calendarlist;
		
	}
	
	/**]
	 * 
	
	 * @Title: getCalendarByInintData
	
	 * @Description: TODO(将日期拆分成 年 月 ,并赋值给calendar)
	
	 * @param: @param initDateTime
	 * @param: @return   
	
	 * @return: List<Calendar>   
	
	 * @throws
	 */
	private List<Calendar> getCalendarByInintData(String[] initDateTime) {
		
		List<Calendar> calendarlist = new ArrayList<Calendar>();
		
		/**
		 * 设置开始时间
		 */
		Calendar startcalendar = Calendar.getInstance();
		
		startcalendar.set(readySplite(initDateTime[0])[0], readySplite(initDateTime[0])[1], 0, 0,0);
				
		calendarlist.add(startcalendar);//开始时间
		
		/**
		 * 设置结束时间
		 */
		Calendar endcalendar = Calendar.getInstance();

		endcalendar.set(readySplite(initDateTime[1])[0], readySplite(initDateTime[1])[1], 0, 0,0);
				
		calendarlist.add(endcalendar);//结束时间
		
		return calendarlist;
	}

	
	/**
	 * 
	
	 * @Title: compare_date
	
	 * @Description: TODO(比较时间大小)
	
	 * @param: @param dt1
	 * @param: @param dt2
	 * @param: @return   
	
	 * @return: int   
	
	 * @throws
	 */
    public static int compare_date(Date dt1, Date dt2) {
        try {
            if (dt1.getTime() > dt2.getTime()) {
                //日期不合法
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
            	//日期合法
                return -1;
            } else {
            	//日期相同
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
	
	/**
	 * 
	
	 * @Title: readySplite
	
	 * @Description: TODO(截取年份和月份)
	
	 * @param: @param initDateTime
	 * @param: @return   
	
	 * @return: int[]   
	
	 * @throws
	 */
	public static int[] readySplite(String initDateTime){
		
				int[] datesz = new int[2];
				
				String yearStr = spliteString(initDateTime, "年", "index", "front"); // 年份
				
				String monthStr = getMon(initDateTime.replaceAll(yearStr+"年", ""));
				
				int currentYear = Integer.valueOf(yearStr.trim()).intValue();
				
				int currentMonth = Integer.valueOf(monthStr.trim()).intValue()+1;
				
				datesz[0]=currentYear;
				
				datesz[1]=currentMonth;
				
		return datesz;
	}
	
	/**
	 * 
	
	 * @Title: getMon
	
	 * @Description: TODO(从字符串中提取月份)
	
	 * @param: @param args
	 * @param: @return   
	
	 * @return: String   
	
	 * @throws
	 */
	public static String  getMon(String args) {
		String regEx="[^0-9]";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(args);   
		return m.replaceAll("").trim();
		}
	
	/**
	 * 截取子串
	 * 
	 * @param srcStr
	 *            源串
	 * @param pattern
	 *            匹配模式
	 * @param indexOrLast
	 * @param frontOrBack
	 * @return
	 */
	public static String spliteString(String srcStr, String pattern,
			String indexOrLast, String frontOrBack) {
		String result = "";
		int loc = -1;
		if (indexOrLast.equalsIgnoreCase("index")) {
			loc = srcStr.indexOf(pattern); // 取得字符串第一次出现的位置
		} else {
			loc = srcStr.lastIndexOf(pattern); // 最后一个匹配串的位置
		}
		if (frontOrBack.equalsIgnoreCase("front")) {
			if (loc != -1)
				result = srcStr.substring(0, loc); // 截取子串
		} else {
			if (loc != -1)
				result = srcStr.substring(loc + 1, srcStr.length()); // 截取子串
		}
		return result;
	}

}
