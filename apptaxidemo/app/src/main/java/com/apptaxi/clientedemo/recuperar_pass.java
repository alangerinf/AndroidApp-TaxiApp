package com.apptaxi.clientedemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
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
 * Created by Administrador on 19/09/2017.
 */
public class recuperar_pass extends AppCompatActivity {
    Context ctx;
    TextInputLayout input_layout_email,input_layout_telefono;
    EditText txt_email,txt_telefono;
    Button btn_recuperar;

    ProgressDialog progress;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recuperar_pass);
        ctx = this;

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Iniciando sessión");

        input_layout_email = (TextInputLayout) findViewById(R.id.input_layout_email);
        input_layout_telefono = (TextInputLayout) findViewById(R.id.input_layout_telefono);

        txt_email = (EditText)findViewById(R.id.txt_email);
        txt_telefono = (EditText)findViewById(R.id.txt_telefono);
        btn_recuperar = (Button)findViewById(R.id.btn_recuperar);

        btn_recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int error = 0;
                global.ShowToast(ctx,"entro");
                String email = txt_email.getText().toString();
                String telefono = txt_telefono.getText().toString();
                if(!validateEmail(email)){error=1;}
                if(!validateTelefono(telefono)){error=1;}
                if(error==0){
                    EnviarRecuperar(email,telefono);
                }
            }
        });

        if(pref.contains("email")){
            txt_email.setText(pref.getString("email",""));
            txt_telefono.requestFocus();
        }
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty() || !isValidEmail(email)) {
            input_layout_email.setError("Ingresar email válido");
            requestFocus(txt_email);
            return false;
        } else {
            input_layout_email.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validateTelefono(String telefono) {
        if (telefono.isEmpty() || telefono.length()==0) {
            input_layout_telefono.setError("Ingresar una contraseña válida");
            requestFocus(txt_telefono);
            return false;
        } else {
            input_layout_telefono.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void EnviarRecuperar(final String email,final String telefono) {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0025,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                global.ShowToast(ctx,"Enviado");
                            }else{
                                global.ShowToast(ctx,"Email o celular no existen");
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
                params.put("email", email);
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
}
