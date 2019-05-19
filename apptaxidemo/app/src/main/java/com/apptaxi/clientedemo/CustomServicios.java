package com.apptaxi.clientedemo;

import android.app.Activity;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import com.apptaxi.clientedemo.app.AppController;

import java.io.File;

/**
 * Created by Administrador on 15/06/2016.
 */
public class CustomServicios extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] conductorList;
    private final String[] vehiculoList;
    private final String[] direccionList;
    private final String[] idList;
    private final String[] fechaList;
    private final String[] fotoList;
    private final String[] dniList;
    private final String[] estadoList;
    public CustomServicios(Activity context,
                           String[] conductor, String[] vehiculo, String[] direccion, String[] id,
                           String[] fecha, String[] foto, String[] dni, String[] estado) {
        super(context, R.layout.mis_servicios_items, conductor);
        this.context = context;
        this.conductorList = conductor;
        this.vehiculoList = vehiculo;
        this.direccionList = direccion;
        this.idList = id;
        this.fechaList = fecha;
        this.fotoList = foto;
        this.dniList = dni;
        this.estadoList = estado;
    }

    int i=0;
    private String root = Environment.getExternalStorageDirectory().toString();
    private File myDir = new File(root + "/com.newtakci/images");
    //TextView txt_conductor,txt_marca,txt_direccion,txtId,txt_fecha,txt_foto;
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView= inflater.inflate(R.layout.mis_servicios_items, null, true);
        TextView txt_conductor = (TextView) rowView.findViewById(R.id.txt_conductor);
        TextView txt_vehiculo = (TextView) rowView.findViewById(R.id.txt_marca);
        TextView txt_direccion = (TextView) rowView.findViewById(R.id.txt_direccion);
        TextView txtId = (TextView) rowView.findViewById(R.id.txt_id);
        TextView txt_fecha = (TextView) rowView.findViewById(R.id.txt_fecha);
        TextView txt_foto = (TextView)rowView.findViewById(R.id.txt_foto);
        TextView txt_dni = (TextView)rowView.findViewById(R.id.txt_dni);
        TextView txt_estado = (TextView)rowView.findViewById(R.id.txt_estado);
        TextView txt_condicion = (TextView)rowView.findViewById(R.id.txt_condicion);

        txt_conductor.setText(conductorList[position]);
        txt_vehiculo.setText(vehiculoList[position]);
        txt_direccion.setText(direccionList[position]);
        txtId.setText(idList[position]);
        txt_fecha.setText(fechaList[position]);
        txt_foto.setText(fotoList[position]);
        txt_dni.setText(dniList[position]);
        txt_estado.setText(estadoList[position]);
        int condicion = Integer.parseInt(estadoList[position]);
        String cond="";
        if(condicion==0) {
            cond = "ESPERANDO";
        }else if(condicion==1){
            cond = "ACEPTADO";
        }else if(condicion==2){
            cond = "EN LA UBICACIÓN";
        }else if(condicion==3){
            cond = "TRANSPORTANDO";
        }else if(condicion==5){
            cond = "FINALIZADO";
        }else if(condicion==7){
            cond = "ANULADO POR CONDUCTOR";
        }else if(condicion==2){
            cond = "ANULADO POR USTED";
        }
        txt_condicion.setText(cond);

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        final NetworkImageView foto = (NetworkImageView)rowView.findViewById(R.id.imageView1);
        foto.setImageUrl(global.UrlServer+fotoList[position], imageLoader);

        return rowView;
    }

}

