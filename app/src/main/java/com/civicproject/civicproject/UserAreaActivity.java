package com.civicproject.civicproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAreaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        TextView textViewUserArea = (TextView) findViewById(R.id.textViewUserArea);
        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        EditText editTextrSurname = (EditText) findViewById(R.id.editTextrSurname);
        EditText editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        EditText editTextAge = (EditText) findViewById(R.id.editTextAge);


    }
}
