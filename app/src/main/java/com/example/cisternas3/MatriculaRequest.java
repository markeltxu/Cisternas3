package com.example.cisternas3;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MatriculaRequest extends StringRequest {
    private static final String Matriucla_REQUEST="http://www.innovabilbao.com/app/matricula.php";
    private Map<String,String> params;

    public MatriculaRequest(Response.Listener<String> listener){
        super(Method.POST,Matriucla_REQUEST,listener,null);
        /*params = new HashMap<>();
        params.put("id",id);
        //params.put("nombre",nombre);
        params.put("pass",pass);*/
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
