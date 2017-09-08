package com.znjtgs.request;

import android.content.Context;
import android.util.Log;

import com.znjtgs.Cantast;
import com.znjtgs.config.AppNetParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JKX_GXL on 2017/5/15.
 */

public class UserLoginRequest extends BaseRequest {
    private static final String TAG = "UserLoginRequest";
    private String userName;
    private String userPwd;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    @Override
    public String getAction() {
        return  AppNetParams.RequestAction.USER_LOGIN;
    }

    @Override
    public String getParameter() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(Cantast.UserKey.KEY_USER_NAME,userName);
            jsonObject.put(Cantast.UserKey.KEY_USER_PWD,userPwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @Override
    public Object getResultObject(String result) {
        try {
            JSONObject jsonObject=new JSONObject(result);
            String res=jsonObject.getString(Cantast.NetResultKey.KEY_RESULT);
            String msg=jsonObject.getString(Cantast.NetResultKey.KEY_MSG);
            if(res.equals( Cantast.NetResultKey.KEY_OK)){
                Log.i(TAG, "anaylizeResponse: "+msg);
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public UserLoginRequest(Context context) {
        super(context);
    }


}
