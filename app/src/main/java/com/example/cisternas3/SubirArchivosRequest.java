package com.example.cisternas3;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SubirArchivosRequest extends StringRequest {
    //clase q hace todo el trabajo

    private static final String insertarMensaje_REQUEST="http://www.innovabilbao.com/app/subirArchivos.php";
    private Map<String,String> params;

    public SubirArchivosRequest(String archivo, String nombre, Response.Listener<String> listener){
        super(Method.POST,insertarMensaje_REQUEST,listener,null);

        params = new HashMap<>();
        params.put("image",archivo);
        params.put("nombre",nombre);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
