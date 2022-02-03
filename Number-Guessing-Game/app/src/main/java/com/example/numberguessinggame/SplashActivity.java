package com.example.numberguessinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView;
    // Using Animation class to set two vars to store animation inside
    Animation animationImage, animationText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        // Load anims to declared vars
        animationImage = AnimationUtils.loadAnimation(this, R.anim.image_animation);
        animationText = AnimationUtils.loadAnimation(this, R.anim.text_animation);

        // Set anims to corresponding views
        textView.setAnimation(animationText);
        imageView.setAnimation(animationImage);

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                // what happens each second
            }

            @Override
            public void onFinish() {
                // Animation takes 4 secs, on 5th sec main activity is opened
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                // this method removes Splash activity from back stack, so you can't go back to it
                finish();
            }
        }.start();

    }
}