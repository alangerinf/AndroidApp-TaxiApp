package com.apptaxi.clientedemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import com.apptaxi.clientedemo.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrador on 13/09/2017.
 */
public class pedido_aceptado extends FragmentActivity implements OnMapReadyCallback, GoogleMap.CancelableCallback {

    private int count =0;

    private GoogleMap mMap;
    Context ctx;
    SharedPreferences pref;
    double lat,lng,latConductor,lngConductor;
    float bearing;
    String empleado,vehiculo,modelo,foto;
    TextView txt_conductor, txt_marca, txt_placa, txt_color, txt_modelo;

    NetworkImageView img_foto_conductor;
    ImageLoader imageLoader;
    Marker mConductor=null,mCliente=null;
    Polyline pLine=null, pLineTemporal=null;
    boolean mapaCreado=false,mapaFinish=true;

    final Handler handler = new Handler();

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.pedido_aceptado);
        ctx = this;

        progress = new ProgressDialog(this);
        progress.setCancelable(false);

        imageLoader = AppController.getInstance().getImageLoader();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);

        empleado = pref.getString("empleado","-");
        modelo = pref.getString("modelo","-");
        vehiculo = pref.getString("vehiculo","-");
        foto = pref.getString("foto","-");
        foto = foto.substring(8);

        txt_conductor = (TextView)findViewById(R.id.txt_conductor);
        txt_marca = (TextView)findViewById(R.id.txt_marca);
        txt_placa = (TextView)findViewById(R.id.txt_placa);
        txt_modelo= findViewById(R.id.txt_modelo);
        txt_color= findViewById(R.id.txt_color);

        txt_conductor.setText(empleado.toUpperCase());
        txt_marca.setText("PLACA: "+vehiculo.toUpperCase());

        String string = vehiculo.toUpperCase();
        String[] parts = string.split("-");
        String marca = parts[0]; // 004
        String placa = parts[1];

        txt_placa.setText("PLACA: "+placa);
        txt_marca.setText("MARCA: "+marca);

        String string1 = modelo.toUpperCase();
        String[] parts1 = string1.split("-");
        String modelo = parts1[0]; // 004
        String color = parts1[1];

        txt_modelo.setText("MODELO: "+modelo);
        txt_color.setText("COLOR: "+color);



        img_foto_conductor = (NetworkImageView)findViewById(R.id.img_foto_conductor);
        img_foto_conductor.setImageUrl(global.UrlServer+"apptaxi/"+foto, imageLoader);

        ImageButton btn_cancelar = (ImageButton)findViewById(R.id.btn_cancelar);
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                alertDialog.setIcon(R.drawable.ic_launcher);
                alertDialog.setTitle(global.nameEmpresa);
                alertDialog.setMessage(global.AnularServicio);
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AnularPedido();
                        alertDialog.dismiss();
                    }
                });
                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

    }



    Runnable runnableMap = new Runnable() {
        @Override
        public void run() {
            count++;
            if(pref.getInt("estado",0)==1) {
                PosicionMarker();
                handler.postDelayed(runnableMap, 1000);
            }else{
                global.ShowToast(ctx,"Servicio Finalizado");
                finish();
            }
        }
    };

    private void PosicionMarker(){
        //if(mapaFinish) {
            mapaFinish=false;
            try {
                lat = Double.parseDouble(pref.getString("lat", "0.0"));
                lng = Double.parseDouble(pref.getString("lng", "0.0"));
                latConductor = Double.parseDouble(pref.getString("latConductor", "0.0"));
                lngConductor = Double.parseDouble(pref.getString("lngConductor", "0.0"));
                bearing = Float.parseFloat(pref.getString("bearing", "0.0f"));

                LatLng posicionCliente = new LatLng(lat, lng);
                LatLng posicionConductor = new LatLng(latConductor, lngConductor);

                if (mConductor == null) {
                    mConductor = mMap.addMarker(new MarkerOptions()
                            .position(posicionConductor)
                            .title("Taxi")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_conductor)));
                } else {
                    global.animateMarker(mConductor, posicionConductor, false, mMap);
                }

                if (mCliente == null) {
                    mCliente = mMap.addMarker(new MarkerOptions()
                            .position(posicionCliente)
                            .title("Yo")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_cliente)));
                } else {
                    global.animateMarker(mCliente, posicionCliente, false, mMap);
                }

                if(mCliente!=null && mConductor!=null){
                    showCurvedPolyline(posicionCliente,posicionConductor,0.5);
                }

            } catch (Exception e) {
                //algo salio mal
              //  global.ShowToast(ctx, "algo salio mal");
            }
        /*}else{
            global.ShowToast(ctx,String.valueOf(count)+"vez q refresca");

        }*/
    }

    boolean statusPoli=false;
    private void showCurvedPolyline (LatLng p1, LatLng p2, double k) {
        //Calculate distance and heading between two points
        double d = SphericalUtil.computeDistanceBetween(p1,p2);
        double h = SphericalUtil.computeHeading(p1, p2);

        //Midpoint position
        LatLng p = SphericalUtil.computeOffset(p1, d*0.5, h);

        //Apply some mathematics to calculate position of the circle center
        double x = (1-k*k)*d*0.5/(2*k);
        double r = (1+k*k)*d*0.5/(2*k);

        LatLng c = SphericalUtil.computeOffset(p, x, h + 90.0);

        //Polyline options
        PolylineOptions options = new PolylineOptions();
        List<PatternItem> pattern = Arrays.<PatternItem>asList(new Dash(10), new Gap(5));

        //Calculate heading between circle center and two points
        double h1 = SphericalUtil.computeHeading(c, p1);
        double h2 = SphericalUtil.computeHeading(c, p2);

        //Calculate positions of points on circle border and add them to polyline options
        int numpoints = 100;
        double step = (h2 -h1) / numpoints;

        for (int i=0; i < numpoints; i++) {
            LatLng pi = SphericalUtil.computeOffset(c, r, h1 + i * step);
            options.add(pi);
        }


        //Draw polyline

        if(!statusPoli){
            pLine = mMap.addPolyline(options.width(5).color(Color.BLACK).geodesic(false).pattern(pattern));
            statusPoli=true;
        }else {
            pLine.setVisible(false);
            pLineTemporal = mMap.addPolyline(options.width(5).color(Color.BLACK).geodesic(false).pattern(pattern));
            pLine = null;
            pLine = pLineTemporal;
            pLine.setVisible(true);
        }

    }

    @Override
    public void onFinish() {
        mapaFinish=true;
    }

    @Override
    public void onCancel() {
        mapaFinish=true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Intent sv = new Intent(ctx, servicioPedido.class);
        startService(sv);
        lat = Double.parseDouble(pref.getString("lat", "0.0"));
        lng = Double.parseDouble(pref.getString("lng", "0.0"));
        LatLng posicionCliente = new LatLng(lat,lng);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(posicionCliente).zoom(15.0f).bearing(bearing).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cameraUpdate, 500, this);
        runnableMap.run();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnableMap);
    }

    private void AnularPedido() {
        progress.setMessage("Aplicando operación");
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0003,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){

                                SharedPreferences.Editor editor = pref.edit();
                                editor.remove("id_pedido");
                                editor.commit();

                                ////////////////////////////////////
                                removersesion();
                                stopService(new Intent(getBaseContext(), servicioPedido.class));

                                Intent i = new Intent(ctx, MapsActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
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
                params.put("id_pedido", String.valueOf(pref.getInt("id_pedido",0)));

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
        editor.remove("id_pedido");
        editor.remove("estado");
        editor.remove("id_empleado");
        editor.remove("latConductor");
        editor.remove("lngConductor");
        editor.remove("bearing");
        editor.remove("empleado");
        editor.remove("modelo");
        editor.remove("vehiculo");
        editor.remove("foto");
        editor.remove("pedidoAceptado");
        editor.remove("pedidoUbicacion");
        editor.commit();
    }
}