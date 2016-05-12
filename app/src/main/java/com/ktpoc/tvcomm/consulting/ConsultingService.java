package com.ktpoc.tvcomm.consulting;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.ktpns.lib.KPNSApis;
import com.ktpns.lib.OnKPNSInitializeEventListener;

import java.util.ArrayList;

public class ConsultingService extends Service {

    private final String _TAG = "[Consulting SERVICE]";

    public static ArrayList<Activity> arrayList = new ArrayList<Activity>();

    public ConsultingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        //TODO: on destroy intent received, kill whole acitivity which was running
        /*
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).finish();
        }
        */
        return START_NOT_STICKY;
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
//                if (Prefs.isMainLibApp(MainActivity.this) == true) {
//                    Log.d(_TAG, "THIS IS CONNECTED TO KPNS SERVER");
//                } else {
//                    Log.d(_TAG, "ANOTHER APP CONNECTED TO KPNS SERVER");
//                }
            }

            @Override
            public void onFailInitialize() {
                Log.d(_TAG, "KPNS API 초기화 실패");
                //startService(new Intent(MainActivity.this, PushClientService.class).setAction(Constant.ACTION_START_SERVICE));
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
    public void onDestroy() {

    }

}
