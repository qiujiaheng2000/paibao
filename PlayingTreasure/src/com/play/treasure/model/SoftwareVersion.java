package com.play.treasure.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.play.treasure.network.NetworkConfig;

/** 
* @ClassName: SoftwareVersion
* @Description: 版本更新
* @author wangchao29
* @date 2014年12月6日 下午9:26:15
* 
*/ 
public class SoftwareVersion implements Serializable
{
	/**
	 * default id
	 */
	private static final long serialVersionUID = 1L;
	
	/** 
	* 版本号
	*/ 
	private String versionCode;
	
	/**
	 * 版本名称
	 */
	private String versionName;
	
	/**
	 * 更新内容
	 */
	private String updateContent;
	
	/**
	 * 下载地址
	 */
	private String downloadUrl;
	
	/** 
	* 是否强制更新
	*/ 
	private String isForceUpdate;
	
	/**
	 * 构造方法
	 * @param json
	 * @throws JSONException
	 */
	public SoftwareVersion(JSONObject json) throws JSONException
	{
		if(json.has("versionCode"))
		{
			this.versionCode = json.getString("versionCode");
		}
		if(json.has("versionName"))
		{
			this.versionName = json.getString("versionName");
		}
		if(json.has("updateContent"))
		{
			this.updateContent = json.getString("updateContent");
		}
		if(json.has("downloadUrl"))
		{
			this.downloadUrl = json.getString("downloadUrl");
		}
		if(json.has("isForceUpdate"))
		{
			this.isForceUpdate = json.getString("isForceUpdate");
		}
	}
	
	public String getVersionCode() 
	{
		return versionCode;
	}

	public String getVersionName() 
	{
		return versionName;
	}

	public String getUpdateContent() 
	{
		return updateContent;
	}

	public String getDownloadUrl() 
	{
		return NetworkConfig.downUrl+downloadUrl;
	}
	
	public String getIsForceUpdate() {
		return isForceUpdate;
	}
}