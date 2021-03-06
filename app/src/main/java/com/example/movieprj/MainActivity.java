package com.example.movieprj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.movieprj.listview.movieListActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button)findViewById(R.id.button);
        login.setOnClickListener(loginClick);

        btnAbout = findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });
    }// initial value when app starts

    private Button.OnClickListener loginClick = new Button.OnClickListener() {
        @Override
        public void onClick (View v) {
            EditText editName = (EditText) findViewById(R.id.editText);

            String name = editName.getText().toString(); //gets you the contents of edit text

            Intent i = new Intent();
            i.setClass(MainActivity.this, movieListActivity.class); //pass value from main to second
            i.putExtra("name", name);
            startActivity(i); //to secondAct.
            //finish(); //end MainAct.
        }
    };// login onClick
}
