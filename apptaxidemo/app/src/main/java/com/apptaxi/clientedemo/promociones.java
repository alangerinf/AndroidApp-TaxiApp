package com.apptaxi.clientedemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

/**
 * Created by Administrador on 15/09/2017.
 */
public class promociones extends AppCompatActivity {

    Context ctx;
    SharedPreferences pref;
    ProgressDialog progress;

    TextView txt_codigo,lbl_mensaje1,txt_acumulado;
    int id_usuario;
    String codigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promociones);
        ctx = this;

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Cargando bonos acumulados...");

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        id_usuario = pref.getInt("id_usuario",0);
        codigo = pref.getString("codigo","");

        txt_codigo = (TextView)findViewById(R.id.txt_codigo);
        txt_codigo.setText(codigo);
        txt_codigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String share = "Prueba "+global.nameEmpresa+" utiliza mi codigo: \""+codigo+"\" y gana un sol S/. 1.00";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, share);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent ,"Compartir"));
            }
        });

        lbl_mensaje1 = (TextView)findViewById(R.id.lbl_mensaje1);
        lbl_mensaje1.setText("Obtén un sol (S/. 1.00) cuando invites a un amigo a probar "+global.nameEmpresa+" y utilízalo en tu próximo viaje.");

        txt_acumulado = (TextView)findViewById(R.id.txt_acumulado);
        CargarBonos();
    }

    private void CargarBonos() {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0009,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                txt_acumulado.setText("Tienes "+data.getString("bono")+" soles de bono");
                            }else{
                                global.ShowToast(ctx,"Email o contraseña incorrecto");
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
