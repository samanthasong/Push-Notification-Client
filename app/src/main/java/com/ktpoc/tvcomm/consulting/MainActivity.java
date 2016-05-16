package com.ktpoc.tvcomm.consulting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ktpns.lib.KPNSApis;
import com.ktpns.lib.OnKPNSInitializeEventListener;
import com.ktpns.lib.service.PushClientService;
import com.ktpns.lib.util.Constant;

import java.util.Properties;


public class MainActivity extends Activity{

    private final String _TAG = "[MAIN PAGE]";

    private String server_url;
    private String deviceAuthResult = "";
    private AndroidHttp httpsClient;
    private Properties prop;
    private String registrationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ViewManager.getInstance().addActivity(this);
        ViewManager.getInstance().printActivityListSofar();

        super.onCreate(savedInstanceState);

        httpsClient = new AndroidHttp(server_url);
        prop = new Properties();
        server_url  = getResources().getString(R.string.consulting_server_url);
        registrationId = getResources().getString(R.string.imei);
    }
    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected  void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        Log.d(_TAG, "onNewIntent");
        if(intent != null){
            setIntent(intent);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        //TODO: check

        checkRegistration();

    }

    private void checkRegistration(){
        prop.setProperty("registerid", registrationId);
        deviceAuthResult = httpsClient.postCMS("deviceAuthSelect?", prop, true);

        switch (deviceAuthResult) {
            case "true": // Customer already registered once before
                Log.d(_TAG, "deviceAuthSelect result is -->" + deviceAuthResult);
                //5.4. https test blocking go to home activity
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
                break;
            case "false": // Customer never registered
                Log.d(_TAG, "deviceAuthSelect result is -->" + deviceAuthResult);
                //registerToKPNS();
                Intent intent = new Intent(MainActivity.this, UserRegisterActivity.class);
                startActivity(intent);
                break;
            case "error":
                Log.d(_TAG, "deviceAuthSelect result is -->" + deviceAuthResult);
                break;
            default:
                break;
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
//                kpnsApis.getConnectionState();
//                //TODO: when Token received from push server, this value needs to be saved to Consulting Server or Local Storage
//                if (Prefs.isMainLibApp(MainActivity.this) == true) {
//                    Log.d(_TAG, "THIS IS CONNECTED TO KPNS SERVER");
//                } else {
//                    Log.d(_TAG, "ANOTHER APP CONNECTED TO KPNS SERVER");
//                }
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
    protected void onPause(){
        super.onPause();
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
