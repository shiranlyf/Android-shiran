package com.car.util;


/**
 * 系统常量表
 * 
 * @author blue
 * 
 */
public class ConstantsUtil
{
	public static String DOMAINNAME = "192.168.56.1:8080";
//	public static String DOMAINNAME = "15y80n4299.51mypc.cn:16057";
	
//	15y80n4299.51mypc.cn:16057
	
	// 服务器地址  
	public static String SERVER_URL = "http://"+DOMAINNAME+"/car/";

	
	//新闻资讯的图片路径
    //newsimportant图片地址(http://127.0.0.1:8080/car/images/newsImages/newsimportant1.jpg)
	public static String IMAGE_URL = "http://"+DOMAINNAME+"/car/images/newsImages/";
	
	//新闻滑动头的路径
	public static String NEWS_HEAD_URL = "http://"+DOMAINNAME+"/car/images/newsHeadImages/";
	
	
	//新闻点击进去的相册
	public static String NEWS_IMAGEITEM_URL = "http://"+DOMAINNAME+"/car/images/newsItemImages/";
	
	//carlogo的图片路径
	public static String CARLOGO__URL = "http://"+DOMAINNAME+"/car/images/carLogoImages/";
	
	//车系找车二级的图片路径
	public static String CARERIES__URL = "http://"+DOMAINNAME+"/car/images/carSerieImages/";
	

}
