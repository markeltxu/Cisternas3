package com.example.cisternas3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Chat extends AppCompatActivity {
//Pag del listado de usuraios para chatear.
    ListView listaUsuarios;
    String guardarId;
    String nombreVentana;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listaUsuarios = (ListView) findViewById(R.id.listaUsuarios);

        nombreVentana = getIntent().getExtras().getString("nombreUser");
        guardarId = getIntent().getExtras().getString("idVentana");
        final ArrayList<String> usuariosLista = new ArrayList<>();
        Toast.makeText(getApplicationContext(),"ID: " + guardarId, Toast.LENGTH_SHORT).show();
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String species;
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray values = jsonResponse.getJSONArray("nombres");

                    for (int i = 0; i < values.length(); i++) {
                        JSONObject matricula = values.getJSONObject(i);

                        //int id = animal.getInt("id");
                        species = matricula.getString("nombre");
                        Log.e("BIENBIEN", "" + ":"+ " animales: " + matricula);
                        usuariosLista.add(species);
                        //String name = animal.getString("name");
                        //println(id + ", " + species + ", " + name);
                    }
                    // String matricula = jsonResponse.getString("");
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Chat.this, android.R.layout.simple_spinner_dropdown_item, usuariosLista);
                    listaUsuarios.setAdapter(adapter);
                    Log.e("ADIOS1", "" +  response + "listaUsuarios " +listaUsuarios + " usuariosLista " +usuariosLista);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ADIOS", "" +  response + "listaUsuarios " +listaUsuarios + " usuariosLista " +usuariosLista);
                }
            }
        };
        listaUsuariosChatRequest listaUsuariosChatRequest = new listaUsuariosChatRequest(nombreVentana,responseListener);
        RequestQueue queue = Volley.newRequestQueue(Chat.this);
        queue.add(listaUsuariosChatRequest);

        listaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String guardarNum = usuariosLista.get(position);


                Intent ven = new Intent(Chat.this, ChatMensaje.class);
                ven.putExtra("usuOrigen", nombreVentana);
                ven.putExtra("usuDestino", guardarNum);
                startActivity(ven);
            }
        });
    }
}
