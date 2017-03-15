package com.example.bjc.bjcp2pdemo.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by 毕静存 on 2016/11/30.
 */

public class AppManager {
    public static AppManager appManager=new AppManager();
    private AppManager() {}
    public static AppManager getInstance(){
        return appManager;
    }
    private Stack<Activity> stack=new Stack<>();

    /**
     * 栈里的Activity添加
     */
    public void add(Activity activity){
        if(activity!=null){
            stack.add(activity);
        }
    }

    /**
     * 栈里的Activity删除
     */
    public void remove(Activity activity){
        if(activity!=null){
            for(int i=stack.size()-1;i>=0;i--){
                if(activity.getClass()==stack.get(i).getClass()){
                    stack.get(i).finish();//关闭显示
                    stack.remove(i);
                }
            }
        }
    }

    /**
     * 移除栈顶的Activity;
     */
    public void removetop(){
        stack.get(stack.size()-1).finish();//关闭显示
        stack.remove(stack.size()-1);
    }

    /**
     * 移除所有的
     */
    public void removeAll(){
        for(int i=stack.size()-1;i>=0;i--){
            stack.get(i).finish();
            stack.remove(i);
        }
    }

    /**
     * 返回stack的大小
     */
    public int getSize(){
        return stack.size();
    }
}
