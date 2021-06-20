package com.rptrack.plus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.rptrack.plus.R;

public class SplashActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        doTimeDelay();
        handler.postDelayed(runnable, 2500);
    }
    private void doTimeDelay() {
        runnable = new Runnable() {
            @Override
            public void run() {

                Intent intent = null;
                intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                handler.removeCallbacks(runnable);
                finish();


            }
        };
    }
}