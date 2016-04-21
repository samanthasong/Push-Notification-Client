package com.ktpoc.tvcomm.consulting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private final String _TAG = "GiGA Genie / MAIN_PAGE";

    //private final String _url = "https://amuzlab.iptime.org:3000/users/amuzlab";
    private final String _url = "https://tvcomm.dlinkddns.com:3000/consulting/amuzlab";

    private WebView mWebView = (WebView)findViewById(R.id.web_view);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //register ID for KPNS
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.tta.push.intent.receive.REGISTRATION");
        registerReceiver(receiver, filter);

        //joining the room via webview


        loadWebView(_url);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if("com.tta.push.intent.receive.REGISTRATION".equals(action)){
                try{
                    byte[] token = intent.getByteArrayExtra("token");
                    String error = intent.getStringExtra("error");
                    String tokenStr = new String(token, "UTF-8");
                    Log.d(_TAG, "Token is --> " + tokenStr + ", error -->" + error);
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else if ("com.ktpns.pa.receive.REGISTRATION".equals(action)){
                Log.d(_TAG, "REGISTRATION result  --> " + intent.getIntExtra("result", -1));
                Log.d(_TAG, "REGISTRATION regId  --> " + intent.getStringExtra("regId"));
                Log.d(_TAG, "REGISTRATION detail  --> " + intent.getStringExtra("detail"));
            }
        }
    };

    private void loadWebView(String url){

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.d("SONG", "onPermissionRequest");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        request.grant(request.getResources());
                    }
                });
            }

        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        mWebView.loadUrl(url);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        Log.d(_TAG, "CHROME VERSION : " + mWebView.getSettings().getUserAgentString());
    }
}
