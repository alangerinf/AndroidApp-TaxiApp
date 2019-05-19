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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by Administrador on 12/09/2017.
 */
public class personalizar_pedido extends AppCompatActivity {
    private Context ctx;
    ProgressDialog progress;
    SharedPreferences pref;

    TextView txt_direccion;
    EditText txt_numero,txt_referencia;
    int id_usuario,guardar;
    String usuario,lat,lng,direccion,numero,referencia,tipo="Cualquiera";
    String numPedidos="1";
    Spinner slc_tipo;
    ImageView img_carro;
    CheckBox chk_guardar;
    EditText numUnidades;
    int test=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalizar_pedido);
        ctx = this;

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        id_usuario = pref.getInt("id_usuario",0);
        usuario = pref.getString("usuario","");
        lat = pref.getString("lat","0.0");
        lng = pref.getString("lng","0.0");
        direccion = pref.getString("direccion","");
        numero = pref.getString("numero","");

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Guardando solicitud de taxi");

        txt_direccion = (TextView)findViewById(R.id.txt_direccion);
        txt_numero = (EditText) findViewById(R.id.txt_numero);
        txt_referencia = (EditText) findViewById(R.id.txt_referencia);

        txt_direccion.setText(direccion);
        txt_numero.setText(numero);

        img_carro = (ImageView)findViewById(R.id.img_carro);

        numUnidades = (EditText)findViewById(R.id.etNumUnidades);

        slc_tipo = (Spinner)findViewById(R.id.slc_tipo);

        slc_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo = parent.getItemAtPosition(position).toString();//sacamos  el tipo apr a deidr el tipo de taxi y para comprara par a cambiar la imagen

                if(tipo.equals("Cualquiera")){
                    img_carro.setImageResource(R.drawable.view_none);
                }else if(tipo.equals("Premium")){
                    img_carro.setImageResource(R.drawable.view_premium);
                }else if(tipo.equals("Auto")){
                    img_carro.setImageResource(R.drawable.view_classic);
                }else {
                    img_carro.setImageResource(R.drawable.view_premium);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btn_confirmar = (Button)findViewById(R.id.btn_confirmar);



        btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numero = txt_numero.getText().toString();
                referencia = txt_referencia.getText().toString();

                int nU = Integer.parseInt(numUnidades.getText().toString());

                if(nU>=2 && nU<=10){
                    global.ShowToast(ctx,"Se solicitó "+nU+ "Taxis");
                    guardar = 0;
                    if(chk_guardar.isChecked()){
                        guardar = 1;
                    }
                    numPedidos=String.valueOf(nU);
                    //GuardarPedidoMultiple2();
                }else{
                    if(nU==1){

                        global.ShowToast(ctx,"Se solicitó un Taxi");
                        guardar = 0;
                        if(chk_guardar.isChecked()){
                            guardar = 1;
                        }
                        GuardarPedido();

                    }else{
                        guardar = 0;
                        if(nU>10){
                            global.ShowToast(ctx,""+nU+ "es un número muy alto \n intente otro");
                        }else{
                            global.ShowToast(ctx,""+nU+" es un número invalido \n intente otro");
                        }
                        numUnidades.setText("1");
                    }

                }
            }
        });
        chk_guardar = (CheckBox)findViewById(R.id.chk_guardar);
    }

    @Override
    protected void onResume(){
        super.onResume();
        overridePendingTransition(R.anim.fade_in_up, R.anim.fade_out_up);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fade_in_down, R.anim.fade_out_down);
    }
/*
    public void GuardarPedidoMultiple2(){
        progress.show();

        Pedido ped = new Pedido();
        ped.lat=lat;
        ped.lng=lng;
        ped.id_usuario=String.valueOf(id_usuario);
        ped.usuario=usuario;
        ped.direccion=direccion;
        ped.numero=numero;
        ped.referencia=referencia;
        ped.tipo=tipo;
        ped.guardar=String.valueOf(guardar);
        ped.numPedidos=numPedidos;
        try{
            progress.dismiss();
            new HttpHandler().execute(ped);
            Intent i = new Intent(ctx, MapsActivity.class);
            startActivity(i);
            global.ShowToast(ctx,"Se envió las solicitudes correctamente, nuestras unidades se contactarán con usted");
        }catch (Exception e){
            progress.dismiss();
            global.ShowToast(ctx,e.toString());
        }


    }



*/

    public void GuardarPedidoMultiple(){
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0102,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            Intent i = getIntent();

                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Intent i = new Intent(ctx, MapsActivity.class);
                startActivity(i);
                global.ShowToast(ctx,"Se envió las solicitudes correctamente, espere a que las unidades se contacten con usted");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("lat", lat);
                params.put("lng", lng);
                params.put("id_cliente", String.valueOf(id_usuario));
                params.put("cliente", usuario);
                params.put("direccion", direccion);
                params.put("numero", numero);
                params.put("referencia", referencia);
                params.put("tipo", tipo);
                params.put("guardar", String.valueOf(guardar));
                params.put("numPedidos",numPedidos);
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
    private void GuardarPedido() {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0002,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                final int id_pedido = data.getInt("id_pedido");
                                if(data.getInt("entregados")>0) {
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putInt("id_pedido", data.getInt("id_pedido"));
                                    editor.putInt("estado", 0);
                                    editor.putInt("pedidoAceptado",0);
                                    editor.putInt("pedidoUbicacion",0);
                                    editor.commit();

                                    Intent i = getIntent();
                                    setResult(RESULT_OK, i);
                                    finish();
                                }else {
                                    final AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                                    alertDialog.setIcon(R.drawable.ic_launcher);
                                    alertDialog.setTitle(global.nameEmpresa);
                                    alertDialog.setMessage(global.SinUnidadescerca);
                                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Si, Envíenme", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            alertDialog.dismiss();
                                            PedirTaxiBase(id_pedido,1);
                                        }
                                    });
                                    alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No, Cancelar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            alertDialog.dismiss();
                                            PedirTaxiBase(id_pedido,0);
                                            //alan---- anula el servicio
                                            Intent i = new Intent(ctx, MapsActivity.class);
                                            startActivity(i);
                                            //alan
                                        }
                                    });
                                    alertDialog.show();
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
                params.put("lat", lat);
                params.put("lng", lng);
                params.put("id_cliente", String.valueOf(id_usuario));
                params.put("cliente", usuario);
                params.put("direccion", direccion);
                params.put("numero", numero);
                params.put("referencia", referencia);
                params.put("tipo", tipo);
                params.put("guardar", String.valueOf(guardar));
                //Toast.makeText(personalizar_pedido.this,tipo,Toast.LENGTH_LONG).show();
               // Log.d("personalizar: ",tipo);
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

    private void PedirTaxiBase(final int id, final int operacion) {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0002_1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                global.ShowToast(ctx,global.SeSolicitoTaxi);
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
                params.put("id_pedido", String.valueOf(id));
                params.put("operacion", String.valueOf(operacion));

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
