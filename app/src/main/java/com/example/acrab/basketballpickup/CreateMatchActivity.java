package com.example.acrab.basketballpickup;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class CreateMatchActivity extends MapsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);
        final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        Button createMatchButton = (Button) findViewById(R.id.confirm_create_match);
        createMatchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String hours = Integer.toString(timePicker.getHour());
                final String minutes = Integer.toString(timePicker.getMinute());
                final String time = hours + ":" + minutes;
                dataSource.createComment("Adam Crabtree" + " " + time);
                Intent myIntent = new Intent(CreateMatchActivity.this, MapsActivity.class);
                startActivity(myIntent);
            }
        });


    }
}
