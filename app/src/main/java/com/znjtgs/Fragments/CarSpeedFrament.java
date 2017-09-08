package com.znjtgs.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;

import com.znjtgs.Adapter.CarSpeedAdapter;
import com.znjtgs.R;
import com.znjtgs.config.AppNetParams;
import com.znjtgs.entity.CarSpeedInfo;
import com.znjtgs.file.FileTools;
import com.znjtgs.request.HttpOkUlits;
import com.znjtgs.ulits.UserManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CarSpeedFrament extends BaseFragment {
    public static String locadPath;
    private ArrayList<CarSpeedInfo> speedInfos = new ArrayList<>();
    private GridView gridView;
    private CarSpeedAdapter adapter;
    private boolean run = false;
    private boolean pause = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
        }
    };


    private Thread thread = new Thread() {
        @Override
        public void run() {
            super.run();
            while (run) {
                while (!pause) {
                    logic();
                    threadSleep(5000);
                }
                threadSleep(5000);
            }
        }

        private void threadSleep(long time) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private void logic() {
        for (CarSpeedInfo info : speedInfos) {
            requestCarSpeed(info);
        }
        sendMsg();
    }
    private void sendMsg(){
        handler.sendMessage(new Message());
    }

    private void requestCarSpeed(CarSpeedInfo carSpeedInfo) {
        JSONObject jsonObject = UserManager.getUserManager().toJson();
        try {
            jsonObject.put(AppNetParams.ParameterName.CAR_ID, carSpeedInfo.getCarId());
            String request = HttpOkUlits.Request(this.getContext(), AppNetParams.RequestAction.GET_CAR_SPEED, jsonObject.toString());
            jsonObject = new JSONObject(request);
            carSpeedInfo.setSpeed(jsonObject.getInt(AppNetParams.ParameterName.CAR_SPEED));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locadPath = this.getActivity().getFilesDir().toString() + "carSpeed.data";
        run=true;
        pause=false;
        thread.start();
    }

    @Override
    protected int initLayout() {
        return R.layout.my_card_frament_car_speed;
    }

    @Override
    protected void initComponent(View root) {
        initApadter();
        setTitle("小车状态");
        gridView = (GridView) root.findViewById(R.id.gv_car_speel);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        pause=false;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        pause=true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        run=false;
        pause=true;
    }

    private void initApadter() {
        speedInfos.clear();
        if (!initLocadData()) {
            for (int i = 0; i < 8; i++) {
                speedInfos.add(new CarSpeedInfo(i + 1, 0));
            }
        }
        adapter = new CarSpeedAdapter(this.getContext(), speedInfos);
    }

    private boolean initLocadData() {
        String data = FileTools.openFileToString(locadPath);
        if (data.isEmpty()) {
            return false;
        }
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int carId = jsonObject.getInt(CarSpeedInfo.KEY_CAR_ID);
                int speed = jsonObject.getInt(CarSpeedInfo.KEY_CAR_SPEED);
                speedInfos.add(new CarSpeedInfo(carId, speed));
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }
}
