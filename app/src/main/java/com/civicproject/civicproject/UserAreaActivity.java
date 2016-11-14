package com.civicproject.civicproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity{

    String getUser_url = "http://192.168.0.102/getUser.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);


        TextView textViewUserArea = (TextView) findViewById(R.id.textViewUserArea);
        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        EditText editTextSurname = (EditText) findViewById(R.id.editTextrSurname);
        EditText editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        EditText editTextAge = (EditText) findViewById(R.id.editTextAge);

        SharedPreferences myprefs = getSharedPreferences("user", MODE_WORLD_READABLE);
        String username = myprefs.getString("username", null);
        String password = myprefs.getString("username", null);
        editTextUsername.setText(username);
        editTextPassword.setText(password);
//        final Downloader downloader = new Downloader(this, getUser_url, listView);
//        downloader.execute();
//
        String type = "getUser";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username);
    }

}
