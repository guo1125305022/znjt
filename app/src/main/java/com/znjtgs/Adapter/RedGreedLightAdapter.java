package com.znjtgs.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.znjtgs.R;
import com.znjtgs.entity.TrafficLightInfo;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/16.
 */

public class RedGreedLightAdapter extends BaseAdapter {
    private ArrayList<TrafficLightInfo> lightInfos = null;
    private LayoutInflater inflater;

    public RedGreedLightAdapter(Context context, ArrayList<TrafficLightInfo> lightInfos) {
        this.lightInfos = lightInfos;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (lightInfos == null ? 0 : lightInfos.size());
    }

    @Override
    public TrafficLightInfo getItem(int position) {
        return lightInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_red_greed_light_layout, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        holder.bindData(lightInfos.get(position));
        return view;
    }

    class ViewHolder {
        View rootView;
        TextView roadId;
        TextView trafficLightId;
        TextView roadStatus;
        TextView souresTime;
        TextView nowTime;
        TextView alserTime;

        public ViewHolder(View view) {
            this.rootView = view;
            initView();
        }

        private void initView() {
            roadId = (TextView) findViewById(R.id.tv_road_id);
            trafficLightId = (TextView) findViewById(R.id.tv_traffic_id);
            roadStatus = (TextView) findViewById(R.id.tv_road_status);
            souresTime = (TextView) findViewById(R.id.tv_traffic_greed_soures);
            nowTime = (TextView) findViewById(R.id.tv_traffic_greed_now);
            alserTime = (TextView) findViewById(R.id.tv_traffic_alert_time);
        }

        /**
         * 绑定红绿灯数据到视图上
         * @param info
         */
        void bindData(TrafficLightInfo info) {
            roadId.setText(String.valueOf(info.getRoadId()));
            roadStatus.setText(String.valueOf(info.getRoadStatus()));
            roadStatus.setBackgroundColor(info.getItemBackGroundColor());
            souresTime.setText(String.valueOf(info.getSouresGreedTime()));
            nowTime.setText(String.valueOf(info.getNowGreedTime()));
            alserTime.setText(String.valueOf(info.getAlertTime()));
            trafficLightId.setText(String.valueOf(info.getTrrfficLightId()));
        }

        View findViewById(int resId) {
            return rootView.findViewById(resId);
        }
    }

}
