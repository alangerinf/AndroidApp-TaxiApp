package com.apptaxi.clientedemo;
import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
/**
 * Created by Administrador on 11/09/2017.
 */

public class global {

    public static String UrlServer = "http://gpsjnisi.com/";
    public static String nameSesion = "MySession";
    public static String nameEmpresa = "AppTaxi";
    public static String marker = "AppTaxi";
    public static double latInicio = -8.1190123;
    public static double lngInicio = -79.0362434;
    public static String NosePudo = "No se pudo concretar la operación";
    public static String NoPrecisa = "No precisa la dirección";
    public static String BuscandoDireccion = "Buscando direccion...";
    public static String AnularServicio = "¿Seguro que desea anular el servicio de taxi?";
    public static String SinUnidadescerca = "No podemos ubicar una móvil cercano con Aplicativo ¿Desea que la base operadora se comunique y te envie un taxi a esta dirección?";
    public static String NoRespondieron = "Ningún conductor ha respondido ¿Desea que la base operadora se comunique y te envie un taxi a esta dirección?";
    public static String SeSolicitoTaxi = "Se solicitó un taxi, en breve recibirá una respuesta";
    public static String NuevaVersion = "Existe una nueva version de APP, ¿desea descargarlo ahora?";
    public static String NoHayConexion = "No podemos conectarnos con nuestra central, verifique la conexión de internet";

    public static final String ApiKey = "AIzaSyDWcWMkt18v2b-_ctjztPPUDNo0n93Y8Zw";

    public static  String path = "apptaxi";

    public static String UL000V = path+"/comun/version.php"; //pruebas
    public static String UL0001 = path+"/cliente/login.php"; //login
    public static String UL0002 = path+"/cliente/pedido_guardar.php";
    public static String UL0002_1 = path+"/cliente/pedido_guardar_base.php";
    public static String UL0003 = path+"/cliente/pedido_anular.php";
    public static String UL0004 = path+"/cliente/pedido_verificarEstado.php";
    public static String UL0005 = path+"/cliente/iniciar_transporte.php";
    public static String UL0006 = path+"/cliente/pedido_evaluar.php";

    public static String UL0007 = path+"/cliente/cliente_update_token.php";
    public static String UL0008 = path+"/cliente/cambiar_contrasenia.php"; //
    public static String UL0009 = path+"/cliente/cliente_cargar_bono.php"; //

    public static String UL0010 = path+"/cliente/mis_servicios_cliente.php"; //
    public static String UL0011 = path+"/cliente/mis_servicios_cliente_ruta.php"; //
    public static String UL0012 = path+"/cliente/mis_direcciones.php"; //
    public static String UL0013 = path+"/cliente/mis_direcciones_guardar.php"; //
    public static String UL0014 = path+"/cliente/pedido_guardar_dire.php";

    public static String UL0015 = path+"/comun/cargar_precios_tarifario_turistico.php"; //
    public static String UL0016 = path+"/comun/cargar_zona_origenes.php"; //
    public static String UL0017 = path+"/comun/cargar_zona_turistica.php"; //
    public static String UL0018 = path+"/comun/cargar_precios_tarifario_aeropuerto.php"; //

    public static String UL0019 = path+"/comun/cargar_modulo_base.php"; //
    public static String UL0020 = path+"/comun/cargar_modulo_base_corp.php"; //

    public static String UL0021 = path+"/cliente/registro_paso1.php"; //
    public static String UL0022 = path+"/cliente/registro_paso2.php"; //
    public static String UL0023 = path+"/cliente/registro_paso3.php"; //
    public static String UL0024 = path+"/cliente/registro_reenviar_codigo.php"; //

    public static String UL0025 = path+"/cliente/recuperar_contrasenia.php"; //

    public static String UL0026 = path+"/cliente/cargar_unidades_cercanas.php"; //

    public static String UL0027 = path+"/cliente/actualizar_telefono.php"; //

    public static String UL0200 = path+"/cliente/pedir_varias_unidades.php";//pedimos  varias  unidades sin guardar
    public static String UL0102 = path+"/cliente/pedido_multiple_guardar.php";

    public static void ShowToast(Context ctx, String text){
        Context context = ctx;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public static void animateMarker(final Marker marker, final LatLng toPosition, final boolean hideMarker, GoogleMap mMap) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;
        final Interpolator interpolator = new LinearInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }


}