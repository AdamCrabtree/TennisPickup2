package com.example.acrab.basketballpickup;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by acrab on 4/12/2017.
 */

public class CreateMatchRequest extends StringRequest {
    public static final String REGISTER_REQUEST_URL = "https://toledopickupapp.000webhostapp.com/matchregister.php";
    private Map<String, String> params;
    public CreateMatchRequest(String playername, String time, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("playername", playername);
        params.put("time", time);
    }
    public Map<String, String> getParams(){
        return params;
    }
}
