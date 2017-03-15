package com.example.bjc.bjcp2pdemo.fragment;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bjc.bjcp2pdemo.R;
import com.example.bjc.bjcp2pdemo.base.BaseFragment;

import butterknife.InjectView;

/**
 * Created by 毕静存 on 2016/11/29.
 */

public class MoreFramgent extends BaseFragment {
    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ib_setting)
    ImageButton ibSetting;
    protected void initTitle() {
        ibBack.setVisibility(View.GONE);
        tvTitle.setText("更多");
        ibSetting.setVisibility(View.GONE);
    }

    @Override
    protected void initData(String content) {

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
        return R.layout.more_fragment;
    }
}
