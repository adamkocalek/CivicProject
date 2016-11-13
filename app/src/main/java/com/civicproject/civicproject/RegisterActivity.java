package com.civicproject.civicproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    EditText etRegisterAge, etRegisterName, etRegisterUsername, etRegisterPassword, etRegisterSurname;

    public void onRegisterButtonClick(View view){
        String str_name = etRegisterName.getText().toString();
        String str_surname = etRegisterSurname.getText().toString();
        String str_age = etRegisterAge.getText().toString();
        String str_username = etRegisterUsername.getText().toString();
        String str_password = etRegisterPassword.getText().toString();
        String type = "register";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, str_name, str_surname, str_age, str_username, str_password);
        etRegisterName.setText("");
        etRegisterSurname.setText("");
        etRegisterAge.setText("");
        etRegisterUsername.setText("");
        etRegisterPassword.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegisterAge = (EditText) findViewById(R.id.etRegisterAge);
        etRegisterName = (EditText) findViewById(R.id.etRegisterName);
        etRegisterUsername = (EditText) findViewById(R.id.etRegisterUsername);
        etRegisterPassword = (EditText) findViewById(R.id.etRegisterPassword);
        etRegisterSurname = (EditText) findViewById(R.id.etRegisterSurname);
    }

}