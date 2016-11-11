package com.civicproject.civicproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WelcomeActivity extends AppCompatActivity {
    ImageView imageViewWelcome;
    TextView textViewWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        imageViewWelcome = (ImageView)findViewById(R.id.imageViewWelcome);
        textViewWelcome = (TextView)findViewById(R.id.textViewWelcome);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.welcome_animation);
        imageViewWelcome.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
