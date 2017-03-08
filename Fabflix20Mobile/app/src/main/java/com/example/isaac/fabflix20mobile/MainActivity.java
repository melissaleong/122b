package com.example.isaac.fabflix20mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText movie;
    private AppCompatButton searchButton;
    private String searchType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movie = (EditText) findViewById(R.id.movie_input);
        searchButton = (AppCompatButton) findViewById(R.id.search_button);

        ToggleButton toggle = (ToggleButton) findViewById(R.id.search_mode);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    searchType= "pattern";
                }
                else{
                    searchType="fulltext";
                }
            }
        });

        searchButton.setOnClickListener(this);
    }

    public void search(View view){

        final String movieInput = movie.getText().toString();

        final Map<String,String> params = new HashMap<String, String>();

        RequestQueue queue = Volley.newRequestQueue(this);

        String url ="http://52.11.120.68:8080/fabflix_webapp/androidSearch";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        String resp = response;
                        System.out.println(resp);
                        //ArrayList<String> movieList = createMovieList(resp);
                        Intent intent = new Intent(MainActivity.this, Search.class);
                        //intent.putStringArrayListExtra("MovieList", movieList);
                        intent.putExtra("MovieList",resp );
                        startActivity(intent);

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
            protected Map<String,String> getParams(){
                params.put("movie", movieInput);
                params.put("searchType", searchType);
                return params;
            }
        };
        queue.add(postRequest);
        return;
    }

    @Override
    public void onClick(View view){
        search(view);
    }

    public ArrayList<String> createMovieList(String movies){
        ArrayList<String> movieList=new ArrayList<String>();

        String[] allMovies = movies.split("\t");
        for (String m : allMovies){
            movieList.add(m);
        }

        return movieList;
    }
}
