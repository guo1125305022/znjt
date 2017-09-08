package com.znjtgs.server;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.znjtgs.Activity.MainActivity;
import com.znjtgs.config.AppNetParams;
import com.znjtgs.db.BusXCapacityDb;
import com.znjtgs.request.HttpOkUlits;
import com.znjtgs.ulits.NotifiactionUlits;
import com.znjtgs.ulits.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

public class BusCapServer extends Service {
    private boolean run = false;
    private boolean pause = false;
    private BusThread busThread;
    private static final int BUS_NOT_1=0x555;
    private static final int BUS_NOT_2=0x554;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            sendNotitf(msg.obj);
        }
    };

    private void sendNotitf(Object obj) {
        int[]  ints= (int[]) obj;
        if(ints[0]>50){
            NotifiactionUlits.ShowNotifiaction(this,"公交超载","1号公交车超载"+(ints[0]-50)+"人", MainActivity.class,BUS_NOT_1);
        }else {
            NotifiactionUlits.Clare(this,BUS_NOT_1);
        }
        if(ints[1]>50){
            NotifiactionUlits.ShowNotifiaction(this,"公交超载","2号公交车超载"+(ints[1]-50)+"人", MainActivity.class,BUS_NOT_2);
        }else {
            NotifiactionUlits.Clare(this,BUS_NOT_2);
        }
    }

    public BusCapServer() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        run = false;
        pause = true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        run = true;
        pause = false;
        busThread = new BusThread();
        busThread.start();
    }


    private class BusThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (run) {
                while (!pause) {
                    int[] ints = new int[2];
                    ints[0] = logic(1);
                    ints[1] = logic(2);
                    Message message = new Message();
                    message.obj = ints;
                    handler.sendMessage(message);
                    ThreadSleep(10000);
                }
                ThreadSleep(10000);
            }
        }
    }

    private void ThreadSleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int logic(int busId) {
        int capacity = -1;
        JSONObject jsonObject = UserManager.getUserManager().toJson();
        try {
            jsonObject.put(AppNetParams.ParameterName.BUS_ID, busId);
            String request = HttpOkUlits.Request(this, AppNetParams.RequestAction.GET_BUS_CAPACITY, jsonObject.toString());
            jsonObject = new JSONObject(request);
            capacity = jsonObject.getInt(AppNetParams.ParameterName.KEY_BUS_CAPACITY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (capacity > 50) {
            BusXCapacityDb.get(this).insert(busId,capacity);
        }
        return capacity;
    }
}
