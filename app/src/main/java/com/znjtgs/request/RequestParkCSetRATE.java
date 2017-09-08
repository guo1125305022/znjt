package com.znjtgs.request;

import android.content.Context;
import android.util.Log;

import com.znjtgs.config.AppNetParams;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestParkCSetRATE extends BaseRequest{
	private int Money;

	@Override
	public String getAction() {
		return  AppNetParams.RequestAction.SET_PARK_RATE;
	}

	@Override
	public String getParameter() {
		JSONObject json=new JSONObject();
		try {
			addUser(json);
			json.put(AppNetParams.ParameterName.MONEY, Money);
			json.put(AppNetParams.ParameterName.RATE_TYPE, "Count");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return json.toString();

	}

	@Override
	public Object getResultObject(String result) {
		Log.i("RequestParkCSetRATE->anaylizeResponse", result);

		StringBuilder sb=new StringBuilder("设置停车费率为："+Money);
		try {
			JSONObject json=new JSONObject(result);
			if(json.getString("result").equals("ok")){
				sb.append("成功");
				return sb.toString();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sb.append("失败").toString();
	}

	public RequestParkCSetRATE(Context context) {
		super(context);

	}
	public void setMoney(int money) {
		Money = money;
	}


}
