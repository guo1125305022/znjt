package com.znjtgs.ulits;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.znjtgs.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by JKX_GXL on 2017/5/17.
 * 通知栏工具类
 */

public class NotifiactionUlits {
    public static void ShowNotifiaction(Context context, String title, String contentText, Class<? extends Activity> aClass, int flag) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(contentText);
        Intent resultIntent = new Intent(context, aClass);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,0, resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(flag, mBuilder.build());
    }
    public static void Clare(Context context,int id){
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }
}
