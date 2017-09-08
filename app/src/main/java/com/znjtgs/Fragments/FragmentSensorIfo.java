package com.znjtgs.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.znjtgs.R;
import com.znjtgs.request.HttpOkUlits;
import com.znjtgs.ulits.UserManager;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Administrator on 2017/5/30 0030.
 */

public class FragmentSensorIfo extends Fragment {

    private String sensorName;
    private LineChart mchart;
    private ArrayList<Entry> entryArrayList = new ArrayList<>();
    private boolean run = true;
    private ArrayList<String> xtime = new ArrayList<>();
    private boolean destory = false;

    public FragmentSensorIfo(){

    }
    public FragmentSensorIfo(String sensorName) {
        this.sensorName = sensorName;
        thread.start();
        for(int i=0;i<20;i++){
            new Thread().start();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mchart.invalidate();
        }
    };

    private Thread thread = new Thread() {
        @Override
        public void run() {
            while (true) {
                if (mchart == null) {
                    continue;
                }
                logic();
                handler.sendMessage(new Message());
                if (destory) {
                    break;
                }
                synchronized (Thread.class) {
                    if (!run) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        this.notify();
                    }
                }
                try {
                    sleep(9000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void logic() {
        JSONObject jsont = UserManager.getUserManager().toJson();
        try {
            jsont.put("SenseName", sensorName);
            String reques = HttpOkUlits.Request(this.getContext(), "GetSenseByName.do", jsont.toString());
            if (reques.isEmpty()) {
                return;
            }
            jsont = new JSONObject(reques);
            int date = jsont.getInt("xxx");
            xtime.add(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
            entryArrayList.add(new Entry(date, entryArrayList.size()));
            LineDataSet dataSet = new LineDataSet(entryArrayList, sensorName);
            dataSet.setCircleColor(Color.BLACK);
            LineData lineData = new LineData(xtime, dataSet);
            mchart.setData(lineData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        synchronized (thread) {
            run = true;
            thread.notify();
        }
        mchart.invalidate();
    }

    @Override
    public void onPause() {
        super.onPause();
        synchronized (thread) {
            run = false;
            thread.notify();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destory = true;
        synchronized (thread) {
            thread.notify();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mchart = (LineChart) inflater.inflate(R.layout.fragment_lingchart, null);
        initView();
        return mchart;
    }

    private void initView() {
        mchart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mchart.getAxisLeft().setTextColor(ColorTemplate.getHoloBlue());
        mchart.getAxisLeft().setDrawGridLines(true);
        mchart.getAxisRight().setEnabled(false);
    }
}
