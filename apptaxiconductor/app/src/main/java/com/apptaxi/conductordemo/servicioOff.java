package com.apptaxi.conductordemo;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.apptaxi.conductordemo.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrador on 28/08/2017.
 */
public class servicioOff extends Service {

    final Handler handler = new Handler();
    Notification notification;
    NotificationManager notificationManager;
    Context ctx;

    SharedPreferences pref;
    double lat, lng;
    float bearing;
    String tokenid,UrlServidor;
    int id_usuario,id_pedido;
    LocationManager locationManager;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent i, int flags, int startId) {

        ctx = this;
        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);

        handler.removeCallbacks(runnable);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener mlocListener = new MyLocationListener();
        mlocListener.setMainActivity(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return 0;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

        Intent intent = new Intent(this, iniciar.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.ic_launcher, "go", pendingIntent).build();

        //Notification
        notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText(global.escaneando)
                .setContentTitle(global.nameEmpresa)
                .addAction(action)
                .build();

        notification.flags = Notification.FLAG_ONGOING_EVENT;
        //Send notification
        notificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

        runnable.run();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        notificationManager.cancel(1);
        handler.removeCallbacks(runnable);
    }

    Runnable runnable = new Runnable() {
        public void run() {

            id_usuario = pref.getInt("id_usuario",0);
            tokenid="none";
            if(pref.contains("tokenid")) {
                tokenid = pref.getString("tokenid", null);
            }
            if(tokenid==null){
                tokenid="none";
            }

            try{

                lat = Double.parseDouble(pref.getString("lat",String.valueOf(global.latInicio)));
                lng = Double.parseDouble(pref.getString("lng",String.valueOf(global.lngInicio)));
                bearing = Float.parseFloat(pref.getString("bearing","0.0f"));

                UrlServidor = global.UL0002;
                //global.ShowToast(ctx,lat+' '+lng+' '+bearing+' '+tokenid);
                UpdateCoordenadas();

            }catch (Exception e){
                //algo salio mal
                global.ShowToast(ctx,"Algo salio mal");
            }

            if(pref.getInt("conectado",1)==1) {
                handler.postDelayed(runnable, 10000);
            }else{
                handler.postDelayed(runnable, 5000);
            }
        }
    };

    private void UpdateCoordenadas() {
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + UrlServidor,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                if(pref.getInt("id_pedido",0)>0){
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putInt("estado_pedido", data.getInt("estado_pedido"));
                                    editor.commit();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_usuario", String.valueOf(id_usuario));
                params.put("id_pedido", String.valueOf(pref.getInt("id_pedido",0)));
                params.put("lat", String.valueOf(lat));
                params.put("lng", String.valueOf(lng));
                params.put("bearing", String.valueOf(bearing));
                params.put("token", tokenid);
                params.put("conectado", String.valueOf(pref.getInt("conectado",1)));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(sr);
    }

    /////////////gps/////////////////////
    public class MyLocationListener implements LocationListener {
        servicioOff servicioDisponible;
        public servicioOff getMainActivity() {
            return servicioDisponible;
        }
        public void setMainActivity(servicioOff mainActivity) {
            this.servicioDisponible = mainActivity;
        }

        @Override
        public void onLocationChanged(Location location) {
            if(location!=null){
                try {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    bearing = location.getBearing();

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("lat", String.valueOf(lat));
                    editor.putString("lng", String.valueOf(lng));
                    editor.putString("bearing", String.valueOf(bearing));
                    editor.commit();

                }catch (Exception e){
                    //algo salio mal
                }
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
            notificationManager.cancel(1);
        }
    }
    /////////////fin gps/////////////////
}
