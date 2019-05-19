package com.apptaxi.conductordemo;

import android.content.Context;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrador on 23/08/2017.
 */
public class global {

    public static String UrlServer = "http://gpsjnisi.com/";
    public static String nameSesion = "MySession";
    public static String nameEmpresa = "App Taxi";
    public static String escaneando = "Escaneando servicios...";
    public static String marker = "Marker AppTaxi";
    public static double latInicio = -8.112213;
    public static double lngInicio = -79.028183;
    public static String NosePudo = "No se pudo concretar la operación";
    public static String FinalizarNoSePudoCalcular = "Algo sucedió y no pudo calcular el precio, Ingrese el importe a cobrar y finaliza el servicio";
    public static String NuevaVersion = "Existe una nueva version de APP, ¿desea descargarlo ahora?";
    public static String NoHayConexion = "No podemos conectarnos con nuestra central, veriique la conexión de internet";
    //urls
    public static String path = "apptaxi";
    public static String UL000V = path+"/comun/version.php"; //pruebas
    public static String UL0000 = path+"/conductor/prueba.php"; //pruebas
    public static String UL0001 = path+"/conductor/login.php"; //login de conductor
    public static String UL0002 = path+"/conductor/actualizarCoordenadas.php"; //actualizar coordenadas (servicioDisponible)
    public static String UL0003 = path+"/conductor/aceptar_pedido_app.php"; //login de conductor
    public static String UL0004 = path+"/conductor/consultar_apikey.php"; //
    public static String UL0005 = path+"/conductor/anular_servicio.php"; //
    public static String UL0006 = path+"/conductor/iniciar_transporte.php"; //
    public static String UL0007 = path+"/conductor/en_ubicacion.php"; //
    public static String UL0008 = path+"/conductor/cargar_recorrido_servicio_app.php"; //
    public static String UL0008_1 = path+"/conductor/cargar_recorrido_servicio_llamada.php"; //
    public static String UL0008_2 = path+"/conductor/cargar_recorrido_servicio_ambulatorio.php"; //
    public static String UL0009 = path+"/conductor/finalizar_servicio_app.php"; //
    public static String UL0009_1 = path+"/conductor/finalizar_servicio_llamada.php"; //
    public static String UL0009_2 = path+"/conductor/finalizar_servicio_ambulatorio.php"; //
    public static String UL0009_3 = path+"/conductor/desconectar_conductor.php";//
    public static String UL0010 = path+"/conductor/en_ubicacion_llamada.php"; //
    public static String UL0011 = path+"/conductor/aceptar_pedido_llamada.php"; //login de conductor
    public static String UL0012 = path+"/conductor/en_ubicacion_llamada_nuevo_dire.php"; //

    public static String UL0013 = path+"/conductor/mis_servicios_conductor.php"; //
    public static String UL0014 = path+"/conductor/cambiar_contrasenia.php"; //

    public static String UL0015 = path+"/comun/cargar_precios_tarifario_turistico.php"; //
    public static String UL0016 = path+"/comun/cargar_zona_origenes.php"; //
    public static String UL0017 = path+"/comun/cargar_zona_turistica.php"; //
    public static String UL0018 = path+"/comun/cargar_precios_tarifario_aeropuerto.php"; //

    public static String UL0019 = path+"/comun/cargar_modulo_base.php"; //
    public static String UL0020 = path+"/comun/cargar_modulo_base_corp.php"; //

    public static String UL0021 = path+"/conductor/mensajes_lista.php"; //
    public static String UL0022 = path+"/conductor/mensajes_conductor.php"; //

    public static String UL0023 = path+"/conductor/iniciar_servicio_ambulatorio.php"; //

    public static String UL0024 = path+"/conductor/cargar_datos_nuevo_pedido.php"; //

    public static String UL0025 = path+"/conductor/enviar_sms_estoy_llegando.php"; // encio de  mensaje q  ya esta llegando

    //fin urls

    //remover sesion de pedidos
    //servicio_app
    //servicio_app_aceptado
    //servicio_app_transportando

    public static void ShowToast(Context ctx,String text){
        Context context = ctx;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public static boolean HorarioNocturno(String inicio, String fin){
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
}
