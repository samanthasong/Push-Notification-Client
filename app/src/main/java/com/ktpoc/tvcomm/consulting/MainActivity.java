package com.ktpoc.tvcomm.consulting;

import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//import android.content.Context;

public class MainActivity extends AppCompatActivity {

    private final String _TAG = "[SONG-MAIN PAGE]";
    private final Handler handler = new Handler();
    private final String THIS_ANDROID_APP = "consultingAndroid";
    public WebView mWebView;
    private Boolean _isInitialized = false;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private final String _url = "https://amuzlab.iptime.org:3000/users/amuzlabtest";
    //private final String _url = "https://172.30.1.58:3000/consulting/amuzlab";
    //private final String _url = "https://tvcomm.dlinkddns.com:3000/consulting/amuzlab";
    //private String _url = "http://naver.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.web_view);

        //TODO: Consulting


        //TODO: user input type detection needs to be done firstly

        //TODO: ID 발급 받기
        //KPNS 2.1.5
        /*
        KPNSApis.requestInstance(this, new OnKPNSInitializeEventListener() {
            @Override
            public void onSuccessInitialize(KPNSApis kpnsApis) {
                kpnsApis.register("0WW4I105s0", "TVConsulting01");
                Log.d(_TAG, "KPNS API 초기화 성공");
                kpnsApis.getConnectionState();
                //TODO: when Token received from push server, this value needs to be saved to Consulting Server or Local Storage
                _isInitialized = true;
                if (Prefs.isMainLibApp(MainActivity.this) == true) {
                    Log.d(_TAG, "THIS IS CONNECTED TO KPNS SERVER");
                } else {
                    Log.d(_TAG, "ANOTHER APP CONNECTED TO KPNS SERVER");
                }
            }

            @Override
            public void onFailInitialize() {
                Log.d(_TAG, "KPNS API 초기화 실패");
               // startService(new Intent(MainActivity.this, PushClientService.class).setAction(Constant.ACTION_START_SERVICE));
            }
        });
        */
        //for KPNS 2.1.2
//        KPNSApis.requestInstance(Context context, new OnKPNSInitializeEventListener() {
//
//            @Override
//            public void onSuccessInitialize(KPNSApis kpnsApis) {
//                kpnsApis.register("0WW4I105s0", "TVConsulting01"); //param: appId, cpId
//            }
//
//            @Override
//            public void onFailInitialize() {
//                Log.d(_TAG, "KPNS initializing failed!!!");
//                startService(new Intent(context, PushClientService.class).setAction(Constant.ACTION_START_SERVICE));
//                //phone state에 대한 퍼미션 있어야 pushClient로 register하라고 되어 있음 내일하장
////                iif ((context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
////                    startService(new Intent(MainActivity.this, PushClientService.class).setAction(Constant.ACTION_START_SERVICE));
////                } else { }
//            }
//        });
        //TODO: connect status request

        //TODO: 토큰 서버 등록 --> CMS 개발 전까지는token값 그대로 이용
        //TODO: 리시버에서 수신(push receiver) 및 팝업 액티비티 노출기
        //TODO: 메세지 파싱해서 웹뷰 url 인자로 넘겨주
        //WHEN PUSH MESSAGE RECEIVED AND POP UP EVENT CONFIRMED

        //TODO: webview load가 완료되고 사용자 이벤트 액션이 발생하면 이벤트 전달 to Consutling TV Web Application
        mWebView.loadUrl(_url);
        loadWebView(_url);
        Log.d(_TAG, "URL -->" +_url);
        setWebViewSetting();

        String currentUserInput = null;
        //bridgeUserEventToJS(currentUserInput);
    }

    //    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d(_TAG, "onResume calling");
//        Bundle extras = getIntent().getExtras();
//        if(extras!=null){
//            String url = extras.getString("url");
//            _url = url;
//            loadWebView(_url);
//            setWebViewSetting();
//        }else{
//            Log.d(_TAG, "No URL LOADED");
//        }
//    }
    private void loadWebView(String url) {


        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        request.grant(request.getResources());
                        Log.d(_TAG, "Permission request");
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

    }

    private void setWebViewSetting() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        mWebView.setWebContentsDebuggingEnabled(true);
        //mWebView.addJavascriptInterface(new AndroidBridge(), THIS_ANDROID_APP);
        Log.d(_TAG, "CHROME VERSION : " + mWebView.getSettings().getUserAgentString());
    }


    /* JS call the Android's function */
     /*
        [USAGE -  Web Application에서의 Android's function call usage]
        - window.consultingAndroid.함수();
        - 이 떄 함수는 AndroidBridge 클래스 내 멤버메소드로용~
     */
    private class AndroidBridge {
        @JavascriptInterface
        public void sendResponseFromJS(final String res) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d(_TAG, "JS sent a responses to this Android --> " + res);
                    //TODO: Insert code when dealing with Consulting TV Web Application RESPONSES(res)
                }
            });
        }
    }

    /* Android call the JS's function */
    private void bridgeUserEventToJS(String userInput) {
        userInput = "USER EVENT IS : Pointing 2nd Div object";
        mWebView.loadUrl("javascript:onReceiveFromAndroid('USER EVENT SENDING...");
    }

}
