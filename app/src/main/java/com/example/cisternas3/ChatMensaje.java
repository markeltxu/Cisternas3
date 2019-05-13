package com.example.cisternas3;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.os.Handler;
import android.widget.Toast;


public class ChatMensaje extends AppCompatActivity {
    String usuarioOrigen, usuarioDestino, fecha, rutaFotoCamaraGaleria ;
    EditText texto;
    ListView verMensaje;
    TextView prueba;
    int tamanioAnterior = 0, tamanioActual = 0;
    boolean semaforo = true;
    Intent myVentanaFile;
    private static final int ACTIVITY_CHOOSE_FILE = 1;
    String filenameGaleria;
    ImageView imagenPrueba;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_mensaje);

        texto = (EditText) findViewById(R.id.texto);
        usuarioOrigen = getIntent().getExtras().getString("usuOrigen");
        usuarioDestino = getIntent().getExtras().getString("usuDestino");
        verMensaje = (ListView) findViewById(R.id.verMensaje);
        prueba = (TextView) findViewById(R.id.prueba);
        imagenPrueba = (ImageView)findViewById(R.id.imagenPrueba);

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

                    //Log.e("BIENBIEN", "SEMAFORO " + semaforo + "tamanioAnterior " + tamanioAnterior + "listaAcutal tamaño" +guardarMensajes.size());
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



    public void chooseFile(View view){
        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("*/*");
        intent = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
        /*String path = this.getFilesDir().getAbsolutePath();
        File file = new File(path + "/abikor.txt");
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);*/
        //Intent intent = new Intent();
        //sets the select file to all types of files
        //intent.setType("*/*");
        //allows to select data and return it
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        //startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICKFILE_RESULT_CODE);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        String path     = "";
        if(requestCode == ACTIVITY_CHOOSE_FILE)
        {
            Uri FilePath = data.getData();
            try {
                String nombreArchivo = getRealPathFromURI(FilePath); // should the path be here in this string
                InputStream inputStream = getContentResolver().openInputStream(FilePath);
                bitmap = BitmapFactory.decodeStream(inputStream);

                //System.out.print("Path  = " + FilePath);
                Log.e("El elegido: ", nombreArchivo + " -- bitmap: " + bitmap);
                imagenPrueba.setImageBitmap(bitmap);
                //prueba.setText(FilePath);


                String archivo = imageToString(bitmap);

                Response.Listener<String> responseListenerSubirArchivos = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /*try {
                            //JSONObject jsonResponse = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Log.e("ADIOS", "" + nombre + " " + pass + " " + response);
                            //Toast.makeText(getApplicationContext(), "Nombre o contraseña mal introducidos", Toast.LENGTH_SHORT).show();
                        }*/
                    }
                };

                SubirArchivosRequest subirArchivosRequest = new SubirArchivosRequest(archivo,nombreArchivo,responseListenerSubirArchivos);
                RequestQueue queue = Volley.newRequestQueue(ChatMensaje.this);
                queue.add(subirArchivosRequest);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String [] proj      = {MediaStore.Images.Media.DATA};
        Cursor cursor       = getContentResolver().query( contentUri, proj, null, null,null);
        if (cursor == null) return null;
        int column_index    = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encode = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encode;
    }

}
