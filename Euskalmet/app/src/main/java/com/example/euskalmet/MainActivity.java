package com.example.euskalmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar = null;
   private ImageView logo;

    class MainActivityAsyncTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected void onPreExecute() {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            );
        }

        @Override
        protected String doInBackground(Integer... params) {

            try {
                Thread.sleep(500);
                progressBar.setProgress(25);
            } catch (InterruptedException e) {
                // Nothing...
            }
            try {
                Thread.sleep(500);
                progressBar.setProgress(50);
            } catch (InterruptedException e) {
                // Nothing...
            }
            try {
                Thread.sleep(500);
                progressBar.setProgress(75);
            } catch (InterruptedException e) {
                // Nothing...
            }
            try {
                Thread.sleep(500);
                progressBar.setProgress(100);
            } catch (InterruptedException e) {
                // Nothing...
            }

            return "";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            //progressBar.setVisibility( View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            (findViewById(R.id.textView)).setVisibility( View.VISIBLE );
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //(findViewById(R.id.constraintLayout)).setBackgroundColor( Color.parseColor("#000000"));

        findViewById(R.id.constraintLayout).setOnTouchListener(
                (v, m) -> {
                    Intent intent = new Intent( MainActivity.this, LoginActivity.class );
                    startActivity(intent);
                    return true;
                }
        );

        ImageView imageView = findViewById(R.id.imageView_LoadingGif);
        Glide.with(this).load(R.raw.loadinggif).into(imageView);

        progressBar = findViewById( R.id.datBaseLoadingBar);
        progressBar.setMax(100);
        progressBar.setProgress(0);

        new MainActivityAsyncTask().execute();
    }
}