package com.znjtgs.entity;

import org.json.JSONObject;

/**
 * Created by lenovo on 2017/7/8.
 * 小车基本信息
 */

public abstract class BaseCar {
    protected int carId;
    public static final String KEY_CAR_ID = "carId";//消除编号

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public BaseCar(int carId) {
        this.carId = carId;
    }
    public abstract JSONObject toJSONObject();
}
