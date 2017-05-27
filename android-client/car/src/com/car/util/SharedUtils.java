package com.car.util;
import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * ʵ�ֱ�ǵ�д�����ȡ
 */
public class SharedUtils {
	//�ļ���
	private  static final String FILE_NAME="car";
	//key
	private  static final String MODE_NAME="welcome";
	//����д��ķ���  ���������Ķ���  ����Ĭ��ֵΪfalse
    public static  boolean  getWelcomeBoolean(Context context){
    	return  context.getSharedPreferences(FILE_NAME, context.MODE_PRIVATE)
    			         .getBoolean(MODE_NAME, false);
    }
    
    //д��Boolean���͵�ֵ
    public static void putWelcomeBoolean(Context  context,boolean isFirst){
    	//�ڶ�������ֵָ���ǲ���ģʽ
    	Editor editor=context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
    	//�������ֵ���б���
    	editor.putBoolean(MODE_NAME, isFirst);
    	//editor��������ύ����
    	editor.commit();
    }
    
    //д��һ������String���͵�����
    public static void putCityName(Context  context,String  cityName){
    	Editor  editor=context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
    	editor.putString("cityName",  cityName);
    	editor.commit();
    }
    
    //д���¼������
    public  static void putUserName(Context context,String userName){
    	Editor editor=context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
        editor.putString("userName", userName);
        //�ύ����
        editor.commit();
    }
    //��ȡ��¼����
    public static String  getUserName(Context context){
    	return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString("userName", "�����¼");
    }
    
    //��ȡcity String���͵�ֵ
    public static String  getCityName(Context  context){
    	
    	return   context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    			.getString("cityName", "ѡ�����");
    }
}
