package com.play.treasure.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

/**
 * PlayInfo 瀑布流属性实体 2014年11月19日 上午12:09:17
 * 
 * @version 1.0.0
 */
/*
 * {"id":"103","uid":"7","title":"马达加斯加石榴石戒指","category":"1","distance":12247.2
 * ,"pixel":"1136,1136","img":"fall_552dd34c343fc.jpg","comment_num":"0",
 * "praise_num" :"0","longitude":"116.511963","latitude":"39.925436","auth":"1"}
 */
public class Waterfall implements Serializable {
	/**
	 * default id
	 */
	private static final long serialVersionUID = 1L;

	private String height;

	public String pixel;
	public int picWidth;
	public int picHeight;

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * 宝贝id
	 */
	private String playId;

	/**
	 * @Fields playUid : 宝贝所属
	 */
	private String playUid;

	/**
	 * 图片地址
	 */
	private String imgUrl;

	/**
	 * 宝贝类别
	 */
	private String playCategory;

	/**
	 * 宝贝名称
	 */
	private String playDes;

	/**
	 * 评论数量
	 */
	private String commentNum;

	/**
	 * 点赞数量
	 */
	private String stackNum;

	/**
	 * 距离
	 */
	private String distance;
	/**
	 * 距离
	 */
	private String auth = "0";

	/**
	 * 构造方法
	 * 
	 * @param json
	 * @throws JSONException
	 */
	public Waterfall(JSONObject json) throws JSONException {
		Log.d("duanzhibo", json.toString());
		if (json.has("uid")) {
			this.playUid = json.getString("uid");
		}
		if (json.has("id")) {
			this.playId = json.getString("id");
		}

		if (json.has("img")) {
			this.imgUrl = json.getString("img");

		}

		if (json.has("category")) {
			this.playCategory = json.getString("category");
		}

		if (json.has("title")) {
			this.playDes = json.getString("title");
		}

		if (json.has("comment_num")) {
			this.commentNum = json.getString("comment_num");
		}

		if (json.has("praise_num")) {
			this.stackNum = json.getString("praise_num");
		}

		if (json.has("distance")) {
			this.distance = json.getString("distance");
		}
		if (json.has("auth")) {
			this.auth = json.getString("auth");
		}
		if (json.has("pixel")) {
			this.pixel = json.getString("pixel");
			if (TextUtils.isEmpty(pixel)) {
				this.picHeight = this.picWidth = 0;
			} else {
				String[] strs = this.pixel.split(",");
				try {
					this.picWidth = Integer.valueOf(strs[0]);
					this.picHeight = Integer.valueOf(strs[1]);
				} catch (NumberFormatException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					this.picHeight = this.picWidth = 0;
				}
			}
		}
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getPlayCategory() {
		return playCategory;
	}

	public void setPlayCategory(String playCategory) {
		this.playCategory = playCategory;
	}

	public String getPlayDes() {
		return playDes;
	}

	public void setPlayDes(String playDes) {
		this.playDes = playDes;
	}

	public String getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}

	public String getStackNum() {
		return stackNum;
	}

	public void setStackNum(String stackNum) {
		this.stackNum = stackNum;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getPlayUid() {
		return playUid;
	}

	public void setPlayUid(String playUid) {
		this.playUid = playUid;
	}

}
