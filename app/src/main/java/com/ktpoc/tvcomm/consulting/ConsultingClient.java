package com.ktpoc.tvcomm.consulting;

import android.net.http.SslError;
import android.util.Log;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by insam on 2016. 5. 2..
 */
public class ConsultingClient {
    private final String _TAG = "[WEBVIEW]";
    private final String THIS_ANDROID_APP = "[CONSULTING ANDROID]";

    public ConsultingClient(WebView webview){
        setWebviewSettings(webview);

    }
    private void setWebviewSettings(WebView wv){
        wv.setWebChromeClient(new WebChromeClient() {
            @Override
        public void onPermissionRequest(final PermissionRequest request){
                request.grant(request.getResources());
            }
//
//            @Override
//            public void onPermissionRequest(final PermissionRequest request) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        request.grant(request.getResources());
//                        Log.d(_TAG, "Permission request");
//                    }
//                });
//            }

        });

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setMediaPlaybackRequiresUserGesture(false);
        wv.setWebContentsDebuggingEnabled(true);
        wv.addJavascriptInterface(new AndroidBridge(), THIS_ANDROID_APP);
        Log.d(_TAG, "CHROME VERSION : " + wv.getSettings().getUserAgentString());
    }

    public void bridgeUserEventToJS(String userInput, WebView wv) {
        userInput = "USER EVENT IS : Pointing 2nd Div object";
        wv.loadUrl("javascript:onReceiveFromAndroid('USER EVENT SENDING...");
    }

}
