package com.apptaxi.clientedemo;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by filipp on 5/23/2016.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{

    String TAG = FirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG,remoteMessage.getData().toString());
        String t = remoteMessage.getData().get("tipo");//si es evaluacion o un mensaje
        if(Integer.parseInt(t)==0){
            Intent i = new Intent(getApplicationContext(), pedido_evaluar.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("id_pedido", Integer.parseInt(remoteMessage.getData().get("id_pedido")));
            i.putExtra("precio", remoteMessage.getData().get("precio"));
            startActivity(i);
        }
    }


    private void sendNotification(String message, String title, String t,String id) {

        /*PendingIntent pendingIntent;
        int v = Integer.parseInt(t);
        if(v==1) {
            Intent i = new Intent(getApplicationContext(), mensajes_leer.class);
            i.putExtra("id_mensaje", id);
            pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,i, 0);
        }else{
            Intent i = new Intent(getApplicationContext(), mensajes_leer.class);
            i.putExtra("id_mensaje", id);
            pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,i, 0);
        }
        //Setup notification
        //Sound
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Build notification
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("My GCM message :X:X")
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSound(sound)
                .setContentTitle(title);
                //.setSmallIcon(R.drawable.mensajes);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noBuilder.build()); //0 = ID of notification*/


    }

}
