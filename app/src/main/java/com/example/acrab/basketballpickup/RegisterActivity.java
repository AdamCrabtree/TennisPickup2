package com.example.acrab.basketballpickup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);


        final Button bRegister = (Button) findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String name = etName.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject myJSON = new JSONObject(response);
                            boolean success = myJSON.getBoolean("success");
                            Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                RegisterRequest myRegister = new RegisterRequest(name, username, password, responseListener){

                };
            }
        });
    }
}
