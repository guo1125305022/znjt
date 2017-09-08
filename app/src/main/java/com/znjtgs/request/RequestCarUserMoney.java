package com.znjtgs.request;

import android.content.Context;

import com.znjtgs.config.AppNetParams;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestCarUserMoney extends BaseRequest {

    private int CarId;
    private int Money;
    public static final int SET_CAR_MONEY = 0xcc;
    public static final int GET_CAR_MONEY = 0xcd;
    private int RequestMethod = GET_CAR_MONEY;

    public int getCarId() {
        return CarId;
    }

    public int getMoney() {
        return Money;
    }

    public void setCarId(int carId) {
        CarId = carId;
    }

    public void setMoney(int money) {
        Money = money;
    }

    public void setRequestMethod(int getAnSet) {
        this.RequestMethod = getAnSet;
    }

    @Override
    public String getAction() {
        return  (RequestMethod == GET_CAR_MONEY ? AppNetParams.RequestAction.GET_CAR_ACCOUNT_BALANCE
                : AppNetParams.RequestAction.SET_CAR_ACCOUNT_RECHARGE);
    }

    @Override
    public String getParameter() {
        JSONObject json = new JSONObject();
        try {
            addUser(json);
            json.put("CarId", CarId);
            if (RequestMethod == SET_CAR_MONEY) {
                json.put("Money", Money);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return json.toString();
    }

    @Override
    public Object getResultObject(String result) {
        try {
            JSONObject json = new JSONObject(result);
            if (RequestMethod == GET_CAR_MONEY) {
                return json.getInt("Balance");
            }
            if (RequestMethod == SET_CAR_MONEY) {
                return json.getString("Result");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RequestCarUserMoney(Context context) {
        super(context);
    }



}
