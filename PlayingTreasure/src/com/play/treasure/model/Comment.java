package com.play.treasure.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Comment implements Serializable
{

	/**
	 * default id
	 */
	private static final long serialVersionUID = 1L;
	
	private String headPic;
	
	private String userName;
	
	private String time;
	
	private String content;
	
	public Comment(JSONObject json) throws JSONException
	{
		if(json.has("photo"))
		{
			this.headPic = json.getString("photo");
		}
		if(json.has("nickname"))
		{
			this.userName = json.getString("nickname");
		}
		if(json.has("addtime"))
		{
			this.time = json.getString("addtime");
		}
		if(json.has("content"))
		{
			this.content = json.getString("content");
		}
	}
	
	public String getHeadPic() 
	{
		return headPic;
	}

	public void setHeadPic(String headPic) 
	{
		this.headPic = headPic;
	}

	public String getUserName() 
	{
		return userName;
	}

	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	public String getTime() 
	{
		return time;
	}

	public void setTime(String time) 
	{
		this.time = time;
	}

	public String getContent() 
	{
		return content;
	}

	public void setContent(String content) 
	{
		this.content = content;
	}

}
