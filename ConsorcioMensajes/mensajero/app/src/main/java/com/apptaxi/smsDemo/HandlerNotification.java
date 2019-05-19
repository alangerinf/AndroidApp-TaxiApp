package com.apptaxi.smsDemo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by alanger on 09/04/18.
 */

public class HandlerNotification {

    static final public int idNotification = 105906;

    private NotificationCompat.Builder mBuilder;
    private Intent resIntent ;
    private Context ctx;
    private NotificationManager mNotificationManager;

    public HandlerNotification(Context ctx, String num, String mes){

        this.ctx = ctx;
        mBuilder = new NotificationCompat.Builder(ctx)
                .setSmallIcon(android.R.drawable.ic_notification_clear_all)
                .setContentTitle(" Mensaje :")
                .setContentText(num+": "+mes+".");
                //.setColor(225)
                //.setTimeoutAfter(10*1000);


    }

    public void setResIntent(Class act){
        resIntent = new Intent(ctx,act);
        PendingIntent resultpendingIntent = PendingIntent.getActivity(
                ctx,
                0,
                resIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultpendingIntent);
        mNotificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);

    }

    public void showNotify(){
        mNotificationManager.notify(idNotification,mBuilder.build());
    }






}
