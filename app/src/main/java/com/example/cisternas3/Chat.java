package com.example.cisternas3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        listaUsuarios = (ListView) findViewById(R.id.listaUsuarios);

        String nombreVentana = getIntent().getExtras().getString("nombreUser");
        final ArrayList<String> usuariosLista = new ArrayList<>();

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String species;
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray values = jsonResponse.getJSONArray("matriculas");

                    for (int i = 0; i < values.length(); i++) {
                        JSONObject matricula = values.getJSONObject(i);

                        //int id = animal.getInt("id");
                        species = matricula.getString("matricula");
                        //Log.e("BIENBIEN", "" + ":"+ " animales: " + matricula);
                        usuariosLista.add(species);
                        //String name = animal.getString("name");
                        //println(id + ", " + species + ", " + name);
                    }
                    // String matricula = jsonResponse.getString("");
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Chat.this, android.R.layout.simple_spinner_dropdown_item, usuariosLista);
                    listaUsuarios.setAdapter(adapter);
                    //Log.e("BIEN", "" + "response:"+ " aux: " + listaCisternas);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        listaUsuariosChatRequest listaUsuariosChatRequest = new listaUsuariosChatRequest(nombreVentana,responseListener);
        //RequestQueue queue = Volley.newRequestQueue(Chat.this);
        //queue.add(actualizarMatriculasRequest);


    }
}
