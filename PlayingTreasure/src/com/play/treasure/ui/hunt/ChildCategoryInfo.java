/**   
* @Title: ChildCategoryInfo.java
* @Package com.play.treasure.ui.hunt
* @Description: TODO(用一句话描述该文件做什么)
* @author wangchao29
* @date 2014年12月7日 下午12:55:50
* @version V1.0
*/ 
package com.play.treasure.ui.hunt;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/** 
 * @ClassName: ChildCategoryInfo
 * @Description: 子分类属性
 * @author wangchao29
 * @date 2014年12月7日 下午12:55:50
 * 
 */
public class ChildCategoryInfo implements Serializable
{

	/** 
	* default id
	*/ 
	private static final long serialVersionUID = 1L;
	
	private String childId;
	
	private String childTitle;
	
	public ChildCategoryInfo(JSONObject json) throws JSONException
	{
		if(json.has("smallId"))
		{
			this.childId = json.getString("smallId");
		}
		if(json.has("smallTitle"))
		{
			this.childTitle = json.getString("smallTitle");
		}
	}
	
	public String getChildId() 
	{
		return childId;
	}

	public String getChildTitle() 
	{
		return childTitle;
	}
}
