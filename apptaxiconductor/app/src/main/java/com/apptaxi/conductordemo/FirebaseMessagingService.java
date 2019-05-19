package com.apptaxi.conductordemo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.messaging.RemoteMessage;
import com.apptaxi.conductordemo.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by filipp on 5/23/2016.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{

    SharedPreferences pref;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        String t = remoteMessage.getData().get("tipo");

        if(Integer.parseInt(t)==0){
            //este es un pedido
            if(pref.getBoolean("disponible",false)) {
                String id_pedido = remoteMessage.getData().get("id_pedido");
                String servicio = remoteMessage.getData().get("servicio");

                sendPedido(id_pedido,Integer.parseInt(servicio));

                /*
                String id_pedido = remoteMessage.getData().get("id_pedido");
                String id_cliente = remoteMessage.getData().get("id_cliente");
                String lat = remoteMessage.getData().get("lat");
                String lng = remoteMessage.getData().get("lng");
                String cliente = remoteMessage.getData().get("cliente");
                String direccion = remoteMessage.getData().get("direccion");
                String numero = remoteMessage.getData().get("numero");
                String referencia = remoteMessage.getData().get("referencia");
                String latDestino = remoteMessage.getData().get("latDestino");
                String lngDestino = remoteMessage.getData().get("lngDestino");
                String destino = remoteMessage.getData().get("destino");
                String servicio = remoteMessage.getData().get("servicio");//identifica el tipo de servicio: app, llamda, corporativo
                String actualizar_dire = remoteMessage.getData().get("actualizar_dire");

                /*sendPedido(Integer.parseInt(id_cliente),
                        Integer.parseInt(id_pedido),
                        lat,
                        lng,
                        Integer.parseInt(servicio),
                        cliente,
                        direccion,
                        numero,
                        referencia,
                        latDestino,
                        lngDestino,
                        destino,
                        Integer.parseInt(actualizar_dire)
                        );*/
            }
        }else{
            //este es un mensaje
            if(Integer.parseInt(t)==1) {//este es una notificacion o mensaje
                String message = remoteMessage.getData().get("message");
                String asunto = remoteMessage.getData().get("asunto");
                String id = remoteMessage.getData().get("id");

                sendNotification(message, asunto,"1", id);
            }else{
                //sancion
            }
        }
    }

    /*private void sendPedido(int id_cliente,
                            int id_pedido,
                            String lat,
                            String lng,
                            int servicio,
                            String cliente,
                            String direccion,
                            String numero,
                            String referencia,
                            String latDestino,
                            String lngDestino,
                            String destino,
                            int actualizar_dire
                            ){

        if(pref.getInt("id_pedido",0)==0){
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("id_cliente", id_cliente);
            editor.putInt("id_pedido", id_pedido);
            editor.putInt("estado_pedido", 0);
            editor.putString("latPedido",lat);
            editor.putString("lngPedido",lng);
            editor.putString("cliente",cliente);
            editor.putString("direccion",direccion);
            editor.putString("numero",numero);
            editor.putString("referencia",referencia);
            editor.putString("latDestino",latDestino);
            editor.putString("lngDestino",lngDestino);
            editor.putString("destino",destino);
            editor.putInt("servicio",servicio);
            editor.putInt("actualizar_dire",actualizar_dire);
            editor.putBoolean("disponible",false);
            editor.commit();

            if(servicio==0){
                //app
                Intent i = new Intent(getApplicationContext(), servicio_app.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }else if(servicio==1){
                //llamada
                Intent i = new Intent(getApplicationContext(), servicio_llamada.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }else if(servicio==2){
                //corporativo
            }
        }
    }*/

    private void sendPedido(final String id_pedido, final int servicio) {
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0024,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putInt("id_cliente", data.getInt("id_cliente"));
                                editor.putInt("id_pedido", data.getInt("id_pedido"));
                                editor.putInt("estado_pedido", 0);
                                editor.putString("latPedido",data.getString("lat"));
                                editor.putString("lngPedido",data.getString("lng"));
                                editor.putString("cliente",data.getString("cliente"));
                                editor.putString("direccion",data.getString("direccion"));
                                editor.putString("numero",data.getString("numero"));
                                editor.putString("referencia",data.getString("referencia"));
                                editor.putString("latDestino",data.getString("latDestino"));
                                editor.putString("lngDestino",data.getString("lngDestino"));
                                editor.putString("destino",data.getString("destino"));
                                editor.putInt("servicio",servicio);
                                editor.putInt("actualizar_dire",data.getInt("actualizar_dire"));
                                editor.putBoolean("disponible",false);
                                editor.commit();

                                if(servicio==0){
                                    //app
                                    Intent i = new Intent(getApplicationContext(), servicio_app.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);

                                }else if(servicio==1){
                                    //llamada
                                    Intent i = new Intent(getApplicationContext(), servicio_llamada.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                }else if(servicio==2){
                                    //corporativo
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
                params.put("id_pedido", id_pedido);
                params.put("servicio", String.valueOf(servicio));

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

    private void sendNotification(String message, String title, String t,String id) {

        PendingIntent pendingIntent;
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
