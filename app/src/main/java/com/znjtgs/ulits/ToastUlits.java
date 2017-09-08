package com.znjtgs.ulits;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by JKX_GXL on 2017/5/11.
 */

public class ToastUlits {

    public static void ShowToastLong(final Context context, final String msg){
        ShowToastTime(context,msg,Toast.LENGTH_LONG);
    }
    public static void ShowToastShort(Context context,String msg){
        ShowToastTime(context,msg,Toast.LENGTH_SHORT);
    }

    public static void ShowToastLong(Context context,int msgRes){
        ShowToastTime(context,context.getString(msgRes),Toast.LENGTH_LONG);
    }
    public static void ShowToastShort(Context context,int msgRes){
        ShowToastTime(context,context.getString(msgRes),Toast.LENGTH_SHORT);
    }

    public static void ShowToastTime(final Context context, final String msg, final int toastLength){
        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,msg,toastLength).show();
            }
        });
    }
}
