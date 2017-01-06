package com.civicproject.civicproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserProjectsActivity extends AppCompatActivity {
    ListView listViewMyProjects;
    String author_key;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_projects);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_user_projects);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        listViewMyProjects = (ListView) findViewById(R.id.listViewMyProjects);

        SharedPreferences myprefs = getSharedPreferences("user", MODE_PRIVATE);
        author_key = myprefs.getString("author_key", null);

        BackgroundWorker backgroundWorker = new BackgroundWorker(UserProjectsActivity.this, UserProjectsActivity.this, listViewMyProjects);
        backgroundWorker.execute("getMyProjects", author_key);
    }
}
