package com.civicproject.civicproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    EditText etLoginUsername, etLoginPassword;
    Button bLogin;
    Button bRegisterLink;
    TextView tvAbout;
    int back;

    @Override
    public void onBackPressed() {
        if(back!=0){
            finish();
            System.exit(0);
        }else{
            Snackbar snackbar = Snackbar.make(getCurrentFocus(), "Wciśnij jeszcze raz aby wyjść...", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        back =1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        back = 0;
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.right_in,R.anim.left_out);

        etLoginUsername = (EditText) findViewById(R.id.etLoginUsername);
        etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);
        bRegisterLink = (Button) findViewById(R.id.bRegisterLink);
        bLogin = (Button) findViewById(R.id.bSignIn);
        tvAbout = (TextView) findViewById(R.id.tvAbout);

        //bLogin.setEnabled(false);

        // ZMIANA LOGIN TEXT
//        etLoginUsername.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(!s.toString().isEmpty() && !etLoginPassword.getText().toString().isEmpty()) {
//                    bLogin.setEnabled(true);
//                }else{
//                    bLogin.setEnabled(false);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        // ZMIANA PASSWORD TEXT
//        etLoginPassword.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(!s.toString().isEmpty() && !etLoginUsername.getText().toString().isEmpty()){
//                    bLogin.setEnabled(true);
//                }else{
//                    bLogin.setEnabled(false);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        bRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        tvAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutIntent = new Intent(LoginActivity.this, AboutActivity.class);
                LoginActivity.this.startActivity(aboutIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etLoginUsername.getText().toString();
                String password = etLoginPassword.getText().toString();
                String type = "login";

                SharedPreferences myprefs = LoginActivity.this.getSharedPreferences("user", MODE_PRIVATE);
                myprefs.edit().putString("username", username).commit();

                BackgroundWorker backgroundWorker = new BackgroundWorker(LoginActivity.this);
                backgroundWorker.execute(type, username, password);
            }
        });
    }
}
