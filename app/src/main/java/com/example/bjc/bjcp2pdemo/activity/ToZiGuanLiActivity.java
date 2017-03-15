package com.example.bjc.bjcp2pdemo.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bjc.bjcp2pdemo.R;
import com.example.bjc.bjcp2pdemo.base.BaseActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnClick;

public class ToZiGuanLiActivity extends BaseActivity {


    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ib_setting)
    ImageButton ibSetting;
    @InjectView(R.id.ll_chat)
    LineChart llChat;
    private Typeface mTf;

    @Override
    protected void initData() {
        mTf = Typeface.createFromAsset(this.getAssets(), "OpenSans-Regular.ttf");
        llChat.setDescription("毕静存的折线图");
        llChat.setDrawGridBackground(false);

        XAxis xAxis = llChat.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = llChat.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);

        YAxis rightAxis = llChat.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);

        // set data
        llChat.setData( generateDataLine());

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        llChat.animateX(750);

    }

    @Override
    protected void initTitle() {
        ibBack.setVisibility(View.VISIBLE);
        ibSetting.setVisibility(View.GONE);
        tvTitle.setText("折线图");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_touziguanli;
    }


    @OnClick({R.id.ib_back, R.id.ll_chat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                closeCurrentActivity();
                break;
            case R.id.ll_chat:

                break;
        }
    }
    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private LineData generateDataLine() {

        ArrayList<Entry> e1 = new ArrayList<Entry>();

        for (int i = 0; i < 12; i++) {
            e1.add(new Entry((int) (Math.random() * 65) + 40, i));
        }

        LineDataSet d1 = new LineDataSet(e1, "New DataSet ");
        d1.setLineWidth(2.5f);
        d1.setCircleSize(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);
        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        sets.add(d1);

        LineData cd = new LineData(getMonths(), sets);
        return cd;
    }
    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Apr");
        m.add("May");
        m.add("Jun");
        m.add("Jul");
        m.add("Aug");
        m.add("Sep");
        m.add("Okt");
        m.add("Nov");
        m.add("Dec");

        return m;
    }
}
