package com.apptaxi.clientedemo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.apptaxi.clientedemo.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrador on 15/09/2017.
 */
public class mis_servicios extends AppCompatActivity {
    Context ctx;
    String fecha_consulta="00/00/0000";
    TextView txt_fecha;

    Dialog dialog;
    ProgressDialog progress;
    SharedPreferences pref;
    String[] idList,conductorList,vehiculoList,direccionList,fechaList,fotoList,dniList,precioList,estadoList;
    JSONArray direcciones = null;
    int id_usuario,exito,result=0;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_servicios);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ctx = this;

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Cargando lista de servicios");

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        id_usuario = pref.getInt("id_usuario",0);

        txt_fecha = (TextView)findViewById(R.id.txt_fecha);
        txt_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog1();
            }
        });

        lv = (ListView) findViewById(R.id.list_servicios);
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

                BuscarServicios();
            }
        });

        dialog.show();
    }


    private void BuscarServicios() {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0010,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                direcciones = data.getJSONArray("direcciones");

                                idList = new String[direcciones.length()];
                                conductorList = new String[direcciones.length()];
                                vehiculoList = new String[direcciones.length()];
                                direccionList = new String[direcciones.length()];
                                fechaList = new String[direcciones.length()];
                                fotoList = new String[direcciones.length()];
                                dniList = new String[direcciones.length()];
                                estadoList = new String[direcciones.length()];

                                result=direcciones.length();

                                for (int i = 0; i < direcciones.length(); i++) {
                                    JSONObject c = direcciones.getJSONObject(i);

                                    String id = c.getString("id");
                                    String conductor = c.getString("conductor");
                                    String vehiculo = c.getString("vehiculo");
                                    String direccion = c.getString("direccion");
                                    String fecha = c.getString("fecha");
                                    String foto = c.getString("foto");
                                    String dni = c.getString("dni");
                                    String estado = c.getString("estado");

                                    idList[i]=id;
                                    conductorList[i]=conductor;
                                    vehiculoList[i]=vehiculo;
                                    direccionList[i]=direccion;
                                    fechaList[i]=fecha;
                                    fotoList[i]=foto;
                                    dniList[i]=dni;
                                    estadoList[i]=estado;
                                }
                                CreateList();
                            }else{
                                lv.setAdapter(null);
                                global.ShowToast(ctx,"No se encontraron servicios solicitados");
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
            CustomServicios adapter = new CustomServicios(mis_servicios.this,
                    conductorList, vehiculoList, direccionList, idList, fechaList, fotoList, dniList,estadoList);
            lv = (ListView) findViewById(R.id.list_servicios);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    TextView txt_id = (TextView)view.findViewById(R.id.txt_id);
                    TextView txt_estado = (TextView)view.findViewById(R.id.txt_estado);
                    TextView txt_conductor = (TextView)view.findViewById(R.id.txt_conductor);
                    TextView txt_vehiculo = (TextView)view.findViewById(R.id.txt_marca);
                    TextView txt_url_foto = (TextView)view.findViewById(R.id.txt_foto);

                    Intent i = new Intent(ctx, mis_servicios_ruta.class);
                    i.putExtra("id_pedido", txt_id.getText().toString());
                    i.putExtra("estado", txt_estado.getText().toString());
                    i.putExtra("conductor", txt_conductor.getText().toString());
                    i.putExtra("vehiculo", txt_vehiculo.getText().toString());
                    i.putExtra("url_foto", txt_url_foto.getText().toString());
                    startActivity(i);
                }
            });
        }else{
            lv.setAdapter(null);
        }
    }
}
