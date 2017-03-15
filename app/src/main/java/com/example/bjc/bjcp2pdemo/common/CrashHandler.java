package com.example.bjc.bjcp2pdemo.common;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 毕静存 on 2016/11/30.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    //单利的模式创建对象
    public static CrashHandler crashHandler=null;
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;//异常捕获的默认的处理器
    private Context context;
    private CrashHandler(){}
    public static CrashHandler getInxtance(){
        if(crashHandler==null){
            crashHandler=new CrashHandler();
        }
        return crashHandler;
    }

    /**
     * 初始化的方法；
     * @param
     * @param
     */
    public void init(Context context){
        this.context=context;
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //异常的捕获显示给客户提高体验度
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context,"程序出现异常了",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        collectionEX(e);
        // 关闭应用
        try {
            Thread.sleep(2000);
            AppManager.getInstance().removeAll();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 把获取的异常发送给后台
     * @param e
     */
    private void collectionEX(Throwable e) {
        final String deviceMessage= Build.PRODUCT+"--"+Build.DEVICE+"--"+Build.MODEL+"--"+Build.VERSION.SDK_INT;
        final String exMessage=e.getMessage();

        new Thread(){
            @Override
            public void run() {
                Log.e("TAG",exMessage);
                Log.e("TAG",deviceMessage);
            }
        }.start();
    }
}
