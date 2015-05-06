package com.play.treasure.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable
{
	/**
	 * default id
	 */
	private static final long serialVersionUID = 1L;
	
	private String userId;
	
	private String userName;
	
	private String password;
	
	private String phoneNumber;
	
	private String address;
	
	private String shopName;
	
	
	public User(JSONObject json) throws JSONException
	{
		if(json.has(""))
		{
			this.userName = json.getString("");
		}
	}
	
	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getShopName() {
		return shopName;
	}


	public void setShopName(String shopName) {
		this.shopName = shopName;
	}


}
