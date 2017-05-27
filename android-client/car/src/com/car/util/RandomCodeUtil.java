package com.car.util;

import android.R.string;

/*
 * ctrl+shift+x   转为大写   
 * ctrl+shift+y   转为小写
 */
public class RandomCodeUtil {
	
	//LoginIntent登录码
	public  static final int RequestLoginCode = 1;
	
    private   static final String RANDOMS = "1234567890poiuytrewqasdfghjklmnbvcxzQWERTYUIOPASDFGHJKLZXCVBNM";
    //获取随机数
    public static String getRandom(int num){
    	StringBuffer  sb = new StringBuffer();
    	for (int i = 0; i < num; i++) {
			int random = (int) (Math.random()*RANDOMS.length());
			sb.append(RANDOMS.charAt(random));
		}
    	return  sb.toString();
    }
}
