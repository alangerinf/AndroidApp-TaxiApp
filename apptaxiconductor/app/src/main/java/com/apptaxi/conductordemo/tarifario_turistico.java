package com.apptaxi.conductordemo;

import android.app.ProgressDialog;
import android.content.Context;
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
public class tarifario_turistico extends AppCompatActivity {
    Context ctx;
    AutoCompleteTextView txt_origen,txt_destino;
    FilterWithSpaceAdapter<String> adapter1;

    String[] ids1,nombres1,ids2,nombres2;
    String id_origen="0",id_destino="0",precio,hora_noche_inicio,hora_noche_fin,add_turistico;
    JSONArray destinos = null, origenes = null;

    TextView txt_precio,txt_precio_referencial;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarifario_turistico);
        ctx = this;

        txt_origen=(AutoCompleteTextView)findViewById(R.id.txt_origen);
        txt_destino=(AutoCompleteTextView)findViewById(R.id.txt_destino);

        txt_precio=(TextView)findViewById(R.id.txt_precio);
        txt_precio_referencial = (TextView) findViewById(R.id.txt_precio_referencial);

        progress = new ProgressDialog(this);
        progress.setCancelable(false);

        CargarOrigenes();
    }

    private void DatosDestinos(){
        adapter1 = new FilterWithSpaceAdapter<String>(this,android.R.layout.simple_list_item_1, nombres2);

        txt_destino.setAdapter(adapter1);
        txt_destino.setThreshold(1);

        txt_destino.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selection = (String) adapterView.getItemAtPosition(position);
                int pos = -1;

                for (int i = 0; i < nombres2.length; i++) {
                    if (nombres2[i].equals(selection)) {
                        pos = i;
                        break;
                    }
                }
                id_destino = String.valueOf(ids2[pos]);
                ValidarcargarPrecio();
            }
        });
    }

    private void DatosOrigen(){
        adapter1 = new FilterWithSpaceAdapter<String>(this,android.R.layout.simple_list_item_1, nombres1);

        txt_origen.setAdapter(adapter1);
        txt_origen.setThreshold(1);

        txt_origen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selection = (String) adapterView.getItemAtPosition(position);
                int pos = -1;

                for (int i = 0; i < nombres1.length; i++) {
                    if (nombres1[i].equals(selection)) {
                        pos = i;
                        break;
                    }
                }
                id_origen = String.valueOf(ids1[pos]);
                ValidarcargarPrecio();
            }
        });
    }

    private void ValidarcargarPrecio(){
        if(Integer.valueOf(id_origen) !=0 && Integer.valueOf(id_destino) != 0) {
            CargarPrecios();
        }
    }

    private void CargarPrecios() {
        progress.setMessage("Consultando precios");
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0015,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                precio = data.getString("precio");
                                add_turistico = data.getString("add_turistico");
                                hora_noche_inicio = data.getString("hora_noche_inicio");
                                hora_noche_fin = data.getString("hora_noche_fin");

                                String valor = "PRECIO REFERENCIAL";
                                if(HorarioNocturno(hora_noche_inicio,hora_noche_fin)) {
                                    precio = String.valueOf(Double.parseDouble(precio) + Double.parseDouble(add_turistico));
                                    valor = "PRECIO REFRENCIAL NOCTURNO";
                                }
                                txt_precio.setText("PRECIO S/. "+precio);
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
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_origen", id_origen);
                params.put("id_destino", id_destino);

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
                                ids1 = new String[origenes.length()];
                                nombres1 = new String[origenes.length()];

                                for (int i = 0; i < origenes.length(); i++) {
                                    JSONObject c = origenes.getJSONObject(i);

                                    String id = c.getString("id");
                                    String nombre = c.getString("nombre");

                                    ids1[i]=id;
                                    nombres1[i]=nombre;
                                }

                                DatosOrigen();

                                CargarDestinos();

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

    private void CargarDestinos() {
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0017,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                destinos = data.getJSONArray("destinos");
                                ids2 = new String[destinos.length()];
                                nombres2 = new String[destinos.length()];

                                for (int i = 0; i < destinos.length(); i++) {
                                    JSONObject c = destinos.getJSONObject(i);

                                    String id = c.getString("id");
                                    String nombre = c.getString("nombre");

                                    ids2[i]=id;
                                    nombres2[i]=nombre;
                                }

                                DatosDestinos();
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

}
