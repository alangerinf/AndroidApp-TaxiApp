package com.apptaxi.clientedemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
public class registrate_paso2 extends AppCompatActivity {
    SharedPreferences pref;
    Context ctx;
    int id_usuario;
    String usuario,telefono;
    EditText txt_codigo;
    TextView txt_nombre,txt_celular,txt_reenviar;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrate_paso2);
        ctx = this;

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Activando...");

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        id_usuario = pref.getInt("id_usuario",0);
        usuario=pref.getString("usuario","");
        telefono=pref.getString("telefono","");

        txt_nombre = (TextView) findViewById(R.id.txt_nombre);
        txt_celular = (TextView) findViewById(R.id.txt_celular);

        txt_nombre.setText(usuario);
        txt_celular.setText(telefono);

        txt_celular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                i.putExtra("opcion", 3);//acceso autrizado
                setResult(RESULT_OK, i);
                finish();
            }
        });

        txt_codigo = (EditText)findViewById(R.id.txt_codigo);

        Button btn_continuar = (Button)findViewById(R.id.btn_continuar);
        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pref.getInt("id_usuario",0)==Integer.parseInt(txt_codigo.getText().toString())){
                    ActivacionPaso2();
                }else{
                    global.ShowToast(ctx,"El código de activación no es correcto");
                    txt_codigo.requestFocus();
                }
            }
        });

        txt_reenviar = (TextView)findViewById(R.id.txt_reenviar);
        txt_reenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                alertDialog.setIcon(R.drawable.ic_launcher);
                alertDialog.setTitle(global.nameEmpresa);
                alertDialog.setMessage("¿Deseas solicitar denuevo el código de activación?");
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Si, envienme denuevo", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ReenviarCodigo();
                        alertDialog.dismiss();
                    }
                });
                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

    }

    private void ReenviarCodigo() {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0024,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1) {

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
                params.put("id_usuario", String.valueOf(id_usuario));
                params.put("telefono", telefono);
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

    private void ActivacionPaso2() {
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
                            if(success==1) {

                                SharedPreferences.Editor editor = pref.edit();
                                editor.putInt("u_estado", 1);
                                editor.putInt("paso", 2);
                                editor.commit();

                                Intent i = getIntent();
                                i.putExtra("opcion", 4);//acceso autrizado
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
                progress.dismiss();
                global.ShowToast(ctx,global.NosePudo);
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
