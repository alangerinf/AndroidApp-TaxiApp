package com.apptaxi.clientedemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
 * Created by Administrador on 16/09/2017.
 */
public class mis_direcciones_editar extends AppCompatActivity {
    SharedPreferences pref;
    Context ctx;
    String id_direccion,direccion,numero,referencia;
    TextView txt_direccion,txt_numero,txt_referencia;

    Button btn_guardar,btn_cancelar;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_direcciones_editar);
        ctx = this;

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Guardando datos ingresados");

        Bundle extras = getIntent().getExtras();
        id_direccion = extras.getString("id_direccion");
        direccion = extras.getString("direccion");
        numero = extras.getString("numero");
        referencia = extras.getString("referencia");

        txt_direccion = (TextView)findViewById(R.id.txt_direccion);
        txt_numero = (TextView)findViewById(R.id.txt_numero);
        txt_referencia = (TextView)findViewById(R.id.txt_referencia);

        txt_direccion.setText(direccion);
        txt_numero.setText(numero);
        txt_referencia.setText(referencia);

        btn_guardar = (Button)findViewById(R.id.btn_guardar);
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direccion = txt_direccion.getText().toString();
                numero = txt_numero.getText().toString();
                referencia = txt_referencia.getText().toString();
                GuardarDireccion();
            }
        });

        btn_cancelar = (Button)findViewById(R.id.btn_cancelar);
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GuardarDireccion() {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0013,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                Intent i = getIntent();
                                setResult(RESULT_OK, i);
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

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_direccion", id_direccion);
                params.put("direccion", direccion);
                params.put("numero", numero);
                params.put("referencia", referencia);
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
