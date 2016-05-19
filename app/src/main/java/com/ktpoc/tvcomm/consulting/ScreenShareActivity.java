package com.ktpoc.tvcomm.consulting;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class ScreenShareActivity extends Activity {

    public WebView mWebView;
    private final String _TAG = "[SCREEN SHARE";

    private final String _url = "https://amuzlab.iptime.org:3000/users/expert_category22?roodId=gaolf7rhe8";
    private ConsultingClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewManager.getInstance().addActivity(this);
        ViewManager.getInstance().printActivityListSofar();

        ///*
        setContentView(R.layout.activity_webview);
        mWebView = (WebView)findViewById(R.id.web_view);
        mClient = new ConsultingClient(_url);
        mClient.setWebviewSettings(mWebView);

        //*/

        //TEST chrome

    }

    @Override
    protected void onResume(){
        super.onResume();
    }
}
