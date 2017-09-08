package com.znjtgs.entity;

import com.znjtgs.Fragments.FramentCarControl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2017/7/7.
 * @author 郭小浪
 * @version 1.0
 */

public class CarControlInfo extends BaseCar {

    private boolean state;
    public CarControlInfo(int carId, boolean state) {
        super(carId);
        this.state = state;
    }

    public int getCarId() {
        return carId;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isState() {
        return state;
    }

    public JSONObject toJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(BaseCar.KEY_CAR_ID, carId);
            jsonObject.put(FramentCarControl.KEY_CONTROL_STATE, state);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
