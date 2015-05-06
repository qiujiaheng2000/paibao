/**   
* @Title: PlayDetail.java
* @Package com.play.treasure.model
* @Description: TODO(用一句话描述该文件做什么)
* @author wangchao29
* @date 2014年11月24日 上午12:34:28
* @version V1.0
*/ 
package com.play.treasure.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/** 
 * @ClassName: PlayDetail
 * @Description: 宝贝详情
 * @author wangchao29
 * @date 2014年11月24日 上午12:34:28
 * 
 */
public class PlayDetail implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String qqNum;
	
	private String phoneNum;
	
	private String title;
	
	private String address;
	
	private String owner;
	
	private String detail;
	
	private String praiseNum;
	
	private String commentNum;
	
	private String is_praise;
	private String diatance;
	private String auth = "0";
	
	
	
	public PlayDetail(JSONObject json) throws JSONException
	{
		if(json.has("mobile"))
		{
			phoneNum = json.getString("mobile");
		}
		if(json.has("distance"))
		{
			diatance = json.getString("distance");
		}
		if(json.has("qq"))
		{
			qqNum = json.getString("qq");
		}
		if(json.has("address"))
		{
			address = json.getString("address");
			
		}
		if(json.has("title"))
		{
			title = json.getString("title");
		}
		if(json.has("praise_num"))
		{
			praiseNum = json.getString("praise_num");
		}
		if(json.has("comment_num"))
		{
			commentNum = json.getString("comment_num");
		}
		if(json.has("owner"))
		{
			owner = json.getString("owner");
		}
		if(json.has("detail"))
		{
			detail = json.getString("detail");
		}
		if(json.has("is_praise"))
		{
			is_praise = json.getString("is_praise");
		}
		if(json.has("auth")){
			this.auth = json.getString("auth");
		}
	}
	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getQqNum() {
		return qqNum;
	}

	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDiatance() {
		return diatance;
	}

	public void setDiatance(String diatance) {
		this.diatance = diatance;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(String praiseNum) {
		this.praiseNum = praiseNum;
	}

	public String getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getIspraise() {
		return is_praise;
	}

	public void setIspraise(String ispraise) {
		this.is_praise = ispraise;
	}
	
	

}
