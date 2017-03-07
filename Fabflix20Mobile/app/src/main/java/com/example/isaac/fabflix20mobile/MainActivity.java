package com.example.isaac.fabflix20mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("user");

        String welcomeMessage = "Welcome, " + username;
        ((TextView) findViewById(R.id.userdisplay)).setText(welcomeMessage);
    }
}