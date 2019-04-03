package com.example.cisternas3;
//CREADO POR MARKEL AGUIRRE Y CRISTIAN MAÑOSO.
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Spinner cisternas;
    EditText nombre, pass;
    Button btnInicio, btnInsertar, ir;
    private Cursor fila;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = (EditText) findViewById(R.id.nombre);
        pass = (EditText) findViewById(R.id.pass);
        btnInicio = (Button) findViewById(R.id.btnInicio);
        ir = (Button) findViewById(R.id.ir);
        cisternas = (Spinner) findViewById(R.id.cisternas);

        final ArrayList<String> listaCisternas = new ArrayList<>();
        final Response.Listener<String> responseListenerMatriucla = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String species = "hola";
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    //boolean success = jsonResponse.getBoolean("success");
                    JSONArray values = jsonResponse.getJSONArray("matriculas");

                    for (int i = 0; i < values.length(); i++) {
                        JSONObject matricula = values.getJSONObject(i);

                        //int id = animal.getInt("id");
                         species = matricula.getString("matricula");
                        //Log.e("BIENBIEN", "" + ":"+ " animales: " + matricula);
                         listaCisternas.add(species);
                        //String name = animal.getString("name");
                        //println(id + ", " + species + ", " + name);
                    }
                    // String matricula = jsonResponse.getString("");
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, listaCisternas);
                    cisternas.setAdapter(adapter);
                    //Log.e("BIEN", "" + "response:"+ " aux: " + listaCisternas);
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Log.e("MALISISMO", "" + "animal " +  " especies: " + species);
                }

            }
        };

            MatriculaRequest matriculaRequest = new MatriculaRequest(responseListenerMatriucla);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(matriculaRequest);

        /*DBHelper admin=new DBHelper(this,"Datos",null,1);
        SQLiteDatabase db=admin.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT matricula FROM Datos ORDER BY matricula", null);

        if (c != null) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para usarlos en lo que necesitemos
                String matricula = c.getString(c.getColumnIndex("matricula"));
                listaCisternas.add(matricula);

            } while (c.moveToNext());
        }

        //Cerramos el cursor y la conexion con la base de datos
        c.close();
        db.close();*/


        //cisternas.setPrompt("Eelgir cisterna");
        }

        public void ingresar (View v){
            final String id = nombre.getText().toString();
            final String contrasena = pass.getText().toString();
            final String guardarCisterna = cisternas.getSelectedItem().toString();
            //Toast.makeText(getApplicationContext(), "Spinner: "+ guardarCisterna, Toast.LENGTH_SHORT).show();

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        final String nombre = jsonResponse.getString("nombre");
                        final String pass = jsonResponse.getString("pass");
                        //Log.e("ADIOS", "" + nombre + " " + pass + " " + response);

                        //if(usuario.equals(nombre)&&contrasena.equals(pass)){
                        //si son iguales entonces vamos a otra ventana
                        //Menu es una nueva actividad empty
                        if (success) {
                            Intent ven = new Intent(MainActivity.this, Principal.class);
                            ven.putExtra("nombreUser", nombre);
                            ven.putExtra("nombreMatricula", guardarCisterna);
                            startActivity(ven);
                            //Toast.makeText(getApplicationContext(), "Login exitoso ", Toast.LENGTH_SHORT).show();
                            //limpiamos las las cajas de texto
                            //}else{
                            //
                            //}
                        } else {
                            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta: ", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("ADIOS", "" + nombre + " " + pass + " " + response);
                        Toast.makeText(getApplicationContext(), "Nombre o contraseña mal introducidos", Toast.LENGTH_SHORT).show();
                    }

                }
            };

            LoginRequest loginRequest = new LoginRequest(id, contrasena, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(loginRequest);

            //Cuidado CisternasDatos es cisternas si va algo mal.
            //DBHelper admin=new DBHelper(this,"Datos",null,1);
            //SQLiteDatabase db=admin.getWritableDatabase();
            //db.execSQL("insert into Datos values('markel','markel','cisterna2')");
            //db.execSQL("insert into Datos values('cristian','cristian','cisterna3333')");

        /*
        fila=db.rawQuery("SELECT nombre,pass FROM Datos WHERE nombre='"+usuario+"' and pass='"+contrasena+"'",null);
                //preguntamos si el cursor tiene algun valor almacenado
        if(fila.moveToFirst()==true){
            //capturamos los valores del cursos y lo almacenamos en variable
            String usua=fila.getString(0);
            String pass=fila.getString(1);
            //preguntamos si los datos ingresados son iguales
            if(usuario.equals(usua)&&contrasena.equals(pass)){
                //si son iguales entonces vamos a otra ventana
                //Menu es una nueva actividad empty
                Intent ven=new Intent(this,Principal.class);
                ven.putExtra("nombreUser", usuario);
                ven.putExtra("nombreMatricula", guardarCisterna);
                startActivity(ven);
                Toast.makeText(getApplicationContext(),"Login exitoso " , Toast.LENGTH_SHORT).show();
                //limpiamos las las cajas de texto
            }
        }else{
            Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrecta: " , Toast.LENGTH_SHORT).show();
        }*/
        }

        public void go (View v){
            nombre.setText("admin");
            pass.setText("admin12");
        }
}
