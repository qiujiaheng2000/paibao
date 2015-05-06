package com.play.treasure.utils;

import android.util.Log;

import com.play.treasure.BuildConfig;


/**
 *  该类有打印调试日志和具体类的日志方法
 *
 * @author 王超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class LogUtil 
{
	/**
	 * 日志名
	 */
	private static final String TAG = "== LogTrace ==";
	private static final String DEBUG_TAG = "debug";

	/**
	 * 日志开关
	 */
	private static final boolean DEBUG = BuildConfig.DEBUG;
	
	public static void e(String tag, String err) 
	{
		if (DEBUG) 
		{
			Log.e(tag, err);
		}
	}
	
	public static void e(String msg) 
	{
		if (DEBUG) 
		{
			Log.e(DEBUG_TAG, msg);
		}
	}
	
	public static void d(String tag, String debug) 
	{
		if (DEBUG && debug != null) 
		{
			Log.d(tag, debug);
		}
	}
	
	public static void d(String tag, String debug, Throwable e) 
	{
		if (DEBUG) 
		{
			Log.d(tag, debug, e);
		}
	}
	
	public static void d(String msg) 
	{
		if (DEBUG) 
		{
			Log.d(DEBUG_TAG, msg);
		}
	}
	
	public static void i(String tag, String info) 
	{
		if (DEBUG) 
		{
			Log.i(tag, info);
		}
	}
	
	public static void i(String msg) 
	{
		if (DEBUG) 
		{
			Log.i(DEBUG_TAG, msg);
		}
	}
	
	public static void w(String msg) 
	{
		if (DEBUG) 
		{
			Log.w(DEBUG_TAG, msg);
		}
	}
	
	public static void jw(Object object, Throwable tr) 
	{
		if (DEBUG) 
		{
			Log.w(getPureClassName(object), "", filterThrowable(tr));
		}
	}

	private static Throwable filterThrowable(Throwable tr) 
	{
		StackTraceElement[] ste = tr.getStackTrace();
		tr.setStackTrace(new StackTraceElement[] { ste[0] });
		return tr;
	}

	private static String getPureClassName(Object object) 
	{
		if (object == null) 
		{
			Log.e(TAG, "getPureClassName() : object is null.");
		}
		String name = object.getClass().getName();
		if ("java.lang.String".equals(name)) {
			return object.toString();
		}
		int idx = name.lastIndexOf('.');
		if (idx > 0) {
			return name.substring(idx + 1);
		}
		return name;
	}
	
	public static void logException(Throwable e) 
	{
		if (BuildConfig.DEBUG) 
		{
			e.printStackTrace();
		}
	}
}
