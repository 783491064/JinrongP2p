package com.example.bjc.bjcp2pdemo.util;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.example.bjc.bjcp2pdemo.common.MyApplication;

/**
 * Created by 毕静存 on 2016/11/30.
 */

public class UIUtils {
    public static Context getContext(){
        return MyApplication.mContext;
    }

    public static Handler getHandler(){
        return MyApplication.mHandler;
    }

    public static Thread getThread(){
        return MyApplication.currentThread;
    }

    public static int getThreadId(){
        return MyApplication.currentThreadId;
    }

    /**
     * 获取布局
     */
    public static View xmlToLayout(int layoutId){
        View view=View.inflate(getContext(),layoutId,null);
        return view;
    }
    /**
     * 获取字符数组的数据
     */
    public static String[] getStringArray(int stringArrayId){
        String[] stringArray = getContext().getResources().getStringArray(stringArrayId);
        return stringArray;
    }

    /**
     * dp2px
     */
    public static int dp2px(int dp){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int)(density * dp+0.5);

    }

    /**
     * px2dp
     */
    public static int px2dp(int px){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int)(px/density+0.5);
    }

    /**
     * 获取颜色值
     */
    public static int getColor(int colorId){
        int color = getContext().getResources().getColor(colorId);
        return color;
    }

    /**
     * 运行在主线程的任务
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        if(isMainThred()){//如果是主线程
            runnable.run();
        }else{
            getHandler().post(runnable);
        }
    }

    /**
     *  是不是主线程的判断；
     * @return
     */
    public static boolean isMainThred() {
        int currentThredId=android.os.Process.myTid();
        return currentThredId==MyApplication.currentThreadId;
    }
}
