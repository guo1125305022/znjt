package com.znjtgs.request;

import android.content.Context;
import android.util.Log;

import com.znjtgs.Cantast;
import com.znjtgs.config.AppNetParams;
import com.znjtgs.entity.MyRoadTrafficLightInfo;

import org.json.JSONException;
import org.json.JSONObject;


public class RequestRedGreedLight extends BaseRequest {
    private int trafficLightID;

    @Override
    public String getAction() {
        return AppNetParams.RequestAction.GET_TRAFFIC_LINGT_CONFIG_ACTION;
    }

    @Override
    public String getParameter() {
        JSONObject json = new JSONObject();
        try {
            addUser(json);
            json.put("TrafficLightId", this.trafficLightID);
            Log.i("RequestRedGreedLight->getParams", json.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json.toString();
    }

    @Override
    public Object getResultObject(String result) {
        //		Log.i("RequestRedGreedLight->anaylizeResponse->responseString", responseString);
        MyRoadTrafficLightInfo myRoadTrafficLightInfo=new MyRoadTrafficLightInfo();
        try {
            JSONObject jsonObject=new JSONObject(result);
            myRoadTrafficLightInfo.setTrrfficLightId(trafficLightID);
            myRoadTrafficLightInfo.setReadTime(jsonObject.getInt(AppNetParams.ParameterName.KEY_RED_TIME));
            myRoadTrafficLightInfo.setGreenTime(jsonObject.getInt(AppNetParams.ParameterName.KEY_GREEN_TIME));
            myRoadTrafficLightInfo.setYellowTime(jsonObject.getInt(AppNetParams.ParameterName.KEY_YELLOW_TIME));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return myRoadTrafficLightInfo;
    }

    public RequestRedGreedLight(Context context) {
        super(context);
    }

    public void setTrafficLightID(int trafficLightID) {
        this.trafficLightID = trafficLightID;
    }




}
