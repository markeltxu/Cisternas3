package com.example.cisternas3;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    private static final String login_REQUEST="http://www.innovabilbao.com/app/login.php";
    private Map<String,String> params;

    public LoginRequest(String id, String pass, Response.Listener<String> listener){
        super(Method.POST,login_REQUEST,listener,null);

        params = new HashMap<>();
        params.put("id",id);
        //params.put("nombre",nombre);
        params.put("pass",pass);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
