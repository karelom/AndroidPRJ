package com.example.movieprj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.movieprj.listview.movieListActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnAbout;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAbout = findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });

        login = findViewById(R.id.Button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //利用Toast的靜態函式makeText來建立Toast物件
//                Toast toast = Toast.makeText(MainActivity.this,
//                        "Hello world!", Toast.LENGTH_LONG);
//                //顯示Toast
//                toast.show();
                EditText editName = (EditText) findViewById(R.id.editText);

                String name = editName.getText().toString(); //gets you the contents of edit text

                Intent i = new Intent();
                i.setClass(MainActivity.this, movieListActivity.class); //pass value from main to second
                i.putExtra("name", name);
                startActivity(i); //to secondAct.
                //finish(); //end MainAct.
            }
        });// login onClick
    }// initial value when app starts
}
