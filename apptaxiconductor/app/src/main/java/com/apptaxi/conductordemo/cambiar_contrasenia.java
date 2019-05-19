package com.apptaxi.conductordemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

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
 * Created by Administrador on 08/09/2017.
 */
public class    cambiar_contrasenia extends AppCompatActivity {

    SharedPreferences pref;
    Context ctx;
    int id_usuario;
    String pass_actual,nuevo_pass,repite_pass;
    TextInputLayout input_pass_actual,input_nuevo_pass,input_repite_pass;
    EditText txt_pass_actual,txt_nuevo_pass,txt_repite_pass;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cambiar_contrasenia);
        ctx = this;

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Cambiando contraseña");

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        id_usuario = pref.getInt("id_usuario",0);

        input_pass_actual = (TextInputLayout)findViewById(R.id.input_pass_actual);
        input_nuevo_pass = (TextInputLayout)findViewById(R.id.input_nuevo_pass);
        input_repite_pass = (TextInputLayout)findViewById(R.id.input_repite_pass);

        txt_pass_actual = (EditText)findViewById(R.id.txt_pass_actual);
        txt_nuevo_pass = (EditText)findViewById(R.id.txt_nuevo_pass);
        txt_repite_pass = (EditText)findViewById(R.id.txt_repite_pass);

        Button btn_guardar = (Button)findViewById(R.id.btn_guardar);
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int error = 0;
                pass_actual = txt_pass_actual.getText().toString();
                nuevo_pass = txt_nuevo_pass.getText().toString();
                repite_pass = txt_repite_pass.getText().toString();

                if(!validatePassActual(pass_actual)){error=1;}
                if(!validateNuevoPass(nuevo_pass)){error=1;}
                if(!validateRepitePass(repite_pass)){error=1;}

                if(error==0) {

                    if (!nuevo_pass.equals(repite_pass)) {
                        input_nuevo_pass.setError("Error");
                        input_repite_pass.setError("Las contraseñas no son iguales");
                        requestFocus(txt_nuevo_pass);
                    }else{
                        cambiarContrasenia();
                    }
                }
            }
        });
    }

    private boolean validatePassActual(String pass) {
        if (pass.isEmpty() || pass.length()==0) {
            input_pass_actual.setError("Ingresar una contraseña válida");
            requestFocus(txt_pass_actual);
            return false;
        } else {
            input_pass_actual.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateNuevoPass(String pass) {
        if (pass.isEmpty() || pass.length()==0) {
            input_nuevo_pass.setError("Ingresar su nueva contraseña");
            requestFocus(txt_nuevo_pass);
            return false;
        } else {
            input_nuevo_pass.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateRepitePass(String pass) {
        if (pass.isEmpty() || pass.length()==0) {
            input_repite_pass.setError("Repita su nueva contraseña");
            requestFocus(txt_repite_pass);
            return false;
        } else {
            input_repite_pass.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void cambiarContrasenia(){
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0014,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){

                                int exito = data.getInt("exito");
                                if(exito==1) {
                                    global.ShowToast(ctx, "Operación exitosa");

                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.remove("id_usuario");
                                    editor.putBoolean("disponible", false);
                                    editor.commit();

                                    stopService(new Intent(getBaseContext(), servicioDisponible.class));

                                    Intent i = new Intent(ctx, login.class);
                                    startActivity(i);

                                    setResult(Activity.RESULT_OK);

                                    finish();
                                }else{
                                    input_pass_actual.setError("Contraseña incorrecta");
                                    requestFocus(txt_pass_actual);
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

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_usuario", String.valueOf(id_usuario));
                params.put("pass_actual", pass_actual);
                params.put("nuevo_pass", nuevo_pass);

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
