package com.sms.mensajes;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by filipp on 5/23/2016.
 */
public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    SharedPreferences pref;
    @Override
    public void onTokenRefresh() {

        pref = getApplicationContext().getSharedPreferences("MySession", MODE_PRIVATE);
        String token = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("tokenid", token);
        editor.commit();

    }

    private void ShowToast(Context ctx, String text){
        Context context = ctx;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }



}
