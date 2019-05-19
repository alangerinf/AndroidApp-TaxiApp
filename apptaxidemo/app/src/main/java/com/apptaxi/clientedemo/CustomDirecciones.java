package com.apptaxi.clientedemo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


/**
 * Created by Administrador on 04/06/2016.
 */
public class CustomDirecciones extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] direccionList;
    private final String[] urbaList;
    private final String[] refeList;
    private final String[] idList;
    private final String[] nroList;
    public CustomDirecciones(Activity context,
                             String[] web, String[] urba, String[] refe, String[] id, String[] nro) {
        super(context, R.layout.mis_direcciones_items, web);
        this.context = context;
        this.direccionList = web;
        this.urbaList = urba;
        this.refeList = refe;
        this.idList = id;
        this.nroList = nro;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.mis_direcciones_items, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt_direccion);
        TextView txtUrba = (TextView) rowView.findViewById(R.id.txt_urba);
        TextView txtRefe = (TextView) rowView.findViewById(R.id.txt_refe);
        TextView txtId = (TextView) rowView.findViewById(R.id.txt_id);

        txtTitle.setText(direccionList[position]);
        txtUrba.setText(nroList[position]);
        txtRefe.setText(refeList[position]);
        txtId.setText(idList[position]);

        return rowView;
    }
}
