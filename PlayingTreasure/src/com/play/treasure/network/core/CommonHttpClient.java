package com.play.treasure.network.core;

import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.play.treasure.network.utils.HttpRequestConfig;


/**
 *  通用httpclient
 * 〈功能详细描述〉
 *
 * @author 王超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CommonHttpClient
{
    public static final DefaultHttpClient createHttpClient()
    {
        // Sets up the http part of the service.
        final SchemeRegistry supportedSchemes = new SchemeRegistry();

        // Register the "http" protocol scheme, it is required
        // by the default operator to look up socket factories.
        final SocketFactory sf = PlainSocketFactory.getSocketFactory();
        supportedSchemes.register(new Scheme("http", sf, 80));
        supportedSchemes.register(new Scheme("https", new EasySSLSocketFactory(), 443));

        // Set some client http client parameter defaults.
        final HttpParams httpParams = createHttpParams();
        HttpClientParams.setRedirecting(httpParams, false);
        HttpClientParams.setCookiePolicy(httpParams, CookiePolicy.BROWSER_COMPATIBILITY);
        final ClientConnectionManager ccm = new ThreadSafeClientConnManager(httpParams, supportedSchemes);
        DefaultHttpClient httpClient = new DefaultHttpClient(ccm, httpParams);
        return httpClient;
    }

   
    /**
     * 功能描述: create http params
     * 〈功能详细描述〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private static final HttpParams createHttpParams()
    {
        final HttpParams params = new BasicHttpParams();

        // Turn off stale checking. Our connections break all the time anyway,
        // and it's not worth it to pay the penalty of checking every time.
        HttpConnectionParams.setStaleCheckingEnabled(params, false);

        HttpConnectionParams.setConnectionTimeout(params, HttpRequestConfig.CONNECTION_TIME_OUT);
        HttpConnectionParams.setSoTimeout(params, HttpRequestConfig.SO_TIME_OUT);
        HttpConnectionParams.setSocketBufferSize(params, HttpRequestConfig.SOCKET_BUFFER_SIZE);

        ConnManagerParams.setTimeout(params, 10000);
        return params;
    }
}
