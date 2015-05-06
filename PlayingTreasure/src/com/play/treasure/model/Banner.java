/**   
* @Title: Banner.java
* @Package com.play.treasure.model
* @Description: TODO(用一句话描述该文件做什么)
* @author wangchao29
* @date 2014年11月22日 下午11:34:03
* @version V1.0
*/ 
package com.play.treasure.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/** 
 * @ClassName: Banner
 * @Description: 轮播广告页面
 * @author wangchao29
 * @date 2014年11月22日 下午11:34:03
 * 
 */
public class Banner implements Serializable
{
	
	/** 
	* @Fields serialVersionUID : default id
	*/ 
	private static final long serialVersionUID = 1L;
	
	private String imgUrl;
	
	public Banner(JSONObject json) throws JSONException
	{
		if(json.has("src"))
		{
			this.imgUrl = json.getString("src");
		}
	}
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

}
