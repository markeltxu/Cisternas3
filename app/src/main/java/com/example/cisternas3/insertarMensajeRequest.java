package com.example.cisternas3;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class insertarMensajeRequest extends StringRequest {
    //clase q hace todo el trabajo

    private static final String insertarMensaje_REQUEST="http://192.168.1.45/insertarMensaje.php";
    private Map<String,String> params;

    public insertarMensajeRequest(String usuarioOrigen, String usuarioDestino, String mensaje, Response.Listener<String> listener){
        super(Method.POST,insertarMensaje_REQUEST,listener,null);

        params = new HashMap<>();
        params.put("usuarioOrigen",usuarioOrigen);
        params.put("usuarioDestino",usuarioDestino);
        params.put("mensaje",mensaje);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
