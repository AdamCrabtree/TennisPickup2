package com.example.acrab.basketballpickup;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by acrab on 4/14/2017.
 */

public class MatchesRequest extends StringRequest {
    public static final String REGISTER_REQUEST_URL = "https://toledopickupapp.000webhostapp.com/getmatches.php";
    private Map<String, String> params;
    public MatchesRequest(Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
    }
    public Map<String, String> getParams(){
        return params;
    }
}

