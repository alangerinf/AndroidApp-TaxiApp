package com.apptaxi.conductordemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrador on 28/08/2017.
 */
public class fuera_servicio extends AppCompatActivity {
    SharedPreferences pref;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuera_servicio);
        ctx = this;

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        String usuario = pref.getString("usuario",null);
        TextView txt_usuario = (TextView)findViewById(R.id.txt_usuario);
        txt_usuario.setText("HOLA: "+usuario);



        Button btn_escanear = (Button)findViewById(R.id.btn_escanear);

        btn_escanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("disponible", true);
                editor.putInt("conectado",1);
                editor.commit();

                Intent i = new Intent(ctx, MapsActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();

            }
        });
    }


}
