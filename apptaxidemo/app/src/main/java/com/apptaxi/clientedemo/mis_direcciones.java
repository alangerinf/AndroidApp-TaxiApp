package com.apptaxi.clientedemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.apptaxi.clientedemo.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrador on 15/09/2017.
 */
public class mis_direcciones extends AppCompatActivity {
    Context ctx;
    String[]idList,direList, urbaList,refeList,nroList;
    int id_usuario;
    String usuario;

    JSONArray direcciones = null;

    ListView lv;
    TextView txt_vacio;
    ProgressDialog progress;
    SharedPreferences pref;

    Dialog dialog;
    String id_direccion,direccion,numero,referencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_direcciones);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        ctx = this;

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Cargando direcciones");

        pref = getApplicationContext().getSharedPreferences(global.nameSesion, MODE_PRIVATE);
        id_usuario = pref.getInt("id_usuario",0);
        usuario = pref.getString("usuario","-");

        txt_vacio = (TextView)findViewById(R.id.txt_vacio);

        lv = (ListView) findViewById(R.id.list_direcciones);

        CargarListaDirecciones();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK ) {
            if(requestCode==1){
                CargarListaDirecciones();
            }

        }
    }

    private void CargarListaDirecciones() {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0012,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                direcciones = data.getJSONArray("direcciones");

                                idList = new String[direcciones.length()];
                                direList = new String[direcciones.length()];
                                urbaList = new String[direcciones.length()];
                                refeList = new String[direcciones.length()];
                                nroList= new String[direcciones.length()];

                                for (int i = 0; i < direcciones.length(); i++) {
                                    JSONObject c = direcciones.getJSONObject(i);

                                    String id = c.getString("id");
                                    String direccion = c.getString("dire");
                                    String urba = c.getString("urba");
                                    String refe = c.getString("refe");
                                    String nro = c.getString("numero");

                                    idList[i]=id;
                                    direList[i]=direccion;
                                    urbaList[i]=urba;
                                    refeList[i]=refe;
                                    nroList[i]=nro;
                                }
                                CreateList();

                            }else{
                                //ShowToast("No existe direcciones guardadas");
                                txt_vacio.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                global.ShowToast(ctx,global.NosePudo);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_usuario", String.valueOf(id_usuario));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(sr);
    }

    private void CreateList(){
        CustomDirecciones adapter = new CustomDirecciones(mis_direcciones.this, direList,urbaList,refeList,idList,nroList);
        lv=(ListView)findViewById(R.id.list_direcciones);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView txt_id = (TextView)view.findViewById(R.id.txt_id);
                TextView txt_direccion = (TextView)view.findViewById(R.id.txt_direccion);
                TextView txt_numero = (TextView)view.findViewById(R.id.txt_urba);
                TextView txt_refe = (TextView)view.findViewById(R.id.txt_refe);

                id_direccion = txt_id.getText().toString();
                direccion = txt_direccion.getText().toString();
                numero = txt_numero.getText().toString();
                referencia = txt_refe.getText().toString();

                openDialogOpcioines();
            }
        });
    }

    public void openDialogOpcioines(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.mis_direcciones_operaciones);

        TextView txt_dire = (TextView)dialog.findViewById(R.id.txt_dire);
        txt_dire.setText(direccion+" "+numero);

        Button btn_pedir = (Button)dialog.findViewById(R.id.btn_pedir);
        btn_pedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PedirTaxiDireccion();
                dialog.dismiss();
            }
        });

        Button btn_editar = (Button)dialog.findViewById(R.id.btn_editar);
        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ctx, mis_direcciones_editar.class);
                i.putExtra("id_direccion", id_direccion);
                i.putExtra("direccion", direccion);
                i.putExtra("numero", numero);
                i.putExtra("referencia", referencia);
                startActivityForResult(i,1);
                dialog.dismiss();
            }
        });

        Button btn_cancelar = (Button)dialog.findViewById(R.id.btn_cancelar);
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void PedirTaxiDireccion() {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0014,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                final int id_pedido = data.getInt("id_pedido");
                                if(data.getInt("entregados")>0) {
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putInt("id_pedido", data.getInt("id_pedido"));
                                    editor.putInt("pedidoAceptado",0);
                                    editor.putInt("pedidoUbicacion",0);
                                    editor.commit();

                                    Intent i = getIntent();
                                    setResult(RESULT_OK, i);
                                    finish();
                                }else {
                                    final AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                                    alertDialog.setIcon(R.drawable.ic_launcher);
                                    alertDialog.setTitle(global.nameEmpresa);
                                    alertDialog.setMessage(global.SinUnidadescerca);
                                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Si, Envíenme", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            alertDialog.dismiss();
                                            PedirTaxiBase(id_pedido,1);
                                        }
                                    });
                                    alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No, Cancelar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            alertDialog.dismiss();
                                            PedirTaxiBase(id_pedido,0);
                                        }
                                    });
                                    alertDialog.show();
                                }
                            }else{
                                global.ShowToast(ctx,global.NosePudo);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                global.ShowToast(ctx,global.NosePudo);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_cliente", String.valueOf(id_usuario));
                params.put("cliente", usuario);
                params.put("id_direccion", id_direccion);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(sr);
    }

    private void PedirTaxiBase(final int id, final int operacion) {
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                global.UrlServer + global.UL0002_1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {
                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if(success==1){
                                global.ShowToast(ctx,global.SeSolicitoTaxi);
                                finish();
                            }else{
                                global.ShowToast(ctx,global.NosePudo);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                global.ShowToast(ctx,global.NosePudo);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_pedido", String.valueOf(id));
                params.put("operacion", String.valueOf(operacion));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(sr);
    }
}
