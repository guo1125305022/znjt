/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  java.lang.Object
 *  java.lang.String
 */
package com.znjtgs.request;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.znjtgs.Cantast;
import com.znjtgs.ulits.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 基本网络数据请求对象
 */
public abstract class BaseRequest {
    private Context context;//定义上下问对象
    private OnResultDataListener listener;//定义网路请求数据接口
    private Handler handler=new Handler(Looper.getMainLooper());//定义消息处理对象
    public abstract String getAction();                         //获取请求动作
    public abstract String getParameter();                      //获取请求数据
    public abstract Object getResultObject(String result);        //获取返回数据并解析成对象

    public BaseRequest(Context context) {
        this.context = context;
    }

    public void setListener(OnResultDataListener listener) {
        this.listener = listener;
    }

    public Context getContext() {
        return context;
    }

    /**
     * 添加当前已登陆的用户信息
     * @param jsonObject
     */
    public void addUser(JSONObject jsonObject) throws JSONException{
        jsonObject.put(Cantast.UserKey.KEY_USER_NAME, UserManager.getUserManager().getUserName());

    }

    /**
     * 链接网络
     */
    public void connection(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String result=HttpOkUlits.Request(getContext(),getAction(),getParameter());
                Object object=getResultObject(result);
                dispathResultData(object);
            }
        }.start();
    }

    /**
     * 分发解析出来的数据
     * @param object
     */
    private void dispathResultData(final Object object){
        if(listener==null){
            return;
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onResultData(BaseRequest.this,object);
            }
        });
    }

    /**
     * 网络数据请求回调接口
     */
    public interface OnResultDataListener{
        void onResultData(BaseRequest baseRequest,Object object);
    }
}

