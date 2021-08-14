package com.example.photoflickr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 5000;

    Animation topAnim, bottomAnim;
    ImageView logoSplashScreen;
    TextView tvSplashScreen, tvSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        initUi();

        //Xet animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logoSplashScreen.setAnimation(topAnim);
        tvSplashScreen.setAnimation(bottomAnim);
        tvSlogan.setAnimation(bottomAnim);

        //Delay activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }

    private void initUi() {
        logoSplashScreen = findViewById(R.id.logo_splash_screen);
        tvSplashScreen = findViewById(R.id.tv_splash_screen);
        tvSlogan = findViewById(R.id.tv_slogan_splash_screen);
    }
}