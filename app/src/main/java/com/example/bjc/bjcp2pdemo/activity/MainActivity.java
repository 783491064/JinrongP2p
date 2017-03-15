package com.example.bjc.bjcp2pdemo.activity;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bjc.bjcp2pdemo.R;
import com.example.bjc.bjcp2pdemo.base.BaseActivity;
import com.example.bjc.bjcp2pdemo.common.AppManager;
import com.example.bjc.bjcp2pdemo.fragment.HomeFramgent;
import com.example.bjc.bjcp2pdemo.fragment.MainMoneyFramgent;
import com.example.bjc.bjcp2pdemo.fragment.MoreFramgent;
import com.example.bjc.bjcp2pdemo.fragment.TouZiFramgent;
import com.example.bjc.bjcp2pdemo.util.UIUtils;

import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.fg_comment)
    FrameLayout fgComment;
    @InjectView(R.id.ib_home)
    ImageButton ibHome;
    @InjectView(R.id.tv_home)
    TextView tvHome;
    @InjectView(R.id.ib_touzi)
    ImageButton ibTouzi;
    @InjectView(R.id.tv_touzi)
    TextView tvTouzi;
    @InjectView(R.id.ib_mainMoney)
    ImageButton ibMainMoney;
    @InjectView(R.id.tv_mainMoney)
    TextView tvMainMoney;
    @InjectView(R.id.ib_more)
    ImageButton ibMore;
    @InjectView(R.id.tv_more)
    TextView tvMore;
    private HomeFramgent homeFragment;
    private MainMoneyFramgent mainMoneyFragment;
    private TouZiFramgent toZiFragment;
    private MoreFramgent moreFragment;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    @Override
    protected void initData() {
        selectFragment(0);//  默认选中主页
        //添加到栈里
        AppManager.getInstance().add(this);

    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.ib_home, R.id.ib_touzi, R.id.ib_mainMoney, R.id.ib_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_home:
                selectFragment(0);
                break;
            case R.id.ib_touzi:
                selectFragment(1);
                break;
            case R.id.ib_mainMoney:
                selectFragment(2);
                break;
            case R.id.ib_more:
                selectFragment(3);
                break;
        }
    }

    private void selectFragment(int i) {
        fragmentManager = this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        //先初始化各个fragmnet;
        hideFragment();
        //先初始化各个按钮的状态；
        hideBottom();
        switch (i) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFramgent();
                    fragmentTransaction.add(R.id.fg_comment, homeFragment);
                }
                fragmentTransaction.show(homeFragment);
                fragmentTransaction.commit();
                selectedBottom(i);
                break;
            case 1:
                if (toZiFragment == null) {
                    toZiFragment = new TouZiFramgent();
                    fragmentTransaction.add(R.id.fg_comment, toZiFragment);
                }
                fragmentTransaction.show(toZiFragment);
                fragmentTransaction.commit();
                selectedBottom(i);
                break;
            case 2:
                if (mainMoneyFragment == null) {
                    mainMoneyFragment = new MainMoneyFramgent();
                    fragmentTransaction.add(R.id.fg_comment, mainMoneyFragment);
                }
                fragmentTransaction.show(mainMoneyFragment);
                fragmentTransaction.commit();
                selectedBottom(i);
                break;
            case 3:
                if (moreFragment == null) {
                    moreFragment = new MoreFramgent();
                    fragmentTransaction.add(R.id.fg_comment, moreFragment);
                }
                fragmentTransaction.show(moreFragment);
                fragmentTransaction.commit();
                selectedBottom(i);
        }
    }

    /**
     * 隐藏底部的按钮控件的状态；
     */
    private void hideBottom() {
        ibHome.setImageResource(R.drawable.bid02);
        tvHome.setTextColor(UIUtils.getColor(R.color.product_detail_common));

        ibTouzi.setImageResource(R.drawable.bid04);
        tvTouzi.setTextColor(UIUtils.getColor(R.color.product_detail_common));

        ibMainMoney.setImageResource(R.drawable.bid06);
        tvMainMoney.setTextColor(UIUtils.getColor(R.color.product_detail_common));

        ibMore.setImageResource(R.drawable.bid08);
        tvMore.setTextColor(UIUtils.getColor(R.color.product_detail_common));
    }

    /**
     * 用来改变底部按钮的显示；
     *
     * @param i
     */
    private void selectedBottom(int i) {
        switch (i) {
            case 0:
                ibHome.setImageResource(R.drawable.bid01);
                tvHome.setTextColor(UIUtils.getColor(R.color.title_text));
                break;
            case 1:
                ibTouzi.setImageResource(R.drawable.bid03);
                tvTouzi.setTextColor(UIUtils.getColor(R.color.title_text));
                break;
            case 2:
                ibMainMoney.setImageResource(R.drawable.bid05);
                tvMainMoney.setTextColor(UIUtils.getColor(R.color.title_text));
                break;
            case 3:
                ibMore.setImageResource(R.drawable.bid07);
                tvMore.setTextColor(UIUtils.getColor(R.color.title_text));
                break;
        }
    }

    /**
     * 隐藏各个fragment;
     */
    private void hideFragment() {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (toZiFragment != null) {
            fragmentTransaction.hide(toZiFragment);
        }
        if (mainMoneyFragment != null) {
            fragmentTransaction.hide(mainMoneyFragment);
        }
        if (moreFragment != null) {
            fragmentTransaction.hide(moreFragment);
        }
    }
}
