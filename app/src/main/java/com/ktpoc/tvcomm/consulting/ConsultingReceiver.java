package com.ktpoc.tvcomm.consulting;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.ktpns.lib.service.PushClientService;

import java.util.List;

public class ConsultingReceiver extends BroadcastReceiver {
//    public ConsultingReceiver() {
//    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        //컨설팅 앱 실행 요청
        if ("com.ktpoc.tvcomm.consulting.start".equals(action)) {
            try {
                Log.d("HOHO", "start msg received");
                //String stateCode = intent.getStringExtra("stateCode");
                //TODO: Do something with stateCode
                Intent i = new Intent(context, MainActivity.class);
                //TODO: set Extras and flag
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        //전문가 리스트 요청
        if ("com.ktpoc.tvcomm.consulting.information".equals(action)) {
            try {
//                String category = intent.getStringExtra("categoryName");
//                String expert = intent.getStringExtra("expertName");

                //TODO: Do something with category and expert
                Intent i = new Intent(context, com.ktpoc.tvcomm.consulting.ExpertsViewActivity.class);
                //TODO: set Extras and flag
//                i.putExtra("category", category);
//                i.putExtra("expert", expert);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //전문가 스케줄 요청
        if ("com.ktpoc.tvcomm.consulting.schedule".equals(action)) {
            try {
                String expert = intent.getStringExtra("expertName");

                //TODO: Do something with expert
                Intent i = new Intent(context, com.ktpoc.tvcomm.consulting.ScheduleViewActivity.class);
                //TODO: set Extras and flag
                i.putExtra("expert", expert);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //menu 이동 명령
        if ("com.ktpoc.tvcomm.consulting.menu".equals(action)) {
            try {
                String actId = intent.getStringExtra("actId");

                //TODO: process activity index with actId
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if ("com.ktpoc.tvcomm.finish.consult".equals(action)) {
            try {

                //TODO: destroy consulting app
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /* is it right ???  */
        if ("com.ktpoc.tvcomm.service.check".equals(action)) {
            try {
                if (isRunningService(context, PushClientService.class) == false) {
                    Intent i = new Intent(context, PushClientService.class);
                    context.startService(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isRunningService(Context context, Class<?> cls) {
        boolean isRunning = false;

//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();


//        if (processInfos != null) {
//            for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
////                if (processInfo.getClass().getName()!=null) {
//
//                    String str = processInfo.processName.getClass().toString();
//                    Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
//                    toast.show();
//                    return isRunning;
//               // }
//            }
//        }
//        return isRunning;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> info = activityManager.getRunningServices(Integer.MAX_VALUE);

        if (info != null) {
            for(ActivityManager.RunningServiceInfo serviceInfo : info)
            {

                ComponentName compName = serviceInfo.service;
                String className = compName.getClassName();



                if(className.equals(cls.getName())) {
                    isRunning = true;
                    String testString = "AM_RUNNING_SERVICE is KPNS SERVICE";

                    Toast toast = Toast.makeText(context, testString, Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                }
            }
        }
        return isRunning;
    }
}
