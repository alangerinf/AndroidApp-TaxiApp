package com.apptaxi.clientedemo;

import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import java.lang.*;

/**
 * Created by filipp on 5/23/2016.
 */
public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    SharedPreferences pref;
    @Override
    public void onTokenRefresh() {

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        String token = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("tokenid", token);
        editor.putBoolean("actualizarToken", true);
        editor.commit();

    }

}
