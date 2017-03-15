package com.example.bjc.bjcp2pdemo.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bjc.bjcp2pdemo.R;
import com.example.bjc.bjcp2pdemo.base.BaseActivity;
import com.example.bjc.bjcp2pdemo.util.UIUtils;

import butterknife.InjectView;
import butterknife.OnClick;

public class TiXianActivity extends BaseActivity {


    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ib_setting)
    ImageButton ibSetting;
    @InjectView(R.id.account_zhifubao)
    TextView accountZhifubao;
    @InjectView(R.id.select_bank)
    RelativeLayout selectBank;
    @InjectView(R.id.chongzhi_text)
    TextView chongzhiText;
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.input_money)
    EditText inputMoney;
    @InjectView(R.id.chongzhi_text2)
    TextView chongzhiText2;
    @InjectView(R.id.textView5)
    TextView textView5;
    @InjectView(R.id.btn_tixian)
    Button btnTixian;

    @Override
    protected void initData() {
//设置button的可点击性
        btnTixian.setClickable(false);
        //给页面中的EditText设置文本的监听
        inputMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("TAG", "before");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("TAG", "changing...");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("TAG", "after");

                String money = inputMoney.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    btnTixian.setBackgroundResource(R.drawable.btn_023);
                    //设置button的可点击性
                    btnTixian.setClickable(false);
                } else {
                    btnTixian.setBackgroundResource(R.drawable.btn_01);
                    //设置button的可点击性
                    btnTixian.setClickable(true);
                }

            }
        });



    }

    @Override
    protected void initTitle() {
        ibBack.setVisibility(View.VISIBLE);
        ibSetting.setVisibility(View.GONE);
        btnTixian.setText("提现");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ti_xian;
    }

    @OnClick({R.id.ib_back, R.id.btn_tixian})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.btn_tixian:
                //设置button的可点击性
                btnTixian.setClickable(false);
                Toast.makeText(TiXianActivity.this, "您的退款申请已经提交。商家在24小时以内会做出回复。如果退款成功，退款会相应的打到您的支付宝账号中", Toast.LENGTH_SHORT).show();

                UIUtils.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closeCurrentActivity();
                    }
                },2000);
                break;
        }
    }
}
