package com.ktpoc.tvcomm.consulting;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Properties;


public class MainActivity extends Activity{

    private final String _TAG = "[MAIN PAGE]";
    private String registerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ViewManager.getInstance().addActivity(this);
        ViewManager.getInstance().printActivityListSofar();

        registerId = ((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.ktpoc.tvcomm.consulting.user.action");
        intentFilter.addAction("com.ktpoc.tvcomm.consulting.send.token");
        //intentFilter.addAction("");
        intentFilter.addCategory("android.intent.category.DEFAULT");

        registerReceiver(receiver, intentFilter);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.ktpoc.tvcomm.consulting.send.token")){
                Bundle extras = intent.getExtras();
                if(extras != null){

                }
            }
            if(intent.getAction().equals("com.ktpoc.tvcomm.consulting.user.action")){
                Bundle extras = intent.getExtras();
                switch (extras.getString("actId")){
                    case "502": //prev

                        break;
                    case "504": //mymenu
                        String result = checkRegistration();
                        switch (result){
                            case "true":
                                Intent i = new Intent(context, MyMenuActivity.class);
                                // i.putExtra("userId", ""); //TODO: it must be checked what params would be needed
                                startActivity(i);
                                break;

                            case "false":
                                Toast t  = Toast.makeText(context, "컨설팅 서비스 회원가입이 필요합니다", Toast.LENGTH_LONG);
                                t.show();
                                //TODO: 회원 가입 하는 페이지로 넘길지??
                                break;

                            case "error":
                                Log.d(_TAG, "deviceAuthSelect in Main Activity results error");
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }
            if(intent.getAction().equals("")){

            }
            if(intent.getAction().equals("")){

            }
        }
    };

    public String checkRegistration(){
        String result;
        AndroidHttp httpsClient = new AndroidHttp(getResources().getString(R.string.consulting_server_url));

        Properties prop = new Properties();

        final String registrationId = ((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        prop.setProperty("registerid", registrationId);

        result = httpsClient.postCMS("deviceAuthSelect?", prop, true);
        return result;
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

        Intent i = new Intent(this, ScreenShareActivity.class);
        startActivity(i);
        //TODO: check
//        Bundle extra = getIntent().getExtras();
//        if(extra != null){ // push pop up call main
//            String url = extra.getString("message");
//            Intent i = new Intent(this, ScreenShareActivity.class);
//            i.putExtra("url", url);
//            startActivity(i);
//        }else{ // call main from intent or launcher
//            //Intent i = new Intent(this, HomeActivity.class);
//            Intent i = new Intent(this, ScreenShareActivity.class);
//            //Intent i = new Intent(this, MyMenuActivity.class)
//
//            startActivity(i);
//        }

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

        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
