package com.apptaxi.clientedemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrador on 11/09/2017.
 */
public class iniciar extends AppCompatActivity {
    SharedPreferences pref;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar);
        ctx = this;

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);

        VerificarConexion();

    }

    private void VerificarConexion() {
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL000V,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                int version = data.getInt("ver_cliente");
                                int vApp = BuildConfig.VERSION_CODE;
                                if(version>vApp){
                                    final AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                                    alertDialog.setIcon(R.drawable.ic_launcher);
                                    alertDialog.setTitle(global.nameEmpresa);
                                    alertDialog.setMessage(global.NuevaVersion);
                                    alertDialog.setButton(Dialog.BUTTON_POSITIVE,"Si, descargar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Intent.ACTION_VIEW);
                                            intent.setData(Uri.parse("market://details?id="+BuildConfig.APPLICATION_ID));
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    alertDialog.setButton(Dialog.BUTTON_NEGATIVE,"Salir", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    });

                                    alertDialog.show();
                                }else{
                                    IniciarActividad();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                alertDialog.setIcon(R.drawable.ic_launcher);
                alertDialog.setTitle(global.nameEmpresa);
                alertDialog.setMessage(global.NoHayConexion);
                alertDialog.setButton(Dialog.BUTTON_POSITIVE,"Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                alertDialog.show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();

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

    private void IniciarActividad() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(pref.contains("id_usuario")){
                    if(pref.getInt("u_estado",0)==1) {
                        if(pref.getInt("paso",1)==2) {
                            if (pref.contains("id_pedido")) {
                                if (pref.getInt("estado", 0) > 0) {
                                    Intent atv = new Intent(ctx, pedido_aceptado.class);
                                    atv.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(atv);
                                    finish();
                                } else {
                                    IniciarMaps();
                                }
                            } else {
                                IniciarMaps();
                            }
                        }else{
                            if(pref.getInt("paso",1)==1){
                                Intent i = new Intent(ctx, opcion_inicio.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.putExtra("activar",1);
                                startActivity(i);
                                finish();
                            }else{
                                IniciarMaps();
                            }
                        }

                    }else{
                        Intent i = new Intent(ctx, opcion_inicio.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("activar",1);
                        startActivity(i);
                        finish();
                    }
                }else{
                    Intent i = new Intent(ctx, opcion_inicio.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("activar",0);
                    startActivity(i);
                    finish();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1000);
    }

    private void IniciarMaps(){
        Intent i = new Intent(ctx, MapsActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

}
