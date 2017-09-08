package com.znjtgs.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.znjtgs.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/8.
 */

public class CarRechagerAdapter extends BaseAdapter {
    private static final String TAG = "CarRechagerAdapter";
    private ArrayList<CarRechagerInfo> rechagerInfos;
    private LayoutInflater inflater;
    private ListViewChildButtonClick listViewChildButtonClick;
    private Boolean[] selects;

    public Boolean[] getSelects() {
        return selects;
    }

    public void setListViewChildButtonClick(ListViewChildButtonClick listViewChildButtonClick) {
        this.listViewChildButtonClick = listViewChildButtonClick;
    }

    public CarRechagerAdapter(Context context, ArrayList<CarRechagerInfo> rechagerInfos) {
        this.rechagerInfos = rechagerInfos;
        selects = new Boolean[rechagerInfos.size()];
        setAllSelect(false);
        inflater = LayoutInflater.from(context);
    }

    public void setAllSelect(boolean state) {
        for (int i = 0; i < selects.length; i++) {
            selects[i] = state;
        }

    }


    @Override
    public int getCount() {
        return (rechagerInfos == null ? 0 : rechagerInfos.size());
    }

    @Override
    public CarRechagerInfo getItem(int position) {
        return rechagerInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        CarRechagerInfo info = rechagerInfos.get(position);
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_car_rechager_layout, null);
            holder = new ViewHolder(position);
            holder.initView(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.bindData(info);
        return view;
    }

    private class ViewHolder {
        TextView tv_CarId;
        TextView tv_Money;
        CheckBox cbSelectCar;
        Button btnRechager;
        private int position;

        public ViewHolder(int position) {
            this.position = position;
        }

        void bindData(CarRechagerInfo info) {
            tv_CarId.setText(String.valueOf(info.getCarId()));
            tv_Money.setText(String.valueOf(info.getMoney()));
            cbSelectCar.setChecked(selects[position]);
        }

        void initView(View view) {
            tv_CarId = (TextView) view.findViewById(R.id.tv_car_id);
            tv_Money = (TextView) view.findViewById(R.id.tv_car_curr_money);
            cbSelectCar = (CheckBox) view.findViewById(R.id.cb_car_select_rechager);
            cbSelectCar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    selects[position] = isChecked;
                }
            });
            btnRechager = (Button) view.findViewById(R.id.btn_car_rechager);
            btnRechager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listViewChildButtonClick == null) {
                        return;
                    }
                    listViewChildButtonClick.onButtonClick(v, position);
                }
            });
        }

    }


    public interface ListViewChildButtonClick {
        void onButtonClick(View v, int position);
    }
}
