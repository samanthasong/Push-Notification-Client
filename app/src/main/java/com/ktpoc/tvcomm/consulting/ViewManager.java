package com.ktpoc.tvcomm.consulting;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by insam on 2016. 5. 13..
 */

public class ViewManager extends Application{
    private static ViewManager actListInstance = null;
    private ArrayList<Activity> mArrayList;
    private int mIdx;
    private String mUser;
    private String mCategory;
    private String mExpert;
    private String mUrl;
    private String mRoomId;

    public static synchronized ViewManager getInstance() {
        if(actListInstance == null)
            actListInstance = new ViewManager();
        return actListInstance;
    }

    private ViewManager() {
        this.mArrayList = new ArrayList<>();
        mIdx = 0;
        mUser = "";
        mCategory = "";
        mExpert = "";
        mUrl = "";
        mRoomId = "";
    }

    public void addActivity(Activity activity){
        this.mArrayList.add(activity);
        mIdx++;
    }

    public void removeActivity(Activity activity){
        this.mArrayList.remove(activity);
        mIdx--;
    }

    public void removeAllActivity(){
        for(int i = 0; i < mArrayList.size(); i++){
            mArrayList.get(i).finish();
        }
        mIdx = 0;
    }

    public void printActivityListSofar(){
        for(int i = 0; i < mArrayList.size(); i++){
            Log.d("[ACTIVITY LIST]", "[" + i + "]" + mArrayList.get(i).getComponentName().toString());
        }
    }

    public int getNumberOfActivities(){
        return mIdx;
    }

    public Activity getPrevActivity(int idx){
        int i = idx -1;
        return mArrayList.get(i);
    }

    public void launchActivity(Context context, Class<?> cls, Bundle extra){
        Intent i = new Intent(context, cls);
        i.putExtras(extra);
        context.startActivity(i);
    }
    public Activity getActivity(int i){

        return mArrayList.get(i);
    }

    public String getParameter(String parameter){
        switch (parameter){
            case "EXPERT":
                return mExpert;
            case "CATEGORY":
                return mCategory;
            case "USER":
                return mUser;
            case "URL":
                return mUrl;
            case "ROOM_ID":
                return mRoomId;
            default:
                return "";
        }
    }

    public void setParameter(String parameter, String value){
        switch (parameter){
            case "EXPERT":
                mExpert = value;
                break;

            case "CATEGORY":
                mCategory = value;
                break;

            case "USER":
                mUser = value;
                break;

            case "URL":
                mUrl = value;
                break;

            case "ROOM_ID":
                mRoomId = value;
                break;

            default:
        }
    }
}
