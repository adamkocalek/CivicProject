package com.civicproject.civicproject;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.duration;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class RegisterActivity extends AppCompatActivity {
    EditText etRegisterAge, etRegisterName, etRegisterUsername, etRegisterPassword, etRegisterSurname, editTextTelephone, editTextEmail;
    TextView tvRegisterRules;
    private MyFTPClientFunctions ftpclient = null;
    private static final String TAG = "RegisterActivity";
    String loginsDownloaded = null, loginsUptaded = null;
    private Validator validator = null;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        overridePendingTransition(R.anim.right_in,R.anim.left_out);

        etRegisterAge = (EditText) findViewById(R.id.etRegisterAge);
        etRegisterName = (EditText) findViewById(R.id.etRegisterName);
        etRegisterUsername = (EditText) findViewById(R.id.etRegisterUsername);
        etRegisterPassword = (EditText) findViewById(R.id.etRegisterPassword);
        etRegisterSurname = (EditText) findViewById(R.id.etRegisterSurname);
        editTextTelephone = (EditText) findViewById(R.id.editTextTelephone);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        tvRegisterRules = (TextView) findViewById(R.id.tvRegisterRules);

        ftpclient = new MyFTPClientFunctions();
        ftpDownloadFileWithLogins();

        tvRegisterRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rulesIntent = new Intent(RegisterActivity.this, RulesActivity.class);
                RegisterActivity.this.startActivity(rulesIntent);
            }
        });
    }

    public void onRegisterButtonClick(View view) {
        String str_name = etRegisterName.getText().toString();
        String str_surname = etRegisterSurname.getText().toString();
        String str_age = etRegisterAge.getText().toString();
        String str_username = etRegisterUsername.getText().toString();
        String str_password = etRegisterPassword.getText().toString();
        String str_telephone = editTextTelephone.getText().toString();
        String str_email = editTextEmail.getText().toString();
        String type = "register";

        if (!str_name.isEmpty() && !str_surname.isEmpty() && !str_age.isEmpty() && !str_username.isEmpty() && !str_password.isEmpty() && !str_telephone.isEmpty() && !str_email.isEmpty()) {
            int i = 0;
            Scanner scanner = new Scanner(loginsDownloaded);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.equals(str_username)) {
                    i++;
                }
            }
            scanner.close();
            if (i == 0) {
                loginsUptaded = loginsDownloaded + "\n" + str_username;
                ftpUploadFileWithLogins();
                BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                backgroundWorker.execute(type, str_name, str_surname, str_age, str_username, str_password, str_telephone, str_email);
                Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(myIntent);
            } else {
                Toast.makeText(getApplicationContext(), "Wybrany login już istnieje.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Wszystkie pola muszą zostać uzupełnione.", Toast.LENGTH_SHORT).show();
        }
    }

    public void ftpUploadFileWithLogins() {
        new Thread(new Runnable() {
            public void run() {
                boolean status = false;
                status = ftpclient.ftpConnect("serwer1633804.home.pl", "serwer1633804", "33murs0tKiby", 21);
                if (status == true) {
                    Log.d(TAG, "Połączenie udane");
                } else {
                    Log.d(TAG, "Połączenie nieudane");
                }

                InputStream input = new ByteArrayInputStream(loginsUptaded.getBytes());

                ftpclient.ftpChangeDirectory("/important_data/");
                ftpclient.ftpUploadString(input, "Logins.txt");

                status = ftpclient.ftpDisconnect();
                if (status == true) {
                    Log.d(TAG, "Połączenie zakończone");
                } else {
                    Log.d(TAG, "Połączenie nie mogło zostać zakończone");
                }
            }
        }).start();
    }

    public void ftpDownloadFileWithLogins() {
        new Thread(new Runnable() {
            public void run() {
                boolean status = false;
                status = ftpclient.ftpConnect("serwer1633804.home.pl", "serwer1633804", "33murs0tKiby", 21);
                if (status == true) {
                    Log.d(TAG, "Połączenie udane");
                } else {
                    Log.d(TAG, "Połączenie nieudane");
                }

                ftpclient.ftpChangeDirectory("/important_data/");
                loginsDownloaded = ftpclient.ftpDownloadString("Logins.txt");

                status = ftpclient.ftpDisconnect();
                if (status == true) {
                    Log.d(TAG, "Połączenie zakończone");
                } else {
                    Log.d(TAG, "Połączenie nie mogło zostać zakończone");
                }
            }
        }).start();
    }
}