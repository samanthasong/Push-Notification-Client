package com.ktpoc.tvcomm.consulting;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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
public class AndroidHttp{

    private String mUrlStr;
    private URL mUrl;
    private HttpsURLConnection mHttpsURLConnection;
    private InputStream mInputStream;
    private DataOutputStream mOutputStream;

    public AndroidHttp(String _url){
        mUrlStr = _url;
        mUrl = null;
        mHttpsURLConnection = null;
        mInputStream = null;

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


    public String postCMS (String api, Properties prop){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String encodedMsg;
        String tmpUrlStr;
        tmpUrlStr = mUrlStr;
        setHTTPSSettings();
        encodedMsg= encodeString(prop);
        tmpUrlStr += api;
        Log.d("CMS", "URL is --> " + tmpUrlStr);
        Log.d("CMS", "property is --> " + encodedMsg);

            try{
                mUrl = new URL(tmpUrlStr);
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            try{
                mHttpsURLConnection = (HttpsURLConnection)mUrl.openConnection();
                mHttpsURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
                mHttpsURLConnection.setRequestMethod("POST");
                mHttpsURLConnection.setDoInput(true);
                mHttpsURLConnection.setDoOutput(true);
                mOutputStream = new DataOutputStream(mHttpsURLConnection.getOutputStream());
                mOutputStream.writeBytes(encodedMsg);

                mOutputStream.flush();
                mInputStream = mHttpsURLConnection.getInputStream();

            }catch (IOException e){
                e.printStackTrace();
            }

        return doJSONParser(mInputStream);
    }

    private String encodeString(Properties params) {
        StringBuffer sb = new StringBuffer(256);
        Enumeration names = params.propertyNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = params.getProperty(name);
            sb.append(URLEncoder.encode(name) + "=" + URLEncoder.encode(value));

            if (names.hasMoreElements()) sb.append("&");
        }
        return sb.toString();
    }

    private String doJSONParser(InputStream is){

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
            JSONArray jsonArray = new JSONArray(response); // JSONArray 생성
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jObject = jsonArray.getJSONObject(i);  // JSONObject 추출
                resultVal = jObject.getString("result");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("CMS", "result str is --> " +response);
        Log.d("CMS", "result val is --> " +resultVal);

        return resultVal;
    }
}
