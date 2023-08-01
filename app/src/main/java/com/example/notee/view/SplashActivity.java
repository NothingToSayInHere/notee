package com.example.notee.view;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notee.R;

public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_SCREEN_DURATION = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Wait for a few seconds and then navigate to the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finish this activity so it would not be in the back stack
            }
        }, SPLASH_SCREEN_DURATION);
    }
}
