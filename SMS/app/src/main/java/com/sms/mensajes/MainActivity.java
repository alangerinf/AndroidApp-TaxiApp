package com.sms.mensajes;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sms.mensajes.app.AppController;


public class MainActivity extends AppCompatActivity {

    Button buttonSend, btnnewtaxi, btnviptaxi, btnpiura;
    EditText textPhoneNo;
    EditText textSMS;
    EditText textToken;

    final Handler handler = new Handler();
    JSONParser jParser = new JSONParser();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_NUMERO = "numero";
    private static final String TAG_MENSAJE = "mensaje";

    private static String url_session = "http://72.55.147.236/~smartingnewtaxi/clientes_nuevos.php";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    String token;
    int exito = 0;
    String url = "",mensaje;

    SharedPreferences pref;
    Context ctx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = this;
        pref = getApplicationContext().getSharedPreferences("MySession", MODE_PRIVATE);

        buttonSend = (Button) findViewById(R.id.buttonSend);
        textPhoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
        textSMS = (EditText) findViewById(R.id.editTextSMS);

        buttonSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String phoneNo = textPhoneNo.getText().toString();
                String sms = textSMS.getText().toString();

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    Toast.makeText(getApplicationContext(), "SMS Sent!",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });


        btnnewtaxi = (Button)findViewById(R.id.btnnewtaxi);
        btnviptaxi = (Button)findViewById(R.id.btnviptaxi);
        btnpiura = (Button)findViewById(R.id.btnpiura);

        btnnewtaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mensaje = "Registro exitoso NewTakci";
                url = "http://72.55.147.236/newtaxi/gcm/gcm.php";
                new UpdateTokenId().execute();
            }
        });

        btnviptaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mensaje = "Registro exitoso Vip Taxi";
                url = "http://72.55.147.237/modulos/gcm/gcm.php";
                new UpdateTokenId().execute();
            }
        });

        btnpiura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mensaje = "Registro exitoso Piura Remisse";
                url = "http://72.55.147.238/modulos/gcm/gcm.php";
                new UpdateTokenId().execute();
            }
        });

        //ComprobarSession.run();
        ShowToast(this,"iniciando");
        runnableToken.run();
    }

    int i=1;
    Runnable runnableToken = new Runnable() {
        @Override
        public void run() {
            try {
                if (pref.contains("tokenid")) {
                    //if(pref.getBoolean("actualizarToken",false)) {

                    ShowToast(ctx,pref.getString("tokenid",null));
                    Guardar(pref.getString("tokenid",null));
                    //ActualizarTokenID();
                    //}
                    handler.removeCallbacks(runnableToken);
                } else {
                    ShowToast(ctx,"No obtenie "+i);
                    handler.postDelayed(runnableToken, 2000);
                }
            } catch (Exception e) {
                handler.postDelayed(runnableToken, 2000);
            }
            i++;
        }
    };

    private void Guardar(final String token) {
        StringRequest sr = new StringRequest(Request.Method.POST,
                "http://104.227.137.163/apptaxi/comun/guardartokenIdSMS.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("tokenid", token);

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

    private void ShowToast(Context ctx, String text){
        Context context = ctx;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    Runnable ComprobarSession = new Runnable() {
        public void run() {
            new LoadSession().execute();
            handler.postDelayed(ComprobarSession, 5000);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(ComprobarSession);
    }

    private void SendMessage(String phoneNo,String sms){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, sms, null, null);
    }

    /////////////////////////////////////////
    class UpdateTokenId extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(String... args) {

            //List params = new ArrayList();
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("action", "add"));
            params.add(new BasicNameValuePair("tokenid", token));

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url, "POST", params);

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    exito=1;
                }else{
                    exito=0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(String file_url) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if(exito==1) {
                        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Falló", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    class LoadSession extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(String... args) {

            //List params = new ArrayList();
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("tipo", "0"));

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_session, "GET", params);

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    String numero = json.getString(TAG_NUMERO);
                    String mensaje = json.getString(TAG_MENSAJE);
                    SendMessage(numero,mensaje);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            //pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {}
            });

        }
    }
    /////////////////////////////////////////



}
