package com.example.acrab.basketballpickup;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by acrab on 4/12/2017.
 */

public class LoginRequest extends StringRequest {
    public static final String REGISTER_REQUEST_URL = "https://toledopickupapp.000webhostapp.com/login.php";
    private Map<String, String> params;
    public LoginRequest(String username, String password, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }
    public Map<String, String> getParams(){
        return params;
    }
}
