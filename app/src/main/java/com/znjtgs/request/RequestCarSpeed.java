package com.znjtgs.request;

import android.content.Context;
import android.util.Log;

import com.znjtgs.config.AppNetParams;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestCarSpeed extends BaseRequest {

	private int carId;
	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}
	public RequestCarSpeed(Context context) {
		super(context);

	}
	@Override
	public String getAction() {
		return AppNetParams.RequestAction.GET_CAR_SPEED;
	}

	@Override
	public String getParameter() {
		JSONObject json=new JSONObject();
		try {
			addUser(json);
			json.put("CarId", getCarId());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return json.toString();

	}

	@Override
	public Object getResultObject(String result) {
		int speed=0;

		try {
			JSONObject json=new JSONObject(result);
			Log.d("json数据为", json.toString());
			speed=json.getInt("CarSpeed");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return speed;
	}




}
