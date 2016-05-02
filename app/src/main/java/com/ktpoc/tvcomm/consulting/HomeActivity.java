package com.ktpoc.tvcomm.consulting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class HomeActivity extends AppCompatActivity {

    public WebView mWebView;
    private final String _TAG ="[HOME ACTIVITY]";
    private final String _url = "https://amuzlab.iptime.org:3000/users/amuzlabtest";
    //private final String _url = "https://172.30.1.58:3000/consulting/amuzlab";
    //private final String _url = "https://tvcomm.dlinkddns.com:3000/consulting/amuzlab";

    ConsultingClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mWebView = (WebView)findViewById(R.id.web_view);

    }

    @Override
    protected void onResume(){
        super.onResume();
//        Bundle extras = getIntent().getExtras();
//        if(extras!=null){
//
//        }
        mWebView.loadUrl(_url);
        client = new ConsultingClient(mWebView);
        String currentUserInput = null;
        //client.bridgeUserEventToJS(currentUserInput, mWebView);
    }


    /*
   [USAGE -  Web Application에서의 Android's function call usage]
   - window.consultingAndroid.함수();
   - 이 떄 함수는 AndroidBridge 클래스 내 멤버메소드로용~
*/


    /* Android call the JS's function */

}
