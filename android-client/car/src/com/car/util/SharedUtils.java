package com.car.util;
import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * 实现标记的写入与读取
 */
public class SharedUtils {
	//文件名
	private  static final String FILE_NAME="car";
	//key
	private  static final String MODE_NAME="welcome";
	//进行写入的方法  传入上下文对象  设置默认值为false
    public static  boolean  getWelcomeBoolean(Context context){
    	return  context.getSharedPreferences(FILE_NAME, context.MODE_PRIVATE)
    			         .getBoolean(MODE_NAME, false);
    }
    
    //写入Boolean类型的值
    public static void putWelcomeBoolean(Context  context,boolean isFirst){
    	//第二个参数值指的是操作模式
    	Editor editor=context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
    	//将传入的值进行保存
    	editor.putBoolean(MODE_NAME, isFirst);
    	//editor对象进行提交事务
    	editor.commit();
    }
    
    //写入一个城市String类型的数据
    public static void putCityName(Context  context,String  cityName){
    	Editor  editor=context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
    	editor.putString("cityName",  cityName);
    	editor.commit();
    }
    
    //写入登录的名称
    public  static void putUserName(Context context,String userName){
    	Editor editor=context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
        editor.putString("userName", userName);
        //提交事务
        editor.commit();
    }
    //获取登录名称
    public static String  getUserName(Context context){
    	return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString("userName", "点击登录");
    }
    
    //获取city String类型的值
    public static String  getCityName(Context  context){
    	
    	return   context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    			.getString("cityName", "选择城市");
    }
}
