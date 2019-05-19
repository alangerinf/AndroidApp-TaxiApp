package com.apptaxi.conductordemo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

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
 * Created by Administrador on 07/09/2017.
 */
public class servicio_llamada extends AppCompatActivity {
    SharedPreferences pref;
    Context ctx;

    int id_pedido,id_usuario;
    double lat, lng;
    ProgressDialog progress;
    SoundManager sound;
    int sonido_taxi;

    boolean finAut = false;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Window win= getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.servicio_llamada);
        ctx = this;

        sound = new SoundManager(getApplicationContext());
        sonido_taxi = sound.load(R.raw.nuevo_servicio);

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        id_pedido = pref.getInt("id_pedido",0);
        id_usuario = pref.getInt("id_usuario",0);
        lat = Double.parseDouble(pref.getString("latPedido",String.valueOf(global.latInicio)));
        lng = Double.parseDouble(pref.getString("lngPedido",String.valueOf(global.latInicio)));

        String cliente = pref.getString("cliente",null);
        String direccion = pref.getString("direccion",null);

        TextView txt_cliente = (TextView)findViewById(R.id.txt_cliente);
        TextView txt_direccion = (TextView)findViewById(R.id.txt_direccion);

        txt_cliente.setText(cliente);
        txt_direccion.setText("DIRECCION\n"+direccion);

        Button btn_rechazar = (Button)findViewById(R.id.btn_rechazar);
        btn_rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removersesion();
                finish();
            }
        });

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Aplicando el servicio");

        Button btn_aceptar = (Button)findViewById(R.id.btn_aceptar);
        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AceptarPedidoAPP();
            }
        });

        sonido();
        runnable.run();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(finAut){
                removersesion();
                finish();
            }
            finAut=true;
            handler.postDelayed(runnable, 10000);
        }
    };

    private void sonido(){

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                sound.play(sonido_taxi);
                Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                v.vibrate(3000);
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 1000);
    }

    private void AceptarPedidoAPP() {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0011,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putInt("conectado",2);
                                editor.putInt("estado_pedido", 1);
                                editor.commit();
                                if(pref.getInt("actualizar_dire",0)==0) {
                                    Intent i = new Intent(getApplicationContext(), servicio_llamada_aceptado.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                }else{
                                    Intent i = new Intent(getApplicationContext(), servicio_llamada_nuevo_dire.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                }
                                finish();
                            }else{
                                showAlertDialog(ctx,global.nameEmpresa,"No se pudo aplicar al servicio de taxi", false);
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
                params.put("id_usuario", String.valueOf(id_usuario));

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

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        if(!status) {
            alertDialog.setButton("Salir", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    removersesion();
                    alertDialog.dismiss();
                    finish();
                }
            });
            alertDialog.show();
        }
    }

    private void removersesion(){
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("id_cliente", 0);
        editor.putInt("id_pedido", 0);
        editor.remove("estado_pedido");
        editor.remove("latPedido");
        editor.remove("lngPedido");
        editor.remove("cliente");
        editor.remove("direccion");
        editor.remove("numero");
        editor.remove("referencia");
        editor.remove("latDestino");
        editor.remove("lngDestino");
        editor.remove("destino");
        editor.remove("actualizar_dire");
        editor.putInt("conectado",1);
        editor.putBoolean("disponible",true);
        editor.commit();
    }
    @Override
    public void onBackPressed() {
        //no hace nada para salir con retroceso
    }
}
