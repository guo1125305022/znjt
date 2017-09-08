package com.znjtgs.entity;

import com.znjtgs.config.AppNetParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2017/7/9.
 * @author 郭小浪
 * @version 1.0
 * @since  解析停止场和ECT缴费记录
 */

public class CarParkPayEtcPay extends BaseCar {
    private int money;
    private String date;
    private String type;
    private static final String TYPE_ETC_KEY="etcout";
    private static final String TYPE_PARK_KEY="parkout";
    private static final String KEY_TIME="Time";
    private static final String KEY_ADDR="Addr";
    private static final String KEY_CONST="Cost";
    public CarParkPayEtcPay(JSONObject jsonObject){
        super(1);
        init(jsonObject);
    }

    private void init(JSONObject jsonObject) {
        int carId= 1;
        try {
            carId = jsonObject.getInt(AppNetParams.ParameterName.CAR_ID);
            type=jsonObject.getString(KEY_ADDR);
            date=jsonObject.getString(KEY_TIME);

            money=jsonObject.getInt(KEY_CONST);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(type.equals(TYPE_ETC_KEY)){
            type="ETC缴费";
        }else if(type.equals(TYPE_PARK_KEY)){
            type="停车缴费";
        }else {
            type="未知类型";
        }
        setCarId(carId);
    }

    public int getMoney() {
        return money;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    @Override
    public JSONObject toJSONObject() {
        return null;
    }
}
