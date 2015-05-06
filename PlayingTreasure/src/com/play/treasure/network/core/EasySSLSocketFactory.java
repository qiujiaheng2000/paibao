package com.play.treasure.network.core;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.play.treasure.utils.LogUtil;

// TODO: Auto-generated Javadoc
/**
 * This socket factory will create ssl socket that accepts self signed
 * certificate.
 * 
 * @author olamy
 * @version $Id: EasySSLSocketFactory.java 765355 2009-04-15 20:59:07Z evenisse
 *          $
 * @since 1.2.3
 */
public class EasySSLSocketFactory implements LayeredSocketFactory
{

    /** The sslcontext. */
    private SSLContext sslcontext = null;

    /**
     * Creates a new EasySSLSocket object.
     * 
     * @return the SSL context
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private static SSLContext createEasySSLContext() throws IOException
    {
        try
        {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[] { new EasyX509TrustManager(
                    null) }, null);
            return context;
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Gets the sSL context.
     * 
     * @return the sSL context
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private SSLContext getSSLContext() throws IOException
    {
        if (this.sslcontext == null)
        {
            this.sslcontext = createEasySSLContext();
        }
        return this.sslcontext;
    }

    /**
     * Connect socket.
     * 
     * @param sock
     *            the sock
     * @param host
     *            the host
     * @param port
     *            the port
     * @param localAddress
     *            the local address
     * @param localPort
     *            the local port
     * @param params
     *            the params
     * @return the socket
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnknownHostException
     *             the unknown host exception
     * @throws ConnectTimeoutException
     *             the connect timeout exception
     * @see org.apache.http.conn.scheme.SocketFactory#connectSocket(java.net.Socket,
     *      java.lang.String, int, java.net.InetAddress, int,
     *      org.apache.http.params.HttpParams)
     */
    @Override
    public Socket connectSocket(Socket sock, String host, int port,
            InetAddress localAddress, int localPort, HttpParams params)
            throws IOException, UnknownHostException, ConnectTimeoutException
    {
        int connTimeout = HttpConnectionParams.getConnectionTimeout(params);
        int soTimeout = HttpConnectionParams.getSoTimeout(params);
        int tempLocalPort = localPort;

        InetSocketAddress remoteAddress = new InetSocketAddress(host, port);
        SSLSocket sslsock = (SSLSocket) ((sock != null) ? sock : createSocket());

        if ((localAddress != null) || (tempLocalPort > 0))
        {
            // we need to bind explicitly
            if (tempLocalPort < 0)
            {
                tempLocalPort = 0; // indicates "any"
            }
            InetSocketAddress isa = new InetSocketAddress(localAddress,
                    tempLocalPort);
            sslsock.bind(isa);
        }

        sslsock.connect(remoteAddress, connTimeout);
        sslsock.setSoTimeout(soTimeout);
        return sslsock;

    }

    /**
     * Creates a new EasySSLSocket object.
     * 
     * @return the socket
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @see org.apache.http.conn.scheme.SocketFactory#createSocket()
     */
    @Override
    public Socket createSocket() throws IOException
    {
        return getSSLContext().getSocketFactory().createSocket();
    }

    /**
     * Checks if is secure.
     * 
     * @param socket
     *            the socket
     * @return true, if is secure
     * @throws IllegalArgumentException
     *             the illegal argument exception
     * @see org.apache.http.conn.scheme.SocketFactory#isSecure(java.net.Socket)
     */
    @Override
    public boolean isSecure(Socket socket) throws IllegalArgumentException
    {
        return true;
    }

    /**
     * Creates a new EasySSLSocket object.
     * 
     * @param socket
     *            the socket
     * @param host
     *            the host
     * @param port
     *            the port
     * @param autoClose
     *            the auto close
     * @return the socket
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnknownHostException
     *             the unknown host exception
     * @see org.apache.http.conn.scheme.LayeredSocketFactory#createSocket(java.net.Socket,
     *      java.lang.String, int, boolean)
     */
    @Override
    public Socket createSocket(Socket socket, String host, int port,
            boolean autoClose) throws IOException, UnknownHostException
    {
        return getSSLContext().getSocketFactory().createSocket(socket, host,
                port, autoClose);
    }

    // -------------------------------------------------------------------
    // javadoc in org.apache.http.conn.scheme.SocketFactory says :
    // Both Object.equals() and Object.hashCode() must be overridden
    // for the correct operation of some connection managers
    // -------------------------------------------------------------------

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (null == obj)
        {
            return false;
        }
        if (!(obj instanceof EasySSLSocketFactory))
        {
            return false;
        }

        EasySSLSocketFactory easySSLSocketFactory = (EasySSLSocketFactory) obj;
        try
        {
            if (this.sslcontext == easySSLSocketFactory.getSSLContext())
            {
                return true;
            }
        }
        catch (IOException e)
        {
            LogUtil.logException(e);
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return EasySSLSocketFactory.class.hashCode();
    }

}