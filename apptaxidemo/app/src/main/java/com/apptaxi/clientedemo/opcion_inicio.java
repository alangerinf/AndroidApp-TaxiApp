package com.apptaxi.clientedemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;



/**
 * Created by Administrador on 11/09/2017.
 */
public class opcion_inicio extends AppCompatActivity {
    SharedPreferences pref;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opcion_inicio);
        ctx = this;

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);

        Button btn_iniciar = (Button)findViewById(R.id.btn_iniciar);
        Button btn_registrarse = (Button)findViewById(R.id.btn_registrarse);

        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, login.class);
                startActivityForResult(i,1);
            }
        });

        btn_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pref.contains("paso")){
                    if(pref.getInt("paso",1)==1){
                        Intent i = new Intent(ctx, registrate_paso2.class);
                        startActivityForResult(i,1);
                    }else{
                        Intent i = new Intent(ctx, registrate_paso1.class);
                        startActivityForResult(i,1);
                    }
                }else {
                    Intent i = new Intent(ctx, registrate_paso1.class);
                    startActivityForResult(i, 1);
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras.getInt("activar")==1){
            Intent i = new Intent(ctx, registrate_paso2.class);
            startActivityForResult(i,1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK ) {
            int opcion = data.getExtras().getInt("opcion");
            if(opcion==1){//viene de login correcto
                Intent i = new Intent(ctx, MapsActivity.class);
                startActivity(i);
                finish();
            }else
            if(opcion==2){//viene de 1 y pasamos a 2
                Intent i = new Intent(ctx, registrate_paso2.class);
                startActivityForResult(i,1);
            }else
            if(opcion==3){//viene de 2 y vamos a 1
                Intent i = new Intent(ctx, registrate_paso1.class);
                startActivityForResult(i,1);
            }else
            if(opcion==4){//viene de 2 y pasamos a home
                Intent i = new Intent(ctx, MapsActivity.class);
                startActivity(i);
                finish();
            }
        }
    }
}
