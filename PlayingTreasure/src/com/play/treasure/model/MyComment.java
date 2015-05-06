package com.play.treasure.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class MyComment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;
	
	private String mycomment;
	private String addtime;
	private String tid;
	
	public MyComment(JSONObject json) throws JSONException
	{
		if(json.has("title"))
		{
			this.title = json.getString("title");
		}
		if(json.has("tid"))
		{
			this.tid = json.getString("tid");
		}
		if(json.has("content"))
		{
			this.mycomment = json.getString("content");
		}
		if(json.has("addtime"))
		{
			this.setAddtime(json.getString("addtime"));
		}
	}
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMycomment() {
		return mycomment;
	}

	public void setMycomment(String mycomment) {
		this.mycomment = mycomment;
	}

	

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}



	public String getAddtime() {
		return addtime;
	}



	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

}
