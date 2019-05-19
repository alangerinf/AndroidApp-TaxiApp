package com.apptaxi.clientedemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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
 * Created by Administrador on 14/09/2017.
 */
public class pedido_evaluar extends AppCompatActivity {
    Context ctx;
    ImageButton img1,img2,img3,img4,img5;
    TextView lbl_titulo;
    EditText txt_comentario;
    int id_pedido,puntos=0;
    String comentario,precio;

    SoundManager sound;
    int sonido_taxi;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.pedido_evaluar);


        //IMPLEMENTANDO EL BOTON HACI ATRAS

        //getActionBar().setDisplayHomeAsUpEnabled(true);


        ctx = this;

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Enviando evaluación");

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            id_pedido = extras.getInt("id_pedido");
            precio = extras.getString("precio");

            lbl_titulo = (TextView)findViewById(R.id.lbl_titulo);
            lbl_titulo.setText("SERVICIO FINALIZADO \n PAGÓ: S/. "+precio);

            img1 = (ImageButton) findViewById(R.id.img_op_1);
            img2 = (ImageButton) findViewById(R.id.img_op_2);
            img3 = (ImageButton) findViewById(R.id.img_op_3);
            img4 = (ImageButton) findViewById(R.id.img_op_4);
            img5 = (ImageButton) findViewById(R.id.img_op_5);

            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    marcarPuntos(1);
                }
            });

            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    marcarPuntos(2);
                }
            });

            img3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    marcarPuntos(3);
                }
            });

            img4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    marcarPuntos(4);
                }
            });

            img5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    marcarPuntos(5);
                }
            });

            sound = new SoundManager(getApplicationContext());
            this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
            sonido_taxi = sound.load(R.raw.evaluar_servicio);
            sonido();

            txt_comentario = (EditText)findViewById(R.id.txt_comentario);

            Button btn_enviar = (Button)findViewById(R.id.btn_enviar);
            btn_enviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(puntos==0){
                        global.ShowToast(ctx,"Debe asignar una puntuacion con las estrellas");
                    }else{
                        if(txt_comentario.getText().toString().length()==0){
                            global.ShowToast(ctx,"Debe ingresar un comentario");
                        }else{
                            comentario = txt_comentario.getText().toString();
                            EnviarPuntuacion();
                        }
                    }
                }
            });

        }
    }

    private void EnviarPuntuacion() {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0006,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){

                                Intent i = new Intent(ctx, MapsActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                global.ShowToast(ctx,global.NosePudo);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                global.ShowToast(ctx,global.NosePudo);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_pedido", String.valueOf(id_pedido));
                params.put("comentario",comentario);
                params.put("puntos",String.valueOf(puntos));

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

    private void marcarPuntos(int p){

        puntos = p;

        img1.setImageResource(R.drawable.estrella_off);
        img2.setImageResource(R.drawable.estrella_off);
        img3.setImageResource(R.drawable.estrella_off);
        img4.setImageResource(R.drawable.estrella_off);
        img5.setImageResource(R.drawable.estrella_off);
        if(p==1) {
            img1.setImageResource(R.drawable.estrella_on);
        }else if(p==2){
            img1.setImageResource(R.drawable.estrella_on);
            img2.setImageResource(R.drawable.estrella_on);
        }else if(p==3){
            img1.setImageResource(R.drawable.estrella_on);
            img2.setImageResource(R.drawable.estrella_on);
            img3.setImageResource(R.drawable.estrella_on);
        }else if(p==4){
            img1.setImageResource(R.drawable.estrella_on);
            img2.setImageResource(R.drawable.estrella_on);
            img3.setImageResource(R.drawable.estrella_on);
            img4.setImageResource(R.drawable.estrella_on);
        }else if(p==5){
            img1.setImageResource(R.drawable.estrella_on);
            img2.setImageResource(R.drawable.estrella_on);
            img3.setImageResource(R.drawable.estrella_on);
            img4.setImageResource(R.drawable.estrella_on);
            img5.setImageResource(R.drawable.estrella_on);
        }
    }
}
