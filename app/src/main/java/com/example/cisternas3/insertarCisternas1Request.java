package com.example.cisternas3;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class insertarCisternas1Request extends StringRequest {
    //clase q hace todo el trabajo

    private static final String insertar1_REQUEST="http://www.innovabilbao.com/app/insertarCisternas1.php";
    private Map<String,String> params;

    public insertarCisternas1Request(String nombre, String matricula, String recogidaFecha, String recogidaKm, String entregaFecha, String entregaKm, String limpieza, String reparacion, String situacion, String observaciones, Response.Listener<String> listener){
        super(Method.POST,insertar1_REQUEST,listener,null);

        params = new HashMap<>();
        params.put("nombre",nombre);
        params.put("matricula",matricula);
        params.put("recogidaFecha",recogidaFecha);
        params.put("recogidaKm",recogidaKm);
        params.put("entregaFecha",entregaFecha);
        params.put("entregaKm",entregaKm);
        params.put("limpieza",limpieza);
        params.put("reparacion",reparacion);
        params.put("situacion",situacion);
        params.put("observaciones",observaciones);
        //se puede cambiar un int  a string  params.put("situacion",situacion + ""); tener en cuenta q donde se asigna el dato este con Integer.parseInt

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
