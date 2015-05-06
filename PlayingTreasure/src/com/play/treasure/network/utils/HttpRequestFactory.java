package com.play.treasure.network.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import com.play.treasure.utils.LogUtil;

/**
 * 〈一句话功能简述〉1.无参get构造;2.有参get构造;3.有参post构造;4.文件post构造.
 * 〈功能详细描述〉请求构造器
 *
 * @author 王超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class HttpRequestFactory
{
    /** LOG TAG **/
    private static final String TAG = "HttpRequestFactory";
    
    /**
     * 功能描述: 构造 HttpEntity
     * 〈功能详细描述〉
     *
     * @param list 请求参数列表.
     * @return
     * @throws UnsupportedEncodingException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private static HttpEntity getHttpEntity(List<NameValuePair> list) throws UnsupportedEncodingException
    {
        if(list == null)
        {
            list = new ArrayList<NameValuePair>();
            LogUtil.e(TAG, "the request params list is null.");
            for (NameValuePair nameValuePair : list) 
            {
				System.out.println(nameValuePair);
			}
            return null;
        }
        
        return new UrlEncodedFormEntity(list, HTTP.UTF_8);
    }
    
    /**
     * 构造 HttpEntity
     * @Description:
     * @param param 请求参数名
     * @param file 请求文件
     * @return
     * @throws UnsupportedEncodingException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private static HttpEntity getHttpEntity(String param, File file) throws UnsupportedEncodingException
    {
        if(file == null || !file.exists())
        {
            LogUtil.e(TAG, "the request file is null or not exists.");
            return null;
        }
        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        entity.addPart("photo", new FileBody(file));
        StringBody stringBody = new StringBody(param);
        entity.addPart("uid", stringBody);
        
        return entity;
    }
    
    /**
     * 得到HttpGet
     * @Description:
     * @param param list 请求参数列表.
     * @param url 请求地址.
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static HttpGet getHttpGet(List<NameValuePair> list, String url)
    {
        if(list == null)
        {
            list = new ArrayList<NameValuePair>();
        }
        
        url = new StringBuffer(url).append("?").append(URLEncodedUtils.format(list, HTTP.UTF_8)).toString();
        LogUtil.e("targetUrl", ""+url);
        return new HttpGet(url);
    }
    
    /**
     * 得到HttpPost
     * @Description:
     * @param list 请求参数列表.
     * @param url 请求地址.
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static HttpPost getHttpPost(List<NameValuePair> list, String url)
    {
        HttpPost httPost = new HttpPost(url);
        try
        {
            HttpEntity httpEntity = getHttpEntity(list);
            if(httpEntity != null)
            {
                httPost.setEntity(httpEntity);
            }
            return httPost;
        }
        catch (UnsupportedEncodingException e)
        {
            LogUtil.logException(e);
        }
        return null;
    }
    
    /**
     * 功能描述: 构造httpPost
     * 〈功能详细描述〉
     *
     * @param param
     * @param file
     * @param url
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static HttpPost getHttpPost(List<NameValuePair> list, List<File> file, String url)
    {
        HttpPost httPost = new HttpPost(url);
        Log.d("upload", url);
        try
        {
            HttpEntity httpEntity = getHttpEntity(list, file);
            if(httpEntity != null)
            {
                httPost.setEntity(httpEntity);
            }
            return httPost;
        }
        catch (UnsupportedEncodingException e)
        {
            LogUtil.logException(e);
        }
        return null;
    }
    
    private static HttpEntity getHttpEntity(List<NameValuePair> list, List<File> file) throws UnsupportedEncodingException
    {
        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        for(int i=0;i<file.size();i++)
        {
        	entity.addPart(String.valueOf(System.currentTimeMillis()+i), new FileBody(file.get(i)));
        }
        
        UrlEncodedFormEntity listEntity = new UrlEncodedFormEntity(list, HTTP.UTF_8);
        StringBody stringBody = new StringBody(listEntity.toString());
        entity.addPart("uid", stringBody);
        
        return entity;
    }
    
    /**
     * 功能描述: 构造httpPost
     * 〈功能详细描述〉
     *
     * @param param
     * @param file
     * @param url
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static HttpPost getHttpPost(String param, File file, String url)
    {
        HttpPost httPost = new HttpPost(url);
        Log.d("upload", url);
        try
        {
            HttpEntity httpEntity = getHttpEntity(param, file);
            if(httpEntity != null)
            {
                httPost.setEntity(httpEntity);
            }
            return httPost;
        }
        catch (UnsupportedEncodingException e)
        {
            LogUtil.logException(e);
        }
        return null;
    }
}
