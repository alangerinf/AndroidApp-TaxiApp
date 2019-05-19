package com.apptaxi.conductordemo;

import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


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
        editor.commit();

    }

}
