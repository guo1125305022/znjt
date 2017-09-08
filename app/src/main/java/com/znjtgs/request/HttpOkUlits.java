package com.znjtgs.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.znjtgs.Cantast;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Admin on 2017/5/20.
 */

public class HttpOkUlits {
    private static final String BASE_URL="http://%s:%s/transportservice/action/%s";
    private static Context context=null;
    public static String Request(Context context, String action,String paremter)  {
        if(context==null){
            return "";
        }
        if(context==null){
            HttpOkUlits.context=context;
        }
        if(HttpOkUlits.context!=context){
            HttpOkUlits.context=context;
        }
        Request.Builder builder = new Request.Builder().url(String.format(getUrl(context,action)));
        builder.method("POST", RequestBody.create(MediaType.parse(""), paremter));
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            Response execute = okHttpClient.newCall(builder.build()).execute();
            if (execute.isSuccessful()) {
                String result = execute.body().string();
                if (result.isEmpty()) {
                    return "";
                }
                return result;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
//        String uri=getUrl(context,action);
//        Request.Builder builder = new Request.Builder().url(uri);
//        RequestBody requestBody = RequestBody.create(MediaType.parse(""), parmare);
//        Request request = builder.build();
//        builder.method("POST", RequestBody.create(MediaType.parse(""), paremter));
//        OkHttpClient okHttpClient = new OkHttpClient();
//        try{
//            Response execute = okHttpClient.newCall(request).execute();
//            if (execute.isSuccessful()) {
//                return execute.body().string();
//            }
//        }catch (Exception e){
//
//        }
//
//        return null;
    }
    private static String getUrl(Context context,String action){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=preferences.getString(Cantast.NetKey.KEY_SERVER_ADDRESS,Cantast.NetKey.KEY_SERVER_IP_DEFAULT_VALUES);
        String port=preferences.getString(Cantast.NetKey.KEY_SERVER_PORT,Cantast.NetKey.KEY_SERVER_PORT_DEFAULT_VALUE);
        return  String.format(BASE_URL,ip,port,action);
    }

    private static void sendMessage(){

    }
}
