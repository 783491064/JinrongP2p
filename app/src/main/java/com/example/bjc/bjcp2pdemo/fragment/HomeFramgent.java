package com.example.bjc.bjcp2pdemo.fragment;

import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bjc.bjcp2pdemo.R;
import com.example.bjc.bjcp2pdemo.base.BaseFragment;
import com.example.bjc.bjcp2pdemo.common.AppNetConfig;
import com.example.bjc.bjcp2pdemo.ui.RoundProcess;

import butterknife.InjectView;

/**
 * Created by 毕静存 on 2016/11/29.
 */

public class HomeFramgent extends BaseFragment {


    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ib_setting)
    ImageButton ibSetting;
    @InjectView(R.id.vp_home)
    ViewPager vpHome;
    @InjectView(R.id.point)
    TextView point;
    @InjectView(R.id.tv_bellow_vp)
    TextView tvBellowVp;
    @InjectView(R.id.tv_1)
    TextView tv1;
    @InjectView(R.id.tv_2)
    TextView tv2;
    @InjectView(R.id.tv_3)
    TextView tv3;
    @InjectView(R.id.bt_pr)
    RoundProcess btPr;
    @InjectView(R.id.tv_prent)
    TextView tvPrent;
    @InjectView(R.id.bt_join)
    Button btJoin;
    private int currentProcess;
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            btPr.setMax(100);
            btPr.setProcess(0);
            for(int i=0;i<currentProcess;i++){
                btPr.setProcess(btPr.getProcess()+1);
                SystemClock.sleep(30);
                btPr.postInvalidate();//强制重绘
            }
        }
    };

    protected void initTitle() {
        ibBack.setVisibility(View.GONE);
        tvTitle.setText("首页");
        ibSetting.setVisibility(View.GONE);
    }
    /**
     * 联网数据；
     */
    @Override
    protected void initData(String content) {
            //动态显示进度条的加载；
            currentProcess=60;
            btPr.setProcess(currentProcess);
            new Thread(runnable){}.start();
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.INDEX;
    }

    @Override
    protected String getParmas() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_fragment;
    }
}
