package com.apptaxi.conductordemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.apptaxi.conductordemo.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrador on 29/08/2017.
 */
public class servicio_app_aceptado extends FragmentActivity implements OnMapReadyCallback, GoogleMap.CancelableCallback{
    Context ctx;
    SharedPreferences pref;
    int id_pedido,id_usuario,estado_pedido;
    double lat, lng,latPedido,lngPedido,latDestino,lngDestino;
    float bearing;
    String cliente,direccion,numero,referencia,destino,api_key;
    private GoogleMap mMap;

    boolean mapaCreado = false,finalizadoMapa=true;
    Marker marker_pedido, marker_conductor;
    final Handler handler = new Handler();

    TextView txt_distancia;

    Dialog dialog,dialog_anular;
    ListView lv;

    Integer[] imageId = {
            R.drawable.marker_iniciar,
            R.drawable.sms,
            R.drawable.marker_ubicacion,
            R.drawable.marker_no_ubica
    };
    String[] nameList = {
            "Iniciar el transporte",
            "Llego en unos minutos.",
            "Estoy en la ubicación",
            "Anular el servicio"
    };

    ProgressDialog progress;

    SoundManager sound;
    int sonido_anulado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.servicio_app_aceptado);
        ctx = this;

        sound = new SoundManager(getApplicationContext());
        sonido_anulado = sound.load(R.raw.servicio_anulado);

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        //progress.setMessage("Iniciando sessión");

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        id_pedido = pref.getInt("id_pedido",0);
        id_usuario = pref.getInt("id_usuario",0);
        lat = Double.parseDouble(pref.getString("lat",String.valueOf(global.latInicio)));
        lng = Double.parseDouble(pref.getString("lng",String.valueOf(global.latInicio)));
        bearing = Float.parseFloat(pref.getString("bearing","0.0f"));
        latPedido = Double.parseDouble(pref.getString("latPedido",String.valueOf(global.latInicio)));
        lngPedido = Double.parseDouble(pref.getString("lngPedido",String.valueOf(global.latInicio)));
        cliente = pref.getString("cliente",null);
        direccion = pref.getString("direccion",null);
        numero = pref.getString("numero",null);
        referencia = pref.getString("referencia",null);
        latDestino = Double.parseDouble(pref.getString("latDestino","0.0"));
        lngDestino = Double.parseDouble(pref.getString("lngDestino","0.0"));
        destino = pref.getString("destino",null);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txt_distancia = (TextView)findViewById(R.id.txt_distancia);

        TextView txt_direccion = (TextView)findViewById(R.id.txt_direccion);
        TextView txt_cliente = (TextView)findViewById(R.id.txt_cliente);
        TextView txt_referencia = (TextView)findViewById(R.id.txt_referencia);
        TextView txt_destino = (TextView)findViewById(R.id.txt_destino);

        txt_direccion.setText(direccion+" "+numero);
        txt_cliente.setText(cliente);
        txt_referencia.setText(referencia);
        txt_destino.setText(destino);

        ImageButton btn_ruta = (ImageButton)findViewById(R.id.btn_ruta);
        btn_ruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+latPedido+","+lngPedido);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        ImageButton btn_sms = (ImageButton)findViewById(R.id.btn_sms);
        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDialogOpciones();
            }
        });
    }

    public void OpenDialogOpciones() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.servicio_app_aceptado_opciones);
        //dialog.setTitle("Elige un motivo");

        lv = (ListView) dialog.findViewById(R.id.list_opciones);
        CustomOpciones adapter = new CustomOpciones(this, nameList, imageId);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(position==3) {
                    openDialogAnular();
                }else{
                    Notificar(position);
                }
            }
        });
        dialog.show();
    }

    int i;
    private void Notificar(int position){
        String mensaje = nameList[position];

        if(position==0) {
            final AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setIcon(R.drawable.ic_launcher);
            alertDialog.setTitle(global.nameEmpresa);
            alertDialog.setMessage("¿El cliente ya subió y va iniciar el transporte?");
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    IniciarTransporte();
                }
            });
            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }else if(position==1){
            //envio de  mensaje
            enviarSMSYaEstoyLLegando();
            dialog.dismiss();
        }else if(position==2){
            EstoyEnLaubicacion();
        }
    }

    private void enviarSMSYaEstoyLLegando() {
        global.ShowToast(ctx,"enviando");
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0025,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                global.ShowToast(ctx,"MSM Enviado");
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
                global.ShowToast(ctx,global.NosePudo);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_cliente", String.valueOf(pref.getInt("id_cliente",0)));
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

    private void EstoyEnLaubicacion() {
        progress.setMessage("Aplicando el servicio");
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0007,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        dialog.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                global.ShowToast(ctx,"El cliente fue alertado");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog_anular.dismiss();
                global.ShowToast(ctx,global.NosePudo);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_pedido", String.valueOf(id_pedido));

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

    private void IniciarTransporte() {
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0006,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putInt("conectado", 3);
                                editor.commit();

                                Intent i = new Intent(ctx, servicio_app_transportando.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog_anular.dismiss();
                global.ShowToast(ctx,global.NosePudo);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_pedido", String.valueOf(id_pedido));

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

    public void openDialogAnular(){
        dialog_anular = new Dialog(this);
        dialog_anular.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_anular.setContentView(R.layout.confirmar_anular_servicio);
        //dialog.setTitle("Elige un motivo");

        Button btn_anular = (Button)dialog_anular.findViewById(R.id.btn_anular);
        btn_anular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnularServicioNoUbicado();
            }
        });

        Button btn_regresar = (Button)dialog_anular.findViewById(R.id.btn_regresar);
        btn_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_anular.dismiss();
            }
        });
        dialog_anular.show();
    }

    private  void removersesion(){
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

    private void AnularServicioNoUbicado() {
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0005,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog_anular.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                removersesion();

                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog_anular.dismiss();
                global.ShowToast(ctx,global.NosePudo);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_pedido", String.valueOf(id_pedido));

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
        handler.removeCallbacks(runnableMap);
        handler.removeCallbacks(runnablePintar);
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
            LatLng posicionCliente, posicionConductor;
            if (!mapaCreado) {
                try {
                    posicionCliente = new LatLng(latPedido, lngPedido);
                    marker_pedido = mMap.addMarker(new MarkerOptions()
                            .position(posicionCliente)
                            .title(global.marker)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_cliente)));

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

                    ConsultarApiKey();

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
            estado_pedido = pref.getInt("estado_pedido",0);
            if(estado_pedido>5) {

                removersesion();
                sound.play(sonido_anulado);
                Intent i = new Intent(ctx, servicio_app_anulado.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();

            }else {

                if(estado_pedido==3){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("conectado", 3);
                    editor.commit();

                    Intent i = new Intent(ctx, servicio_app_transportando.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }else {
                    posicionarMarker();

                    handler.postDelayed(runnableMap, 1000);
                }
            }
        }
    };

    Runnable runnablePintar = new Runnable() {
        @Override
        public void run() {
            LatLng positionConductor = new LatLng(lat,lng);
            LatLng positionCliente = new LatLng(latPedido,lngPedido);

            String url = getUrl(positionConductor, positionCliente);
            FetchUrl FetchUrl = new FetchUrl();
            FetchUrl.execute(url);

            handler.postDelayed(runnablePintar, 30000);
        }
    };

    private void pintarDireccion(){
        LatLng positionConductor = new LatLng(lat,lng);
        LatLng positionCliente = new LatLng(latPedido,lngPedido);

        String url = getUrl(positionConductor, positionCliente);
        FetchUrl FetchUrl = new FetchUrl();
        FetchUrl.execute(url);
    }
    private void ConsultarApiKey() {
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0004,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                api_key = data.getString("api_key");
                                pintarDireccion();
                                runnablePintar.run();
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
                params.put("id", "2");

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
    //////////////paint draw

    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        String key = "key=" + api_key;

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            //Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            //Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                //Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                //Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    Polyline linea1=null,linea2=null;
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                //Log.d("ParserTask",jsonData[0].toString());
                //DataParser parser = new DataParser();
                DirectionsJSONParser parser = new DirectionsJSONParser();
                //Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                //Log.d("ParserTask","Executing routes");
                //Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                //Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;
            String distance = "";
            String duration = "";

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    if(j==0){	// Get distance from the list
                        distance = (String)point.get("distance");
                        continue;
                    }else if(j==1){ // Get duration from the list
                        duration = (String)point.get("duration");
                        continue;
                    }

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(15);
                int myColor = Color.rgb(129,190,247);
                lineOptions.color(myColor);
                //lineOptions.color(Color.CYAN);

                //Log.d("onPostExecute","onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                txt_distancia.setText(distance+" - "+duration);
                //txt_tiempo.setText(duration);
                if(linea1==null) {
                    linea1 = mMap.addPolyline(lineOptions);
                    if(linea2!=null){
                        linea2.remove();
                        linea2 = null;
                    }
                }else{
                    linea2 = mMap.addPolyline(lineOptions);
                    linea1.remove();
                    linea1 = null;
                }
            }
            else {
                //Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }


    //////////////paint draw fin
}
