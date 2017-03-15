package com.example.bjc.bjcp2pdemo.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bjc.bjcp2pdemo.R;
import com.example.bjc.bjcp2pdemo.ui.RoundProcess;
import com.example.bjc.bjcp2pdemo.util.UIUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 毕静存 on 2016/12/5.
 */
public class LvAllAdapter extends BaseAdapter {
    private final List data;

    public LvAllAdapter(List data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return 9+1;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        if(itemViewType==0){
            TextView tv=new TextView(parent.getContext());
            tv.setText("不一样的烟火");
            tv.setTextColor(Color.RED);
            tv.setTextSize(UIUtils.dp2px(20));
            return tv;

        }else{
            if(position==2){
                position=position-1;
            }
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.lv_fragment_item, null);
                holder=new ViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder  = (ViewHolder)convertView.getTag();
            }
            //绑定数据；
            holder.pProgresss.setProcess(60);
            return convertView;
        }

    }

    @Override
    public int getItemViewType(int position) {//获取当前的位置的类型
        if(position==2){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {//获取不同的类型
        return 2;
    }

    class ViewHolder {
        @InjectView(R.id.p_name)
        TextView pName;
        @InjectView(R.id.p_money)
        TextView pMoney;
        @InjectView(R.id.p_yearlv)
        TextView pYearlv;
        @InjectView(R.id.p_suodingdays)
        TextView pSuodingdays;
        @InjectView(R.id.p_minzouzi)
        TextView pMinzouzi;
        @InjectView(R.id.p_minnum)
        TextView pMinnum;
        @InjectView(R.id.p_progresss)
        RoundProcess pProgresss;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
