package com.example.movieprj.listview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.movieprj.R;

public class movieListActivity extends AppCompatActivity {

    private ListView movieList;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        // get intent from main activity
        Intent i = getIntent();
        String s = i.getStringExtra("name");
        TextView show = (TextView) findViewById(R.id.textView);
        show.setText(s);

        movieList = findViewById(R.id.listView);
        movieList.setAdapter(new MyListAdapter(movieListActivity.this));
    }
}
