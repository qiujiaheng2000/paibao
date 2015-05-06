package com.play.treasure.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 *  用户信息持久化 
 *
 * @author 王超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SharedPreferencesUserInfo 
{
	private static final String USER_INFO = "play_userinfo";
	
	private SharedPreferences mSharedPreferences;

    private Context mContext;
    
    /**
     * 初始化
     * 
     * @param context
     */
    public SharedPreferencesUserInfo(Context context)
    {
        this.mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(USER_INFO,Context.MODE_PRIVATE);
    }
    
    /**
     * 功能描述: 获取用户名
     * 〈功能详细描述〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String getSharedPreLoginName()
    {
        return mSharedPreferences.getString("loginName", "");
    }
    
    /**
     * 功能描述: 保存用户名
     * 〈功能详细描述〉
     *
     * @param sharedPreLoginName
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void setSharedPreLoginName(String sharedPreLoginName)
    {
        Editor editor = mSharedPreferences.edit();
        editor.putString("loginName", sharedPreLoginName);
        editor.commit();
    }
    
    /**
     * 功能描述: 获取密码
     * 〈功能详细描述〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String getSharedPreLoginPwd()
    {
        return mSharedPreferences.getString("loginPwd", "");
    }
    
    /**
     * 功能描述: 保存密码
     * 〈功能详细描述〉
     *
     * @param mSharedPreLoginPwd
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void setSharedPreLoginPwd(String mSharedPreLoginPwd)
    {
        Editor editor = mSharedPreferences.edit();
        editor.putString("loginPwd", mSharedPreLoginPwd);
        editor.commit();
    }
    
    /**
     * 功能描述: 获取登录id
     * 〈功能详细描述〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String getUserId()
    {
        return mSharedPreferences.getString("loginId", "");
    }

    /**
     * 功能描述: 设置登录id
     * 〈功能详细描述〉
     *
     * @param loginId
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void setUserId(String loginId)
    {
        Editor editor = mSharedPreferences.edit();
        editor.putString("loginId", loginId);
        editor.commit();
    }
    
    /**
     * 功能描述: 保存用户密码
     * 〈功能详细描述〉
     *
     * @param password
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void setUserPwd(String password)
    {
        Editor editor = mSharedPreferences.edit();
        editor.putString("password", password);
        editor.commit();
    }

    /**
     * 功能描述: 取得用户密码
     * 〈功能详细描述〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String getUserPwd()
    {
        return mSharedPreferences.getString("password", "");
    }
}
