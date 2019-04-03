package com.example.cisternas3;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class actualizarMatriculasRequest extends StringRequest {
    private static final String actualizarMatriculas_request="http://www.innovabilbao.com/app/actualizarMatriculas.php";
    private Map<String,String> params;

    public actualizarMatriculasRequest(String entregaFecha,String entregaKm,String limpieza,String reparacion,String situacion,String observaciones, String nombre, String matricula,Response.Listener<String> listener){
        super(Method.POST,actualizarMatriculas_request,listener,null);

        params = new HashMap<>();
        params.put("entregaFecha",entregaFecha);
        params.put("entregaKm",entregaKm);
        params.put("limpieza",limpieza);
        params.put("reparacion",reparacion);
        params.put("situacion",situacion);
        params.put("observaciones",observaciones);
        params.put("nombre",nombre);
        params.put("matricula",matricula);
        //se puede cambiar un int  a string  params.put("situacion",situacion + ""); tener en cuenta q donde se asigna el dato este con Integer.parseInt

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
