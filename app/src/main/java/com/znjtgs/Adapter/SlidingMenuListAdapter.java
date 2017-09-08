package com.znjtgs.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.znjtgs.R;
import com.znjtgs.entity.SlidingMenuListAdapterItem;

import java.util.ArrayList;

/**
 * Created by JKX_GXL on 2017/5/13.
 */

public class SlidingMenuListAdapter extends BaseAdapter {
    private ArrayList<SlidingMenuListAdapterItem> slidingMenuListAdapterItems;
    private LayoutInflater layoutInflater;

    public SlidingMenuListAdapter(Context context, ArrayList<SlidingMenuListAdapterItem> slidingMenuListAdapterItems) {
        layoutInflater = LayoutInflater.from(context);
        this.slidingMenuListAdapterItems = slidingMenuListAdapterItems;
    }

    @Override
    public int getCount() {
        return (slidingMenuListAdapterItems == null ? 0 : slidingMenuListAdapterItems.size());
    }

    @Override
    public SlidingMenuListAdapterItem getItem(int position) {
        return slidingMenuListAdapterItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        Holder holder;
        view = convertView;
        if (view == null) {
            holder = new Holder();
            view = layoutInflater.inflate(R.layout.slidingmenu_list_item_layout, null);
            holder.title = (TextView) view.findViewById(R.id.actv_sildingmenu_list_item_layout_title);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.title.setText(slidingMenuListAdapterItems.get(position).getTitle());

        return view;
    }


    private class Holder {
        TextView title;
    }
}
