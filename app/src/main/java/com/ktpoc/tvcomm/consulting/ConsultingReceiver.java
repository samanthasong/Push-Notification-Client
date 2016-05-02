package com.ktpoc.tvcomm.consulting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ConsultingReceiver extends BroadcastReceiver {
    public ConsultingReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        //컨설팅 앱 실행 요청
        if("com.ktpoc.tvcomm.consulting.start".equals(action)){
            try{
                String stateCode = intent.getStringExtra("stateCode");
                //TODO: Do something with stateCode
                Intent i = new Intent(context, com.ktpoc.tvcomm.consulting.HomeActivity.class);
                //TODO: set Extras and flag
                context.startActivity(i);
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        //전문가 리스트 요청
        if("com.ktpoc.tvcomm.consulting.information".equals(action)){
            try{
                String category = intent.getStringExtra("categoryName");
                String expert = intent.getStringExtra("expertName");

                //TODO: Do something with category and expert
                Intent i = new Intent(context, com.ktpoc.tvcomm.consulting.ExpertsViewActivity.class);
                //TODO: set Extras and flag
                i.putExtra("category", category);
                i.putExtra("expert", expert);
                context.startActivity(i);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        //전문가 스케줄 요청
        if("com.ktpoc.tvcomm.consulting.schedule".equals(action)){
            try{
                String expert = intent.getStringExtra("expertName");

                //TODO: Do something with expert
                Intent i = new Intent(context, com.ktpoc.tvcomm.consulting.ScheduleViewActivity.class);
                //TODO: set Extras and flag
                i.putExtra("expert", expert);
                context.startActivity(i);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        //menu 이동 명령
        if("com.ktpoc.tvcomm.consulting.menu".equals(action)){
            try{
                String actId = intent.getStringExtra("actId");

                //TODO: process activity index with actId
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
