package com.civicproject.civicproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView tvAboutText,tvAboutUsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        tvAboutText = (TextView) findViewById(R.id.tvAboutText);
        tvAboutUsText = (TextView) findViewById(R.id.tvAboutUs);
        String textAbout = "Jest dobrze wchuj, testowy tekst do sprawdzenia widoku, musi być długi więc znajdują się tu pierdoły. Patryk się nie myje.";
        String textAboutUs = "Pozdrawiamy, wybitni programiści PPAP";
        tvAboutText.setText(textAbout);
        tvAboutUsText.setText(textAboutUs);
    }
}
