package com.apptaxi.conductordemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by Administrador on 11/09/2017.
 */
public class mensajes_leer extends AppCompatActivity {
    Context ctx;
    String id_mensaje;

    ProgressDialog progress;

    TextView txt_asunto, txt_mensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensajes_leer);
        ctx = this;

        Bundle extras = getIntent().getExtras();
        id_mensaje = extras.getString("id_mensaje");

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Cargando datos");

        txt_asunto = (TextView)findViewById(R.id.txt_asunto);
        txt_mensaje = (TextView)findViewById(R.id.txt_mensaje);

        CargarMensaje();

    }

    private void CargarMensaje() {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0022,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                String asunto = data.getString("asunto");
                                String mensaje = data.getString("mensaje");

                                txt_asunto.setText(asunto);
                                txt_mensaje.setText(mensaje);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_mensaje", id_mensaje);

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
