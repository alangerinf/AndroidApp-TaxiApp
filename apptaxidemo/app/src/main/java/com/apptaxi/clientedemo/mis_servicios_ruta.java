package com.apptaxi.clientedemo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.apptaxi.clientedemo.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrador on 15/09/2017.
 */
public class mis_servicios_ruta extends FragmentActivity implements OnMapReadyCallback {
    Context ctx;
    private GoogleMap mMap;
    String id_pedido,estado,conductor,tipo,vehiculo,url_foto;
    JSONArray coordenadas = null;

    NetworkImageView foto;
    ImageLoader imageLoader;
    TextView txt_conductor,txt_vehiculo;

    final Handler handler = new Handler();
    boolean creado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_servicios_ruta);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();
        id_pedido = extras.getString("id_pedido");
        estado = extras.getString("estado");
        conductor = extras.getString("conductor");
        vehiculo = extras.getString("vehiculo");
        url_foto = extras.getString("url_foto");

        txt_conductor = (TextView)findViewById(R.id.txt_conductor);
        txt_vehiculo = (TextView)findViewById(R.id.txt_marca);
        foto = (NetworkImageView)findViewById(R.id.img_foto_conductor);

        txt_conductor.setText(conductor);
        txt_vehiculo.setText(vehiculo);

        imageLoader = AppController.getInstance().getImageLoader();
        foto.setImageUrl(global.UrlServer+url_foto, imageLoader);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        int t_estado = Integer.parseInt(estado);
        if(t_estado==5) {
            CargarDatosPedidio();
        }else if(t_estado==3) {
            runnable.run();
        }else{
            showAlertDialog(this,global.nameEmpresa,"El servicio no tiene historial de ruta",false);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    Runnable runnable = new Runnable() {
        public void run() {
            CargarDatosPedidio();
            handler.postDelayed(runnable, 10000);
        }
    };

    private void CargarDatosPedidio() {
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0011,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                mMap.clear();
                                LatLng LatLng = new LatLng(data.getDouble("lat"),data.getDouble("lng"));
                                mMap.addMarker(new MarkerOptions()
                                        .position(LatLng)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_cliente)));
                                coordenadas = data.getJSONArray("coordenadas");
                                com.google.android.gms.maps.model.LatLng latLng1=LatLng,latLng2=LatLng;
                                double bearing=0.0;
                                for (int i = 0; i < coordenadas.length(); i++) {
                                    JSONObject c = coordenadas.getJSONObject(i);
                                    latLng2 = new LatLng(c.getDouble("lat"),c.getDouble("lng"));
                                    try{
                                        bearing = Float.parseFloat(c.getString("bearing"));
                                    }catch (NumberFormatException e){
                                        bearing=0.0;
                                    }

                                    DibujarRecorrido(latLng1,latLng2);
                                    latLng1=latLng2;
                                }
                                mMap.addMarker(new MarkerOptions()
                                        .position(latLng1)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_conductor)));
                                float myZoom;
                                if(creado) {
                                    myZoom = mMap.getCameraPosition().zoom;
                                }else{
                                    myZoom=16.0f;
                                    creado=true;
                                }

                                CameraPosition cameraPosition = new CameraPosition
                                        .Builder()
                                        .target(latLng2)
                                        .zoom(myZoom)
                                        .bearing((float)bearing)
                                        .build();
                                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                                mMap.moveCamera(cameraUpdate);

                            }else{
                                showAlertDialog(ctx, global.nameEmpresa, "Este servicio no cuenta con historial de ruta.", false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //dialog_load.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showAlertDialog(ctx, global.nameEmpresa, "Ocurrió un inconveniente, intente nuevamente en unos minutos", false);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_pedido", id_pedido);
                params.put("estado", estado);
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

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        if(!status) {
            alertDialog.setButton("Salir", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertDialog.show();
        }
    }

    private void DibujarRecorrido(LatLng origen, LatLng destino){
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(origen, destino)
                .width(12)
                .color(Color.rgb(129,190,247)));
    }
}
