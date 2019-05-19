package com.apptaxi.conductordemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrador on 07/09/2017.
 */
public class servicio_llamada_anulado extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicio_llamada_anulado);

        Button btn_aceptar = (Button)findViewById(R.id.btn_aceptar);
        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
        FinAutomatico();
    }

    private void FinAutomatico() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 5000);
    }
}
