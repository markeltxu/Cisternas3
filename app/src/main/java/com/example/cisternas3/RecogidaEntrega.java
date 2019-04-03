package com.example.cisternas3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RecogidaEntrega extends AppCompatActivity {
    EditText recogidaFecha,entregaFecha;


    private int anyo,mes,dia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recogida_entrega);

        entregaFecha = (EditText)findViewById(R.id.entregaFecha);
        recogidaFecha = (EditText)findViewById(R.id.recogidaFecha);
    }//recogidaFecha

    public void fechaRegodia(View v){
        Calendar cal = Calendar.getInstance();
        anyo = cal.get(Calendar.YEAR);
        mes = cal.get(Calendar.MONTH);
        dia = cal.get(Calendar.DAY_OF_MONTH);



        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                recogidaFecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        },anyo,mes,dia);
        datePickerDialog.show();
    }

    public void fechaEntrega(View v){
        Calendar cal = Calendar.getInstance();
        anyo = cal.get(Calendar.YEAR);
        mes = cal.get(Calendar.MONTH);
        dia = cal.get(Calendar.DAY_OF_MONTH);



        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                entregaFecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        },anyo,mes,dia);
        datePickerDialog.show();
    }

    public void confirmar(View v){
        entregaFecha = (EditText)findViewById(R.id.entregaFecha);
        recogidaFecha = (EditText)findViewById(R.id.recogidaFecha);

        String guardarRecogida = recogidaFecha.getText().toString();
        String guardarEntrega = entregaFecha.getText().toString();

        if(guardarRecogida.matches("") && guardarEntrega.matches("")){
            //Toast.makeText(getApplicationContext(),"MAL FALTA FECHA:  " +  recogidaFecha.getText(), Toast.LENGTH_SHORT).show();
        }else{
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date fechaRecogida = sdf.parse(guardarRecogida);
                Date fechaEntrega = sdf.parse(guardarEntrega);

                if(fechaRecogida.before(fechaEntrega)){
                    Toast.makeText(getApplicationContext(),"Fecha recogida mas tarde", Toast.LENGTH_SHORT).show();
                    Log.d("PRUEBA","AAAAAAAAAAAAAAAAAAAAAAA");
                }else{
                    Toast.makeText(getApplicationContext(),"Bien", Toast.LENGTH_SHORT).show();
                    Log.d("PRUEBA","OOOOOOOOOOOOOOOOOO");
                }
            }catch(ParseException e1){
                e1.printStackTrace();
            }
            //Toast.makeText(getApplicationContext(),"Fecha:"+  recogidaFecha.getText(), Toast.LENGTH_SHORT).show();
        }

    }

    public void atras(View v){
        Intent i=new Intent(this,Principal.class);
        startActivity(i);
    }
}
