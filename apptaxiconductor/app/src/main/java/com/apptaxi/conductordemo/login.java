package com.apptaxi.conductordemo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
 * Created by Administrador on 26/08/2017.
 */
public class login extends AppCompatActivity {
    Context ctx;
    TextInputLayout input_layout_dni,input_layout_pass;
    EditText txt_dni,txt_pass;
    Button btn_iniciar;

    ProgressDialog progress;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ctx = this;

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Iniciando sessión");

        input_layout_dni = (TextInputLayout)findViewById(R.id.input_layout_dni);
        input_layout_pass = (TextInputLayout)findViewById(R.id.input_layout_pass);

        txt_dni = (EditText)findViewById(R.id.txt_dni);
        txt_pass = (EditText)findViewById(R.id.txt_pass);
        btn_iniciar = (Button)findViewById(R.id.btn_iniciar);

        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int error = 0;
                String dni = txt_dni.getText().toString();
                String pass = txt_pass.getText().toString();
                if(!validateDNI(dni)){error=1;}
                if(!validatePass(pass)){error=1;}
                if(error==0){
                    IniciarSesion(dni,pass);
                }
            }
        });
    }

    private boolean validateDNI(String dni) {
        if (dni.isEmpty() || dni.length()==0) {
            input_layout_dni.setError("Ingresa DNI válido");
            requestFocus(txt_dni);
            return false;
        } else {
            input_layout_dni.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePass(String pass) {
        if (pass.isEmpty() || pass.length()==0) {
            input_layout_pass.setError("Ingresa contraseña válida");
            requestFocus(txt_pass);
            return false;
        } else {
            input_layout_pass.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void IniciarSesion(final String dni,final String pass) {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0001,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){

                                int estado = data.getInt("estado");
                                if(estado==1){
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putInt("id_usuario", data.getInt("id_usuario"));
                                    editor.putInt("id_pedido", 0);
                                    editor.putString("usuario", data.getString("usuario"));
                                    editor.putBoolean("disponible", false);
                                    editor.putString("tipo", data.getString("tipo"));
                                    editor.putInt("estado", estado);
                                    editor.putString("factura", data.getString("factura"));
                                    editor.putInt("conectado",1);
                                    editor.commit();

                                    Intent i = new Intent(ctx, fuera_servicio.class);
                                    startActivity(i);
                                    finish();

                                }else if (estado == 0) {
                                    //ShowToast("SU CUENTA SE ENCUENTRA BLOQUEADO");
                                    AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                                    alertDialog.setTitle(global.nameEmpresa+" Conductor");
                                    alertDialog.setMessage("Su cuenta ha sido bloqueado, acérquese a la oficina para solucionar este problema");
                                    alertDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {}
                                    });
                                    alertDialog.show();
                                }

                            }else{
                                global.ShowToast(ctx,"DNI o contraseña incorrecto");
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
                params.put("dni", dni);
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
