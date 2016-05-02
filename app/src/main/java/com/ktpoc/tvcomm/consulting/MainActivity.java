package com.ktpoc.tvcomm.consulting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ktpns.lib.KPNSApis;
import com.ktpns.lib.OnKPNSInitializeEventListener;
import com.ktpns.lib.service.PushClientService;
import com.ktpns.lib.util.Constant;
import com.ktpns.lib.util.Prefs;


public class MainActivity extends AppCompatActivity {

    private final String _TAG = "[SONG-MAIN PAGE]";
    private Boolean isRegistered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //CMS api
        isRegistered = false;

//        mWebView.loadUrl(_url);
//

    }
    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();

        Intent i;
        String server_url = getResources().getString(R.string.consulting_server_url);
        AndroidHttp httpClient = new AndroidHttp(server_url);
        httpClient.requestCMS("deviceAuthSelect", "?regId=REGISTRATION_ID");
        if(isRegistered){
            //USER REGISTER COMPLETE, GOTO HOME PAGE
            i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
        }else{
            //TODO: register
            if(registerToKPNS()!= true){
                //TODO: go to User Register Activity
                i = new Intent(MainActivity.this, UserRegisterActivity.class);
                startActivity(i);

            }else{

            }
        }
    }

    private Boolean registerToKPNS(){
        //KPNS 2.1.5
        ///*
        KPNSApis.requestInstance(this, new OnKPNSInitializeEventListener() {
            @Override
            public void onSuccessInitialize(KPNSApis kpnsApis) {
                kpnsApis.register("0WW4I105s0", "TVConsulting01");
                Log.d(_TAG, "KPNS API 초기화 성공");
                kpnsApis.getConnectionState();
                //TODO: when Token received from push server, this value needs to be saved to Consulting Server or Local Storage
                if (Prefs.isMainLibApp(MainActivity.this) == true) {
                    Log.d(_TAG, "THIS IS CONNECTED TO KPNS SERVER");
                } else {
                    Log.d(_TAG, "ANOTHER APP CONNECTED TO KPNS SERVER");
                }
            }

            @Override
            public void onFailInitialize() {
                Log.d(_TAG, "KPNS API 초기화 실패");
                 startService(new Intent(MainActivity.this, PushClientService.class).setAction(Constant.ACTION_START_SERVICE));
            }
        });
        //*/
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
        return true;
        }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
