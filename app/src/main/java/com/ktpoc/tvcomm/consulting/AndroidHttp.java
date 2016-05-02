package com.ktpoc.tvcomm.consulting;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;


/**
 * Created by insam on 2016. 5. 2..
 */
public class AndroidHttp {

    private URL u;
    private String url ;
    private DataInputStream mInputStream;
    private DataOutputStream mOutputStream;
    private String result;

    public AndroidHttp(String _url){
        this.url = _url;
        this.result = "error";
    }

    public String postCMS(String api, Properties prop){
        String encodedMsg = encodeString(prop);
        url += api;
        setUrl(url);
        try{
            HttpURLConnection conn = (HttpURLConnection)u.openConnection();
            conn.setRequestMethod("POST");

            conn.setDoInput(true);
            conn.setDoOutput(true);
            mOutputStream = new DataOutputStream(conn.getOutputStream());
            mOutputStream.writeBytes(encodedMsg);
            mOutputStream.flush();

            result = JsonContainer(conn.getInputStream());

        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    private void setUrl(String _url){
        try{
            this.u = new URL(_url);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    private String JsonContainer(InputStream is){

        //TODO:JSON PARSING
        return is.toString();
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
}
