package com.znjtgs.Activity;

import android.os.Handler;
import android.os.Message;

import com.znjtgs.Cantast;
import com.znjtgs.R;
import com.znjtgs.config.AppNetParams;
import com.znjtgs.request.HttpOkUlits;
import com.znjtgs.ulits.ToastUlits;
import com.znjtgs.ulits.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2017/7/9.
 *
 * @author 郭小浪
 * @version 1.0
 * @since 车辆限行
 */

public class CarLimitActivity extends BaseActivity {
    private boolean run = false;
    private boolean pause = false;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showToast(msg);
        }
    };

    private void showToast(Message msg) {
        ToastUlits.ShowToastShort(this,msg.obj.toString());
    }

    private Thread thread = new Thread() {
        @Override
        public void run() {
            super.run();
            while (run) {
                while (!pause) {
                    logic();
                    threadSleep(10000);
                }
            }
            threadSleep(5000);
        }

    };

    private void threadSleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void logic() {
        StringBuilder setcarMsg=new StringBuilder();
        int pm=getPM();
        if (pm<400){
            setcarMsg.append( setCarRunState(1,true)+"\n");
            setcarMsg.append( setCarRunState(2,true)+"\n");
            setcarMsg.append( setCarRunState(3,true)+"\n");
        }else if(pm>=400&&pm<500){
            setcarMsg.append( setCarRunState(1,false)+"\n");
            setcarMsg.append( setCarRunState(2,false)+"\n");
        }else if(pm>500){
            setcarMsg.append( setCarRunState(1,false)+"\n");
            setcarMsg.append( setCarRunState(2,false)+"\n");
            setcarMsg.append( setCarRunState(3,false)+"\n");
        }
        Message message=new Message();
        message.obj=setcarMsg.toString();
        handler.sendMessage(message);
    }

    private int getPM() {
        JSONObject jsonObject = UserManager.getUserManager().toJson();
        int pm=0;
        try {
            jsonObject.put(AppNetParams.ParameterName.SENSE_NAME, AppNetParams.ParameterName.SENSE_NAME_PM);
            String request = HttpOkUlits.Request(this, AppNetParams.RequestAction.GET_SEMSOR_BY_SENSOR_NAME, jsonObject.toString());
            jsonObject = new JSONObject(request);
            pm=jsonObject.getInt("xxx");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pm;
    }

    private String setCarRunState(int carId, boolean state) {
        JSONObject jsonObject = UserManager.getUserManager().toJson();
        String s="号小车"+(state?"启动":"停止")+"状态设置失败";;
        try {
            jsonObject.put(AppNetParams.ParameterName.CAR_ID, carId);
            jsonObject.put(AppNetParams.ParameterName.CAR_ACTION,
                    (state ? AppNetParams.ParameterName.CAR_ACTION_START :
                            AppNetParams.ParameterName.CAR_ACTION_STOP));
            String request = HttpOkUlits.Request(this, AppNetParams.RequestAction.SET_CAR_MOVE, jsonObject.toString());
            jsonObject = new JSONObject(request);
            boolean setState=jsonObject.getString(Cantast.NetResultKey.KEY_RESULT).equals(Cantast.NetResultKey.KEY_OK);

            if(setState){
                s=carId+"号小车"+(state?"启动":"停止")+"状态设置成功";
            }
            return s;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;
    }


    @Override
    protected int initLayouotId() {
        return R.layout.activity_car_limit_layout;
    }

    @Override
    protected void initComponent() {
        setMainTitle("车辆限行");
        run = true;
        pause = false;
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
