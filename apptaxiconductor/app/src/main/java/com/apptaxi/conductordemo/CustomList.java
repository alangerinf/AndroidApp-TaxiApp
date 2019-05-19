package com.apptaxi.conductordemo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] nameList;
    private final Integer[] imageId;
    public CustomList(Activity context,
                      String[] web, Integer[] imageId) {
        super(context, R.layout.lyt_item_menu, web);
        this.context = context;
        this.nameList = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.lyt_item_menu, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView1);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
        txtTitle.setText(nameList[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
