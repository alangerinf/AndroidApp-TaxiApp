package com.apptaxi.clientedemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
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
 * Created by Administrador on 11/09/2017.
 */
public class login extends AppCompatActivity {
    Context ctx;
    TextInputLayout input_layout_email,input_layout_pass;
    EditText txt_email,txt_pass;
    Button btn_iniciar;
    TextView txt_olvide;

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

        input_layout_email = (TextInputLayout)findViewById(R.id.input_layout_email);
        input_layout_pass = (TextInputLayout)findViewById(R.id.input_layout_pass);

        txt_email = (EditText)findViewById(R.id.txt_email);
        txt_pass = (EditText)findViewById(R.id.txt_pass);
        btn_iniciar = (Button)findViewById(R.id.btn_iniciar);

        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int error = 0;
                String email = txt_email.getText().toString();
                String pass = txt_pass.getText().toString();
                if(!validateEmail(email)){error=1;}
                if(!validatePass(pass)){error=1;}
                if(error==0){
                    IniciarSesion(email,pass);
                }
            }
        });

        if(pref.contains("email")){
            txt_email.setText(pref.getString("email",""));
            txt_pass.requestFocus();
        }

        txt_olvide = (TextView)findViewById(R.id.txt_olvide);
        txt_olvide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, recuperar_pass.class);
                startActivity(i);
            }
        });

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

    private boolean validatePass(String pass) {
        if (pass.isEmpty() || pass.length()==0) {
            input_layout_pass.setError("Ingresar una contraseña válida");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK ) {
            int opcion = data.getExtras().getInt("opcion");
            if(opcion==1){//viene de login correcto
                Intent i = new Intent(ctx, MapsActivity.class);
                startActivity(i);
                finish();
            }else
            if(opcion==2){//viene de 1 y pasamos a 2
                Intent i = new Intent(ctx, registrate_paso2.class);
                startActivityForResult(i,1);
            }else
            if(opcion==3){//viene de 2 y vamos a 1
                Intent i = new Intent(ctx, registrate_paso1.class);
                startActivityForResult(i,1);
            }else
            if(opcion==4){//viene de 2 y pasamos a home
                Intent i = new Intent(ctx, MapsActivity.class);
                startActivity(i);
                finish();
            }
        }
    }

    private void IniciarSesion(final String email,final String pass) {
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

                                SharedPreferences.Editor editor = pref.edit();
                                editor.putInt("id_usuario", data.getInt("id_usuario"));
                                editor.putString("usuario", data.getString("usuario"));
                                editor.putString("email", data.getString("email"));
                                editor.putString("codigo", data.getString("codigo"));
                                editor.putInt("u_estado", data.getInt("estado"));
                                editor.putInt("paso", data.getInt("paso"));
                                editor.putString("telefono", data.getString("telefono"));
                                editor.putInt("version", data.getInt("version"));
                                if(pref.contains("id_pedido")) {
                                    editor.remove("id_pedido");
                                }
                                editor.commit();

                                if(estado==1){
                                    Intent i = getIntent();
                                    i.putExtra("opcion", 1);//acceso autrizado
                                    setResult(RESULT_OK, i);
                                    finish();
                                }else{
                                    Intent i = getIntent();
                                    i.putExtra("opcion", 2);//pasar al paso 2
                                    setResult(RESULT_OK, i);
                                    finish();
                                }

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
                params.put("email", email);
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
