package com.ktpoc.tvcomm.consulting;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ktpns.lib.KPNSApis;
import com.ktpns.lib.OnKPNSInitializeEventListener;
import com.ktpns.lib.service.PushClientService;
import com.ktpns.lib.util.Constant;

import java.util.Properties;

public class UserRegisterActivity extends Activity {

    private String _TAG = "[USER REGISTER]";

    private EditText mPhoneEditTxt, mNameEditTxt;
    private Button mRegisterBtn;
    private RadioGroup mDeviceradioGroup;
    private RadioButton mGenieRadioBtn, mPhoneRadioBtn;
    private String m_server_url;
    private String result;
    private AndroidHttp httpsClient;

    private String tokenStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        ViewManager.getInstance().addActivity(this);
        ViewManager.getInstance().printActivityListSofar();

        mPhoneEditTxt = (EditText)findViewById(R.id.phone_edit_text);
        mNameEditTxt = (EditText)findViewById(R.id.name_edit_text);
        mRegisterBtn= (Button)findViewById(R.id.register_button);
        mDeviceradioGroup = (RadioGroup)findViewById(R.id.device_radio_group);
        mGenieRadioBtn = (RadioButton)findViewById(R.id.genie_radio_btn);
        mPhoneRadioBtn = (RadioButton)findViewById(R.id.phone_radio_btn);

        m_server_url = getResources().getString(R.string.consulting_server_url);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.ktpoc.tvcomm.consulting.send.token");
        intentFilter.addCategory("android.intent.category.DEFAULT");
        registerReceiver(receiver, intentFilter);
        tokenStr = "";
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        Log.d(_TAG, "onNewIntent");
        if(intent != null){
            setIntent(intent);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        mPhoneEditTxt.setText("01032490813");
        mNameEditTxt.setText("samsami");
    }
    @Override
    protected void onResume(){

        super.onResume();
        Log.d(_TAG, "onResume called~~~~");
        if(tokenStr == null)
            registerToKPNS();


//        Bundle extras = getIntent().getExtras();
//        if(extras != null) {
//            tokenStr = extras.getString("token");
//        }else{
//            Log.d(_TAG, "TOKEN STRING NOT YET RECEIVED");
//        }

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.ktpoc.tvcomm.consulting.send.token")){
                Bundle extras = intent.getExtras();
                if(extras != null){
                    tokenStr = extras.getString("token");
                    Log.d(_TAG, "token string is --> " + tokenStr);
                }
            }

        }
    };
    public void onClickRegister(View v) {

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.loading_bar);
        progressBar.setVisibility(View.VISIBLE);

        synchronized (tokenStr) {

            String phoneNum, nickName, deviceType, regId;
            regId = getResources().getString(R.string.imei);
            deviceType = "2";

            phoneNum = mPhoneEditTxt.getText().toString();
            nickName = mNameEditTxt.getText().toString();
            AndroidHttp httpsClient = new AndroidHttp(m_server_url);
            Properties properties = new Properties();
            properties.setProperty("registerid", regId);
            properties.setProperty("tokenvalue", tokenStr);
            if (mDeviceradioGroup.getCheckedRadioButtonId() == R.id.phone_radio_btn) {
                deviceType = "1"; //default is 2 (Genie box)
            }
            properties.setProperty("devicetype", deviceType);
            properties.setProperty("nickname", nickName);
            properties.setProperty("phone", phoneNum);

            result = httpsClient.postCMS("deviceInsert?", properties, false);
            Log.d(_TAG, "deviceInsert result is --> " + result);
            switch (result) {
                case "true":
                    Toast toast = Toast.makeText(this, "REGISTER TO CMS SUCCEED", Toast.LENGTH_SHORT);
                    toast.show();
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                case "false":
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                default:
                    break;
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
                startService(new Intent(UserRegisterActivity.this, PushClientService.class).setAction(Constant.ACTION_START_SERVICE));
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
    protected void onDestroy(){
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
