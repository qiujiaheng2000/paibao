package com.play.treasure.network.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.text.TextUtils;

import com.play.treasure.network.NetworkConfig;
import com.play.treasure.network.core.CommonHttpClient;
import com.play.treasure.network.core.GzipDecompressingEntity;
import com.play.treasure.utils.LogUtil;

/**
 *  网络请求调用 包含httpget 和 httppost 
 * 〈功能详细描述〉
 *
 * @author 王超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class Caller
{
    /** LOG TAG. */
    private static final String TAG = "Caller";

    /** HTTP Client. */
    private final DefaultHttpClient mClient;
    
    private static String TGC = "";

    /**
     * 构造方法.
     */
    public Caller()
    {
        mClient = CommonHttpClient.createHttpClient();
        mClient.setHttpRequestRetryHandler(requestRetryHandler);
    }
    
    /**
     * 功能描述: HttpGet
     * 〈功能详细描述〉
     *
     * @param params 请求参数
     * @param action 请求对应动作
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String httpGet(List<NameValuePair> params, String action)
    {
        return httpsGet(params, action);
    }
    
    /**
     * HttpsGet
     * @Description:
     * @param params 请求参数.
     * @param action 请求对应的动作.
     */
    public String httpsGet(List<NameValuePair> params, String action)
    {
        return get(params, getHttpUrl(action, true), null);
    }
    
    /**
     * get请求最终处理.
     * @Description:
     * @param params 请求参数.
     * @param url 请求地址.
     * @param headers 请求头.
     */
    public String get(List<NameValuePair> params, String url, List<NameValuePair> headers)
    {
        HttpGet httpGet = HttpRequestFactory.getHttpGet(params, url);
        if(headers != null)
        {
            for(NameValuePair header : headers)
            {
                httpGet.addHeader(header.getName(), header.getValue());
            }
        }
        if(!TextUtils.isEmpty(TGC))
        {
            httpGet.addHeader("Cookie", TGC);
            LogUtil.d(TAG, "add header TGC:"+TGC);
        }
        httpGet.addHeader("Accept-Encoding", "gzip");
        
        for(Header header : httpGet.getAllHeaders())
        {
            LogUtil.d("MSG", header.getName()+":"+header.getValue());
        }
        
        String result = "";
        try
        {
            result = mClient.execute(httpGet, responseHandler);
            
            return result;
        }
        catch (ClientProtocolException e)
        {
            LogUtil.logException(e);
            result = "";
        }
        catch (IOException e)
        {
            LogUtil.logException(e);
            result = "";
        }
        return result;
    }
    
    /**
     * HttpPost
     * @Description:
     * @param params 请求参数.
     * @param action 请求对应的动作.
     */
    public String httpPost(List<NameValuePair> params, String action)
    {
        return post(params, getHttpUrl(action, false));
    }
    
    /**
     * 功能描述: HttpsPost
     * 〈功能详细描述〉
     *
    * @param params 请求参数.
     * @param action 请求对应的动作.
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String httpsPost(List<NameValuePair> params, String action)
    {
        return post(params, getHttpUrl(action, true));
    }
    
    /**
     * 功能描述: HttpPost 文件.
     * 〈功能详细描述〉
     *
      * @param param 请求文件对应的参数.
     * @param file 请求文件.
     * @param action 请求对应的动作.
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String httpPost(String param, File file, String action)
    {
        HttpPost httpPost = HttpRequestFactory.getHttpPost(param, file, getHttpUrl(action, false));
        return post(httpPost, null);
    }
    
    /** 
    * @Title: httpPost
    * @Description: 多图片上传
    * @param @param param
    * @param @param file
    * @param @param action
    * @param @return
    * @return String
    * @throws 
    */ 
    public String uploadPost(List<NameValuePair> params, List<File> file, String action)
    {
        HttpPost httpPost = HttpRequestFactory.getHttpPost(params, file, getHttpUrl(action, false));
        return post(httpPost, null);
    }
    
    /**
      * <原生态>post请求.
     * @Description:
     * @param params 请求参数.
     * @param url 请求地址.
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String post(List<NameValuePair> params, String url)
    {
        return post(params, url, null);
    }
    
    /**
     * 功能描述: post请求.
     *
     * @param params 请求参数.
     * @param url 请求地址.
     * @param headers 请求头.
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String post(List<NameValuePair> params, String url, List<NameValuePair> headers)
    {
        HttpPost httpPost = HttpRequestFactory.getHttpPost(params, url);
        LogUtil.d(TAG, "post : "+url);
        return post(httpPost, headers);
    }
    
    
    /**
     * 功能描述: post请求最终处理.
     * 〈功能详细描述〉
     *
     * @param httpPost
     * @param headers
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private String post(HttpPost httpPost, List<NameValuePair> headers)
    {
        String result = "";
        if(httpPost == null) return result;
        
        if(headers != null)
        {
            for(NameValuePair header : headers)
            {
                httpPost.addHeader(header.getName(), header.getValue());
            }
        }
        if(!TextUtils.isEmpty(TGC))
        {
            httpPost.addHeader("Cookie", TGC);
            LogUtil.d(TAG, "add header TGC:"+TGC);
        }
        httpPost.addHeader("Accept-Encoding", "gzip");
        
        for(Header header : httpPost.getAllHeaders())
        {
            LogUtil.d("MSG", header.getName()+":"+header.getValue());
        }
        
        try
        {
            result = mClient.execute(httpPost, responseHandler);
            
            return result;
        }
        catch (ClientProtocolException e)
        {
            LogUtil.logException(e);
            result = "";
        }
        catch (IOException e)
        {
            LogUtil.logException(e);
            result = "";
        }
        return result;
    }
    
    /**
     * 请求返回处理.
     */
    private ResponseHandler<String> responseHandler = new ResponseHandler<String>()
    {
        @Override
        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException
        {
            String result = null;
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                HttpEntity entity = response.getEntity();
                if (entity != null)
                {
                    Header header = entity.getContentEncoding();
                    if (header != null)
                    {
                        String contentEncoding = header.getValue();
                        if (contentEncoding != null && contentEncoding.contains("gzip"))
                        {
                            entity = new GzipDecompressingEntity(entity);
                        }
                    }
                    String charset = EntityUtils.getContentCharSet(entity);
                    if(TextUtils.isEmpty(charset))
                    {
                        charset = HTTP.UTF_8;
                    }
                    
                    try
                    {
                        result = new String(EntityUtils.toByteArray(entity), charset);
                        return result;
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        LogUtil.logException(e);
                        result = null;
                    }
                    catch (IOException e)
                    {
                        LogUtil.logException(e);
                        result = null;
                    }
                }
            }
            return result;
        }
    };
    
    /**
     * 功能描述: 组装请求地址.
     * 〈功能详细描述〉
     *
     * @param action
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private String getHttpUrl(String action, boolean https)
    {
        if(https)
        {
            return NetworkConfig.baseurl + action;
        }
        return NetworkConfig.baseurl + action;
    }
    
    /**
     * 请求异常重试处理.
     */
    private HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler()
    {
        @Override
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context)
        {
            if (executionCount >= HttpRequestConfig.MAX_RETRY_NUM)
            {
                // Do not retry if over max retry count
                return false;
            }
            if (exception instanceof NoHttpResponseException)
            {
                // Retry if the server dropped connection on us
                return true;
            }
            if (exception instanceof SSLHandshakeException)
            {
                // Do not retry on SSL handshake exception
                return false;
            }
            HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
            boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
            if (!idempotent)
            {
                // Retry if the request is considered idempotent
                return true;
            }
            return false;
        }
    };
}