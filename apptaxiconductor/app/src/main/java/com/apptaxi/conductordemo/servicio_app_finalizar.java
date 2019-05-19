package com.apptaxi.conductordemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;
import com.apptaxi.conductordemo.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrador on 31/08/2017.
 */
public class servicio_app_finalizar extends AppCompatActivity {

    SharedPreferences pref;
    Context ctx;
    int id_pedido,id_cliente;
    JSONArray coordenadas = null;
    TextView txt_distancia,txt_bolsa;
    EditText txt_precio;

    private String precio,bolsa,id_vale,add_urbano,hora_noche_inicio,hora_noche_fin,precio_calculado,precio_cobrado,motivo="--";
    private Double precio_base,p_Km,km_LDist;
    private int km_min,dist_Min;

    ProgressDialog progress;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.servicio_app_finalizar);
        ctx = this;

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        id_pedido = pref.getInt("id_pedido",0);
        id_cliente = pref.getInt("id_cliente",0);

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Aplicando precio a cobrar");

        txt_distancia = (TextView)findViewById(R.id.txt_distancia);
        txt_bolsa = (TextView)findViewById(R.id.txt_bolsa);
        txt_precio = (EditText)findViewById(R.id.txt_precio);

        Button btn_finalizar = (Button)findViewById(R.id.btn_finalizar);
        btn_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    precio_cobrado = txt_precio.getText().toString();
                    if(precio_cobrado.length()==0) {
                        global.ShowToast(ctx,"Debe ingresar un importe válido");
                    }else{
                        if (Double.parseDouble(precio_cobrado) > Double.parseDouble(precio_calculado)) {
                            if(Double.parseDouble(precio_calculado)==0){
                                FinalizarServicio();//en el caso de que no haya podido calcular el precio
                            }else{
                                //mostramos los motivos de por que cobra mas que la tarifa
                                AbrirMotivoCobro();
                            }
                        } else {
                            FinalizarServicio();
                        }
                    }
                }catch (Exception e){
                    //algo salio mal
                }
            }
        });

        CargarRecorridoServicio();
    }

    private void AbrirMotivoCobro() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.motivo_cobra_mayor);
        //dialog.setTitle("Elige un motivo");
        final EditText txt_otro = (EditText)dialog.findViewById(R.id.txt_otro);
        RadioButton radio_hora = (RadioButton)dialog.findViewById(R.id.radio_hora);
        RadioButton radio_varios = (RadioButton)dialog.findViewById(R.id.radio_varios);
        RadioButton radio_otro = (RadioButton)dialog.findViewById(R.id.radio_otro);
        radio_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                motivo = "El servicio es por hora";
                txt_otro.setVisibility(View.INVISIBLE);
                txt_otro.setText("");
            }
        });
        radio_varios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                motivo = "Se hizo una o mas paradas";
                txt_otro.setVisibility(View.INVISIBLE);
                txt_otro.setText("");
            }
        });
        radio_otro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                motivo = "**indicarmotivo**";
                txt_otro.setVisibility(View.VISIBLE);
                txt_otro.setText("");
                txt_otro.requestFocus();
            }
        });

        Button btn_enviar = (Button)dialog.findViewById(R.id.btn_enviar);
        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (motivo.equals("--")) {
                    global.ShowToast(ctx,"Debes seleccionar un motivo");
                }else if(motivo.equals("**indicarmotivo**")){
                    if(txt_otro.getText().length()>0){
                        motivo = txt_otro.getText().toString();
                    }else{
                        global.ShowToast(ctx,"Debes indicar un motivo");
                        txt_otro.setSelectAllOnFocus(true);
                    }
                } else {
                    FinalizarServicio();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private void CargarAlertDialog(){
        final AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setIcon(R.drawable.ic_launcher);
        alertDialog.setTitle(global.nameEmpresa);
        alertDialog.setMessage(global.FinalizarNoSePudoCalcular);
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                txt_precio.setText("4.0");
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void FinalizarServicio() {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0009,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                setResult(Activity.RESULT_OK);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            CargarAlertDialog();
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
                params.put("id_pedido", String.valueOf(id_pedido));
                params.put("id_cliente", String.valueOf(id_cliente));
                params.put("precio", String.valueOf(precio_cobrado));
                params.put("prec_sug", String.valueOf(precio_calculado));
                params.put("descuento", bolsa);
                params.put("id_vale", id_vale);
                params.put("motivo", motivo);
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

    private void CargarRecorridoServicio() {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0008,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                bolsa = data.getString("bolsa");
                                id_vale = data.getString("id_vale");
                                precio_base = data.getDouble("precio_base");
                                km_min = data.getInt("km_minimo");
                                dist_Min = data.getInt("dist_minimo");
                                p_Km = data.getDouble("prec_km");
                                km_LDist = data.getDouble("km_dist_larga");

                                add_urbano = data.getString("add_urbano");
                                hora_noche_inicio = data.getString("hora_noche_inicio");
                                hora_noche_fin = data.getString("hora_noche_fin");

                                LatLng LatLng = new LatLng(data.getDouble("lat"),data.getDouble("lng"));

                                coordenadas = data.getJSONArray("coordenadas");
                                com.google.android.gms.maps.model.LatLng latLng1=LatLng,latLng2=LatLng;

                                float total = 0;
                                double latP=0.0,lngP=0.0, latI,lngI;
                                latI = LatLng.latitude;
                                lngI = LatLng.longitude;

                                for (int i = 0; i < coordenadas.length(); i++) {
                                    JSONObject c = coordenadas.getJSONObject(i);
                                    latP = c.getDouble("lat");
                                    lngP = c.getDouble("lng");

                                    float[] distance = new float[1];
                                    Location.distanceBetween(latI, lngI, latP, lngP, distance);
                                    total += distance[0];
                                    latI=latP;
                                    lngI=lngP;
                                }

                                Distancia(String.valueOf(total));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            CargarAlertDialog();
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
                params.put("id_pedido", String.valueOf(id_pedido));
                params.put("id_cliente", String.valueOf(id_cliente));
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

    private void Distancia(String distancia){
        try {
            int error = 0;

            if (error == 0) {
                Double dist;
                dist = Double.valueOf(distancia) / 1000;


                txt_distancia.setText(String.format("%.1f", dist) + " km. Aprox.");
                precio = CalcularPrecio(dist);
                if (global.HorarioNocturno(hora_noche_inicio, hora_noche_fin)) {
                    precio = String.valueOf(Double.parseDouble(precio) + Double.parseDouble(add_urbano));
                }

                if (Double.parseDouble(bolsa) > 0) {
                    txt_bolsa.setText("BONO: " + bolsa);
                    Double diferencia = Double.parseDouble(precio) - Double.parseDouble(bolsa);
                    if (diferencia <= 0) {
                        diferencia = 0.0;
                    }
                    precio = diferencia.toString();
                }

                txt_precio.setText(precio);
                precio_calculado = precio;
                if(Double.parseDouble(precio)==0){
                    CargarAlertDialog();
                }

            }
        }catch (Exception e){
            CargarAlertDialog();
        }
    }

    public String CalcularPrecio(Double distancia){
        String importe = "0.0";
        try {
            Double d1 = distancia;
            Double pBase = precio_base; //precio base de los servicios minimos
            int kMin = km_min; //kilometraje de recorrido minimo para cobrar el precio base
            int distMin = dist_Min; //distancia minimo para cobrar 1 sol por km mas el precio base
            Double pKm = p_Km; // el precio a cobrar por cada km que exece a la distancia minima
            Double kmLDist = km_LDist;//minimo de km para largas distancias y sumar 1 por km

            if (distancia >= distMin) {
                importe = precioBase((double) distMin - 1, pBase, kMin);
                if (distancia < kmLDist) {
                    Double diferencia = Math.ceil(d1 - distMin);//redondeamos la diferencia en una unidad
                    importe = String.valueOf(Math.ceil(Double.parseDouble(importe) + diferencia * pKm)); //calculamos el precio con el agregado del precio base
                } else {//calculamos precio con mayor distancia a 15
                    Double diferencia = Math.ceil(d1 - distMin);//redondeamos la diferencia en una unidad
                    Double kmMayor = diferencia - kmLDist;//la diferencia sumamos 1 * km
                    importe = String.valueOf(Math.ceil(Double.parseDouble(importe) + (distMin + kMin) * pKm + kmMayor)); //calculamos el precio con el agregado del precio base
                }
            } else {
                importe = precioBase(d1, pBase, kMin);
            }
        }catch (Exception e){
            //algo salio mal
        }
        return importe;
    }

    private String precioBase(Double d1,Double pBase,int kMin){
        String importe = "0.0";
        try {
            if (d1 <= kMin) {
                importe = String.valueOf(pBase);
            } else {
                d1 = Math.ceil(d1);
                d1 = d1 - kMin;
                importe = String.valueOf(pBase + d1);//calculamos el precio restando el km minimmo para suma 1 por km
            }
        }catch (Exception e) {
            //algo salio mal
        }
        return importe;
    }
}
