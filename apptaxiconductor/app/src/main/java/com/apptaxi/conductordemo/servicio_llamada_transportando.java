package com.apptaxi.conductordemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

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

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrador on 07/09/2017.
 */
public class servicio_llamada_transportando extends FragmentActivity implements OnMapReadyCallback, GoogleMap.CancelableCallback{

    private GoogleMap mMap;
    Context ctx;
    ProgressDialog progress;

    SharedPreferences pref;
    int id_pedido,id_usuario;
    double lat, lng,latPedido,lngPedido,latDestino,lngDestino;
    float bearing;
    String cliente,direccion,numero,referencia,destino,api_key;

    final Handler handler = new Handler();
    boolean mapaCreado = false,finalizadoMapa=true;
    Marker marker_conductor;

    SoundManager sound;
    int sonido_taxi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.servicio_llamada_transportando);
        ctx = this;

        progress = new ProgressDialog(this);
        progress.setCancelable(false);

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        id_pedido = pref.getInt("id_pedido",0);
        id_usuario = pref.getInt("id_usuario",0);
        lat = Double.parseDouble(pref.getString("lat",String.valueOf(global.latInicio)));
        lng = Double.parseDouble(pref.getString("lng",String.valueOf(global.latInicio)));
        bearing = Float.parseFloat(pref.getString("bearing","0.0f"));
        cliente = pref.getString("cliente",null);
        direccion = pref.getString("direccion",null);
        numero = pref.getString("numero",null);
        referencia = pref.getString("referencia",null);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button btn_finalizar = (Button)findViewById(R.id.btn_finalizar);
        btn_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, servicio_app_finalizar.class);
                startActivityForResult(i,1);
            }
        });

        sound = new SoundManager(getApplicationContext());
        sonido_taxi = sound.load(R.raw.transportando);

        sonido();
    }

    private void sonido(){

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                sound.play(sonido_taxi);
                Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                v.vibrate(3000);
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK ) {

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
            editor.putInt("conectado",1);
            editor.putBoolean("disponible",true);
            editor.commit();

            Intent sv = new Intent(ctx, servicioDisponible.class);
            startService(sv);

            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnableMap);
    }

    @Override
    public void onFinish() {
        finalizadoMapa=true;
    }

    @Override
    public void onCancel() {
        finalizadoMapa=true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        posicionarMarker();

        Intent sv = new Intent(ctx, servicioDisponible.class);
        startService(sv);
    }

    private void posicionarMarker(){
        if(finalizadoMapa) {
            finalizadoMapa=false;
            LatLng posicionConductor;
            if (!mapaCreado) {
                try {
                    posicionConductor = new LatLng(lat, lng);
                    marker_conductor = mMap.addMarker(new MarkerOptions()
                            .position(posicionConductor)
                            .title(global.marker)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_conductor)));

                    CameraPosition cameraPosition = new CameraPosition.Builder().target(posicionConductor).zoom(16.0f).bearing(bearing).build();
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    mMap.animateCamera(cameraUpdate, 1, this);
                    mapaCreado = true;
                    runnableMap.run();

                    //ConsultarApiKey();

                } catch (Exception e) {
                    //algo salio mal
                }
            } else {
                try {

                    posicionConductor = new LatLng(lat, lng);
                    if(marker_conductor==null) {
                        marker_conductor = mMap.addMarker(new MarkerOptions()
                                .position(posicionConductor)
                                .title(global.marker)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_conductor)));
                    }else{
                        marker_conductor.setPosition(posicionConductor);
                    }

                    float myZoom = mMap.getCameraPosition().zoom;

                    CameraPosition cameraPosition = new CameraPosition.Builder().target(posicionConductor).zoom(myZoom).bearing(bearing).build();
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    mMap.animateCamera(cameraUpdate, 500, this);

                } catch (Exception e) {
                    //algo salio mal
                }
            }
        }
    }

    Runnable runnableMap = new Runnable() {
        @Override
        public void run() {
            lat = Double.parseDouble(pref.getString("lat",String.valueOf(global.latInicio)));
            lng = Double.parseDouble(pref.getString("lng",String.valueOf(global.lngInicio)));
            bearing = Float.parseFloat(pref.getString("bearing","0.0f"));

            posicionarMarker();

            handler.postDelayed(runnableMap, 1000);
        }
    };

}
