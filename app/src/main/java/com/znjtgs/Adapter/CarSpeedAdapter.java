package com.znjtgs.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.znjtgs.R;
import com.znjtgs.entity.CarSpeedInfo;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/8.
 */

public class CarSpeedAdapter extends BaseAdapter {
    private ArrayList<CarSpeedInfo> carSpeedInfos;
    private LayoutInflater inflater;

    public CarSpeedAdapter(Context context, ArrayList<CarSpeedInfo> carSpeedInfos) {
        this.carSpeedInfos = carSpeedInfos;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (carSpeedInfos==null?0:carSpeedInfos.size());
    }

    @Override
    public CarSpeedInfo getItem(int position) {
        return carSpeedInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        ViewHoler holer;
        CarSpeedInfo info=carSpeedInfos.get(position);
        if(convertView==null){
            view=inflater.inflate(R.layout.gv_item_car_speed,parent,false);
            holer=new ViewHoler();
            holer.initView(view);
            view.setTag(holer);
        }else {
            holer= (ViewHoler) view.getTag();
        }
        holer.bindDataa(info);
        return view;
    }
    private class ViewHoler{
        TextView tv_carId;
        TextView tv_speed;
        void initView(View view){
            tv_carId= (TextView) view.findViewById(R.id.tv_car_id);
            tv_speed= (TextView) view.findViewById(R.id.tv_car_speed);
        }
        void bindDataa(CarSpeedInfo info){
            tv_carId.setText(String.valueOf(info.getCarId()));
            tv_speed.setText(String.valueOf(info.getSpeed()));
        }
    }
}
