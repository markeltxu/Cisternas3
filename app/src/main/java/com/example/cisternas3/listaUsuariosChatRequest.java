package com.example.cisternas3;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class listaUsuariosChatRequest extends StringRequest{
        private static final String listaUsuariosChat_REQUEST="http://192.168.1.42/listaUsuariosChat.php";
        private Map<String,String> params;

        public listaUsuariosChatRequest(String id,Response.Listener<String> listener){
            super(Method.POST,listaUsuariosChat_REQUEST,listener,null);
            params = new HashMap<>();
            params.put("id",id);
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }

}

