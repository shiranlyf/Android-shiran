package com.car.util;

import com.car.R;

public class MyUtiils {
	public static final int  RequestLoginCode=1;
	//��ȡ���е���Ӧ��
	public static final int  RequestCityCode=2;
	//FragmentHome���񲿷���ʾ������
	public static String[] navsSort= {"��ʳ","��Ӱ","�Ƶ�","KTV","������","��������",
		"����","����","��������","ĸӤ","Ůװ","��ױ","�����˶�","�������","ȫ��"};
	//��Ӧ��ͼƬ
	public static int[] navsSortImages = {R.drawable.icon_home_food_99,R.drawable.icon_home_movie_29,
		R.drawable.icon_home_hotel_300,R.drawable.icon_home_ktv_31,R.drawable.icon_home_self_189,
		R.drawable.icon_home_happy_2,R.drawable.icon_home_flight_400,R.drawable.icon_home_shopping_3,
		R.drawable.icon_home_liren_442,R.drawable.icon_home_child_13,R.drawable.icon_home_nvzhuang_84,
		R.drawable.icon_home_meizhuang_173,R.drawable.icon_home_yundong_20,R.drawable.icon_home_life_46,
		R.drawable.icon_home_all_0};
	
	//����ķ��������ݿ�һһ��Ӧ
	public static String[] allCategray = {"ȫ������","�����µ�","��ʳ","��������",
		"��Ӱ","�������","д������","�Ƶ�","����","��������","������ѵ","�齱����","����"};
	
	public static int[] allCategrayImages = {R.drawable.ic_all,R.drawable.ic_newest,
		R.drawable.ic_food,R.drawable.ic_entertain,R.drawable.ic_movie,R.drawable.ic_life,
		R.drawable.ic_photo,R.drawable.ic_hotel,R.drawable.ic_travel,R.drawable.ic_beauty,
		R.drawable.ic_edu,R.drawable.ic_luck,R.drawable.ic_shopping};
	
	/**
	 * ���������
	 * �����Щ  ��Ϊ���ݿ��
	 * ѯ�Ľ�����Ա���Ҫ��ʾ��allCategray��
	 */
	public static long allCategoryNumber[] = new long[allCategray.length+5];
	
	
	
	public  static final String RANDOMS="1234567890poiuytrewqasdfghjklmnbvcxzQWERTYUIOPASDFGHJKLZXCVBNM";
	//������֤��
	public static String  getRandom(int num){
		StringBuffer  sdf=new StringBuffer();
		for (int i = 0; i < num; i++) {
			int random=(int) (Math.random()*RANDOMS.length());
			sdf.append(RANDOMS.charAt(random));
		}
		return  sdf.toString();
	} 
	
}
