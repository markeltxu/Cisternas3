package com.example.cisternas3;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class RecogidaRequest extends StringRequest {
    private static final String Recogida_request="http://www.innovabilbao.com/app/recogida.php";
    private Map<String,String> params;

    public RecogidaRequest(String nombre, String matricula, Response.Listener<String> listener){
        super(Method.POST,Recogida_request,listener,null);

        params = new HashMap<>();
        params.put("nombre",nombre);
        params.put("matricula",matricula);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}