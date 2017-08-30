package com.jsse.example;

import java.io.BufferedInputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


import java.io.IOException;

public class SSLTest {
/*	
	public static void main(String [] args) throws Exception{
		HttpClient client = new HttpClient();

		HttpMethod get = new GetMethod("https://mms.nw.ru");
		//get.setDoAuthentication(true);

		try {
		    int status = client.executeMethod(get);
		    System.out.println(status);

		    BufferedInputStream is = new BufferedInputStream(get.getResponseBodyAsStream());
		    int r=0;byte[] buf = new byte[10];
		    while((r = is.read(buf)) > 0) {
		        System.out.write(buf,0,r);
		    }

		} catch(Exception ex) {
		    ex.printStackTrace();
		}

	}
*/

    public static void main(String [] args) throws Exception {
    	/*
    	DefaultHttpClient httpclient = new DefaultHttpClient();
    	HttpHost proxy = new HttpHost("127.0.0.1", 8086);
    	httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy); */
    	System.setProperty("https.proxyHost", "127.0.0.1");  
    	System.setProperty("https.proxyPort", "8086");
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
        SSLContext.setDefault(ctx);

        URL url = new URL("https://userstream.twitter.com");
        
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
            	System.out.println("ok " + arg0);
                return true;
            }
        });
        System.out.println(conn.getResponseCode());

        conn.disconnect();
    }

    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }

}

