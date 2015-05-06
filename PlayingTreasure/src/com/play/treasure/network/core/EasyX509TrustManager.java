package com.play.treasure.network.core;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * 〈一句话功能简述〉EasyX509TrustManager 
 * 〈功能详细描述〉
 *
 * @author 王超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class EasyX509TrustManager implements X509TrustManager
{
    
    /** The standard trust manager. */
    private X509TrustManager standardTrustManager = null;

    /**
     * Constructor for EasyX509TrustManager.
     * 
     * @param keystore
     *            the keystore
     * @throws NoSuchAlgorithmException
     *             the no such algorithm exception
     * @throws KeyStoreException
     *             the key store exception
     */
    public EasyX509TrustManager(KeyStore keystore)
            throws NoSuchAlgorithmException, KeyStoreException
    {
        super();
        TrustManagerFactory factory = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init(keystore);
        TrustManager[] trustmanagers = factory.getTrustManagers();
        if (trustmanagers.length == 0)
        {
            throw new NoSuchAlgorithmException("no trust manager found");
        }
        this.standardTrustManager = (X509TrustManager) trustmanagers[0];
    }

    /**
     * Check client trusted.
     * 
     * @param certificates
     *            the certificates
     * @param authType
     *            the auth type
     * @throws CertificateException
     *             the certificate exception
     * @see javax.net.ssl.X509TrustManager#checkClientTrusted(X509Certificate[],
     *      String authType)
     */
    @Override
    public void checkClientTrusted(X509Certificate[] certificates,
            String authType) throws CertificateException
    {
        standardTrustManager.checkClientTrusted(certificates, authType);
    }

    /**
     * Check server trusted.
     * 
     * @param certificates
     *            the certificates
     * @param authType
     *            the auth type
     * @throws CertificateException
     *             the certificate exception
     * @see javax.net.ssl.X509TrustManager#checkServerTrusted(X509Certificate[],
     *      String authType)
     */
    @Override
    public void checkServerTrusted(X509Certificate[] certificates,
            String authType) throws CertificateException
    {
        // Clean up the certificates chain and build a new one.
        // Theoretically, we shouldn't have to do this, but various web servers
        // in practice are mis-configured to have out-of-order certificates or
        // expired self-issued root certificate.
        int chainLength = certificates.length;
        if (certificates.length > 1)
        {
            // 1. we clean the received certificates chain.
            // We start from the end-entity certificate, tracing down by
            // matching
            // the "issuer" field and "subject" field until we can't continue.
            // This helps when the certificates are out of order or
            // some certificates are not related to the site.
            int currIndex;
            for (currIndex = 0; currIndex < certificates.length; ++currIndex)
            {
                boolean foundNext = false;
                for (int nextIndex = currIndex + 1; nextIndex < certificates.length; ++nextIndex)
                {
                    if (certificates[currIndex].getIssuerDN().equals(
                            certificates[nextIndex].getSubjectDN()))
                    {
                        foundNext = true;
                        // Exchange certificates so that 0 through currIndex + 1
                        // are in proper order
                        if (nextIndex != currIndex + 1)
                        {
                            X509Certificate tempCertificate = certificates[nextIndex];
                            certificates[nextIndex] = certificates[currIndex + 1];
                            certificates[currIndex + 1] = tempCertificate;
                        }
                        break;
                    }
                }
                if (!foundNext)
                    break;
            }

            // 2. we exam if the last traced certificate is self issued and it
            // is expired.
            // If so, we drop it and pass the rest to checkServerTrusted(),
            // hoping we might
            // have a similar but unexpired trusted root.
            chainLength = currIndex + 1;
            X509Certificate lastCertificate = certificates[chainLength - 1];
            Date now = new Date();
            if (lastCertificate.getSubjectDN().equals(
                    lastCertificate.getIssuerDN())
                    && now.after(lastCertificate.getNotAfter()))
            {
                --chainLength;
            }
        }

        // standardTrustManager.checkServerTrusted(certificates, authType);
    }

    /**
     * Gets the accepted issuers.
     * 
     * @return the accepted issuers
     * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
     */
    @Override
    public X509Certificate[] getAcceptedIssuers()
    {
        return this.standardTrustManager.getAcceptedIssuers();
    }
}