package com.apptaxi.conductordemo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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
import com.apptaxi.conductordemo.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.CancelableCallback {

    SharedPreferences pref;

    private GoogleMap mMap;
    private Context ctx;
    LocationManager locationManager;
    private Marker marker;
    boolean creadoMap = false, mapaCreado = false, primeraUbicacion = false;
    double lat, lng;
    float bearing;
    String tk;
    final Handler handler = new Handler();

    ListView lv;
    Integer [] imageList={R.drawable.op_1,R.drawable.op_2,R.drawable.op_7,R.drawable.op_9,R.drawable.op_6};
    String [] nameList={"Mis servicios","Mensajes","Cambiar contraseña","Tarifario","Cerrar sesión"};

    private DrawerLayout drawerLayout;
    private RelativeLayout mDrawerRelativeLayout;
    ImageButton btn_opciones;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_maps);
        ctx = this;

        progress = new ProgressDialog(this);
        progress.setCancelable(false);

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);

        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {
            showGPSDisabledAlert();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Obtener drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerRelativeLayout = (RelativeLayout) findViewById(R.id.drawer_left);

        CreateMenu();

        btn_opciones = (ImageButton)findViewById(R.id.btn_opciones);
        btn_opciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(mDrawerRelativeLayout);
            }
        });

        Button btn_no_escanear = (Button)findViewById(R.id.btn_no_escanear);
        btn_no_escanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("disponible", false);
                editor.putInt("conectado",0);
                editor.commit();
                desconectar();

                stopService(new Intent(getBaseContext(), servicioDisponible.class));

                Intent i = new Intent(ctx, fuera_servicio.class);
                startActivity(i);
                finish();
            }
        });


        ImageButton btn_transportar_calle = (ImageButton)findViewById(R.id.btn_transportar_calle);
        btn_transportar_calle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(MapsActivity.this).create();
                alertDialog.setIcon(R.drawable.ic_launcher);
                alertDialog.setTitle(global.nameEmpresa);
                alertDialog.setMessage("¿Va a iniciar el transporte de un pasajero ubicado amulatoriamente?");
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Si, transportar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        IniciarPasajeroCalle();
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

    private void removersesion(){
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
    private void IniciarPasajeroCalle() {
        progress.setMessage("Iniciando servicio");
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0023,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){

                                SharedPreferences.Editor editor = pref.edit();
                                editor.putInt("id_cliente", data.getInt("id_cliente"));
                                editor.putInt("id_pedido", data.getInt("id_pedido"));
                                editor.putInt("estado_pedido", 3);
                                editor.putInt("conectado",3);
                                editor.putString("latPedido",data.getString("lat"));
                                editor.putString("lngPedido",data.getString("lng"));
                                editor.putString("cliente",data.getString("cliente"));
                                editor.putString("direccion",data.getString("direccion"));
                                editor.putString("numero",data.getString("numero"));
                                editor.putString("referencia",data.getString("referencia"));
                                editor.putString("latDestino","0.0");
                                editor.putString("lngDestino","0.0");
                                editor.putString("destino","-");
                                editor.putInt("servicio",3);
                                editor.putInt("actualizar_dire",0);
                                editor.putBoolean("disponible",false);
                                editor.commit();

                                Intent i = new Intent(ctx, servicio_amb_transportando.class);
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
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_usuario", String.valueOf(pref.getInt("id_usuario",0)));
                params.put("lat", pref.getString("lat",String.valueOf(global.latInicio)));
                params.put("lng", pref.getString("lng",String.valueOf(global.latInicio)));

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
    private void CreateMenu(){
        CustomList adapter = new CustomList(MapsActivity.this, nameList, imageList);
        lv=(ListView)findViewById(R.id.left_drawer);
        //lv.setBackgroundResource(R.color.azul);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selectItem(position);

            }
        });
    }

    public void selectItem(int position) {

        lv.setItemChecked(position, true);
        drawerLayout.closeDrawer(mDrawerRelativeLayout);

        if(position==0) {
            Intent i = new Intent(ctx, mis_servicios.class);
            startActivity(i);
        }else if(position==1) {
            Intent i = new Intent(ctx, mensajes.class);
            startActivity(i);
        }else if(position==2) {
            Intent i = new Intent(ctx, cambiar_contrasenia.class);
            startActivityForResult(i, 2);
        }else if(position==3){
            Intent i = new Intent(ctx, tarifario.class);
            startActivity(i);
        }else if(position==4){
            //cerrar sesion
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Seguro que desea salir del aplicativo de "+global.nameEmpresa)
                    .setCancelable(false)
                    .setPositiveButton("Cerrar sesión",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.remove("id_usuario");
                                    editor.putBoolean("disponible", false);
                                    editor.commit();

                                    stopService(new Intent(getBaseContext(), servicioDisponible.class));

                                    Intent i = new Intent(ctx, login.class);
                                    startActivity(i);
                                    finish();

                                }
                            });
            alertDialogBuilder.setNegativeButton("Cancelar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }

    }


    private void desconectar() {
        final int sd = pref.getInt("id_usuario",111);
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0009_3, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
              //  Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getApplicationContext(),error.toString().trim(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() {
                final String email = String.valueOf(sd).trim();
                final String password = String.valueOf(sd).trim();
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("id", email);
                MyData.put("id_usuario", password);
                return MyData;
            }
        };


        AppController.getInstance().addToRequestQueue(sr);



        /*
        int sd = pref.getInt("id_usuario",111);


        global.ShowToast(ctx, String.valueOf(sd) );
        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("id_usuario", sd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST,
                global.UrlServer + global.UL0009_3, jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response;
                            int success = data.getInt("success");
                            if(success==1){
                                global.ShowToast(ctx,"Desconexión Exitosa");
                            }else{
                                global.ShowToast(ctx,"Desconexión Fallida");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            global.ShowToast(ctx,"Falló");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                global.ShowToast(ctx,"Falló x 2");
            }
        });
        AppController.getInstance().addToRequestQueue(sr);
        */

        //=====================

        /*
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST,
                global.UrlServer + global.UL0009_3,jsonParams,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                global.ShowToast(ctx,"Desconexión Exitosa");
                            }else{
                                global.ShowToast(ctx,"Desconexión Fallida");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                global.ShowToast(ctx,global.NosePudo);
            }
        });
        AppController.getInstance().addToRequestQueue(sr);*/
    }

    private void showGPSDisabledAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("EL GPS está deshabilitado, ¿Desea habilitarlo?")
                .setCancelable(false)
                .setPositiveButton("Habilitar GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK ) {
            if(requestCode==2) {
                finish();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnableMap);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        lat = -18.0194071;
        lng = -70.2851854;
        bearing = 0.0f;
        posicionarMarker();

        VerificandoPermiso();

    }

    //validando permisos de acceso a mylocation
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    VerificandoPermiso();
                } else {
                    finish();

                }
                return;
            }

        }
    }

    private void posicionarMarker() {
        if (!mapaCreado) {
            mapaCreado = true;
            LatLng posicion = new LatLng(lat, lng);
            if (marker == null) {
                try {
                    marker = mMap.addMarker(new MarkerOptions()
                            .position(posicion)
                            .title(global.marker)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_conductor)));
                } catch (Exception e) {

                }
            } else {
                try {
                    marker.setPosition(posicion);
                } catch (Exception e) {
                    //algo salio mal
                }
            }

            if (creadoMap) {
                try {
                    float myZoom = mMap.getCameraPosition().zoom;

                    CameraPosition cameraPosition = new CameraPosition.Builder().target(posicion).zoom(myZoom).bearing(bearing).build();
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    mMap.animateCamera(cameraUpdate, 500, this);
                } catch (Exception e) {
                    //algo salio mal
                }

            } else {
                try {
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(posicion).zoom(17.0f).bearing(bearing).build();
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    mMap.animateCamera(cameraUpdate, 1, this);
                    creadoMap = true;
                } catch (Exception e) {
                    //algo salio mal
                }
            }
        }
    }

    private void VerificandoPermiso() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //si el permiso no está habilitado, solicitamos el permiso
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        mMap.setMyLocationEnabled(true);

        runnable.run();
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

    Runnable runnable = new Runnable() {
        public void run() {
            if(!primeraUbicacion) {
                try {
                    Location loc = mMap.getMyLocation();
                    if(loc!=null) {
                        lat = loc.getLatitude();
                        lng = loc.getLongitude();
                        bearing = loc.getBearing();

                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("lat", String.valueOf(lat));
                        editor.putString("lng", String.valueOf(lng));
                        editor.putString("bearing", String.valueOf(bearing));
                        editor.commit();

                        primeraUbicacion = true;
                        if (ActivityCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }

                        mMap.setMyLocationEnabled(false);

                        posicionarMarker();

                        Intent sv = new Intent(ctx, servicioDisponible.class);
                        startService(sv);

                        runnableMap.run();
                    }
                }catch (Exception e){
                    //algo salio mal
                }

                handler.postDelayed(runnable, 100);
            }else{
                handler.removeCallbacks(runnable);
            }
        }
    };

    @Override
    public void onFinish() {
        mapaCreado = false;
    }

    @Override
    public void onCancel() {
        mapaCreado = false;
    }

}
