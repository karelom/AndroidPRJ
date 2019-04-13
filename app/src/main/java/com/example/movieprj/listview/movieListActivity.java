package com.example.movieprj.listview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieprj.DetailActivity;
import com.example.movieprj.MainActivity;
import com.example.movieprj.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;


public class movieListActivity extends AppCompatActivity {

    private ListView movieList;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        // get intent from main activity
//        Intent i = getIntent();
//        String s = "Welcome! "+i.getStringExtra("name");
//        TextView show = (TextView) findViewById(R.id.textView);
//        show.setText(s);

        movieList = findViewById(R.id.listView);
//        movieList.setAdapter(new MyListAdapter(movieListActivity.this));
        new ParseTask().execute();


        //设置点击事件
        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(movieListActivity.this,"movie id is :"+arrayList.get(position).get("movie_id"),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(movieListActivity.this, DetailActivity.class);
                intent.putExtra("movie_id",arrayList.get(position).get("movie_id"));
                startActivity(intent);
            }
        });

        movieList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(movieListActivity.this,"Long pos : "+position,Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }


    public ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
    //链接API库
    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                String $url_json = "https://api.themoviedb.org/3/discover/movie?api_key=db6f65246e5ecf8ecf9eb07210b60456&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
                URL url = new URL($url_json);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
//                Log.d("FOR_LOG", resultJson);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);

            final ListView lView = (ListView) findViewById(R.id.listView);

            String[] from = {"tv_movieName","tv_movieOnscreenTime","tv_movieDescription","iv"};
            int[] to = {R.id.tv_movieName,R.id.tv_movieOnscreenTime,R.id.tv_movieDescription,R.id.iv};
//            ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> hashmap;

            try {
                JSONObject json = new JSONObject(strJson);
                JSONArray jArray = json.getJSONArray("results");

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject friend = jArray.getJSONObject(i);

                    String movieName = friend.getString("title");
                    String onScreenTime = friend.getString("release_date");
                    String movieDescription =friend.getString("overview");
                    String movieid = friend.getString("id");
                    String moviePoster = "https://image.tmdb.org/t/p/w400"+friend.getString("poster_path");
//                    Log.d("FOR_LOG", moviePoster);

                    hashmap = new HashMap<String, String>();
                    hashmap.put("tv_movieName", "" + movieName);
                    hashmap.put("tv_movieOnscreenTime", "" + onScreenTime);
                    hashmap.put("tv_movieDescription", "" + movieDescription);
                    hashmap.put("iv", "" + moviePoster);
                    hashmap.put("movie_id", "" + movieid);
                    arrayList.add(hashmap);
                }

                MyListAdapter adapter = new MyListAdapter(movieListActivity.this, arrayList);
                lView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
