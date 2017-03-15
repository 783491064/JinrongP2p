package com.example.bjc.bjcp2pdemo.activity;

import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bjc.bjcp2pdemo.R;
import com.example.bjc.bjcp2pdemo.base.BaseActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnClick;

public class ToZiActivity extends BaseActivity {


    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ib_setting)
    ImageButton ibSetting;
    @InjectView(R.id.bar_chat)
    BarChart barChat;
    private Typeface mTf;
    @Override
    protected void initData() {

        mTf = Typeface.createFromAsset(this.getAssets(), "OpenSans-Regular.ttf");
        barChat.setDescription("");
        barChat.setDrawGridBackground(false);
        barChat.setDrawBarShadow(false);

        XAxis xAxis = barChat.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = barChat.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setSpaceTop(20f);

        YAxis rightAxis = barChat.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);
        BarData barData = generateDataBar();
        barData.setValueTypeface(mTf);

        // set data
        barChat.setData((barData));

        // do not forget to refresh the chart
//        holder.chart.invalidate();
        barChat.animateY(700);
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private BarData generateDataBar() {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry((int) (Math.random() * 70) + 30, i));
        }

        BarDataSet d = new BarDataSet(entries, "New DataSet ");
        d.setBarSpacePercent(20f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);

        BarData cd = new BarData(getMonths(), d);
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

    @Override
    protected void initTitle() {
        ibBack.setVisibility(View.VISIBLE);
        ibSetting.setVisibility(View.GONE);
        tvTitle.setText("柱状图");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_touzi;
    }

    @OnClick(R.id.ib_back)
    public void onClick() {
        closeCurrentActivity();
    }
}
