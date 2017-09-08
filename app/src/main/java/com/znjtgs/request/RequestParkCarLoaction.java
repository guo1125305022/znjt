package com.znjtgs.request;

import android.content.Context;

import com.znjtgs.config.AppNetParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestParkCarLoaction extends BaseRequest {

	@Override
	public String getAction() {
		return AppNetParams.RequestAction.GET_PARK_FREE;
	}

	@Override
	public String getParameter() {
		JSONObject jsonObject=new JSONObject();
		try {
			addUser(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}

	@Override
	public int[] getResultObject(String result) {
		int[] ints = null;

		try {
			JSONArray jsonArray=new JSONArray(result);
			ints=new int[jsonArray.length()];
			for(int i=0;i<jsonArray.length();i++){
				ints[i]=jsonArray.getJSONObject(i).getInt(AppNetParams.ParameterName.PARK_FREE_ID);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return ints;
	}

	public RequestParkCarLoaction(Context context) {
		super(context);
		
	}
}
