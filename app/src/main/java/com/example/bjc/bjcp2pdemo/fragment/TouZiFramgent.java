package com.example.bjc.bjcp2pdemo.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bjc.bjcp2pdemo.R;
import com.example.bjc.bjcp2pdemo.base.BaseFragment;
import com.example.bjc.bjcp2pdemo.util.UIUtils;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 毕静存 on 2016/11/29.
 */

public class TouZiFramgent extends BaseFragment {


    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ib_setting)
    ImageButton ibSetting;
    @InjectView(R.id.tb_indicator)
    TabPageIndicator tbIndicator;
    @InjectView(R.id.vp_touzi)
    ViewPager vpTouzi;
    private List<Fragment> fgList;
    private AllFragment allFragment;
    private HotFragment hotFragment;
    private RecommentFragment recommentFragment;

    protected void initTitle() {
        ibBack.setVisibility(View.GONE);
        tvTitle.setText("投资");
        ibSetting.setVisibility(View.GONE);
    }

    @Override
    protected void initData(String content) {
        initFragments();
        MyFragmentAdapter adapter = new MyFragmentAdapter(getFragmentManager());
        vpTouzi.setAdapter(adapter);
        tbIndicator.setViewPager(vpTouzi);
    }

    private void initFragments() {
        fgList=new ArrayList<>();
        allFragment = new AllFragment();
        hotFragment = new HotFragment();
        recommentFragment = new RecommentFragment();
        fgList.add(allFragment);
        fgList.add(recommentFragment);
        fgList.add(hotFragment);
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
        return R.layout.touzi_fragment;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fgList == null ? 0 : fgList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return UIUtils.getStringArray(R.array.invest_tab)[position];
        }


        @Override
        public Fragment getItem(int position) {
            return fgList.get(position);
        }
    }
}
