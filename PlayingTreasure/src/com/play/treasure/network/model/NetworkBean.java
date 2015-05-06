package com.play.treasure.network.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;


/**
 *  请求结果集
 *
 * @author 王超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class NetworkBean implements Serializable 
{
	/**
	 * default id
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * 请求状态
	 */
	private String code;

	/**
	 * 请求信息
	 */
	private String message;
	
	private String result;

	/**
	 * 请求数据对象
	 */
	private JSONObject data;
	
	/**
	 * 无参构造方法
	 */
	public NetworkBean() 
	{

	}

	/**
	 * 构造方法获取返回结果
	 */
	public NetworkBean(String requestResult) 
	{
		Log.d("requestResult", requestResult);
		
		if (!TextUtils.isEmpty(requestResult)) 
		{
			try 
			{
				JSONObject json = new JSONObject(requestResult);
				this.code = json.getString("code");
				this.message = json.getString("msg");
				this.result = json.getString("result");
				
				try 
				{
					String dataString = json.getString("result");
					if (!dataString.equals("null") && !TextUtils.isEmpty(dataString)) 
					{
						this.data = json.getJSONObject("result");
					}
				} 
				catch (Exception e) 
				{
					Log.d("NetworkBean", "数据解析异常");
					e.printStackTrace();
				}
				Log.d("NetworkBean", "数据解析成功");
			} 
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		} 
		else 
		{
			Log.d("NetworkBean", "没有解析到数据");
		}
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	

	public String getMessage() 
	{
		return message;
	}

	public void setMessage(String message) 
	{
		this.message = message;
	}

	public JSONObject getData() 
	{
		if(data == null)
		{
			data = new JSONObject();
		}
		return data;
	}

	public void setData(JSONObject data) 
	{
		this.data = data;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	
}