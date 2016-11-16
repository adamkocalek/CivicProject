package com.civicproject.civicproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import org.json.*;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

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
        String name = myprefs.getString("name", null);
        String surname = myprefs.getString("surname", null);
        String username = myprefs.getString("username", null);
        String age = myprefs.getString("age", null);
        String password = myprefs.getString("password", null);

        editTextName.setText(name);
        editTextSurname.setText(surname);
        editTextUsername.setText(username);
        editTextAge.setText(age);
        editTextPassword.setText(password);
    }
}
