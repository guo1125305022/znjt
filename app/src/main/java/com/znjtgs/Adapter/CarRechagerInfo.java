package com.znjtgs.Adapter;

import com.znjtgs.entity.BaseCar;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2017/7/8.
 * 小车充值信息对象
 */

public class CarRechagerInfo extends BaseCar {
    public static final String KEY_CAR_CURR_MONEY="carCurrMoney";
    private int money;//金额
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public CarRechagerInfo(int carId, int money) {
        super(carId);
        this.money=money;
    }
    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(KEY_CAR_ID,carId);
            jsonObject.put(KEY_CAR_CURR_MONEY,money);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
