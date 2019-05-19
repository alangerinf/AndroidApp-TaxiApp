package com.apptaxi.conductordemo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.apptaxi.conductordemo.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrador on 07/09/2017.
 */
public class mis_servicios extends AppCompatActivity {
    Context ctx;
    String[]clienteList,direccionList,calificaList,precioList;
    JSONArray servicios = null;

    ListView lv;
    int exito = 0,result=0,id_usuario;

    TextView txt_fecha;
    String fecha_consulta="00/00/0000";

    Dialog dialog;
    ProgressDialog progress;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_servicios);
        ctx = this;

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Cargando lista de servicios");

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        id_usuario = pref.getInt("id_usuario",0);


        txt_fecha = (TextView)findViewById(R.id.txt_fecha);
        lv = (ListView) findViewById(R.id.list_servicios);

        txt_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog1();
            }
        });

        ImageButton btn_lapiz = (ImageButton)findViewById(R.id.btn_lapiz);
        btn_lapiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog1();
            }
        });

        openDialog1();

    }

    public void openDialog1() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.datepicker);

        final DatePicker datePicker = (DatePicker)dialog.findViewById(R.id.datePicker1);

        String fecha = fecha_consulta;
        String[] parts = fecha.split("/");
        int year = Integer.parseInt(parts[2]);
        int month = Integer.parseInt(parts[1])-1;
        int day = Integer.parseInt(parts[0]);

        if(year != 0 && month != 0 && day != 0) {
            datePicker.updateDate(year, month, day);
        }

        final Button btn_aceptar = (Button)dialog.findViewById(R.id.btn_aceptar);
        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth()+1;
                int year = datePicker.getYear();

                String dia = String.valueOf(day);
                String mes = String.valueOf(month);
                String anio = String.valueOf(year);

                if(dia.length()==1){
                    dia = "0"+dia;
                }

                if(mes.length()==1){
                    mes = "0"+mes;
                }
                fecha_consulta = dia+"/"+mes+"/"+anio;
                txt_fecha.setText("FECHA: "+fecha_consulta);

                dialog.dismiss();
                ListaServicios();
            }
        });

        dialog.show();
    }

    private void ListaServicios() {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0013,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                exito = 1;
                                servicios = data.getJSONArray("servicios");

                                clienteList = new String[servicios.length()];
                                direccionList = new String[servicios.length()];
                                calificaList = new String[servicios.length()];
                                precioList = new String[servicios.length()];

                                result=servicios.length();

                                for (int i = 0; i < servicios.length(); i++) {
                                    JSONObject c = servicios.getJSONObject(i);

                                    String cliente = c.getString("cliente");
                                    String direccion = c.getString("direccion");
                                    String califica = c.getString("califica");
                                    String precio = c.getString("precio");

                                    clienteList[i]=cliente;
                                    direccionList[i]=direccion;
                                    calificaList[i]=califica;
                                    precioList[i]=precio;
                                }

                                CreateList();

                            }else{
                                lv.setAdapter(null);
                                global.ShowToast(ctx,"No se encontraron servicios aceptados");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                global.ShowToast(ctx,global.NosePudo);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_usuario", String.valueOf(id_usuario));
                params.put("fecha", fecha_consulta);

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

    private void CreateList(){
        if(result!=0) {
            CustomServicios adapter = new CustomServicios(mis_servicios.this, clienteList, direccionList, calificaList, precioList);
            lv.setAdapter(adapter);
        }else{
            lv.setAdapter(null);
        }
    }
}
