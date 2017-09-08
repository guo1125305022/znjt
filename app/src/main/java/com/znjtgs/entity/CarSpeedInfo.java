package com.znjtgs.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2017/7/8.
 */

public class CarSpeedInfo extends BaseCar {
    private int speed;
    public static final String KEY_CAR_SPEED="carSpeed";

    public int getSpeed() {
        return speed;
    }

    public CarSpeedInfo(int carId, int speed) {
        super(carId);
        this.speed=speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(KEY_CAR_ID,carId);
            jsonObject.put(KEY_CAR_SPEED,speed);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
