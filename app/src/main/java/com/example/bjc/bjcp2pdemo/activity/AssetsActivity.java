package com.example.bjc.bjcp2pdemo.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bjc.bjcp2pdemo.R;
import com.example.bjc.bjcp2pdemo.base.BaseActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnClick;

public class AssetsActivity extends BaseActivity {


    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ib_setting)
    ImageButton ibSetting;
    @InjectView(R.id.Pie_chat)
    PieChart PieChat;
    private Typeface mTf;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void initData() {
        mTf = Typeface.createFromAsset(this.getAssets(), "OpenSans-Regular.ttf");
// apply styling
        PieChat.setDescription("");
        PieChat.setHoleRadius(52f);
        PieChat.setTransparentCircleRadius(57f);
        PieChat.setCenterText("MPChart\nAndroid");
        PieChat.setCenterTextTypeface(mTf);
        PieChat.setCenterTextSize(18f);
        PieChat.setUsePercentValues(true);
        PieData pieData = generateDataPie();
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTypeface(mTf);
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.WHITE);
        // set data
        PieChat.setData(pieData);

        Legend l = PieChat.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        PieChat.animateXY(900, 900);
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private PieData generateDataPie() {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int i = 0; i < 4; i++) {
            entries.add(new Entry((int) (Math.random() * 70) + 30, i));
        }

        PieDataSet d = new PieDataSet(entries, "");

        // space between slices
        d.setSliceSpace(2f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData cd = new PieData(getQuarters(), d);
        return cd;
    }

    private ArrayList<String> getQuarters() {

        ArrayList<String> q = new ArrayList<String>();
        q.add("1st Quarter");
        q.add("2nd Quarter");
        q.add("3rd Quarter");
        q.add("4th Quarter");
        return q;
    }

    @Override
    protected void initTitle() {
        ibBack.setVisibility(View.VISIBLE);
        ibSetting.setVisibility(View.GONE);
        tvTitle.setText("饼状图");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_assts;
    }


    @OnClick(R.id.ib_back)
    public void onClick() {
        closeCurrentActivity();
    }
}
