package com.car.util;

public class StringUtil {
	
	public static boolean isNull(String str)
	{
		if (str==null || str.trim().length()==0)
			return true;
		
		return false;
	}
	
	public static boolean isNotNull(String str)
	{
		return !isNull(str);
	}

}
