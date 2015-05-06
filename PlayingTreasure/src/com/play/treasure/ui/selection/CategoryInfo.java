package com.play.treasure.ui.selection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName: CategoryInfo
 * @Description: 分类信息
 * @author wangchao29
 * @date 2014年12月7日 下午1:40:47
 * 
 */
public class CategoryInfo 
{
	/**
	 * 大分类标题
	 */
	private String parentTitle;

	/**
	 * 子类信息
	 */
	private JSONArray childJson;

	public CategoryInfo(JSONObject json) throws JSONException 
	{
		if (json.has("bigTitle")) 
		{
			this.parentTitle = json.getString("bigTitle");
		}
		if (json.has("small")) {
			this.childJson = json.getJSONArray("small");
		}
	}

	public String getParentTitle() 
	{
		return parentTitle;
	}

	public JSONArray getChildJson() 
	{
		return childJson;
	}
}
