package com.example.bjc.bjcp2pdemo.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bjc.bjcp2pdemo.ui.LoadingPage;
import com.example.bjc.bjcp2pdemo.util.UIUtils;

import butterknife.ButterKnife;

/**
 * Created by 毕静存 on 2016/12/2.
 */

public abstract class BaseFragment extends Fragment {


    private LoadingPage loadingPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = UIUtils.xmlToLayout(getLayoutId());
//        ButterKnife.inject(this, view);
//        initTitle();
//        initData();
        loadingPage = new LoadingPage(container.getContext()) {
            @Override
            protected void onSuccess(ResultState resultState, View successView) {
                ButterKnife.inject(BaseFragment.this,successView);
                initTitle();
                initData(resultState.getContent());
            }

            @Override
            protected String parmas() {
                return getParmas();
            }

            @Override
            protected String url() {
                return getUrl();
            }

            @Override
            public int layoutId() {
                return getLayoutId();
            }
        };

        return loadingPage;
    }



    protected abstract String getUrl();

    protected abstract String getParmas();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLoadingPage();
            }
        },2000);
    }

    protected void showLoadingPage(){
        loadingPage.show();
    };
    /**
     * 每个页面的布局的id
     * @return
     */
    public abstract int getLayoutId();

    /**
     *  每个页面加载自己的头部布局；
     */
    protected abstract void initTitle();
    /**
     * 每个页面加载自己的数据
     */
    protected abstract void initData(String content);
}
