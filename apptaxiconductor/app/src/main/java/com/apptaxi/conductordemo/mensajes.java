package com.apptaxi.conductordemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.apptaxi.conductordemo.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrador on 11/09/2017.
 */
public class mensajes extends AppCompatActivity {
    Context ctx;
    String[]asuntoList,mensajeList,nuevoList,idList;
    int id_usuario;

    JSONArray mensajes = null;

    ListView lv;
    int exito = 0;

    SharedPreferences pref;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensajes);
        ctx = this;

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        id_usuario = pref.getInt("id_usuario",0);

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Cargando datos");

        lv = (ListView) findViewById(R.id.list_mensajes);
        ListaMensajes();
    }

    private void ListaMensajes() {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0021,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                exito = 1;
                                mensajes = data.getJSONArray("mensajes");

                                asuntoList = new String[mensajes.length()];
                                mensajeList = new String[mensajes.length()];
                                nuevoList = new String[mensajes.length()];
                                idList = new String[mensajes.length()];

                                for (int i = 0; i < mensajes.length(); i++) {
                                    JSONObject c = mensajes.getJSONObject(i);

                                    String id = c.getString("id");
                                    String asunto = c.getString("asunto");
                                    String mensaje = c.getString("mensaje");
                                    String nuevo = c.getString("nuevo");

                                    asuntoList[i]=asunto;
                                    mensajeList[i]=mensaje;
                                    nuevoList[i]=nuevo;
                                    idList[i]=id;
                                }

                                CreateList();
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

    private void CreateList(){
        CustomMensajes adapter = new CustomMensajes(mensajes.this, asuntoList,mensajeList,nuevoList,idList);
        lv=(ListView)findViewById(R.id.list_mensajes);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt_id = (TextView)view.findViewById(R.id.txt_id);
                Intent i = new Intent(ctx, mensajes_leer.class);
                i.putExtra("id_mensaje", txt_id.getText().toString());
                startActivity(i);
            }
        });
    }
}
