package com.apptaxi.conductordemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

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
 * Created by Administrador on 07/09/2017.
 */
public class servicio_llamada_nuevo_dire  extends AppCompatActivity {
    SharedPreferences pref;
    Context ctx;

    int id_pedido,id_usuario;
    double lat, lng;
    ProgressDialog progress;

    Dialog dialog_anular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.servicio_llamada_nuevo_dire);
        ctx = this;

        progress = new ProgressDialog(this);
        progress.setCancelable(false);

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        id_pedido = pref.getInt("id_pedido",0);
        id_usuario = pref.getInt("id_usuario",0);
        lat = Double.parseDouble(pref.getString("latPedido",String.valueOf(global.latInicio)));
        lng = Double.parseDouble(pref.getString("lngPedido",String.valueOf(global.latInicio)));

        String cliente = pref.getString("cliente",null);
        String direccion = pref.getString("direccion",null);
        String numero = pref.getString("numero",null);
        String referencia = pref.getString("referencia",null);

        TextView txt_cliente = (TextView)findViewById(R.id.txt_cliente);
        TextView txt_direccion = (TextView)findViewById(R.id.txt_direccion);
        TextView txt_referencia = (TextView)findViewById(R.id.txt_referencia);

        txt_cliente.setText(cliente);
        txt_direccion.setText("DIRECCION\n"+direccion+" "+numero);
        txt_referencia.setText("REFERENCIA\n"+referencia);

        Button btn_ubicacion = (Button)findViewById(R.id.btn_ubicacion);
        btn_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                alertDialog.setIcon(R.drawable.ic_launcher);
                alertDialog.setTitle(global.nameEmpresa);
                alertDialog.setMessage("Indica que se encuentra en la dirección del cliente, " +
                        "Esto guardará las coordenadas de la ubicación para el próximo servicio. ¿Desea continuar?");
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Si, Guardar coordenadas", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        EstoyEnLaubicacion();
                    }
                });
                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        Button btn_anular = (Button)findViewById(R.id.btn_anular);
        btn_anular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAnular();
            }
        });

        Intent sv = new Intent(ctx, servicioDisponible.class);
        startService(sv);
    }

    private void EstoyEnLaubicacion() {
        progress.setMessage("Aplicando el servicio");
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0012,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putInt("actualizar_dire", 0);
                                editor.commit();

                                Intent i = new Intent(getApplicationContext(), servicio_llamada_aceptado.class);
                                startActivity(i);
                                finish();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog_anular.dismiss();
                global.ShowToast(ctx,global.NosePudo);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_pedido", String.valueOf(id_pedido));
                params.put("lat", pref.getString("lat",String.valueOf(global.latInicio)));
                params.put("lng", pref.getString("lng",String.valueOf(global.lngInicio)));

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

    public void openDialogAnular(){
        dialog_anular = new Dialog(this);
        dialog_anular.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_anular.setContentView(R.layout.confirmar_anular_servicio);
        //dialog.setTitle("Elige un motivo");

        Button btn_anular = (Button)dialog_anular.findViewById(R.id.btn_anular);
        btn_anular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnularServicioNoUbicado();
            }
        });

        Button btn_regresar = (Button)dialog_anular.findViewById(R.id.btn_regresar);
        btn_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_anular.dismiss();
            }
        });
        dialog_anular.show();
    }

    private void AnularServicioNoUbicado() {
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0005,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog_anular.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                removersesion();

                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog_anular.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog_anular.dismiss();
                global.ShowToast(ctx,global.NosePudo);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_pedido", String.valueOf(id_pedido));

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

    private  void removersesion(){
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("id_cliente", 0);
        editor.putInt("id_pedido", 0);
        editor.remove("estado_pedido");
        editor.remove("latPedido");
        editor.remove("lngPedido");
        editor.remove("cliente");
        editor.remove("direccion");
        editor.remove("numero");
        editor.remove("referencia");
        editor.remove("latDestino");
        editor.remove("lngDestino");
        editor.remove("destino");
        editor.remove("actualizar_dire");
        editor.putInt("conectado",1);
        editor.putBoolean("disponible",true);
        editor.commit();
    }

}
