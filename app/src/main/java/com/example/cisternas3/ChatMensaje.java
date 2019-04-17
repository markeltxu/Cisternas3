package com.example.cisternas3;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.os.Handler;

public class ChatMensaje extends AppCompatActivity {
    String usuarioOrigen, usuarioDestino, fecha;
    EditText texto;
    ListView verMensaje;
    int tamanioAnterior = 0, tamanioActual = 0;
    boolean semaforo = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_mensaje);

        texto = (EditText) findViewById(R.id.texto);
        usuarioOrigen = getIntent().getExtras().getString("usuOrigen");
        usuarioDestino = getIntent().getExtras().getString("usuDestino");
        verMensaje = (ListView) findViewById(R.id.verMensaje);


        verMensaje.getLastVisiblePosition();
        final Handler handler = new Handler();
        final Parcelable state = verMensaje.onSaveInstanceState();
        final int index = verMensaje.getFirstVisiblePosition();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    verMensajes();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm a", Locale.getDefault());
                    Date date = new Date();

                    fecha = dateFormat.format(date);
                    //Log.e("BIENBIEN", "SEMAFORO " + semaforo );
                    /*View v = verMensaje.getChildAt(0);
                    int top = (v == null) ? 0 : (v.getTop() - verMensaje.getPaddingTop());
                    verMensaje.setSelectionFromTop(index, top);*/

                    //verMensaje.getLastVisiblePosition();
                    //verMensaje.onRestoreInstanceState(state);
                    handler.postDelayed(this, 1000);
                }
            }, 1);

        //Toast.makeText(getApplicationContext(), "Fecha:" + fecha, Toast.LENGTH_SHORT).show();

    }

    public void textoA(View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(texto, InputMethodManager.SHOW_IMPLICIT);
    }

    public void insertarMensaje(View view){
        String guardarMensaje = texto.getText().toString();
        texto.setText("");



        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

            }
        };

        insertarMensajeRequest insertarMensajeRequest = new insertarMensajeRequest(usuarioOrigen,usuarioDestino,guardarMensaje,fecha, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ChatMensaje.this);
        queue.add(insertarMensajeRequest);

        //verMensajes();
    }

    public void verMensajes(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            String guardarIdMensaje, guardarUsuOrigen, guardarUsuDestino, guardarMensaje, guardarFechaHora;
            String guardaFilaCompleta;
            ArrayList<String> guardarMensajes = new ArrayList<>();
            ArrayList<MensajesPlantilla> listaMensajes = new ArrayList<MensajesPlantilla>();
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray values = jsonResponse.getJSONArray("mensajes");

                    for (int i = 0; i < values.length(); i++) {
                        JSONObject matricula = values.getJSONObject(i);

                        //int id = animal.getInt("id");
                        guardarIdMensaje = matricula.getString("idMensaje");
                        guardarUsuOrigen = matricula.getString("usuarioOrigen");
                        guardarUsuDestino = matricula.getString("usuarioDestino");
                        guardarMensaje = matricula.getString("mensaje");
                        guardarFechaHora = matricula.getString("FechaHora");
                        guardaFilaCompleta = guardarUsuOrigen + " \n" + guardarMensaje + " \n" + fecha;
                        guardarMensajes.add(guardaFilaCompleta);

                        listaMensajes.add(new MensajesPlantilla(usuarioOrigen,guardarMensaje,guardarFechaHora));
                    }

                    Log.e("BIENBIEN", "SEMAFORO " + semaforo + "tamanioAnterior " + tamanioAnterior + "listaAcutal tamaño" +guardarMensajes.size());
                    if(tamanioAnterior == guardarMensajes.size()){
                        semaforo = false;
                    }else{
                        semaforo = true;
                    }
                    if(semaforo) {
                        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChatMensaje.this, android.R.layout.simple_spinner_dropdown_item, guardarMensajes);
                        //verMensaje.setAdapter(adapter);
                        AdaptadorMensaje miAdaptador = new AdaptadorMensaje(getApplicationContext(),listaMensajes);
                        verMensaje.setAdapter(miAdaptador);
                    }

                    tamanioAnterior = guardarMensajes.size();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

            ObtenerMensajesRequest obtenerMensajesRequest = new ObtenerMensajesRequest(usuarioOrigen, usuarioDestino, responseListener);
            RequestQueue queue = Volley.newRequestQueue(ChatMensaje.this);
            queue.add(obtenerMensajesRequest);

    }
}
