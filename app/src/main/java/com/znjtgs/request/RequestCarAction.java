package com.znjtgs.request;

import android.content.Context;

import com.znjtgs.Cantast;
import com.znjtgs.config.AppNetParams;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestCarAction extends BaseRequest {

    private int CarId;
    private Boolean CarAction;

    @Override
    public String getAction() {
        return AppNetParams.RequestAction.SET_CAR_MOVE;
    }

    @Override
    public String getParameter() {
        String action = (CarAction ? "Start" : "Stop");
        JSONObject json = new JSONObject();
        try {
            addUser(json);
            json.put("CarId", CarId);
            json.put("CarAction", action);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json.toString();
    }

    @Override
    public String getResultObject(String result) {
        StringBuilder state = new StringBuilder("设置ID" + CarId + "小车状态");
        JSONObject json;
        try {
            json = new JSONObject(result);
            if (json.getString(Cantast.NetResultKey.KEY_RESULT).equals(Cantast.NetResultKey.KEY_OK)) {
                state.append("成功");
                return state.toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        state.append("失败");
        return state.toString();
    }

    public RequestCarAction(Context context) {
        super(context);
    }

    public void setCarId(int carId) {
        CarId = carId;
    }

    public void setCarAction(Boolean carAction) {
        CarAction = carAction;
    }




}
