package com.example.bjc.bjcp2pdemo.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by 毕静存 on 2016/11/29.
 */

public class MyApplication extends Application {
    public static Context mContext;
    public static Handler mHandler;
    public static Thread currentThread;
    public static int currentThreadId;
    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext=getApplicationContext();
        this.mHandler=new Handler();
        this.currentThread=Thread.currentThread();//主线程；
        this.currentThreadId=android.os.Process.myTid();//线程的ID;
//        CrashHandler.getInxtance().init(mContext);
    }
}
