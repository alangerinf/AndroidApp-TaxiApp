package com.apptaxi.conductordemo;

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
import com.apptaxi.conductordemo.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrador on 25/08/2017.
 */

public class iniciar extends AppCompatActivity {

    SharedPreferences pref;
    Context ctx;
    String tk;

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
                                int version = data.getInt("ver_conductor");
                                Integer vApp = BuildConfig.VERSION_CODE;
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

    private void IniciarActividad(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                /*if (id != null) {
                    Intent i = new Intent(
                            SplashScreenActivity.this, MapsActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                } else {
                    Intent mainIntent = new Intent().setClass(
                            SplashScreenActivity.this, atv_inisiar_session.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainIntent);

                    finish();
                }*/

                if(pref.contains("id_usuario")){
                    if(!pref.getBoolean("disponible",false)){//verificamos si no está escaneando los servicios
                        if(pref.getInt("id_pedido",0)==0){//verificamos si no está con servicio
                            Intent i = new Intent(ctx, fuera_servicio.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }else{//cuando esté con servicio
                            if(pref.getInt("servicio",0)==0){
                                //servicio de app
                                if(pref.getInt("conectado",2)==2) {
                                    Intent i = new Intent(getApplicationContext(), servicio_app_aceptado.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                    finish();
                                }else{
                                    Intent i = new Intent(getApplicationContext(), servicio_app_transportando.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                    finish();
                                }
                            }else if(pref.getInt("servicio",0)==1){
                                if(pref.getInt("conectado",2)==2) {
                                    if(pref.getInt("actualizar_dire",2)==0) {
                                        Intent i = new Intent(getApplicationContext(), servicio_llamada_aceptado.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        finish();
                                    }else{
                                        Intent i = new Intent(getApplicationContext(), servicio_llamada_nuevo_dire.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        finish();
                                    }
                                }else{
                                    Intent i = new Intent(getApplicationContext(), servicio_llamada_transportando.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                    finish();
                                }
                            }else if(pref.getInt("servicio",0)==2){
                                //corporativo
                            }else{
                                Intent i = new Intent(getApplicationContext(), servicio_amb_transportando.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                finish();
                            }
                        }
                    }else{
                        Intent i = new Intent(ctx, MapsActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                }else{//para logearse
                    Intent mainIntent = new Intent().setClass(iniciar.this, login.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainIntent);
                    finish();
                }


            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 1000);


    }
}
