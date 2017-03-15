package com.example.bjc.bjcp2pdemo.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.bjc.bjcp2pdemo.bean.User;
import com.example.bjc.bjcp2pdemo.common.AppManager;
import com.loopj.android.http.AsyncHttpClient;

import butterknife.ButterKnife;

/**
 * Created by 毕静存 on 2016/12/6.
 */

public abstract class BaseActivity extends FragmentActivity {
    public AsyncHttpClient httpClient = new AsyncHttpClient();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.inject(this);
        //将当前的activity添加到自己的栈空间中
        AppManager.getInstance().add(this);

        initTitle();
        initData();
    }

    protected abstract void initData();

    protected abstract void initTitle();

    public abstract int getLayoutId();
    public void goToActivity(Class activity,Bundle bundle){
        Intent intent=new Intent(this,activity);
        if(bundle!=null){
            intent.putExtra("data",bundle);
        }
        startActivity(intent);
    }

    /**
     * 读取登录用户的保存信息；
     */
    public User readUserInfo() {
        User user=new User();
        SharedPreferences userinfo = getSharedPreferences("userinfo", MODE_PRIVATE);
        user.UF_ACC = userinfo.getString("UF_ACC", "");
        user.UF_AVATAR_URL=userinfo.getString("UF_AVATAR_URL", "");
        user.UF_IS_CERT=userinfo.getString("UF_IS_CERT", "");
        user.UF_PHONE=userinfo.getString("UF_PHONE", "");
       return user;
    }
    /**
     * 关闭当前的Activity的方法；
     */
    public void closeCurrentActivity(){
        AppManager.getInstance().removetop();
    }
}
