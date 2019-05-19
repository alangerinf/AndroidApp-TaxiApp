package com.apptaxi.conductordemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrador on 09/09/2017.
 */
public class tarifario_urbano extends FragmentActivity implements OnMapReadyCallback {
    Context ctx;
    private GoogleMap mMap;
    Geocoder geocode;
    double lat=0,lng=0,latActual=0, lngActual=0,lat1,lng1,lat2,lng2;;
    private CheckBox chk_corp;
    Button btn1,btn2;
    ImageButton btn_cancelar;
    TextView txt_direccion,txt_mensaje;
    Marker m1=null,m2=null;

    String direccion;
    int exito=0;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.tarifario_urbano);
        ctx = this;

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        geocode = new Geocoder(this, Locale.getDefault());

        chk_corp = (CheckBox)findViewById(R.id.chk_corp);

        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn_cancelar = (ImageButton) findViewById(R.id.btnclear);

        txt_direccion = (TextView)findViewById(R.id.txt_direccion);

        txt_mensaje = (TextView)findViewById(R.id.txt_mensaje);
        txt_mensaje.setText("Arrastra el mapa y marca el punto de origen");

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m1!=null) {
                    m1.remove();
                }
                lat1 = mMap.getCameraPosition().target.latitude;
                lng1 = mMap.getCameraPosition().target.longitude;

                LatLng LatLng = new LatLng(lat1, lng1);

                m1 = mMap.addMarker(new MarkerOptions()
                        .position(LatLng)
                        .title("Marker")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_cliente)));

                btn1.setVisibility(View.INVISIBLE);

                btn_cancelar.setVisibility(View.VISIBLE);

                txt_mensaje.setText("Arrastra el mapa y marca el punto de Destino");
                global.ShowToast(ctx,"AHORA ARRASTRA EL MAPA Y UBICA EL DESTINO");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m2!=null) {
                    m2.remove();
                }
                lat2 = mMap.getCameraPosition().target.latitude;
                lng2 = mMap.getCameraPosition().target.longitude;

                LatLng LatLng2 = new LatLng(lat2, lng2);
                m2 = mMap.addMarker(new MarkerOptions()
                        .position(LatLng2)
                        .title("Marker")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_conductor)));

                String corp = "no";
                if(chk_corp.isChecked()){
                    corp = "si";
                }
                Intent i = new Intent(ctx, tarifario_urbano_precio.class);
                i.putExtra("lat1", String.valueOf(lat1) );
                i.putExtra("lng1", String.valueOf(lng1) );
                i.putExtra("lat2", String.valueOf(lat2) );
                i.putExtra("lng2", String.valueOf(lng2) );
                i.putExtra("corp", corp );
                startActivity(i);
            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                btn1.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.INVISIBLE);

                btn_cancelar.setVisibility(View.INVISIBLE);

                m1 = null;
                m2 = null;

                txt_mensaje.setText("Arrastra el mapa y marca el punto de origen");

                posicionActual();
            }
        });

    }

    private void posicionActual(){
        latActual = Double.parseDouble(pref.getString("lat",String.valueOf(global.latInicio)));
        lngActual = Double.parseDouble(pref.getString("lng",String.valueOf(global.lngInicio)));
        LatLng center = new LatLng(latActual,lngActual);
        CameraPosition cameraPosition;
        cameraPosition = new CameraPosition.Builder().target(center).zoom(16.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.moveCamera(cameraUpdate);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        posicionActual();

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition loc) {
                if(loc!=null) {
                    isDrag(loc.target.latitude, loc.target.longitude);
                }
            }
        });
    }

    private void isDrag(double cor_lat, double cor_lng){
        txt_direccion.setText("Buscando tu dirección");
        lat = cor_lat;
        lng = cor_lng;
        new BuscarDireccion().execute();
    }

    class BuscarDireccion extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exito=0;
            btn1.setVisibility(View.INVISIBLE);
            btn2.setVisibility(View.INVISIBLE);
        }

        protected String doInBackground(String... args) {
            if(lat!=0 && lng != 0) {
                if(latActual != lat & lngActual != lng) {

                    latActual = lat;
                    lngActual = lng;
                    Address ad;
                    try {
                        List<Address> addresses = geocode.getFromLocation(lat, lng, 1);
                        ad = addresses.get(0);
                        //direccion = ad.getThoroughfare();
                        direccion = ad.getAddressLine(0);
                        exito=1;

                    } catch (IOException e) {
                        direccion = "Problemas con el internet...";
                    }
                }
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            txt_direccion.setText(direccion);
            if(exito==1){
                if(m1==null) {
                    btn1.setVisibility(View.VISIBLE);
                }else{
                    btn2.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
