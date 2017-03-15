package com.example.bjc.bjcp2pdemo.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bjc.bjcp2pdemo.R;
import com.example.bjc.bjcp2pdemo.activity.AssetsActivity;
import com.example.bjc.bjcp2pdemo.activity.ChongZhiActivity;
import com.example.bjc.bjcp2pdemo.activity.LoginActivity;
import com.example.bjc.bjcp2pdemo.activity.SeettingActivity;
import com.example.bjc.bjcp2pdemo.activity.TiXianActivity;
import com.example.bjc.bjcp2pdemo.activity.ToZiActivity;
import com.example.bjc.bjcp2pdemo.activity.ToZiGuanLiActivity;
import com.example.bjc.bjcp2pdemo.base.BaseActivity;
import com.example.bjc.bjcp2pdemo.base.BaseFragment;
import com.example.bjc.bjcp2pdemo.bean.User;
import com.example.bjc.bjcp2pdemo.util.BitmapUtils;
import com.example.bjc.bjcp2pdemo.util.UIUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 毕静存 on 2016/11/29.
 */

public class MainMoneyFramgent extends BaseFragment {


    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ib_setting)
    ImageButton ibSetting;
    @InjectView(R.id.imageView1)
    ImageView imageView1;
    @InjectView(R.id.icon_time)
    RelativeLayout iconTime;
    @InjectView(R.id.textView11)
    TextView textView11;
    @InjectView(R.id.relativeLayout1)
    RelativeLayout relativeLayout1;
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.recharge)
    ImageView recharge;
    @InjectView(R.id.withdraw)
    ImageView withdraw;
    @InjectView(R.id.ll_touzi)
    TextView llTouzi;
    @InjectView(R.id.ll_touzi_zhiguan)
    TextView llTouziZhiguan;
    @InjectView(R.id.ll_zichang)
    TextView llZichang;
    @InjectView(R.id.ll_zhanquan)
    TextView llZhanquan;

    protected void initTitle() {
        ibBack.setVisibility(View.GONE);
        tvTitle.setText("我的投资");
        ibSetting.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData(String content) {
        //1获取本地存储的用户的额信息，
        SharedPreferences sp = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String username = sp.getString("UF_ACC", "");
        //2.1如果获取的用户信息为空，提示登录的对话框，点击对话框进入登录页面；
        if (TextUtils.isEmpty(username)) {
            login();
        } else {//2.2如果有用户的信息就执行登录操作；
            doUser();
        }

    }

    /**
     * 登录操作
     */
    private void login() {
        new AlertDialog.Builder(this.getActivity())
                .setTitle("提示信息")
                .setMessage("用户必须先登录")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getActivity(),"登录的操作",Toast.LENGTH_SHORT).show();
                        //跳转到登录页面；
                        ((BaseActivity) MainMoneyFramgent.this.getActivity()).goToActivity(LoginActivity.class, null);
                    }
                })
                .setCancelable(false)
                .show();

    }

    /**
     * 登录成功后的操作；读取保存的用户信息
     */
    private void doUser() {
        User user = ((BaseActivity) MainMoneyFramgent.this.getActivity()).readUserInfo();
        textView11.setText(user.UF_ACC);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalFilesDir = MainMoneyFramgent.this.getActivity().getExternalFilesDir(null);
            File file = new File(externalFilesDir, "icon.png");
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                imageView1.setImageBitmap(bitmap);
                return;
            }
        }
        String uf_avatar_url = user.UF_AVATAR_URL;
        Picasso.with(MainMoneyFramgent.this.getActivity()).load(uf_avatar_url).transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                //压缩图片；
                Bitmap zoomBitmap = BitmapUtils.zoomBitmap(source, UIUtils.dp2px(62), UIUtils.dp2px(62));
                //转换成圆形的图片；
                Bitmap bitmap = BitmapUtils.changeToCircle(zoomBitmap);
                source.recycle();//回收资源；
                return bitmap;
            }

            @Override
            public String key() {
                return "";
            }
        }).into(imageView1);
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected String getParmas() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_money_fragment;
    }

    @OnClick(R.id.ib_setting)
    public void onClick() {
        Intent intent = new Intent(MainMoneyFramgent.this.getActivity(), SeettingActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.recharge, R.id.withdraw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recharge://冲值
                Intent intent = new Intent(MainMoneyFramgent.this.getActivity(), ChongZhiActivity.class);
                startActivity(intent);
                break;
            case R.id.withdraw://提现
                Intent intent1 = new Intent(MainMoneyFramgent.this.getActivity(), TiXianActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @OnClick({R.id.ll_touzi, R.id.ll_touzi_zhiguan, R.id.ll_zichang, R.id.ll_zhanquan})
    public void onllClick(View view) {
        switch (view.getId()) {
            case R.id.ll_touzi:
                BaseActivity baseActivity1 = (BaseActivity) (MainMoneyFramgent.this.getActivity());
                baseActivity1.goToActivity(ToZiGuanLiActivity.class,null);
                break;
            case R.id.ll_touzi_zhiguan:
                BaseActivity baseActivity2 = (BaseActivity) (MainMoneyFramgent.this.getActivity());
                baseActivity2.goToActivity(ToZiActivity.class,null);
                break;
            case R.id.ll_zichang:
                BaseActivity baseActivity3 = (BaseActivity) (MainMoneyFramgent.this.getActivity());
                baseActivity3.goToActivity(AssetsActivity.class,null);
                break;
            case R.id.ll_zhanquan:
                break;
        }
    }
}
