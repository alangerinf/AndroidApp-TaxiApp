package com.apptaxi.smsDemo;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.apptaxi.smsDemo.app.AppController;

import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.SEND_SMS;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pref;
    final Handler handler = new Handler();



    private EditText eTxtPhone;
    private EditText eTxtMenssage;

    private FloatingActionButton fab;



    Context ctx = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //enlazando XML id's con java
        this.eTxtPhone      = (EditText) findViewById(R.id.eTxtPhone);
        this.eTxtMenssage   = (EditText) findViewById(R.id.eTxtMenssage);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }


        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String num =eTxtPhone.getText().toString();     //rescatando numero de telefono
                String mes =eTxtMenssage.getText().toString();  //rescatando mensaje
                String r; //recibiendo respuesta del envio

                Boolean flag = true;

                if(num.length()!=9){
                    ShowToast(ctx,"Solo se aceptan teléfonos de 9 dígitos");
                    flag = false;
                }
                if(mes.length()==0){
                    Snackbar.make(view, "Ingrese un mensaje válido", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    flag = false;
                }else if(mes.length()>160){
                    Snackbar.make(view, "El mensaje tiene más de 160 caracteres, es muy grande", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    flag = false;
                }

                if(flag){
                    r= sendMessage(num,mes);//enviando mensaje

                    Snackbar.make(view, r, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    if(r == "Enviando Mensaje..."){

    //                     n=num;//paprametros  declarados  ensima de runable, lo siento mundo, no soy lo q  creian
    //                     m=mes;

                          /*  try{
                                handler.postDelayed(runnableCheckSend,2000);
                            }catch (Exception e){
                                ShowToast(ctx,"nada"+e);
                            }
*/
                    }


                }


            }
        });

        //obteniendo datos de cache
        pref = (getApplicationContext().getSharedPreferences("MySession", MODE_PRIVATE));

        //hilo de verificacion y guardado de  token
        ShowToast(this,"iniciando");
        runnableToken.run();

        if (validarPermisos()){

        } else{

        }

    }

    private boolean validarPermisos(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(SEND_SMS)== PackageManager.PERMISSION_GRANTED)
                &&
                (checkSelfPermission(READ_SMS)== PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(SEND_SMS))
                ||
                (shouldShowRequestPermissionRationale(READ_SMS))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{SEND_SMS,READ_SMS},100);
        }
        return false;

    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Permisos Desactivados");
        dialog.setMessage("Debe aceptar todos los permisos");
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                solicitarPermisosManual();
            }
        });
        dialog.show();
    }


    private void solicitarPermisosManual() {
        final CharSequence[] opciones = {
                "si",
                "no"
        };
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(MainActivity.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de Forma Manual?");
        alertOpciones.setItems(
                opciones,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(opciones[i].equals("si")){
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",getPackageName(),null);
                            intent.setData(uri);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }

                    }
                });
        alertOpciones.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length == 2
                    &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                    &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED){
            } else {
                solicitarPermisosManual();

            }
        }
    }
/*
    String n;
    String m;
    Runnable runnableCheckSend = new Runnable() {

        @Override
        public void run() {
                if(checkSend(n,m)){
                 HandlerNotification not = new HandlerNotification(ctx,n,m);
                 not.setResIntent(MainActivity.class);
                 not.showNotify();
                }
        }

    };
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    String token = "";

    int i=1;
    Runnable runnableToken = new Runnable() {
        @Override
        public void run() {
            try {
                if (pref.contains("tokenid")) {
                    //if(pref.getBoolean("actualizarToken",false)) {
                    token = pref.getString("tokenid",null);
                    ShowToast(ctx,token);
                    Guardar(token);
                    EditText et = (EditText) findViewById(R.id.eTxtMenssage);
                    et.setText(token);
                    //ActualizarTokenID();
                    //}
                    return;
                    //handler.removeCallbacks(runnableToken);
                } else {
                    ShowToast(ctx,"No obtenie "+i);
                    handler.postDelayed(runnableToken, 2000);
                }
            } catch (Exception e) {
                Log.d("holahola",e.toString());
                handler.postDelayed(runnableToken, 2000);
            }
            i++;
        }
    };


    private void Guardar(final String token) {
        StringRequest sr = new StringRequest(Request.Method.POST,
                "http://gpsnisi.pe/newclass/comun/guardartokenIdSMS.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ctx,"RS:"+response,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx,"RS:"+error.toString(),Toast.LENGTH_LONG).show();
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


    public String sendMessage(String phone,String message){

        String r = "error";
        boolean flagSend = false;

        try{
            int permisionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

            PackageManager pm = this.getPackageManager();

            if (!pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY) &&
                    !pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_CDMA)) {//verifica si es un telefono
                   // r="Lo sentimos, tu dispositivo probablemente no pueda enviar SMS...";
            }else{

                if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
                    r="Permiso de Lectura SMS no habilitado para esta  aplicacíon";
                    Toast.makeText(getApplicationContext(),r,Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},225);

                }else {

                    if (permisionCheck != PackageManager.PERMISSION_GRANTED) {
                        r = "Permiso de envio SMS no habilitado para esta aplicación";
                        Toast.makeText(getApplicationContext(), r, Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 225);


                    } else {
                        android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
                        sms.sendTextMessage(phone, null, message, null, null);
                        r = "Enviando Mensaje...";
                        //Toast.makeText(getApplicationContext(),"Mensaje Enviado",Toast.LENGTH_LONG).show();
                        //verificar esi el mensaje  fue  enviado correctamente

                        flagSend = true;


                    }
                }
            }





        }catch(Exception e){
            r = "Error interno, no se envio el mensaje";
            Toast.makeText(getApplicationContext(),r+": "+e,Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


        return r;
    }

    boolean checkSend(String phone,String message){

        boolean r = false;
        String select = "address='"+phone+"' AND  body='"+message+"'";
        Cursor cur = getContentResolver().query(Uri.parse("content://sms/sent"),
                new String[]{"_id", "address","status","body"}, select, null, null);

        //_id address status
        if (cur.moveToFirst()) { /* false = no sms */
            do {
                String msgInfo = "";
                String temp ="";
                for (int i = 0; i < cur.getColumnCount(); i++) {

                    msgInfo += temp =" " + cur.getColumnName(i) + ":" + cur.getString(i);

                }

                Toast.makeText(this, msgInfo, Toast.LENGTH_LONG).show();
                if(!msgInfo.equals("")){
                    r=true;
                    break;
                }

            } while (cur.moveToNext());
        }else{
            Toast.makeText(this, "...el mensaje pudo no haberse enviado correctamente", Toast.LENGTH_SHORT).show();
        }

    return r;
    }

}
