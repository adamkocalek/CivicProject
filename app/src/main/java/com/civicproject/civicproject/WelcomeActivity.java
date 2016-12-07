package com.civicproject.civicproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class WelcomeActivity extends Activity {
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    Thread splashTread;
    Thread textThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        StartAnimations();
    }

    private void StartAnimations() {
        ImageView iv = (ImageView) findViewById(R.id.splash);
        final ProgressBar bar = (ProgressBar) findViewById(R.id.loading);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);
        iv.clearAnimation();
        iv.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();

        bar.setVisibility(View.GONE);
        //iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited1 = 0;
                    while (waited1 < 5000) {
                        sleep(100);
                        waited1 += 100;
                    }
                    Intent intent = new Intent(WelcomeActivity.this,
                            LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    WelcomeActivity.this.finish();
                } catch (InterruptedException e) {
                    System.out.println(e);
                } finally {
                    WelcomeActivity.this.finish();
                }

            }
        };

        textThread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited1 = 0;
                    while (waited1 < 2500) {
                        sleep(100);
                        waited1 += 100;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bar.setVisibility(View.VISIBLE);
                        }
                    });

                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        };
        splashTread.start();
        textThread.start();
    }
}