package com.apptaxi.conductordemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrador on 08/09/2017.
 */
public class tarifario_aeropuerto extends AppCompatActivity {
    Context ctx;
    private TextView txt_precio,txt_precio_referencial;
    private AutoCompleteTextView txt_origen;

    private JSONArray origenes = null;

    private String[] ids, nombres;

    private String id_origen="0",precio,add_aeropuerto,hora_noche_inicio,hora_noche_fin;

    FilterWithSpaceAdapter<String> adapter1;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarifario_aeropuerto);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ctx = this;

        progress = new ProgressDialog(this);
        progress.setCancelable(false);

        txt_origen = (AutoCompleteTextView)findViewById(R.id.txt_origen);

        txt_precio = (TextView)findViewById(R.id.txt_precio);
        txt_precio_referencial = (TextView) findViewById(R.id.txt_precio_referencial);

        CargarOrigenes();

    }

    private void CargarOrigenes() {
        progress.setMessage("Cargando datos");
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0016,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                origenes = data.getJSONArray("origenes");
                                ids = new String[origenes.length()];
                                nombres = new String[origenes.length()];

                                for (int i = 0; i < origenes.length(); i++) {
                                    JSONObject c = origenes.getJSONObject(i);

                                    String id = c.getString("id");
                                    String nombre = c.getString("nombre");

                                    ids[i]=id;
                                    nombres[i]=nombre;
                                }

                                DatosDestinos();
                            }else{
                                global.ShowToast(ctx,global.NosePudo);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progress.dismiss();
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

    private void DatosDestinos(){
        adapter1 = new FilterWithSpaceAdapter<String>(this,android.R.layout.simple_list_item_1, nombres);

        txt_origen.setAdapter(adapter1);
        txt_origen.setThreshold(1);

        txt_origen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selection = (String) adapterView.getItemAtPosition(position);
                int pos = -1;

                for (int i = 0; i < nombres.length; i++) {
                    if (nombres[i].equals(selection)) {
                        pos = i;
                        break;
                    }
                }
                id_origen = String.valueOf(ids[pos]);
                CargarPrecios();
            }
        });
    }

    private void CargarPrecios() {
        progress.setMessage("Cargando precio");
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0018,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                precio = data.getString("precio");
                                add_aeropuerto = data.getString("add_aeropuerto");
                                hora_noche_inicio = data.getString("hora_noche_inicio");
                                hora_noche_fin = data.getString("hora_noche_fin");

                                String valor = "PRECIO REFERENCIAL";
                                if(HorarioNocturno(hora_noche_inicio,hora_noche_fin)) {
                                    precio = String.valueOf(Double.parseDouble(precio) + Double.parseDouble(add_aeropuerto));
                                    valor = "PRECIO REFRENCIAL NOCTURNO";
                                }
                                txt_precio.setText("PRECIO S/. " + precio);
                                txt_precio_referencial.setText(valor);
                            }else{
                                txt_precio.setText("PRECIO S/. 0.00");
                                txt_precio_referencial.setText("");
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
                params.put("id_origen", id_origen);

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

    private boolean HorarioNocturno(String inicio, String fin){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        String h24 = "23:59:59",h0="00:00:00";
        Date hora_24=null,hora_0=null,inicio_noche = null,fin_noche=null, hora_actual=null;
        Boolean retorno=false;

        try {
            hora_24 = sdf.parse( h24 );
            hora_0 = sdf.parse( h0 );
            inicio_noche = sdf.parse( inicio );
            fin_noche = sdf.parse( fin );
            hora_actual = sdf.parse( sdf.format(cal.getTime()) );

            if( hora_actual.after(inicio_noche)  && hora_actual.before(hora_24) ) {
                retorno = true;
            }else if( hora_actual.after(hora_0)  && hora_actual.before(fin_noche)  ){
                retorno = true;
            }else{
                retorno = false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return retorno;
    }
}
