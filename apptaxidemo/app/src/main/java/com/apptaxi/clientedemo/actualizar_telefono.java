package com.apptaxi.clientedemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
 * Created by Administrador on 20/09/2017.
 */

public class actualizar_telefono extends AppCompatActivity {
    SharedPreferences pref;
    Context ctx;

    int id_usuario = 0, estado;
    String usuario = "", telefono = "", correo = "";
    EditText txt_nombre, txt_celular, txt_correo;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_telefono);
        ctx = this;

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Registrando...");

        txt_nombre = (EditText) findViewById(R.id.txt_nombre);
        txt_celular = (EditText) findViewById(R.id.txt_celular);
        txt_correo = (EditText) findViewById(R.id.txt_correo);

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        id_usuario = pref.getInt("id_usuario", 0);
        usuario = pref.getString("usuario", "");
        telefono = pref.getString("telefono", "");
        correo = pref.getString("email", "");

        txt_nombre.setText(usuario);
        txt_celular.setText(telefono);
        txt_correo.setText(correo);

        Button btn_continuar = (Button) findViewById(R.id.btn_continuar);
        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = txt_nombre.getText().toString();
                telefono = txt_celular.getText().toString();
                correo = txt_correo.getText().toString();
                int error = 0;
                if (usuario.length() == 0) {
                    global.ShowToast(ctx, "Debes ingresar tu nombre");
                    txt_nombre.requestFocus();
                    error = 1;
                } else if (telefono.length() < 9) {
                    global.ShowToast(ctx, "Debes ingresar un número de celular válido");
                    txt_celular.requestFocus();
                    error = 1;
                } else if (!isValidEmail(correo)) {
                    global.ShowToast(ctx, "Debes ingresar un correo eletrónico válido");
                    txt_correo.requestFocus();
                    error = 1;
                }

                if (error == 0) {
                    RegistrarTePaso1();
                } else {
                    Vibrar();
                }
            }
        });

    }

    private void Vibrar() {
        Vibrator vibrar = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        long[] pattern = {0, 200, 100};
        vibrar.vibrate(pattern, -1);
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void RegistrarTePaso1() {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0027,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1) {

                                if(data.getInt("existe") == 0){
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putInt("id_usuario", data.getInt("id_usuario"));
                                    editor.putString("usuario", data.getString("usuario"));
                                    editor.putString("telefono", telefono);
                                    editor.putString("email", correo);
                                    editor.putInt("version", 2);
                                    editor.commit();

                                    finish();
                                }else if(data.getInt("existe") == 1){
                                    Vibrar();
                                    global.ShowToast(ctx, "Los campos: celular y correo existen en otro registro de cliente");
                                }else if(data.getInt("existe") == 2){
                                    Vibrar();
                                    global.ShowToast(ctx, "El correo electrónico ya existe en otro registro de cliente");
                                }else if(data.getInt("existe") == 3){
                                    Vibrar();
                                    global.ShowToast(ctx, "El número de celular ya existe en otro registro de cliente");
                                }

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
                params.put("usuario", usuario);
                params.put("telefono", telefono);
                params.put("correo", correo);
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

    @Override
    public void onBackPressed() {}
}