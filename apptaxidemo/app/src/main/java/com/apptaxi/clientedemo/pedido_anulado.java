package com.apptaxi.clientedemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


/**
 * Created by Administrador on 13/09/2017.
 */
public class pedido_anulado extends AppCompatActivity {
    Context ctx;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedido_anulado);
        ctx = this;

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);

        Button btn_aceptar = (Button)findViewById(R.id.btn_aceptar);
        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
