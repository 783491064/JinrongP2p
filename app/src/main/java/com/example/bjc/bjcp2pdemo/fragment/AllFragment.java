package com.example.bjc.bjcp2pdemo.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bjc.bjcp2pdemo.R;
import com.example.bjc.bjcp2pdemo.adapter.LvAllAdapter;
import com.example.bjc.bjcp2pdemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 毕静存 on 2016/12/5.
 */

public class AllFragment extends BaseFragment {

    @InjectView(R.id.lv_allfragment)
    ListView lvAllfragment;

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
        return R.layout.all_fragment;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData(String content) {
        //解析数据成功；
        List data=new ArrayList<>();
        LvAllAdapter adapter=new LvAllAdapter(data);
        lvAllfragment.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

}
