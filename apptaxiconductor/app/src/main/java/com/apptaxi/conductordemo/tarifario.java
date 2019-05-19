package com.apptaxi.conductordemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrador on 08/09/2017.
 */
public class tarifario extends AppCompatActivity {

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarifario);
        ctx = this;

        Button btn_urbano = (Button)findViewById(R.id.btn_urbano);
        Button btn_aeropuerto = (Button)findViewById(R.id.btn_aeropuerto);
        Button btn_turistico = (Button)findViewById(R.id.btn_turistico);

        btn_urbano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, tarifario_urbano.class);
                startActivity(i);
            }
        });

        btn_aeropuerto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, tarifario_aeropuerto.class);
                startActivity(i);
            }
        });

        btn_turistico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, tarifario_turistico.class);
                startActivity(i);
            }
        });
    }
}
