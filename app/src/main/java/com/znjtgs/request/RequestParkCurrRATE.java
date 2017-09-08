package com.znjtgs.request;

import android.content.Context;

import com.znjtgs.config.AppNetParams;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestParkCurrRATE extends BaseRequest {

	@Override
	public String getAction() {
		return AppNetParams.RequestAction.GET_PARK_RATE;
	}

	@Override
	public String getParameter() {
		JSONObject jsonObject = new JSONObject();
		try {
			addUser(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}

	@Override
	public Object getResultObject(String result) {
		//		RateTyobjpe’:’ Count’,’ Money’:10
		int mony=-1;
//		Log.i("RequestParkCurrRATE", responseString);
		try{
			JSONObject json=new JSONObject(result);
			mony=json.getInt("Money");
			return mony;
		}catch (Exception ignored){

		}

		return mony;
	}

	public RequestParkCurrRATE(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

}
