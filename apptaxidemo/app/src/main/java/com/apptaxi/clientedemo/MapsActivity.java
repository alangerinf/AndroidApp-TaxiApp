package com.apptaxi.clientedemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

import com.apptaxi.clientedemo.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.CancelableCallback {
    Context ctx;
    private GoogleMap mMap;
    double lat, lng, latInicio, lngInicio;

    final Handler handler = new Handler();

    ImageView marker_pedir;
    TextView txt_direccion,txt_tiempo;
    ImageButton btn_pedir, btn_cancelar,btn_mylocation;
    SharedPreferences pref;

    RelativeLayout relativeLayout_transparente;

    ProgressDialog progress;

    ///////////////////////
    ListView lv;
    //Integer[] imageList = {R.drawable.op_1, R.drawable.op_4, R.drawable.op_3, R.drawable.op_7, R.drawable.op_8, R.drawable.op_6};
    Integer[] imageList = {R.drawable.op_1, R.drawable.op_4, R.drawable.op_3, R.drawable.op_8, R.drawable.op_6};
    //String[] nameList = {"Historial de servicios", "Mis direcciones favoritas", "Cambiar contraseña", "Promociones", "Tarifario", "Cerrar sesión"};
    String[] nameList = {"Historial de servicios", "Mis direcciones favoritas", "Cambiar contraseña", "Tarifario", "Cerrar sesión"};

    ImageButton btn_opciones;
    private RelativeLayout mDrawerRelativeLayout;
    private DrawerLayout drawerLayout;
    ///////////////////

    String direccion, numero;

    //opciones de cargar unidades
    int cantidad = 0;

    double[] latidud, longitud;
    float[] bearing;
    String[] nro, mExiste, tipo;
    String unidades;
    Marker m0=null,m1=null,m2=null,m3=null,m4=null,m5=null,m6=null,m7=null,m8=null,m9=null,m10=null;
    Marker m11=null,m12=null,m13=null,m14=null,m15=null,m16=null,m17=null,m18=null,m19=null;
    /////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_maps);
        ctx = this;

        progress = new ProgressDialog(this);
        progress.setCancelable(false);

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);

        relativeLayout_transparente = (RelativeLayout) findViewById(R.id.relativeLayout_transparente);

        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txt_tiempo = (TextView)findViewById(R.id.txt_tiempo);

        marker_pedir = (ImageView) findViewById(R.id.marker_pedir);
        txt_direccion = (TextView) findViewById(R.id.txt_direccion);
        btn_pedir = (ImageButton) findViewById(R.id.btn_pedir);
        btn_pedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = pref.edit();
                editor.putString("direccion", direccion);
                editor.putString("numero", numero);
                editor.putString("lat", String.valueOf(lat));
                editor.putString("lng", String.valueOf(lng));
                editor.commit();

                Intent i = new Intent(ctx, personalizar_pedido.class);
                startActivityForResult(i, 1);
            }
        });

        btn_cancelar = (ImageButton) findViewById(R.id.btn_cancelar);
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

        runnableToken.run();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerRelativeLayout = (RelativeLayout) findViewById(R.id.drawer_left);

        CreateMenu();

        btn_opciones = (ImageButton) findViewById(R.id.btn_opciones);
        btn_opciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(mDrawerRelativeLayout);

            }
        });


        TextView txt_usuario = (TextView) findViewById(R.id.txt_usuario);
        txt_usuario.setText(pref.getString("usuario", "-"));

        btn_mylocation = (ImageButton)findViewById(R.id.btn_mylocation);
        btn_mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerificandoPermiso();
            }
        });
    }

    private void CreateMenu() {
        CustomList adapter = new CustomList(MapsActivity.this, nameList, imageList);
        lv = (ListView) findViewById(R.id.left_drawer);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selectItem(position);
            }
        });
    }

    private void selectItem(int position) {
        lv.setItemChecked(position, true);
        drawerLayout.closeDrawer(mDrawerRelativeLayout);
        if (position == 0) {
            Intent i = new Intent(this, mis_servicios.class);
            startActivity(i);
        } else if (position == 1) {
            Intent i = new Intent(this, mis_direcciones.class);
            startActivityForResult(i, 1);
        } else if (position == 2) {
            Intent i = new Intent(this, cambiar_contrasenia.class);
            startActivityForResult(i, 2);
        //} else if (position == 3) {
          //  Intent i = new Intent(this, promociones.class);
            //startActivity(i);
        } else if (position == 3) {
            //Intent i = new Intent(this, tarifario.class);
            //startActivity(i);
            Intent i = new Intent(ctx, tarifario_urbano.class);
            startActivity(i);
        } else if (position == 4) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Seguro que desea salir del aplicativo de " + global.nameEmpresa)
                    .setCancelable(false)
                    .setPositiveButton("Cerrar sesión",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.remove("id_usuario");
                                    editor.commit();

                                    Intent i = new Intent(ctx, opcion_inicio.class);
                                    i.putExtra("activar", 0);
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

    Runnable runnableToken = new Runnable() {
        @Override
        public void run() {
            try {
                if (pref.contains("tokenid")) {
                    //if(pref.getBoolean("actualizarToken",false)) {

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("actualizarToken", false);
                    editor.commit();

                    ActualizarTokenID();
                    //}
                    handler.removeCallbacks(runnableToken);
                } else {

                    handler.postDelayed(runnableToken, 1000);
                }
            } catch (Exception e) {
                handler.postDelayed(runnableToken, 1000);
            }
        }
    };

    private void ActualizarTokenID() {
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0007,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if (success == 1) {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_usuario", String.valueOf(pref.getInt("id_usuario", 0)));
                params.put("tokenid", pref.getString("tokenid", "-"));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(sr);
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
                            if (success == 1) {

                                SharedPreferences.Editor editor = pref.edit();
                                editor.remove("id_pedido");
                                editor.commit();

                                disableInicio(true);
                                stopService(new Intent(getBaseContext(), servicioPedido.class));
                            } else {
                                global.ShowToast(ctx, global.NosePudo);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                global.ShowToast(ctx, global.NosePudo);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_pedido", String.valueOf(pref.getInt("id_pedido", 0)));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(sr);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                disableInicio(false);
            } else if (requestCode == 2) {
                finish();
            }
        }
    }

    private void disableInicio(boolean opcion) {
        if (opcion) {
            relativeLayout_transparente.setVisibility(View.INVISIBLE);
            mMap.getUiSettings().setScrollGesturesEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setZoomGesturesEnabled(true);
            marker_pedir.setVisibility(View.VISIBLE);
            btn_pedir.setVisibility(View.VISIBLE);
            runnable_coor.run();
        } else {
            //cuando hay pedido
            handler.removeCallbacks(runnable_coor);

            Intent sv = new Intent(ctx, servicioPedido.class);
            startService(sv);

            relativeLayout_transparente.setVisibility(View.VISIBLE);
            mMap.getUiSettings().setScrollGesturesEnabled(false);
            mMap.getUiSettings().setZoomControlsEnabled(false);
            mMap.getUiSettings().setZoomGesturesEnabled(false);
            marker_pedir.setVisibility(View.INVISIBLE);
            btn_pedir.setVisibility(View.INVISIBLE);

            runnableEstado.run();
        }
    }


    int intento=0;
    Runnable runnableEstado = new Runnable() {
        @Override
        public void run() {
            if (pref.getInt("estado", 0) >= 1) {
                handler.removeCallbacks(runnableEstado);
                finish();
            } else {
                txt_tiempo.setText("0:"+(30-intento));
                intento++;
                if(intento<=30) {
                    handler.postDelayed(runnableEstado, 1000);
                }else{
                    handler.removeCallbacks(runnableEstado);
                    final AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                    alertDialog.setIcon(R.drawable.ic_launcher);
                    alertDialog.setTitle(global.nameEmpresa);
                    alertDialog.setMessage(global.NoRespondieron);
                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Si, Envíenme", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                            PedirTaxiBase(pref.getInt("id_pedido",0),1);
                        }
                    });
                    alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No, Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                            PedirTaxiBase(pref.getInt("id_pedido",0),0);
                        }
                    });
                    alertDialog.show();
                }
            }
        }
    };

    private void PedirTaxiBase(final int id, final int operacion) {
        progress.setMessage("Enviando datos...");
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0002_1,
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

                                disableInicio(true);
                                stopService(new Intent(getBaseContext(), servicioPedido.class));

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
                params.put("id_pedido", String.valueOf(id));
                params.put("operacion", String.valueOf(operacion));

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnableEstado);
        handler.removeCallbacks(runnable_coor);
    }

    private String TAG = MapsActivity.class.getSimpleName();

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG,"ONMAP READY");
        mMap = googleMap;

        lat = -8.1190123;
        lng = -79.0362434;
        posicionarMarker();

        VerificandoPermiso();
        Log.d(TAG,"INCIANDO CAMARA");

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                try {
                    if (mMap.getCameraPosition().target != null) {
                        isDrag(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
                    } else {
                        txt_direccion.setText(global.NoPrecisa);
                    }
                } catch (Exception e) {
                    //algo salio mal
                    txt_direccion.setText(global.NoPrecisa);
                }
            }
        });
        Log.d(TAG,"INCIANDO CAMARA MOVE");

        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                btn_pedir.setVisibility(View.INVISIBLE);
                txt_direccion.setText(global.BuscandoDireccion);
            }
        });

        if (pref.contains("id_pedido")) {
            disableInicio(false);
        }

        if(pref.getInt("version",1)<2){
            Intent i = new Intent(ctx, actualizar_telefono.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }

    private void isDrag(double cor_lat, double cor_lng) {
        lat = cor_lat;
        lng = cor_lng;
        BuscarDireccion();
    }

    private void posicionarMarker() {
        LatLng posicion = new LatLng(lat, lng);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(posicion).zoom(16.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cameraUpdate, 500, this);
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

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Location loc = mMap.getMyLocation();
                if (loc != null) {
                    lat = loc.getLatitude();
                    lng = loc.getLongitude();

                    latInicio = lat;
                    lngInicio = lng;

                    Log.d("MAPS: ","lat: "+latInicio);
                    Log.d("MAPS: ","lon: "+lngInicio);


                    if (ActivityCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mMap.setMyLocationEnabled(false);

                    posicionarMarker();

                    txt_direccion.setText(global.BuscandoDireccion);
                    BuscarDireccion();
                    runnable_coor.run();

                    handler.removeCallbacks(runnable);
                } else {
                    handler.postDelayed(runnable, 100);
                }
            } catch (Exception e) {
                //algo salio mal
                handler.postDelayed(runnable, 100);
            }
        }
    };

    private void BuscarDireccion() {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&key=" + global.ApiKey;
        Log.d(TAG,"buscando: "+url);
        JsonObjectRequest sr = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            JSONObject zero = results.getJSONObject(0);
                            JSONArray address_components = zero.getJSONArray("address_components");

                            direccion = global.NoPrecisa;
                            int procede = 0;

                            for (int i = 0; i < address_components.length(); i++) {
                                JSONObject zero2 = address_components.getJSONObject(i);
                                String long_name = zero2.getString("long_name");
                                JSONArray mtypes = zero2.getJSONArray("types");
                                String Type = mtypes.getString(0);

                                if (Type.equalsIgnoreCase("street_number")) {
                                    numero = long_name;
                                }

                                if (Type.equalsIgnoreCase("route")) {
                                    direccion = long_name;
                                    procede = 1;
                                }
                            }

                            txt_direccion.setText(direccion + " " + numero);
                            if (procede == 1) {
                                btn_pedir.setVisibility(View.VISIBLE);
                                CargarUnidades();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG,"jsonE: "+e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txt_direccion.setText(global.NoPrecisa);
                Log.d(TAG,"voleyE "+error.toString());
            }
        });

        AppController.getInstance().addToRequestQueue(sr);
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

    @Override
    public void onFinish() {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onBackPressed() {
        if (!pref.contains("id_pedido")) {
            finish();
        }
    }

    Runnable runnable_coor = new Runnable() {
        public void run() {
            CargarUnidades();
            handler.postDelayed(runnable_coor, 10000);
        }
    };


    private void CargarUnidades() {
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0026,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //ShowToast(response.toString());
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if (success == 1) {
                                unidades = data.getString("unidades");
                                //mMap.clear();
                                String taxis[] = unidades.split("--");
                                //asignacion por taxis  al array con sus campos
                                cantidad = taxis.length;
                                latidud = new double[cantidad];
                                longitud = new double[cantidad];
                                tipo = new String[cantidad];
                                bearing = new float[cantidad];
                                nro = new String[cantidad];
                                mExiste = new String[cantidad];
                                //recorremos el array
                                //solo es verificacopm
                                //verificacion y asignacion dentro del VERIFICAREXISTEMARKER
                                for (int i = 0; i < taxis.length; i++) {
                                    String coor[] = taxis[i].split(",");//diividimos por partes el array para sacar los campos q  estan divididos por ,s
                                    if (coor[1] != null && coor[2] != null) {
                                        //LatLng ltln = new LatLng(Double.parseDouble(coor[1]), Double.parseDouble(coor[2]));//sacamos las  coordenadas
                                        //Float bearing = 0.0f;//establecemos  un bearing  0 de  default
                                        String tipo = "Premium";
                                        //if (coor[3] != null) {
                                        //    bearing = Float.parseFloat(coor[3]);//si cambiamos el vearig por el corresponfiante
                                        //}
                                        if (coor[5] != null) {
                                            tipo =  coor[5];//si cambiamos el vearig por el corresponfiante
                                        }

                                            VerificarExisteMarker(i, Double.parseDouble(coor[1]), Double.parseDouble(coor[2]), coor[0], 0.0f,tipo);

                                    }
                                }
                                //1978

                                //una vez verificado, reeemplazamos
                                String[] reemplazado;
                                reemplazado = new String[cantidad];
                                for (int i = 0; i < cantidad; i++) {
                                    if (mExiste[i].toString() == "0") {
                                        for (int j = 0; j < cantidad; j++) {
                                            if (!useSet(mExiste, String.valueOf(j)) && !useSet(reemplazado, String.valueOf(j))) {
                                                LatLng latLng = new LatLng(latidud[i], longitud[i]);
                                               // global.ShowToast(ctx,tipo[i]);
                                                AsignarMarker(j, latLng, bearing[i], nro[i],tipo[i]);
                                                reemplazado[i] = String.valueOf(j);
                                                break;
                                            }
                                        }
                                    }
                                }

                            } else {
                                //ShowToast("No hay unidades cercanas a tu posición");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("lat", String.valueOf(lat));
                params.put("lng", String.valueOf(lng));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(sr);
    }

    public static boolean useSet(String[] arr, String targetValue) {
        Set<String> set = new HashSet<String>(Arrays.asList(arr));
        return set.contains(targetValue);
    }

    private void VerificarExisteMarker(int i,double latidudP,double longitudP,String nroP,float bearingP, String tipoP){
        int v = titleMarker(nroP);
        if(v>=0) {
            LatLng latLng = new LatLng(latidudP, longitudP);
            AsignarMarker(i, latLng, bearingP, nroP,tipoP);
            mExiste[i]=String.valueOf(v);
            latidud[i]=latidudP;
            longitud[i]=longitudP;
            bearing[i]=bearingP;
            nro[i]=nroP;
            tipo[i]=tipoP;
        }else{
            mExiste[i]=String.valueOf(v);
            latidud[i]=latidudP;
            longitud[i]=longitudP;
            bearing[i]=bearingP;
            nro[i]=nroP;
            tipo[i]=tipoP;
        }
    }


    private boolean checkTittleMarker(String nro){
        boolean flag = false;
        if((m0!=null && m0.getTitle().toString().equals(nro))
        ||(m1!=null && m1.getTitle().toString().equals(nro))
        ||(m2!=null && m2.getTitle().toString().equals(nro))
        ||(m3!=null && m3.getTitle().toString().equals(nro))
        ||(m4!=null && m4.getTitle().toString().equals(nro))
        ||(m5!=null && m5.getTitle().toString().equals(nro))
        ||(m6!=null && m6.getTitle().toString().equals(nro))
        ||(m7!=null && m7.getTitle().toString().equals(nro))
        ||(m8!=null && m8.getTitle().toString().equals(nro))
        ||(m9!=null && m9.getTitle().toString().equals(nro))
        ||(m10!=null && m10.getTitle().toString().equals(nro))
        ||(m11!=null && m11.getTitle().toString().equals(nro))
        ||(m12!=null && m12.getTitle().toString().equals(nro))
        ||(m13!=null && m13.getTitle().toString().equals(nro))
        ||(m14!=null && m14.getTitle().toString().equals(nro))
        ||(m15!=null && m15.getTitle().toString().equals(nro))
        ||(m16!=null && m16.getTitle().toString().equals(nro))
        ||(m17!=null && m17.getTitle().toString().equals(nro))
        ||(m18!=null && m18.getTitle().toString().equals(nro))
        ||(m19!=null && m19.getTitle().toString().equals(nro))){
            flag = true;
        }
        return flag;
    }

    private int titleMarker(String nro){
        int v=0;
        if(m0!=null && m0.getTitle().toString().equals(nro)){
            v=0;
        }else if(m1!=null && m1.getTitle().toString().equals(nro)){
            v=1;
        }else if(m2!=null && m2.getTitle().toString().equals(nro)){
            v=2;
        }else if(m3!=null && m3.getTitle().toString().equals(nro)){
            v=3;
        }else if(m4!=null && m4.getTitle().toString().equals(nro)){
            v=4;
        }else if(m5!=null && m5.getTitle().toString().equals(nro)){
            v=5;
        }else if(m6!=null && m6.getTitle().toString().equals(nro)){
            v=6;
        }else if(m7!=null && m7.getTitle().toString().equals(nro)){
            v=7;
        }else if(m8!=null && m8.getTitle().toString().equals(nro)){
            v=8;
        }else if(m9!=null && m9.getTitle().toString().equals(nro)){
            v=9;
        }else if(m10!=null && m10.getTitle().toString().equals(nro)){
            v=10;
        }else if(m11!=null && m11.getTitle().toString().equals(nro)){
            v=11;
        }else if(m12!=null && m12.getTitle().toString().equals(nro)){
            v=12;
        }else if(m13!=null && m13.getTitle().toString().equals(nro)){
            v=13;
        }else if(m14!=null && m14.getTitle().toString().equals(nro)){
            v=14;
        }else if(m15!=null && m15.getTitle().toString().equals(nro)){
            v=15;
        }else if(m16!=null && m16.getTitle().toString().equals(nro)){
            v=16;
        }else if(m17!=null && m17.getTitle().toString().equals(nro)){
            v=17;
        }else if(m18!=null && m18.getTitle().toString().equals(nro)){
            v=18;
        }else if(m19!=null && m19.getTitle().toString().equals(nro)){
            v=19;
        }
        return v;
    }

    private void AsignarMarker(int i, LatLng latLng, float bearing, String nro,String tipo){

        if(i==0){
            if(m0==null){
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }
                else{
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }

            }else{
                //m0.setRotation(bearing);
                m0.setTitle(nro);
                if("Premium".equals(tipo)){
                    m0.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m0.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m0,latLng,false,mMap);
            }
        }

        if(i==1){
            if(m1==null){
                //m1= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m1= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }else{   //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m1= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m1.setTitle(nro);
                if("Premium".equals(tipo)){
                    m1.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m1.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m1,latLng,false,mMap);
            }
        }

        if(i==2){
            if(m2==null){
                //m2= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m2= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }
                    else{
                //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m2= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m2.setTitle(nro);
                if("Premium".equals(tipo)){
                    m2.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m2.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m2,latLng,false,mMap);
            }
        }
        if(i==3){
            if(m3==null){
                //m3= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m3= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }else{   //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m3= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m3.setTitle(nro);
                if("Premium".equals(tipo)){
                    m3.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m3.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m3,latLng,false,mMap);
            }
        }
        if(i==4){
            if(m4==null){
                //m4= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m4= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }else{   //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m4= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m4.setTitle(nro);
                if("Premium".equals(tipo)){
                    m4.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m4.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m4,latLng,false,mMap);
            }
        }
        if(i==5){
            if(m5==null){
                //m5= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m5= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }else{   //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m5= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m5.setTitle(nro);
                if("Premium".equals(tipo)){
                    m5.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m5.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m5,latLng,false,mMap);
            }
        }
        if(i==6){
            if(m6==null){
                //m6= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m6= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }else{
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m6= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m6.setTitle(nro);
                if("Premium".equals(tipo)){
                    m6.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m6.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m6,latLng,false,mMap);
            }
        }
        if(i==7){
            if(m7==null){
                //m7= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m7= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }else{
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m7= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m7.setTitle(nro);
                if("Premium".equals(tipo)){
                    m7.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m7.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m7,latLng,false,mMap);
            }
        }
        if(i==8){
            if(m8==null){
                //m8= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m8= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }else{
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m8= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m8.setTitle(nro);
                if("Premium".equals(tipo)){
                    m8.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m8.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m8,latLng,false,mMap);
            }
        }
        if(i==9){
            if(m9==null){
                //m9= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m9= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }else{
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m9= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m9.setTitle(nro);
                if("Premium".equals(tipo)){
                    m9.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m9.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m9,latLng,false,mMap);
            }
        }
        if(i==10){
            if(m10==null){
                //m10= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m10= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }else{
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m10= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m10.setTitle(nro);
                if("Premium".equals(tipo)){
                    m10.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m10.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m10,latLng,false,mMap);
            }
        }
        if(i==11){
            if(m11==null){
                //m11= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m11= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }else{
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m11= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m11.setTitle(nro);
                if("Premium".equals(tipo)){
                    m11.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m11.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m11,latLng,false,mMap);
            }
        }
        if(i==12){
            if(m12==null){
                //m12= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m12= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }else{
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m12= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m12.setTitle(nro);
                if("Premium".equals(tipo)){
                    m12.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m12.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m12,latLng,false,mMap);
            }
        }
        if(i==13){
            if(m13==null){
                //m13= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m13= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }else{
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m13= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m13.setTitle(nro);
                if("Premium".equals(tipo)){
                    m13.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m13.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m13,latLng,false,mMap);
            }
        }
        if(i==14){
            if(m14==null){
                //m14= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerObearingptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m14= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }else{
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m14= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m14.setTitle(nro);
                if("Premium".equals(tipo)){
                    m14.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m14.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m14,latLng,false,mMap);
            }
        }
        if(i==15){
            if(m15==null){
                //m15= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m15= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }else{
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m15= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m15.setTitle(nro);
                if("Premium".equals(tipo)){
                    m15.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m15.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m15,latLng,false,mMap);
            }
        }
        if(i==16){
            if(m16==null){
                //m16= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m16= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }else{
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m16= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m16.setTitle(nro);
                if("Premium".equals(tipo)){
                    m16.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m16.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m16,latLng,false,mMap);
            }
        }
        if(i==17){
            if(m17==null){
                //m17= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m17= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }else{
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m17= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m17.setTitle(nro);
                if("Premium".equals(tipo)){
                    m17.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m17.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m17,latLng,false,mMap);
            }
        }
        if(i==18){
            if(m18==null){
                //m18= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m18= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }else{
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m18= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m18.setTitle(nro);
                if("Premium".equals(tipo)){
                    m18.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m18.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m18,latLng,false,mMap);
            }
        }
        if(i==19){
            if(m19==null){
                //m19= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                if("Premium".equals(tipo)){
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoPremium)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                    m19= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium)));
                }else{
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(bearing).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoClassic)));
                    m19= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic)));
                    //m0= mMap.addMarker(new MarkerOptions().position(latLng).title(nro).rotation(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.auto)));
                }
            }else{
                //m0.setRotation(bearing);
                m19.setTitle(nro);
                if("Premium".equals(tipo)){
                    m19.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autopremium));
                }else{
                    m19.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.autoclassic));
                }
                global.animateMarker(m19,latLng,false,mMap);
            }
        }
    }
}
