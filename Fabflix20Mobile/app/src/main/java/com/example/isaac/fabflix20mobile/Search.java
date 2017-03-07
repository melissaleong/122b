package com.example.isaac.fabflix20mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bundle bundle = getIntent().getExtras();
        String movies = bundle.getString("MovieList");

        TextView allMovies= (TextView)findViewById(R.id.list_view);
        String[] splitString = movies.split("\n");
        allMovies.append("Search Results \n");
        allMovies.append("----------------------------------- \n");
        for (String line : splitString){
            allMovies.append(line);
            allMovies.append("\n");
        }

    }

//    private String[] arrayListtoArray(ArrayList<String> movieList){
//        String[] result = new String[movieList.size()];
//        for (int i = 0; i<movieList.size();i++){
//            result[i] = movieList.get(i);
//        }
//        return result;
//    }
}
