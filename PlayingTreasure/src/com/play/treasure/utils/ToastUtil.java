package com.play.treasure.utils;

import com.play.treasure.PlayApplication;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Toast显示消息工具类
 *
 * @author 王超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ToastUtil 
{

	private static Handler handler = new Handler(Looper.getMainLooper());

	private static Toast toast = null;

	private static Object synObj = new Object();

	private static String toDBC(String input) 
	{
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) 
		{
			if (c[i] == 12288) 
			{
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375) 
			{
				c[i] = (char) (c[i] - 65248);
			}
		}
		return new String(c);
	}

	public static void showMessage(final Context act, final String msg) 
	{
		showMessage(act, TextUtils.isEmpty(msg) ? "" : toDBC(msg),
				Toast.LENGTH_SHORT);
	}

	public static void showMessage(final Context act, final int msg) 
	{
		showMessage(act, msg, Toast.LENGTH_SHORT);
	}

	public static void showMessage(final int msg) 
	{
		showMessage(PlayApplication.getApplication(), msg, Toast.LENGTH_SHORT);
	}

	/**
	 * 显示消息 long
	 */
	public static void showMessageLong(final int msg) 
	{
		showMessage(PlayApplication.getApplication(), msg, Toast.LENGTH_LONG);
	}

	public static void showMessage(String msg) 
	{
		showMessage(PlayApplication.getApplication(), TextUtils.isEmpty(msg) ? ""
				: toDBC(msg), Toast.LENGTH_SHORT);
	}

	private static void showMessage(final Context act, final String msg,final int len) 
	{
		new Thread(new Runnable() 
		{
			/**
			 * 异步执行，弹出Toast
			 */
			public void run() 
			{
				handler.post(new Runnable() 
				{
					@Override
					public void run() 
					{
						synchronized (synObj) 
						{
							if (toast != null) 
							{
								toast.setText(toDBC(msg));
							} 
							else 
							{
								toast = Toast.makeText(act, toDBC(msg), len);
							}
							toast.show();
						}
					}
				});
			}
		}).start();
	}

	private static void showMessage(final Context act, final int msg,final int len) 
	{
		new Thread(new Runnable() 
		{
			/**
			 * 异步执行，弹出Toast
			 */
			public void run() 
			{
				handler.post(new Runnable() 
				{
					@Override
					public void run() 
					{
						synchronized (synObj) 
						{
							if (toast != null) 
							{
								toast.setText(msg);
							} 
							else 
							{
								toast = Toast.makeText(act, msg, len);
							}
							toast.show();
						}
					}
				});
			}
		}).start();
	}
}