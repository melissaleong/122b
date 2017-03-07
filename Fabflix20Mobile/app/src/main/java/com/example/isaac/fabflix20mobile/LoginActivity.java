package com.example.isaac.fabflix20mobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.util.Log;
import android.os.StrictMode;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener{
    private AppCompatButton loginButton;
    private EditText username;
    private EditText password;
    private String err = "Incorrect username or password. Please Try again.";

    private boolean loggedin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.email_input);
        password = (EditText) findViewById(R.id.password_input);

        loginButton = (AppCompatButton) findViewById(R.id.login_button);

        loginButton.setOnClickListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedin = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        if(loggedin){
            //We will start the Profile Activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }

    private void login(View view){

        final String inputUsername = username.getText().toString();
        final String inputPassword = password.getText().toString();

        final Map<String,String> params = new HashMap<String, String>();

        RequestQueue queue = Volley.newRequestQueue(this);

        final Context context = this;
        String url ="http://52.11.120.68:8080/Android/androidLogin";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        String resp = response;
                        System.out.println(resp);
                        if (resp.equals(Config.LOGIN_SUCCESS)) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else{
                            ((TextView)findViewById(R.id.login_message)).setText(err);
                        }
                    }
                },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError e){
                            e.printStackTrace();
                        }
                    }
                ) {
            @Override
            protected Map<String, String> getParams(){
                params.put(Config.KEY_EMAIL, inputUsername);
                params.put(Config.KEY_PASSWORD, inputPassword);
                return params;
            }
        };
        //System.out.println(postRequest.getUrl());
        queue.add(postRequest);
        return;

    }


    @Override
    public void onClick(View view){
        //System.out.println("Clicked");
        login(view);
    }

}
