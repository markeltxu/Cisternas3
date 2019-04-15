package com.example.cisternas3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdaptadorMensaje extends BaseAdapter {

    Context contexto;
    List<MensajesPlantilla> listaMensajes;


    public AdaptadorMensaje(Context contexto, List<MensajesPlantilla> listaMensajes) {
        this.contexto = contexto;
        this.listaMensajes = listaMensajes;
    }




    @Override
    public int getCount() {
        return listaMensajes.size();
    }

    @Override
    public Object getItem(int position) {
        return listaMensajes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;

        LayoutInflater inflater = LayoutInflater.from(contexto);
        vista = inflater.inflate(R.layout.layout_formato_mensaje, null);

        TextView nombre = (TextView) vista.findViewById(R.id.nombre);
        TextView mensaje = (TextView) vista.findViewById(R.id.mensaje);
        TextView fechaHora = (TextView) vista.findViewById(R.id.fechaHora);

        nombre.setText(listaMensajes.get(position).getUsuarioOrigen().toString());
        mensaje.setText(listaMensajes.get(position).getMensaje().toString());
        fechaHora.setText(listaMensajes.get(position).getFechaHora().toString());

        return vista;
    }
}