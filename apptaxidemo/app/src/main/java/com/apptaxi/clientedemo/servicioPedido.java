package com.apptaxi.clientedemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.apptaxi.clientedemo.app.AppController;


import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrador on 28/08/2017.
 */
public class servicioPedido extends Service {

    final Handler handler = new Handler();
    Notification notification;
    NotificationManager notificationManager;
    Context ctx;

    SharedPreferences pref;
    double lat, lng,latConductor,lngConductor;
    float bearing;
    String UrlServidor,empleado,vehiculo,modelo,foto,cambiarEstado="no";
    int id_usuario,id_pedido,estado,id_empleado;
    int sonido_camino, sonido_abordar;
    SoundManager sound;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent i, int flags, int startId) {

        ctx = this;
        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        id_pedido = pref.getInt("id_pedido",0);
        id_usuario = pref.getInt("id_usuario",0);
        lat = Double.parseDouble(pref.getString("lat","0.0"));
        lng = Double.parseDouble(pref.getString("lng","0.0"));

        sound = new SoundManager(getApplicationContext());
        sonido_camino = sound.load(R.raw.taxi_en_camino);
        sonido_abordar = sound.load(R.raw.abordar_taxi);

        handler.removeCallbacks(runnable);

        /*Intent intent = new Intent(this, iniciar.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "Go", pendingIntent).build();
    */
        //Notification
        notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(global.nameEmpresa)
                .setContentTitle(global.nameEmpresa)
                //.addAction(action)
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

            UrlServidor = global.UL0004;
            VerificarEstadoPedido();

            handler.postDelayed(runnable, 5000);
        }
    };

    private void Vibrar(){
        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        v.vibrate(3000);
    }

    private void VerificarEstadoPedido() {
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + UrlServidor,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                estado = data.getInt("estado");
                                cambiarEstado="no";
                                if(estado>0) {
                                    id_empleado = data.getInt("id_empleado");

                                    latConductor = data.getDouble("ubica_lat");
                                    lngConductor = data.getDouble("ubica_lon");
                                    bearing = Float.parseFloat(data.getString("bearing"));

                                    empleado = data.getString("empleado");
                                    modelo = data.getString("modelo");
                                    vehiculo = data.getString("vehiculo");
                                    foto = data.getString("foto");


                                    float[] distance = new float[1];
                                    double latI = Double.parseDouble(pref.getString("lat","0.0"));
                                    double lngI = Double.parseDouble(pref.getString("lng","0.0"));
                                    Location.distanceBetween(latI, lngI, latConductor, lngConductor, distance);
                                    if(distance[0]<=30){
                                        if(estado==1) {
                                            cambiarEstado = "si";//solo por que el taxi ya está cerca
                                        }
                                    }
                                }

                                SharedPreferences.Editor editor = pref.edit();
                                editor.putInt("estado", estado);

                                if(estado>0) {
                                    editor.putInt("id_empleado", id_empleado);
                                    editor.putString("latConductor", String.valueOf(latConductor));
                                    editor.putString("lngConductor", String.valueOf(lngConductor));
                                    editor.putString("bearing", String.valueOf(bearing));
                                    editor.putString("empleado", empleado);
                                    editor.putString("modelo", modelo);
                                    editor.putString("vehiculo", vehiculo);
                                    editor.putString("foto", foto);
                                }

                                editor.commit();

                                if(estado==1){
                                    if(pref.getInt("pedidoAceptado",0)==0) {
                                        sound.play(sonido_camino);
                                        Vibrar();

                                        editor.putInt("pedidoAceptado", 1);
                                        editor.commit();

                                        Intent i = new Intent(ctx, pedido_aceptado.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                    }
                                }else if(estado==2){
                                    if(pref.getInt("pedidoUbicacion",0)==0){
                                        sound.play(sonido_abordar);
                                        Vibrar();

                                        editor.putInt("pedidoUbicacion", 1);
                                        editor.commit();

                                        Intent i = new Intent(ctx, pedido_ubicacion.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                    }
                                }else if(estado==3){
                                    removersesion();
                                    stopService(new Intent(getBaseContext(), servicioPedido.class));
                                }else if(estado==7){
                                    removersesion();
                                    stopService(new Intent(getBaseContext(), servicioPedido.class));
                                    Intent i = new Intent(ctx, pedido_anulado.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
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
                params.put("id_pedido", String.valueOf(id_pedido));
                params.put("cambiarEstado",cambiarEstado);

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

    private  void removersesion(){
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("id_pedido");
        editor.remove("estado");
        editor.remove("id_empleado");
        editor.remove("latConductor");
        editor.remove("lngConductor");
        editor.remove("bearing");
        editor.remove("empleado");
        editor.remove("modelo");
        editor.remove("vehiculo");
        editor.remove("foto");
        editor.remove("pedidoAceptado");
        editor.remove("pedidoUbicacion");
        editor.commit();
    }
}
