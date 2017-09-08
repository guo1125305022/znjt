package com.znjtgs.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znjtgs.Adapter.FragmentAdapter;
import com.znjtgs.Fragments.FragmentSensorIfo;
import com.znjtgs.R;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/5/30 0030.
 */

public class SensorActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private FragmentAdapter adapters;
    private ViewPager pager;
    private TextView tv_titles;
    private LinearLayout ll_poing;
    public static final String[][] strings = {{"temperature", "空气温度"}, {"humidity", "空气湿度"}, {"LightIntensity", "光线强度"}, {"pm2.5", "PM2.5"}, {"co2", "Co2"}};


    @Override
    protected int initLayouotId() {
        return R.layout.activity_sensor;
    }

    @Override
    protected void initComponent() {
        initAdaters();
        initview();
    }



    private void initview() {
        ll_poing = (LinearLayout) findViewById(R.id.ll_poing);
        for (int i = 0; i < ll_poing.getChildCount(); i++) {
            ll_poing.getChildAt(i).setOnClickListener(this);
        }
        tv_titles = (TextView) findViewById(R.id.tv_titles);
        tv_titles.setText(strings[0][1]);
        pager = (ViewPager) findViewById(R.id.vp_view);
        pager.setAdapter(adapters);
        pager.setOnPageChangeListener(this);
        pager.setCurrentItem(getIntent().getIntExtra("index", 0));

    }

    private void initAdaters() {
        ArrayList<Fragment> list = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            list.add(new FragmentSensorIfo(strings[i][0]));
        }
        adapters = new FragmentAdapter(this.getSupportFragmentManager(), list);
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < ll_poing.getChildCount(); i++) {
            if (ll_poing.getChildAt(i) == v) {
                pager.setCurrentItem(i);
                tv_titles.setText(strings[i][1]);
                break;
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < ll_poing.getChildCount(); i++) {
            ll_poing.getChildAt(i).setBackgroundResource(R.drawable.poing_on);
        }
        ll_poing.getChildAt(position).setBackgroundResource(R.drawable.poing_off);
        tv_titles.setText(strings[position][1]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
