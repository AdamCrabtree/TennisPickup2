package com.example.acrab.basketballpickup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateMatchActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);
        final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        Button createMatchButton = (Button) findViewById(R.id.confirm_create_match);
        SharedPreferences settings = getSharedPreferences("prefs", 0);
        final String playername = settings.getString("username","default");
        createMatchButton.setOnClickListener(new View.OnClickListener() {
            final String hours = Integer.toString(timePicker.getHour());
            final String minutes = Integer.toString(timePicker.getMinute());
            final String time = hours + ":" + minutes;
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent mapsIntent = new Intent(CreateMatchActivity.this, MapsActivity.class);
                        startActivity(mapsIntent);
                    }
                };
                CreateMatchRequest myRegister = new CreateMatchRequest(playername, time, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CreateMatchActivity.this);
                queue.add(myRegister);
            }
        });


    }
}
