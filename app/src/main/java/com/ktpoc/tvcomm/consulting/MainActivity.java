package com.ktpoc.tvcomm.consulting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ktpns.lib.KPNSApis;
import com.ktpns.lib.OnKPNSInitializeEventListener;
import com.ktpns.lib.service.PushClientService;
import com.ktpns.lib.util.Constant;
import com.ktpns.lib.util.Prefs;


public class MainActivity extends Activity {

    private final String _TAG = "[MAIN PAGE]";
    private String server_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected  void onNewIntent(Intent intent){
        super.onNewIntent(intent);
//        Toast t = Toast.makeText(this, "token passed to onNewIntent()", Toast.LENGTH_SHORT);
//        t.show();
//        if(intent != null){
//            setIntent(intent);
//        }
    }

    @Override
    protected void onResume(){
        super.onResume();


      //  /* server part included
//        server_url  = getResources().getString(R.string.consulting_server_url);
//        AndroidHttp httpClient = new AndroidHttp(server_url);
//        Properties prop = new Properties();

        //deviceAuthSelect API

        Toast t2 = Toast.makeText(this, "MAIN RESUME", Toast.LENGTH_SHORT);
        t2.show();

        //2016.05.09 test
        registerToKPNS();

//
//        prop.setProperty("registerid", "20160504");
//        String res = httpClient.postCMS("deviceAuthSelect?", prop);
//
//        if(res == "true"){
//            //USER REGISTER COMPLETE, GOTO HOME PAGE
//            Toast t = Toast.makeText(this, "ALREADY REGISTERED BEFORE", Toast.LENGTH_SHORT);
//            t.show();
//            //5.4. https test blocking go to home activity
//            Intent i = new Intent(MainActivity.this, HomeActivity.class);
//            startActivity(i);
//        }else if(res == "false"){
//            //TODO: register
//            registerToKPNS();
//                //TODO: go to User Register Activity
//            String tokenStr = "";
//            Bundle extras = getIntent().getExtras();
//            if(extras != null){
//                tokenStr = extras.getString("token");
//            }
//
//            //deviceInsert API
//            Properties properties = new Properties();
//            properties.setProperty("registerid", "20160504");
//            properties.setProperty("tokenValue", tokenStr);
//            properties.setProperty("devicetype", "2");
//            properties.setProperty("nickname", "samsam");
//            properties.setProperty("phone", "01044447777");
//            String result = httpClient.postCMS("deviceInsert?", properties);
//            if(result == "true"){
//                Toast toast = Toast.makeText(this, "REGISTER TO CMS SUCCEED", Toast.LENGTH_SHORT);
//                toast.show();
//
//            }
//
//            Intent i = new Intent(MainActivity.this, UserRegisterActivity.class);
//            startActivity(i);
//        }else{
//            Log.d(_TAG, "HTTP POST ERROR");
//        }

        //*/

        ///* TEST
//        i = new Intent(MainActivity.this, HomeActivity.class);
//        startActivity(i);
        //*/
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
