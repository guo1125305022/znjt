package com.znjtgs.Activity;

import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.znjtgs.Adapter.RedGreedLightAdapter;
import com.znjtgs.Adapter.RoadRedGreeLightAdapter;
import com.znjtgs.R;
import com.znjtgs.config.AppNetParams;
import com.znjtgs.entity.TrafficLightInfo;
import com.znjtgs.request.HttpOkUlits;
import com.znjtgs.ulits.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/15.
 */

public class ActivityRedGreeLightManager extends BaseActivity {
    private boolean run=false;
    private boolean pause=false;
    private ListView listView;
    private RedGreedLightAdapter adapter;
    private ArrayList<TrafficLightInfo> lightInfos;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
        }
    };
    private Thread thread=new Thread(){
        @Override
        public void run() {
            super.run();
            while (run){
                while (!pause){
                    logic();
                    ThreadSleep(10000);
                }
                ThreadSleep(10000);
            }
        }
    };

    private void logic() {
        for (TrafficLightInfo info :lightInfos){
            info.setRoadStatus(getStatus(info.getRoadId()));
            info.setSouresGreedTime(getRedGreedLight(info.getTrrfficLightId()));
        }
        handler.sendMessage(new Message());
    }

    private int getRedGreedLight(int id){
        int time=-1;
        JSONObject jsonObject=UserManager.getUserManager().toJson();
        try {
            jsonObject.put(AppNetParams.ParameterName.KEY_TRAFFIC_LIGHT_ID,id);
            jsonObject.put(AppNetParams.ParameterName.KEY_TRAFFIC_TYPE,
                    AppNetParams.ParameterName.KEY_GREEN_TIME);
            String request = HttpOkUlits.Request(this, AppNetParams.RequestAction.GET_TRAFFIC_LIGHT_NOW_STATUS, jsonObject.toString());
            jsonObject=new JSONObject(request);
            time=jsonObject.getInt(AppNetParams.ParameterName.KEY_TIME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return time;
    }

    private int getStatus(int roadId){
        int roadStatus=-1;
        JSONObject jsonObject = UserManager.getUserManager().toJson();
        try {
            jsonObject.put(AppNetParams.ParameterName.ROAD_ID,roadId);
            String request = HttpOkUlits.Request(this, AppNetParams.RequestAction.GET_ROAD_STATUS, jsonObject.toString());
            jsonObject=new JSONObject(request);
            roadStatus=jsonObject.getInt(AppNetParams.ParameterName.ROAD_STATUS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return roadStatus;
    }

    private void ThreadSleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int initLayouotId() {
        //ActivityRedGreeLightManager
        return R.layout.activity_red_gree_light_manager_layout;
    }

    @Override
    protected void initComponent() {
        setMainTitle("红绿灯管理");
        listView= (ListView) findViewById(R.id.lv_red_gree_light);
        lightInfos=new ArrayList<>();
        lightInfos.add(new TrafficLightInfo(1,1,20));
        lightInfos.add(new TrafficLightInfo(2,2,20));
        lightInfos.add(new TrafficLightInfo(3,3,20));
        adapter= new RedGreedLightAdapter(this,lightInfos);
        listView.setAdapter(adapter);
        run=true;
        pause=false;
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        pause=false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        run=false;
        pause=true;
    }
}
