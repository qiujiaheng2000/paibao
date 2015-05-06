/**   
 * @Title: NetworkUtil.java
 * @Package com.play.treasure.utils
 * @Description: TODO(用一句话描述该文件做什么)
 * @author wangchao29
 * @date 2014年12月21日 下午5:10:03
 * @version V1.0
 */
package com.play.treasure.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @ClassName: NetworkUtil
 * @Description: 网络环境工具类
 * @author wangchao29
 * @date 2014年12月21日 下午5:10:03
 * 
 */
public class NetworkUtil 
{
	/** 
	* @Title: checkConnection
	* @Description: 检查网络连接
	* @param @param context
	* @param @return
	* @return boolean
	* @throws 
	*/ 
	public static boolean checkConnection(Context context) 
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null) 
		{
			return networkInfo.isAvailable();
		}
		return false;
	}

	/** 
	* @Title: isWifi
	* @Description: 检查是否为wifi
	* @param @param mContext
	* @param @return
	* @return boolean
	* @throws 
	*/ 
	public static boolean isWifi(Context mContext) 
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null && activeNetInfo.getTypeName().equals("WIFI"))
		{
			return true;
		}
		return false;
	}
}
