package com.znjtgs.entity;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.znjtgs.Cantast;
import com.znjtgs.R;
import com.znjtgs.config.AppNetParams;
import com.znjtgs.request.HttpOkUlits;
import com.znjtgs.ulits.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2017/7/11.
 */

public class CarStateRoadInfo extends BaseCar {
    private int roadId = 0;
    private int status;
    private Context context;
    private boolean carAction;
    private View rootView;
    private TextView tvCarId;
    private TextView tvRoadId;
    private TextView tvRoadZStatus;
    private SwitchCompat switchCompat;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            bindData();
        }
    };


    private void bindData() {
        tvRoadId.setText(roadId+"号道路:");
        tvRoadZStatus.setText(getRoadStatus());
        tvCarId.setText(carId+"号小车:");
        switchCompat.setChecked(!carAction);
    }
    private String getRoadStatus(){
        String sta="顺畅";
        switch (status){
            case 3:
            case 4:
                sta="拥挤";
                break;
            case 5:
                sta="爆表";
                break;
        }
        return sta;
    }

    private LayoutInflater inflater;

    public CarStateRoadInfo(Context context, int carId, int roadId) {
        super(carId);
        this.roadId = roadId;
        this.context = context;
        inflater=LayoutInflater.from(context);
        rootView=inflater.inflate(R.layout.list_item_road_condition_query_layout,null);
        initView();
        bindData();
    }

    public View getView() {
        return rootView;
    }

    public void Rqueest() {
        JSONObject jsonObject = UserManager.getUserManager().toJson();
        try {
            jsonObject.put(AppNetParams.ParameterName.ROAD_ID, roadId);
            jsonObject.put(AppNetParams.ParameterName.CAR_ID, carId);
            String request = HttpOkUlits.Request(context, AppNetParams.RequestAction.GET_ROAD_STATUS, jsonObject.toString());
            JSONObject resultJson = new JSONObject(request);
            status = resultJson.getInt(AppNetParams.ParameterName.ROAD_STATUS);
            carAction = status >=3;
            jsonObject.put(AppNetParams.ParameterName.CAR_ACTION,carAction);
            request = HttpOkUlits.Request(context, AppNetParams.RequestAction.SET_CAR_MOVE, jsonObject.toString());
            resultJson = new JSONObject(request);
            if(!resultJson.getString(Cantast.NetResultKey.KEY_RESULT).equals(Cantast.NetResultKey.KEY_OK)){
                carAction=!carAction;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        handler.sendMessage(new Message());

    }
    private CarStateRoadInfo initView(){
        tvCarId= (TextView) findViewById(R.id.tv_road_car_id_title);
        tvRoadId= (TextView) findViewById(R.id.tv_road_condition_query_road_id_title);
        tvRoadZStatus= (TextView) findViewById(R.id.tv_road_condition_road_state);
        switchCompat= (SwitchCompat) findViewById(R.id.wc_road_car_state);
        return this;
    }

    private View findViewById(int resId) {
        if (rootView == null)
            return null;

        return rootView.findViewById(resId);
    }

    @Override
    public JSONObject toJSONObject() {
        return null;
    }
}
