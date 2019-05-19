package com.sms.mensajes;

import android.content.SharedPreferences;
import android.telephony.SmsManager;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by filipp on 5/23/2016.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    SharedPreferences pref;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String message = remoteMessage.getData().get("sms");
        String numero = remoteMessage.getData().get("numero");
        sendNotification(numero, message);
    }

    private void sendNotification(String phoneNo, String sms) {
        //SmsManager smsManager = SmsManager.getDefault();
        //smsManager.sendTextMessage(phoneNo, null, sms, null, null);
        //Toast.makeText(getApplicationContext(), phoneNo+" "+sms,
        //      Toast.LENGTH_LONG).show();
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, sms, null, null);
            //Toast.makeText(getApplicationContext(), "SMS Sent!",
            //        Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(),
              //      "SMS faild, please try again later!",
                //    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}