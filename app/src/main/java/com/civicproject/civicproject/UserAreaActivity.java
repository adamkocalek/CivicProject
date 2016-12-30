package com.civicproject.civicproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class UserAreaActivity extends AppCompatActivity {

    Button buttonEdit;
    TextView textViewUsername, tvDeleteUser;
    EditText editTextName, editTextSurname, editTextUsername, editTextPassword, editTextAge, editTextTelephone, editTextEmail;
    String userId;

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
        tvDeleteUser = (TextView) findViewById(R.id.tvDeleteUser);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextSurname = (EditText) findViewById(R.id.editTextrSurname);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextTelephone = (EditText) findViewById(R.id.editTextTelephone);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
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

        tvDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "deleteUser";
                BackgroundWorker backgroundWorker = new BackgroundWorker(UserAreaActivity.this);
                backgroundWorker.execute(type, userId);
                finish();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}