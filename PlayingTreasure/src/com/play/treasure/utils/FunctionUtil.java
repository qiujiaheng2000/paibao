package com.play.treasure.utils;

import java.text.DecimalFormat;

import android.content.Context;

/** 
* @ClassName: FunctionUtil
* @Description: 功能工具类
* @author wangchao29
* @date 2014年12月6日 下午9:52:44
* 
*/ 
public class FunctionUtil 
{
	/**
     * HTTP方式
     */
    public static final String HTTP_WAP = "wap";
    
    /**
     * 判断是否是快速双击
     */
    private static long lastClickTime;
    
    /**
     * 功能描述: dip转换px方法
     * 〈功能详细描述〉
     *
     * @param context
     * @param dipValue
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static int dip2px(Context context, float dipValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    
    /**
     * 功能描述: 防止快速双击
     * 〈功能详细描述〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean isFastDoubleClick()
    {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000)
        {
            return true;
        }
        lastClickTime = time;
        return false;
    }
    
    /**
     * 功能描述: String转换成int方法
     * 〈功能详细描述〉
     *
     * @param string
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static int parseIntByString(String string)
    {
        try
        {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException e)
        {
            LogUtil.logException(e);
            return 0;
        }
    }
    
    /**
     * 功能描述: String转换成double类型
     * 〈功能详细描述〉
     *
     * @param string
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static double parseDoubleByString(String string)
    {
        try
        {
            return Double.parseDouble(string);
        }
        catch (NumberFormatException e)
        {
            LogUtil.logException(e);
            return 0.00;
        }
        catch (Exception e)
        {
            LogUtil.logException(e);
            return 0.00;
        }
    }
    
    /**
     * 功能描述: String转换成String
     * 〈功能详细描述〉
     *
     * @param string
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String parseNull(String string)
    {
        return null == string ? "" : string;
    }
    
    /**
     * 功能描述: double转换成double
     * 〈功能详细描述〉
     *
     * @param data
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getDouble(double data)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(data);
    }
    
    /**
     * 功能描述:String转换成double
     * 〈功能详细描述〉
     *
     * @param data
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getDouble(String data)
    {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(parseDoubleByString(data));
    }
}