package com.apptaxi.clientedemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.apptaxi.clientedemo.app.AppController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrador on 13/09/2017.
 */
public class pedido_ubicacion extends AppCompatActivity {
    Context ctx;
    SharedPreferences pref;
    ProgressDialog progress;

    int id_pedido;

    String empleado,vehiculo,modelo,foto;
    TextView txt_conductor,txt_vehiculo,txt_modelo;

    NetworkImageView img_foto_conductor;
    ImageLoader imageLoader;

    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedido_ubicacion);
        ctx = this;

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Apicando operación");

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        id_pedido = pref.getInt("id_pedido",0);

        imageLoader = AppController.getInstance().getImageLoader();

        empleado = pref.getString("empleado","-");
        modelo = pref.getString("modelo","-");
        vehiculo = pref.getString("vehiculo","-");
        foto = pref.getString("foto","-");
        foto = foto.substring(8);
        txt_conductor = (TextView)findViewById(R.id.txt_conductor);
        txt_vehiculo = (TextView)findViewById(R.id.txt_marca);
        txt_modelo = (TextView)findViewById(R.id.txt_placa);

        txt_conductor.setText(empleado);
        txt_vehiculo.setText(modelo);

        img_foto_conductor = (NetworkImageView)findViewById(R.id.img_foto_conductor);
        img_foto_conductor.setImageUrl(global.UrlServer+"newclass/"+foto, imageLoader);

        Button btn_abordar = (Button)findViewById(R.id.btn_abordar);
        btn_abordar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IniciarTransporte();
            }
        });

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
            if(pref.contains("estado")) {
                if (pref.getInt("estado", 0) > 2) {
                    finish();
                }
                handler.postDelayed(runnable, 1000);
            }else{
                finish();
            }
        }
    };

    private void IniciarTransporte() {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0005,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        finish();
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
}
