package com.apptaxi.conductordemo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrador on 03/06/2016.
 */
public class CustomOpciones extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] nameList;
    private final Integer[] imageId;
    public CustomOpciones(Activity context,
                          String[] web, Integer[] imageId) {
        super(context, R.layout.servicio_app_aceptado_opciones_items, web);
        this.context = context;
        this.nameList = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.servicio_app_aceptado_opciones_items, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView1);
        txtTitle.setText(nameList[position]);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
        imageView.setImageResource(imageId[position]);

        return rowView;
    }
}

