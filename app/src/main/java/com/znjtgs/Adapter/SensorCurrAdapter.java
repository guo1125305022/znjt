package com.znjtgs.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.znjtgs.R;
import com.znjtgs.entity.SensorinfoBySensorName;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/5/30 0030.
 */

public class SensorCurrAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SensorinfoBySensorName> list;
    private LayoutInflater inflater;

    public SensorCurrAdapter(Context context, ArrayList<SensorinfoBySensorName> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (list == null ? 0 : list.size());
    }

    @Override
    public SensorinfoBySensorName getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHole hole = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.gv_item, parent, false);
            hole = new ViewHole();
            hole.tv_title = (TextView) convertView.findViewById(R.id.tv_grid);
            hole.tv_date = (TextView) convertView.findViewById(R.id.tv_values);
            convertView.setTag(hole);
        } else {
            hole = (ViewHole) convertView.getTag();
        }
        hole.tv_title.setText(String.valueOf(list.get(position).getSensorName()));
        hole.tv_date.setText(String.valueOf(list.get(position).getCurDate()));
        return convertView;
    }

    private class ViewHole {
        TextView tv_title;
        TextView tv_date;
    }
}
