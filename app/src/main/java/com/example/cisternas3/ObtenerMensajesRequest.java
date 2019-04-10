package com.example.cisternas3;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ObtenerMensajesRequest extends StringRequest {
    private static final String listaUsuariosChat_REQUEST="http://192.168.1.45/obtenerMensajes.php";
    private Map<String,String> params;

    public ObtenerMensajesRequest(String usuarioOrigen,String usuarioDestino, Response.Listener<String> listener){
        super(Method.POST,listaUsuariosChat_REQUEST,listener,null);
        params = new HashMap<>();
        params.put("usuarioOrigen",usuarioOrigen);
        params.put("usuarioDestino",usuarioDestino);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
