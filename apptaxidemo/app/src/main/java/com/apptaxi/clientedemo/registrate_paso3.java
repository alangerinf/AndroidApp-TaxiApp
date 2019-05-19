package com.apptaxi.clientedemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
 * Created by Administrador on 18/09/2017.
 */
public class registrate_paso3 extends AppCompatActivity {
    SharedPreferences pref;
    Context ctx;
    int id_usuario;
    String usuario,telefono,correo,pass,repite;
    ProgressDialog progress;
    TextView txt_nombre,txt_celular;
    EditText txt_correo,txt_pass,txt_repite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrate_paso3);
        ctx = this;

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Enviando datos...");

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        id_usuario = pref.getInt("id_usuario", 0);
        usuario = pref.getString("usuario", "");
        telefono = pref.getString("telefono", "");

        txt_nombre = (TextView) findViewById(R.id.txt_nombre);
        txt_celular = (TextView) findViewById(R.id.txt_celular);

        txt_nombre.setText(usuario);
        txt_celular.setText(telefono);

        txt_correo = (EditText)findViewById(R.id.txt_correo);
        txt_pass = (EditText)findViewById(R.id.txt_pass);
        txt_repite = (EditText)findViewById(R.id.txt_repite);

        Button btn_continuar = (Button)findViewById(R.id.btn_continuar);
        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = txt_correo.getText().toString();
                pass = txt_pass.getText().toString();
                repite = txt_repite.getText().toString();
                int error = 0;
                if(!isValidEmail(correo)){
                    global.ShowToast(ctx,"La dirección de correo ingresado es incorrecto");
                    txt_correo.requestFocus();
                    error=1;
                }else if(!pass.equals(repite)){
                    global.ShowToast(ctx,"Las contraseñas ingresadas son incorrectas");
                    txt_pass.requestFocus();
                    error=1;
                }
                if(error==0){
                    FinalizarPaso3();
                }

            }
        });
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void FinalizarPaso3() {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0023,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1) {

                                SharedPreferences.Editor editor = pref.edit();
                                editor.putInt("paso", 3);
                                editor.commit();

                                Intent i = getIntent();
                                i.putExtra("opcion", 5);//fin
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
                params.put("correo", correo);
                params.put("pass", pass);
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
