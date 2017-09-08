package com.znjtgs.request;

import android.content.Context;
import android.util.Log;

import com.znjtgs.Cantast;
import com.znjtgs.config.AppNetParams;
import com.znjtgs.ulits.ToastUlits;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JKX_GXL on 2017/5/15.
 */

public class UserRegisteRequest extends BaseRequest {
    private static final String TAG = "UserLoginRequest";
    private String userName;
    private String userPwd;
    private String userPhoneNumber;
    private boolean authority;

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public void setAuthority(boolean authority) {
        this.authority = authority;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    @Override
    public String getAction() {
        return AppNetParams.RequestAction.USER_REGISTER;
    }

    @Override
    public String getParameter() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Cantast.UserKey.KEY_USER_NAME, userName);
            jsonObject.put(Cantast.UserKey.KEY_USER_PWD, userPwd);
            jsonObject.put(Cantast.UserKey.KEY_EMAIL, authority);
            jsonObject.put(Cantast.UserKey.KEY_PHONE_NUMBER, userPhoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @Override
    public Boolean getResultObject(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String res = jsonObject.getString(Cantast.NetResultKey.KEY_RESULT);
            String msg = jsonObject.getString(Cantast.NetResultKey.KEY_MSG);
            ToastUlits.ShowToastShort(getContext(),msg);
            if (!res.equals(Cantast.NetResultKey.KEY_RESULT)) {
                Log.i(TAG, "anaylizeResponse: " + msg);
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            ToastUlits.ShowToastShort(getContext(),"数据解析失败");
            return false;
        }
        return true;
    }

    public UserRegisteRequest(Context context) {
        super(context);
    }




}
