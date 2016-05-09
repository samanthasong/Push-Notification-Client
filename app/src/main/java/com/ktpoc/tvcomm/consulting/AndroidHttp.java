package com.ktpoc.tvcomm.consulting;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * Created by insam on 2016. 5. 2..
 */
public class AndroidHttp {

    private String url ;
    private URL u;
    private HttpsURLConnection httpsURLConnection;
    private DataInputStream mInputStream;
    private DataOutputStream mOutputStream;
    private String result;

    public AndroidHttp(String _url){
        url = _url;
    }

    private void setHTTPSSettings(){

        /* Logic for ignoring SSL Cert */
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {

                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
//                        return new X509Certificate[0];
                        return null;
                    }
                }
        };

        SSLContext sc;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        HostnameVerifier allHostsValid = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }


    public String postCMS(String api, Properties prop){

        //https settings
        setHTTPSSettings();

        String encodedMsg = encodeString(prop);

       // url += api;

        Log.d("CMS", "1 url is -- >"+ url);




        try{

            httpsURLConnection = (HttpsURLConnection)u.openConnection();
            try{
                u = new URL(url);
                //u = new URL("HTTPS", "amuzlab.iptime.org?", 3000, "deviceAuthSelect");
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            Log.d("CMS", "2 url is -- >" + url);
            httpsURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setDoOutput(true);
            mOutputStream = new DataOutputStream(httpsURLConnection.getOutputStream());
            mOutputStream.writeBytes(encodedMsg);
            mOutputStream.flush();

            Log.d("CMS", "POST params is " + encodedMsg);
            Log.d("CMS", "RESPONSE CODE is " + httpsURLConnection.getResponseCode());

            result = JsonContainer(httpsURLConnection.getInputStream());

        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

//    private void setUrl(String _url){
//
//    }

    private String JsonContainer(InputStream is){

        //TODO:JSON PARSING
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] byteBuffer = new byte[1024];
        byte[] byteData;
        int length;
        String resultVal = "";

        try {
            while((length = is.read(byteBuffer, 0, byteBuffer.length)) != -1){
                byteArrayOutputStream.write(byteBuffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byteData = byteArrayOutputStream.toByteArray();
        String response = new String(byteData);

        try {
            JSONObject responseJSON = new JSONObject(response);
            resultVal = (String)responseJSON.get("result");
            return resultVal;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return resultVal;
    }

    private String encodeString(Properties params) {
        StringBuffer sb = new StringBuffer(256);
        Enumeration names = params.propertyNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = params.getProperty(name);
            sb.append(URLEncoder.encode(name) + "=" + URLEncoder.encode(value) );

            if (names.hasMoreElements()) sb.append("&");
        }
        return sb.toString();
    }

//    private static void trustAllHosts() {
//        // Create a trust manager that does not validate certificate chains
//        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
//            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                return new java.security.cert.X509Certificate[]{};
//            }
//
//            @Override
//            public void checkClientTrusted(
//                    java.security.cert.X509Certificate[] chain,
//                    String authType)
//                    throws java.security.cert.CertificateException {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void checkServerTrusted(
//                    java.security.cert.X509Certificate[] chain,
//                    String authType)
//                    throws java.security.cert.CertificateException {
//                // TODO Auto-generated method stub
//
//            }
//        }};
//
//        // Install the all-trusting trust manager
//        try {
//            SSLContext sc = SSLContext.getInstance("TLS");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//            HttpsURLConnection
//                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
