package com.znjtgs.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.znjtgs.R;
import com.znjtgs.entity.MyRoadTrafficLightInfo;

import java.util.ArrayList;

public class RoadRedGreeLightAdapter extends BaseAdapter {
	private ArrayList<MyRoadTrafficLightInfo> list;
	private LayoutInflater inflater;

	public void setList(ArrayList<MyRoadTrafficLightInfo> list) {
		this.list = list;
	}

	public RoadRedGreeLightAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	public void list(ArrayList<MyRoadTrafficLightInfo> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return (list == null ? 0 : list.size());
	}

	@Override
	public Object getItem(int index) {

		return list.get(index);
	}
	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int index, View converView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View view = converView;
		ViewHolder holder = null;
		if (view == null) {
			view = inflater.inflate(R.layout.my_road_item_red_yellow_gree, null);
			holder = new ViewHolder();
			holder.greed = (TextView) view.findViewById(R.id.tv_show_greed_light);
			holder.red = (TextView) view.findViewById(R.id.tv_show_red_light);
			holder.yellow = (TextView) view.findViewById(R.id.tv_show_yowlle_light);
			holder.road = (TextView) view.findViewById(R.id.tv_show_road_id);
			holder.bottom=(TextView) view.findViewById(R.id.tv_bottm_line);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		MyRoadTrafficLightInfo lights = this.list.get(index);

		holder.road.setText(String.valueOf(lights.getTrrfficLightId()+"路口"));
		holder.red.setText(String.valueOf(lights.getReadTime()));
		holder.greed.setText(String.valueOf(lights.getGreenTime()));
		holder.yellow.setText(String.valueOf(lights.getYellowTime()));
		if(index==getCount()-1){
			holder.bottom.setVisibility(View.VISIBLE);
		}else{
			holder.bottom.setVisibility(View.GONE);
		}
		return view;
	}

	private class ViewHolder {
		TextView red;
		TextView greed;
		TextView yellow;
		TextView road;
		TextView bottom;

	}
}
