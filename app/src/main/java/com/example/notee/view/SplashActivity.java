package com.example.notee.view;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notee.R;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DURATION = 2000; // Two seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);

            startActivity(intent);

            finish();
        }, SPLASH_SCREEN_DURATION);
    }
}