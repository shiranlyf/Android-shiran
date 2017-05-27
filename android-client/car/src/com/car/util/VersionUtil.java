package com.car.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.car.application.LocalApplication;

/**
 * 获取版本信息
 * 
 * @author blue
 */
public class VersionUtil
{

	/**
	 * 返回当前程序版本名versionName
	 */
	public static String getAppVersionName()
	{
		String versionName = "";
		try
		{
			PackageManager pm = LocalApplication.getInstance().getPackageManager();
			PackageInfo pi = pm.getPackageInfo(LocalApplication.getInstance().getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0)
			{
				return "";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * 返回当前程序版本号versionCode
	 */
	public static Integer getAppVersionCode()
	{
		try
		{
			PackageManager pm = LocalApplication.getInstance().getPackageManager();
			PackageInfo pi = pm.getPackageInfo(LocalApplication.getInstance().getPackageName(), 0);
			return pi.versionCode;
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return 0;
	}

}
