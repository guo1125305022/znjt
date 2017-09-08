package com.znjtgs.ulits;

import com.znjtgs.Cantast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JKX_GXL on 2017/5/16.
 * @author 郭小浪
 * @version 1.0
 * @since  这是一个用于管理用户信息的一个单例对象
 * 全局只有一个这个对象
 */

public class UserManager {
    private static String userName ;//用户名
    private boolean loginState = false;//登陆状态
    private static UserManager userManager;//用户管理器对象

    private UserManager() {//实现单例化对象
    }

    public static UserManager getUserManager() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean getLoginState() {
        return loginState;
    }

    public JSONObject toJson(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(Cantast.UserKey.KEY_USER_NAME,userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void setLoginState(boolean loginState) {
        this.loginState = loginState;
    }

    public void clear(){
        userName=null;
    }
}
