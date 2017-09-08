package com.znjtgs.Adapter;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.znjtgs.R;
import com.znjtgs.entity.CarControlInfo;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/7.
 */

public class CarControlAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;//定义布局加载器
    private ArrayList<CarControlInfo> carControlInfos;//定义小车信息集合
    private OnListSwitchCheckListener onListSwitchCheckListener;//定义listView中Switch变化监听器
    private ArrayList<SwitchOnCheckedChangeListener> switchOnCheckedChangeListenerArrayList=new ArrayList<>();
    public void setOnListSwitchCheckListener(OnListSwitchCheckListener onListSwitchCheckListener) {
        this.onListSwitchCheckListener = onListSwitchCheckListener;
    }

    public CarControlAdapter(Context context, ArrayList<CarControlInfo> carControlInfos) {
        this.carControlInfos = carControlInfos;
        this.layoutInflater=LayoutInflater.from(context);
        for(int i=0;i<carControlInfos.size();i++){
            switchOnCheckedChangeListenerArrayList.add(new SwitchOnCheckedChangeListener());
        }
    }

    @Override
    public int getCount() {
        return (carControlInfos ==null?0: carControlInfos.size());
    }

    @Override
    public CarControlInfo getItem(int position) {
        return carControlInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        CarControlInfo info = carControlInfos.get(position);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.lits_item_car_control_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.tvCarId = (TextView) view.findViewById(R.id.tv_car_control_id);
            viewHolder.tvState = (TextView) view.findViewById(R.id.tv_car_control_state);
            viewHolder.swCarControl = (SwitchCompat) view.findViewById(R.id.sc_car_set_control);
            switchOnCheckedChangeListenerArrayList.get(position).setHolder(viewHolder);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.bindData(position,info);
        return view;
    }

    private class ViewHolder{
        public TextView tvCarId;
        public TextView tvState;
        public SwitchCompat swCarControl;
        public CarControlInfo carControlInfo;

        /**
         * 绑定数据
         * @param carControlInfo 数据对象
         */
        public void bindData(int position,CarControlInfo carControlInfo){
            this.carControlInfo = carControlInfo;
            tvCarId.setText(String.valueOf(carControlInfo.getCarId()));
            tvState.setText((carControlInfo.isState()?"正在运行":"停止中"));
            swCarControl.setOnCheckedChangeListener(null);
            swCarControl.setChecked(carControlInfo.isState());
            swCarControl.setOnCheckedChangeListener(switchOnCheckedChangeListenerArrayList.get(position));
        }



    }

    public class SwitchOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{
        private ViewHolder holder;
        public void setHolder(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            dispEvent(isChecked);
        }
        private void dispEvent(boolean isChecked){
            if(onListSwitchCheckListener==null){
                return;
            }
            holder.carControlInfo.setState(isChecked);
            onListSwitchCheckListener.onCheck(holder.swCarControl,false,isChecked, holder.carControlInfo);
        }
    }

    public interface OnListSwitchCheckListener{
        void onCheck(View v, boolean isUser, boolean isChecked, CarControlInfo controlInfo);
    }
}
