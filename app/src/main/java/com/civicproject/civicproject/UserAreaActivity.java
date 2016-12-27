package com.civicproject.civicproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class UserAreaActivity extends AppCompatActivity {

    Button buttonEdit, buttonDeleteUser;
    TextView textViewUsername;
    EditText editTextName, editTextSurname, editTextUsername, editTextPassword, editTextAge, editTextTelephone, editTextEmail;
    String userId;
    private MyFTPClientFunctions ftpclient = null;
    private static final String TAG = "UserAreaActivity";
    String loginsDownloaded = null, loginsUpdated = null;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        overridePendingTransition(R.anim.right_in,R.anim.left_out);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_user_area);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        init();
        events();

        SharedPreferences myprefs = getSharedPreferences("user", MODE_PRIVATE);
        String username = myprefs.getString("username", null);
        String name = myprefs.getString("name", null);
        String surname = myprefs.getString("surname", null);
        String age = myprefs.getString("age", null);
        String password = myprefs.getString("password", null);
        String telephone = myprefs.getString("telephone", null);
        String email = myprefs.getString("email", null);
        userId = myprefs.getString("author_key", null);

        editTextUsername.setText(username);
        editTextAge.setText(age);
        editTextName.setText(name);
        editTextSurname.setText(surname );
        editTextPassword.setText(password );
        textViewUsername.setText(username);
        editTextTelephone.setText(telephone);
        editTextEmail.setText(email);
    }

    public void init() {
        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        buttonDeleteUser = (Button) findViewById(R.id.buttonDeleteUser);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextSurname = (EditText) findViewById(R.id.editTextrSurname);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextTelephone = (EditText) findViewById(R.id.editTextTelephone);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        ftpclient = new MyFTPClientFunctions();
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
                String telephone = editTextTelephone.getText().toString();
                String email = editTextEmail.getText().toString();
                BackgroundWorker backgroundWorker = new BackgroundWorker(UserAreaActivity.this);
                backgroundWorker.execute(type, name, surname, age, login, password, telephone, email);
            }
        });

        buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ftpDownloadFileWithLogins();
                loginsUpdated.replace(editTextUsername.getText(), "");
                ftpUploadFileWithLogins();

                String type = "deleteUser";;
                BackgroundWorker backgroundWorker = new BackgroundWorker(UserAreaActivity.this);
                backgroundWorker.execute(type, userId);
                Intent myIntent = new Intent(UserAreaActivity.this, LoginActivity.class);
                UserAreaActivity.this.startActivity(myIntent);
                Toast toast = Toast.makeText(getApplicationContext(), "Twoje konto zostalo skasowane", Toast.LENGTH_LONG);
                toast.show();
            }
        });
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

                InputStream input = new ByteArrayInputStream(loginsUpdated.getBytes());

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