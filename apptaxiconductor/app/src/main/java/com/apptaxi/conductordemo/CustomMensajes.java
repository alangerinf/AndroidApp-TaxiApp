package com.apptaxi.conductordemo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Administrador on 12/07/2016.
 */

public class CustomMensajes extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] asuntoList;
    private final String[] mensajeList;
    private final String[] nuevoList;
    private final String[] idList;

    public CustomMensajes(Activity context, String[] web, String[] mensaje, String[] nuevo, String[] id) {
        super(context, R.layout.mensajes_items, web);
        this.context = context;
        this.asuntoList = web;
        this.mensajeList = mensaje;
        this.nuevoList = nuevo;
        this.idList = id;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.mensajes_items, null, true);
        TextView txtAsunto = (TextView) rowView.findViewById(R.id.txt_asunto);
        TextView txtMensaje = (TextView) rowView.findViewById(R.id.txt_mensaje);
        TextView txtNuevo = (TextView) rowView.findViewById(R.id.txt_nuevo);
        TextView txtId = (TextView)rowView.findViewById(R.id.txt_id);
        txtAsunto.setText(asuntoList[position]);
        txtMensaje.setText(mensajeList[position]);
        txtId.setText(idList[position]);
        if(Integer.parseInt(nuevoList[position])==1) {
            txtNuevo.setVisibility(View.VISIBLE);
        }
        return rowView;
    }
}