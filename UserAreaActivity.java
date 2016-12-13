package com.civicproject.civicproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import org.json.*;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    Button buttonEdit;
    TextView textViewUserArea;
    EditText editTextName, editTextSurname, editTextUsername, editTextPassword, editTextAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        init();
        events();



        SharedPreferences myprefs = getSharedPreferences("user", MODE_WORLD_READABLE);
        String username = myprefs.getString("username", null);
        String name = myprefs.getString("name", null);
        String surname = myprefs.getString("surname", null);
        String age = myprefs.getString("age", null);
        String password = myprefs.getString("password", null);

        editTextUsername.setText(username + "");
        editTextAge.setText(age + "");
        editTextName.setText(name + "");
        editTextSurname.setText(surname + "");
        editTextPassword.setText(password + "");


    }

    public void init() {
        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        textViewUserArea = (TextView) findViewById(R.id.textViewUserArea);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextSurname = (EditText) findViewById(R.id.editTextrSurname);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
    }



    public void events() {
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "updateUser";
                String name = editTextName.getText().toString();
                String surname = editTextSurname.getText().toString();
                String age = editTextAge.getText().toString();
                String login = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                BackgroundWorker backgroundWorker = new BackgroundWorker(UserAreaActivity.this);
                backgroundWorker.execute(type, name, surname, age, login, password);
            }
        });
    }


}
