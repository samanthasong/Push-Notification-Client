package com.ktpoc.tvcomm.consulting;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;


/**
 * Created by insam on 2016. 5. 2..
 */
public class AndroidHttp {

    private URL u;

    public AndroidHttp(String url){
        try {
            u = new URL(url);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public Boolean requestCMS(String api, String params){
        try{
            HttpURLConnection conn = (HttpURLConnection)u.openConnection();
            conn.setRequestMethod("GET");
        }catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }
}
