package com.example.cisternas3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;


public class Chat extends AppCompatActivity {
//Pag del listado de usuraios para chatear.
    ListView listaUsuarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        listaUsuarios = (ListView) findViewById(R.id.listaUsuarios);

        final ArrayList<String> usuariosLista = new ArrayList<>();



    }
}
