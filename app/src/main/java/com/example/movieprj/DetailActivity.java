package com.example.movieprj;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieprj.listview.MyListAdapter;
import com.example.movieprj.listview.movieListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    //声明所有控件
    private TextView tvName;
    private TextView tvScore;
    private TextView tvTime;
    private TextView tvLength;
    private TextView tvLanguage;
    private TextView tvDescription;
    private ImageView ivPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        tvName =  findViewById(R.id.tv_movieName);
        tvScore = findViewById(R.id.tv_score);
        tvDescription = findViewById(R.id.tv_description);
        tvLanguage = findViewById(R.id.tv_language);
        tvTime = findViewById(R.id.tv_onScreenTime);
        tvLength = findViewById(R.id.tv_length);
        ivPoster = findViewById(R.id.iv);

        Intent intent = getIntent();
        String movieid = intent.getStringExtra("movie_id");;
        Log.d("Test2:",movieid);

        new SetInfo().execute(movieid);

    }

    //链接API库
    private class SetInfo extends AsyncTask<String, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        HashMap<String, String> hashmap = new HashMap<String, String>();

        @Override
        protected String doInBackground(String... params) {
            try {
                String $url_json = "https://api.themoviedb.org/3/movie/"+params[0]+"?api_key=db6f65246e5ecf8ecf9eb07210b60456&language=en-US";
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
                Log.d("FOR_LOG", resultJson);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);

            try {
                JSONObject json = new JSONObject(strJson);
                JSONArray jArray = json.getJSONArray("spoken_languages");
                JSONObject spoken_languages = jArray.getJSONObject(0);


                    hashmap.put("tv_movieName", "" + json.getString("original_title"));
                    hashmap.put("tv_score", "" + json.getString("vote_average"));
                    hashmap.put("tv_onScreenTime", "" + json.getString("release_date"));
                    hashmap.put("tv_length", "" + json.getString("runtime"));
                    hashmap.put("tv_language", "" + spoken_languages.get("name"));
                    hashmap.put("tv_description", "" + json.getString("overview"));
                    hashmap.put("posterPath", "https://image.tmdb.org/t/p/w400"+json.getString("poster_path"));
//                    The API doesn't provide this information- -!
//                    hashmap.put("artistName", "" + json.getString("artistName"));
//
                String name = hashmap.get("tv_movieName");
                String score = hashmap.get("tv_score");
                String des = hashmap.get("tv_description");
                String language = hashmap.get("tv_language");
                String time = hashmap.get("tv_onScreenTime");
                String length = hashmap.get("tv_length") + " min";
                String poster =hashmap.get("posterPath");


                //将数据传入相应得控件
                tvName.setText(name);
                tvScore.setText(score);
                tvDescription.setText(des);
                tvLanguage.setText(language);
                tvTime.setText(time);
                tvLength.setText(length);
                Glide.with(DetailActivity.this).load(poster).into(ivPoster);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            tvName.setText(hashmap.get("tv_movieName"));
//            tvScore.setText(hashmap.get("tv_score"));
//            tvDescription.setText(hashmap.get("tv_description"));
//            tvLanguage.setText(hashmap.get("tv_language"));
//            tvTime.setText(hashmap.get("tv_movieOnscreenTime"));
//            tvLength.setText(hashmap.get("tv_length"));
//            Glide.with(DetailActivity.this).load(hashmap.get("posterPath")).into(ivPoster);
//        }


    }


}
