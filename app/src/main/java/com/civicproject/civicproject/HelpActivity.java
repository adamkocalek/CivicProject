package com.civicproject.civicproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HelpActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        overridePendingTransition(R.anim.right_in,R.anim.left_out);
    }
}
