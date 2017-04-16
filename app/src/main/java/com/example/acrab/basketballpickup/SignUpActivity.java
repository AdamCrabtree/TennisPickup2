package com.example.acrab.basketballpickup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        String nameAndTime;
        Bundle extras = getIntent().getExtras();
        nameAndTime = extras.getString("NAME_AND_TIME_STRING");
        TextView timeTextView = (TextView) findViewById(R.id.timeView);
        TextView nameTextView = (TextView) findViewById(R.id.nameView);
        String name[] = nameAndTime.split(",");
        nameTextView.setText(name[0]);
        timeTextView.setText(name[1]);
    }
}
