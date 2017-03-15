package com.example.bjc.bjcp2pdemo.activity;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.bjc.bjcp2pdemo.R;
import com.example.bjc.bjcp2pdemo.base.BaseActivity;
import com.example.bjc.bjcp2pdemo.bean.User;
import com.example.bjc.bjcp2pdemo.common.AppManager;
import com.example.bjc.bjcp2pdemo.common.AppNetConfig;
import com.example.bjc.bjcp2pdemo.util.MD5Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 毕静存 on 2016/12/6.
 */
public class LoginActivity extends BaseActivity {


    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ib_setting)
    ImageButton ibSetting;
    @InjectView(R.id.textView1)
    TextView textView1;
    @InjectView(R.id.log_ed_mob)
    EditText logEdMob;
    @InjectView(R.id.about_com)
    RelativeLayout aboutCom;
    @InjectView(R.id.tv_2)
    TextView tv2;
    @InjectView(R.id.log_ed_pad)
    EditText logEdPad;
    @InjectView(R.id.log_log_btn)
    Button logLogBtn;
    private User user;

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        ibBack.setVisibility(View.VISIBLE);
        tvTitle.setText("登录");
        ibSetting.setVisibility(View.GONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.ib_back, R.id.log_log_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                AppManager.getInstance().removeAll();
                goToActivity(MainActivity.class,null);
                break;
            case R.id.log_log_btn://点击登录
                //1交验用户名和密码
                String name = logEdMob.getText().toString().trim();
                String password = logEdPad.getText().toString().trim();
                if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(password)){
                    RequestParams params=new RequestParams();
                    params.put("username", MD5Utils.MD5(name));
                    params.put("password", MD5Utils.MD5(password));
                    httpClient.post(AppNetConfig.LOGIN,params,new AsyncHttpResponseHandler(){
                        @Override
                        public void onSuccess(String content) {
                            Log.e("LogInActivity", "登录成功: "+AppNetConfig.LOGIN+"===="+content );
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            //数据的解析 用到了fastJSON
                            JSONObject jsonObject = JSON.parseObject(content);
                            Boolean isSuccess = jsonObject.getBoolean("success");
                            if(isSuccess){
                                String data = jsonObject.getString("data");
                                user = JSON.parseObject(data, User.class);
                                saveUser();//保存
                                AppManager.getInstance().removeAll();
                                goToActivity(MainActivity.class,null);
                            }else{
                                Toast.makeText(LoginActivity.this, "用户名或密码不匹配", Toast.LENGTH_SHORT).show();
                            }

                        }
                        @Override
                        public void onFailure(Throwable error, String content) {
                            Log.e("LogInActivity", "联网失败: "+AppNetConfig.LOGIN+"===="+error.getMessage() );
                            Toast.makeText(LoginActivity.this,"联网失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(this,"账户和密码不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 保存用户的信息
     */
    private void saveUser() {
        SharedPreferences userinfo = getSharedPreferences("userinfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = userinfo.edit();
        editor.putString("UF_ACC", user.UF_ACC);
        editor.putString("UF_AVATAR_URL", user.UF_AVATAR_URL);
        editor.putString("UF_IS_CERT", user.UF_IS_CERT);
        editor.putString("UF_PHONE", user.UF_PHONE);
        editor.commit();
    }
}
