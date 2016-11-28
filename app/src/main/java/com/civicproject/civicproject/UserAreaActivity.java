package com.civicproject.civicproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import org.json.*;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

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
        System.out.println(username);
        editTextUsername.setText(username + "");
        Log.d("h", username);
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute("getUser", username);
        String json = backgroundWorker.tempAuthor;
        editTextName.setText(json);
        try {
            Thread.sleep(1000);
            //ADD THAT DATA TO JSON ARRAY FIRST
            JSONArray ja = new JSONArray(json);
            //CREATE JO OBJ TO HOLD A SINGLE ITEM
            JSONObject jo = null;
            jo = ja.getJSONObject(0);
            //RETRIOEVE DATA
            String name = jo.getString("name");
            String surname = jo.getString("surname");
            String age = jo.getString("age");
            String password = jo.getString("password");

            editTextAge.setText(age + "");
            editTextName.setText(name + "");
            editTextSurname.setText(surname + "");
            editTextPassword.setText(password + "");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
