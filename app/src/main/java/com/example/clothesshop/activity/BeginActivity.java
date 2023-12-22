package com.example.clothesshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.clothesshop.R;

public class BeginActivity extends AppCompatActivity {
     ImageView logo, app_name;
     LottieAnimationView lottie;
     float alpha =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
        logo = findViewById(R.id.logo);
        app_name = findViewById(R.id.app_name);
        lottie = findViewById(R.id.lottie);

        app_name.setTranslationX(200);
        logo.setTranslationY(-200);
        app_name.setAlpha(alpha);
        logo.setAlpha(alpha);

        app_name.animate().alpha(1).translationX(0).setDuration(1000).setStartDelay(800).start();
        logo.animate().alpha(1).translationY(0).setDuration(1000).setStartDelay(800).start();
        lottie.animate().translationX(2000).setDuration(3000).setStartDelay(3900);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(BeginActivity.this,LoginActivity.class));
            }
        },4000);
    }
}