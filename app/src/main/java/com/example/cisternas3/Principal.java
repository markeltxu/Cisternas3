package com.example.cisternas3;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Principal extends AppCompatActivity {
    TextView nombreUser,matricula;
    //Spinner cisternas;
    EditText recogidaKm,entregaKm,situacion,recogidaFecha,entregaFecha,observaciones,limpieza,reparacion;
    private int anyo,mes,dia;
    int diaRecogida,diaEntrega;
    boolean semaforo = false;
    ArrayList<String> datosSQL = new ArrayList<String>();
    String nombreVentana;
    String nombreMatricula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        nombreUser = (TextView)findViewById(R.id.nombreUser);
        matricula = (TextView)findViewById(R.id.matricula);
        //cisternas = (Spinner)findViewById(R.id.cisternas);
        entregaFecha = (EditText)findViewById(R.id.entregaFecha);
        recogidaFecha = (EditText)findViewById(R.id.recogidaFecha);
        observaciones = (EditText)findViewById(R.id.observaciones);
        recogidaKm = (EditText) findViewById(R.id.recogidaKM);
        entregaKm = (EditText) findViewById(R.id.entregaKm);
        limpieza = (EditText)findViewById(R.id.limpieza);
        reparacion = (EditText)findViewById(R.id.reparacion);


        //---------------------------------------------------
        situacion = (EditText)findViewById(R.id.situacion);

        nombreVentana = getIntent().getExtras().getString("nombreUser");
        nombreUser.setText(nombreVentana);
        nombreMatricula = getIntent().getExtras().getString("nombreMatricula");
        matricula.setText(nombreMatricula);


        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String recogidaFecha2 = "";
                String recogidaKm2 = "";

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    recogidaFecha2 = jsonResponse.getString("recogidaFecha");
                    recogidaKm2 = jsonResponse.getString("recogidaKm");
                    if (success) {
                        //Toast.makeText(getApplicationContext(), "BIEN " + recogidaFecha2, Toast.LENGTH_SHORT).show();
                        //Log.e("ALGOVABIEN", "" +  recogidaFecha2 + " recogidaKM: " + recogidaKm2 +" response: " + response);
                        datosSQL.add(recogidaFecha2);
                        datosSQL.add(recogidaKm2);
                        recogidaFecha.setText(datosSQL.get(0));
                        recogidaKm.setText(datosSQL.get(1));
                        semaforo = true;
                        //limpiamos las las cajas de texto
                        //}else{
                        //
                        //}
                    } else {
                        //Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta: ", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    //Log.e("ALGOVAMAL", "" +  recogidaFecha2 + " recogidaKM: " + recogidaKm2 +" nombre: "+nombreVentana + " matricula " + nombreMatricula + "response " +response);
                    semaforo = false;

                }

            }
        };

        RecogidaRequest recogidaRequest = new RecogidaRequest(nombreVentana,nombreMatricula,responseListener);
        RequestQueue queue = Volley.newRequestQueue(Principal.this);
        queue.add(recogidaRequest);
        /*DBHelper admin=new DBHelper(this,"Datos",null,1);
        SQLiteDatabase db=admin.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT recogidaFecha,recogidaKm FROM CisternasDatos WHERE nombre='"+nombreVentana+"' and matricula='"+nombreMatricula+"' and situacion=''", null);

        //if (c != null) {
        if(c.moveToFirst()){
            //c.moveToFirst();

            semaforo = true;
            do {
                //Asignamos el valor en nuestras variables para usarlos en lo que necesitemos
                String recogidaFecha = c.getString(c.getColumnIndex("recogidaFecha"));
                String recogidaKm = c.getString(c.getColumnIndex("recogidaKm"));
                datosSQL.add(recogidaFecha);
                datosSQL.add(recogidaKm);
            } while (c.moveToNext());
            recogidaFecha.setText(datosSQL.get(0));
            recogidaKm.setText(datosSQL.get(1));
        }else{
            semaforo = false;
        }
        Log.e("HOLA MUNDO", "" + datosSQL + " " + nombreVentana + " " + nombreMatricula);

        //Cerramos el cursor y la conexion con la base de datos
        c.close();
        db.close();
        */
        //Toast.makeText(getApplicationContext(),"ok" + c , Toast.LENGTH_SHORT).show();
    }

    public void introducir(View v){
        //DBHelper admin = new DBHelper (this,"Datos",null,1);
        //SQLiteDatabase db = admin.getWritableDatabase();

        String guardarNombre = nombreUser.getText().toString();
        String guardarMatricula = matricula.getText().toString();
        String guardarRecogidaFecha = recogidaFecha.getText().toString();
        String guardarRecogidaKm =  recogidaKm.getText().toString();
        String guardarEntregaFecha = entregaFecha.getText().toString();
        String guardarEntregaKm = entregaKm.getText().toString();
        String guardarSituacion = situacion.getText().toString();
        String guardarLimpieza = limpieza.getText().toString();
        String guardarReparacion = reparacion.getText().toString();
        String guardarObservaciones = observaciones.getText().toString();

        if(semaforo == false){
            Response.Listener<String> responseListener = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        //Log.e("ADIOS", "" + jsonResponse);
                        //el retorena una respuesta de tipo booleano en funcíon al succes del PHP para saber si se hace cambio o no.
                        boolean success = jsonResponse.getBoolean("success");
                        //importante para hacer lo de insertar o update.
                        if(success){
                            Toast.makeText(getApplicationContext(),"Datos insertados correctamente" , Toast.LENGTH_SHORT).show();
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(Principal.this);
                            builder.setMessage("No se a realizado la query o no hay niongun registro.")
                                   .setNegativeButton("Reintentar",null)
                                   .create().show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Log.e("ADIOS", "" + response);
                    }
                }
            };

            insertarCisternas1Request registerRequest = new insertarCisternas1Request(guardarNombre,guardarMatricula,guardarRecogidaFecha,guardarRecogidaKm,guardarEntregaFecha,guardarEntregaKm,guardarLimpieza,guardarReparacion,guardarSituacion,guardarObservaciones,responseListener);
            RequestQueue queue = Volley.newRequestQueue(Principal.this);
            queue.add(registerRequest);
        }else{
            Response.Listener<String> responseListener = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(success){
                            Toast.makeText(getApplicationContext(),"Datos actualizados correctamente" , Toast.LENGTH_SHORT).show();
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(Principal.this);
                            builder.setMessage("No se a realizado la query o no hay niongun registro.")
                                    .setNegativeButton("Reintentar",null)
                                    .create().show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Log.e("ADIOS", "" + response);
                    }
                }
            };

            actualizarMatriculasRequest actualizarMatriculasRequest = new actualizarMatriculasRequest(guardarEntregaFecha,guardarEntregaKm,guardarLimpieza,guardarReparacion,guardarSituacion,guardarObservaciones,guardarNombre,guardarMatricula,responseListener);
            RequestQueue queue = Volley.newRequestQueue(Principal.this);
            queue.add(actualizarMatriculasRequest);
        }
        /*
        if(semaforo == false){
            ContentValues values = new ContentValues();
            values.put("nombre",guardarNombre);
            values.put("matricula",guardarMatricula);
            values.put("recogidaFecha",guardarRecogidaFecha);
            values.put("recogidaKm",guardarRecogidaKm);
            values.put("entregaFecha",guardarEntregaFecha);
            values.put("entregaKm",guardarEntregaKm);
            values.put("limpieza",guardarLimpieza);
            values.put("reparacion",guardarReparacion);
            values.put("situacion",guardarSituacion);
            values.put("observaciones",guardarObservaciones);
            db.insert("CisternasDatos",null,values);
            db.close();

            Toast.makeText(getApplicationContext(),"SEMAFORO TRUE Datos insertados correctamente" , Toast.LENGTH_SHORT).show();

        }else{                                                                          //falta updatear todos los campos
            db.execSQL("UPDATE CisternasDatos SET entregaFecha='"+guardarEntregaFecha+"',entregaKm='"+guardarEntregaKm +"',limpieza='"+guardarLimpieza+"',reparacion='"+guardarReparacion+"',situacion='"+guardarSituacion+"',observaciones='"+guardarObservaciones+"'  WHERE nombre = '"+guardarNombre+"' and matricula='"+guardarMatricula+"' and situacion=''");
            Toast.makeText(getApplicationContext(),"SEMAFORO FALSE Datos UPDATE correcto" , Toast.LENGTH_SHORT).show();
        }
        */
        //Sentencia para hacer el update la 2º vez q introudces datos.
        //UPDATE CisternasDatos SET entregaFecha='21/03/2019' WHERE nombre = 'markel' and matricula='prueba122222A' and situacion=''
    }



   /* public void recogidaEntregaIr(View v){
        Intent i=new Intent(this,RecogidaEntrega.class);
        startActivity(i);
    }*/


    public void fechaRecogida(View v){
        String guardarEntregaFecha = entregaFecha.getText().toString();

        Calendar cal = Calendar.getInstance();
        anyo = cal.get(Calendar.YEAR);
        mes = cal.get(Calendar.MONTH);
        dia = cal.get(Calendar.DAY_OF_MONTH);


        if(guardarEntregaFecha.matches("")) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    recogidaFecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    diaRecogida = dayOfMonth;
                }
            }, anyo, mes, dia);

            datePickerDialog.show();
        }else{
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    recogidaFecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    diaRecogida = dayOfMonth;
                }
            },anyo,mes,diaEntrega-1);
            datePickerDialog.show();
        }
    }

    public void fechaEntrega1(View v){
        String guardarRecogidaFecha = recogidaFecha.getText().toString();

        Calendar cal = Calendar.getInstance();
        anyo = cal.get(Calendar.YEAR);
        mes = cal.get(Calendar.MONTH);
        dia = cal.get(Calendar.DAY_OF_MONTH);


        if(guardarRecogidaFecha.matches("")){
            Toast.makeText(getApplicationContext(),"VACIO " , Toast.LENGTH_SHORT).show();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    entregaFecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    diaEntrega = dayOfMonth;
                }
            },anyo,mes,dia);
            datePickerDialog.show();
        }else{
            Toast.makeText(getApplicationContext(),"TIENE DATOS"+diaRecogida , Toast.LENGTH_SHORT).show();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    entregaFecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    diaEntrega = dayOfMonth;
                }
            },anyo,mes,diaRecogida+1);
            datePickerDialog.show();
        }


    }

    public void mirarFecha(View v){
        String guardarRecogidaFecha = recogidaFecha.getText().toString();
        String guardarEntregaFecha = entregaFecha.getText().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date strRecogidaFecha = sdf.parse(guardarRecogidaFecha);
            Date strEntregaFecha = sdf.parse(guardarEntregaFecha);

            if (strRecogidaFecha.after(strEntregaFecha)) {
                Toast.makeText(getApplicationContext(),"MAL LA RECOGIDA FECHA MAS TARDE Q" , Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"BIEN ENTREGA MAS TARDE Q RECOGIDA" , Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void chat(View view){
        Intent ven = new Intent(Principal.this, Chat.class);
        ven.putExtra("nombreUser", nombreVentana);
        //ven.putExtra("nombreMatricula", guardarCisterna);
        startActivity(ven);

    }
}
