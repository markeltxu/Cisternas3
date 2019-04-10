package com.example.cisternas3;

import android.content.Context;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_mensaje);

        texto = (EditText) findViewById(R.id.texto);
        usuarioOrigen = getIntent().getExtras().getString("usuOrigen");
        usuarioDestino = getIntent().getExtras().getString("usuDestino");
        verMensaje = (ListView) findViewById(R.id.verMensaje);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm a", Locale.getDefault());
        Date date = new Date();

        fecha = dateFormat.format(date);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                verMensajes();
                handler.postDelayed(this, 1000);
            }
        }, 1000);
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

        insertarMensajeRequest insertarMensajeRequest = new insertarMensajeRequest(usuarioOrigen,usuarioDestino,guardarMensaje, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ChatMensaje.this);
        queue.add(insertarMensajeRequest);

        //verMensajes();
    }

    public void verMensajes(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            String guardarIdMensaje, guardarUsuOrigen, guardarUsuDestino, guardarMensaje;
            String guardaFilaCompleta;
            final ArrayList<String> guardarMensajes = new ArrayList<>();
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

                        guardaFilaCompleta = guardarUsuOrigen + " \n" + guardarMensaje + " \n" + fecha;
                        guardarMensajes.add(guardaFilaCompleta);
                        //Toast.makeText(getApplicationContext(), guardaFilaCompleta, Toast.LENGTH_SHORT).show();
                        Log.e("BIENBIEN", "" + ":"+ " animales: " + matricula);
                        //usuariosLista.add(species);
                        //String name = animal.getString("name");
                        //println(id + ", " + species + ", " + name);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChatMensaje.this, android.R.layout.simple_spinner_dropdown_item, guardarMensajes);
                    verMensaje.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        ObtenerMensajesRequest obtenerMensajesRequest = new ObtenerMensajesRequest(usuarioOrigen,usuarioDestino,responseListener);
        RequestQueue queue = Volley.newRequestQueue(ChatMensaje.this);
        queue.add(obtenerMensajesRequest);
    }
}
