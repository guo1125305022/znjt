package com.znjtgs.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.znjtgs.R;
import com.znjtgs.entity.CarParkPayEtcPay;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/9.
 */

public class CarPayHistoryAdapter extends BaseAdapter {
    private ArrayList<CarParkPayEtcPay> pays;
    private LayoutInflater inflater;

    public CarPayHistoryAdapter(Context context, ArrayList<CarParkPayEtcPay> pays) {
        inflater = LayoutInflater.from(context);
        this.pays = pays;
    }

    @Override
    public int getCount() {
        return (pays == null ? 0 : pays.size());
    }

    @Override
    public CarParkPayEtcPay getItem(int position) {
        return pays.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_car_pay_history, null);
            viewHolder = new ViewHolder(view);
            viewHolder.initView();
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.bindData(pays.get(position));
        return view;
    }

    class ViewHolder {
        private View itemView;
        TextView carId;
        TextView payType;
        TextView payTime;
        TextView payMoney;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }

        void initView() {
            carId = (TextView) findViewById(R.id.tv_car_id);
            payType = (TextView) findViewById(R.id.tv_car_pay_type);
            payTime = (TextView) findViewById(R.id.tv_car_pay_time);
            payMoney = (TextView) findViewById(R.id.tv_car_pay_money);
        }

        void bindData(CarParkPayEtcPay carParkPayEtcPay) {
            carId.setText(String.valueOf(carParkPayEtcPay.getCarId()));
            payType.setText(String.valueOf(carParkPayEtcPay.getType()));
            payTime.setText(String.valueOf(carParkPayEtcPay.getDate()));
            payMoney.setText(String.valueOf(carParkPayEtcPay.getMoney()));
        }

        View findViewById(int id) {
            return itemView.findViewById(id);
        }
    }
}
