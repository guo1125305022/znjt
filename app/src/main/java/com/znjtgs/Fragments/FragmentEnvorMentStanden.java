package com.znjtgs.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.znjtgs.Activity.SensorActivity;
import com.znjtgs.Adapter.SensorCurrAdapter;
import com.znjtgs.R;
import com.znjtgs.entity.SensorinfoBySensorName;
import com.znjtgs.request.HttpOkUlits;
import com.znjtgs.ulits.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;


public class FragmentEnvorMentStanden extends BaseFragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "FragmentEnvorMentStanden";
    private GridView gv_huanjingzhibiao;
    private SensorCurrAdapter adapter;
    private ArrayList<SensorinfoBySensorName> list=new ArrayList<SensorinfoBySensorName>(){
    };
    private boolean run = true;
    private boolean pause = true;

    public FragmentEnvorMentStanden() {
        thread.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        run = true;
        pause = true;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_huangjinzhibiao;
    }

    @Override
    protected void initComponent(View root) {
        setTitle("环境指标");
        gv_huanjingzhibiao = (GridView) root.findViewById(R.id.gv_view);
        initSensorItmes();
        gv_huanjingzhibiao.setAdapter(adapter);
        gv_huanjingzhibiao.setOnItemClickListener(this);

    }


    private void initSensorItmes() {
        list=new ArrayList<>();
        for (int i = 0; i < SensorActivity.strings.length; i++) {
            list.add(new SensorinfoBySensorName(SensorActivity.strings[i][1], 0, new Random().nextInt(200)));
        }
        adapter = new SensorCurrAdapter(this.getContext(), list);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
            Log.i(TAG, "handleMessage: 数据刷新");
        }
    };

    private Thread thread = new Thread() {
        @Override
        public void run() {
            super.run();
            while (run) {
                while (!pause) {
                    logic();
                  //  Log.i(TAG, "run: 线程开始请求数据");
                    handler.sendMessage(new Message());
                    sleepTime(3000);
                }

                //Log.i(TAG, "run: 线程开始运行");
               // handler.sendMessage(new Message());
                sleepTime(3000);
            }

        }

        private void sleepTime(long time) {
            try {
                sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        pause = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        run = false;
        pause = false;

    }

    @Override
    public void onResume() {
        super.onResume();
        pause = false;
        handler.sendMessage(new Message());//请求刷新界面
    }

    private void logic() {
        JSONObject jsonObject =UserManager.getUserManager().toJson();
        try {
            String result = HttpOkUlits.Request(this.getActivity(), "GetAllSense.do", jsonObject.toString());
            jsonObject = new JSONObject(result);
            list.get(0).setCurDate(jsonObject.getInt(SensorActivity.strings[0][0]));
            list.get(1).setCurDate(jsonObject.getInt(SensorActivity.strings[1][0]));
            list.get(2).setCurDate(jsonObject.getInt(SensorActivity.strings[2][0]));
            list.get(3).setCurDate(jsonObject.getInt(SensorActivity.strings[3][0]));
            list.get(4).setCurDate(jsonObject.getInt(SensorActivity.strings[4][0]));
            Log.i(TAG, "logic: "+list.get(0).getCurDate());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i(TAG, "logic: "+e.getMessage());
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this.getContext(), SensorActivity.class);
        intent.putExtra("index", position);
        startActivity(intent);
    }

}
