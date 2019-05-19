package com.apptaxi.conductordemo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrador on 13/07/2016.
 */
public class CustomServicios extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] clienteList;
    private final String[] direccionList;
    private final String[] calificaList;
    private final String[] precioList;
    public CustomServicios(Activity context,
                           String[] cliente, String[] direccion, String[] califica, String[] precio) {
        super(context, R.layout.mis_servicios_items, cliente);
        this.context = context;
        this.clienteList = cliente;
        this.direccionList = direccion;
        this.calificaList = califica;
        this.precioList = precio;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.mis_servicios_items, null, true);

        TextView txtCliente = (TextView) rowView.findViewById(R.id.txt_cliente);
        TextView txtDireccion = (TextView) rowView.findViewById(R.id.txt_direccion);
        TextView txtCalificacion = (TextView) rowView.findViewById(R.id.txt_calificacion);
        TextView txtPrecio = (TextView) rowView.findViewById(R.id.txt_precio);

        txtCliente.setText(clienteList[position]);
        txtDireccion.setText(direccionList[position]);
        txtCalificacion.setText(calificaList[position]);
        txtPrecio.setText(precioList[position]);

        ImageView img1 = (ImageView)rowView.findViewById(R.id.img1);
        ImageView img2 = (ImageView)rowView.findViewById(R.id.img2);
        ImageView img3 = (ImageView)rowView.findViewById(R.id.img3);
        ImageView img4 = (ImageView)rowView.findViewById(R.id.img4);
        ImageView img5 = (ImageView)rowView.findViewById(R.id.img5);

        img1.setVisibility(View.INVISIBLE);
        img2.setVisibility(View.INVISIBLE);
        img3.setVisibility(View.INVISIBLE);
        img4.setVisibility(View.INVISIBLE);
        img5.setVisibility(View.INVISIBLE);

        if(Integer.parseInt(calificaList[position])==1){
            img1.setVisibility(View.VISIBLE);
        }else if(Integer.parseInt(calificaList[position])==2){
            img1.setVisibility(View.VISIBLE);
            img2.setVisibility(View.VISIBLE);
        }else if(Integer.parseInt(calificaList[position])==3){
            img1.setVisibility(View.VISIBLE);
            img2.setVisibility(View.VISIBLE);
            img3.setVisibility(View.VISIBLE);
        }else if(Integer.parseInt(calificaList[position])==4){
            img1.setVisibility(View.VISIBLE);
            img2.setVisibility(View.VISIBLE);
            img3.setVisibility(View.VISIBLE);
            img4.setVisibility(View.VISIBLE);
        }else if(Integer.parseInt(calificaList[position])==5){
            img1.setVisibility(View.VISIBLE);
            img2.setVisibility(View.VISIBLE);
            img3.setVisibility(View.VISIBLE);
            img4.setVisibility(View.VISIBLE);
            img5.setVisibility(View.VISIBLE);
        }

        return rowView;
    }

}

