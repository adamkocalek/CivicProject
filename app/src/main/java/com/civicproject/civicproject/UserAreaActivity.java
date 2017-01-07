package com.civicproject.civicproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserAreaActivity extends AppCompatActivity {

    Button buttonEdit;
    TextView textViewUsername, tvDeleteUser;
    EditText editTextName, editTextSurname, editTextUsername, editTextPassword, editTextAge, editTextTelephone, editTextEmail;
    String userId;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_user_area);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
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
        editTextSurname.setText(surname);
        editTextPassword.setText(password);
        textViewUsername.setText(username);
        editTextTelephone.setText(telephone);
        editTextEmail.setText(email);
    }

    public void init() {
        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        tvDeleteUser = (TextView) findViewById(R.id.tvDeleteUser);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextSurname = (EditText) findViewById(R.id.editTextSurname);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextTelephone = (EditText) findViewById(R.id.editTextTelephone);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
    }

    public void events() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(UserAreaActivity.this, R.style.Dialog_Theme))
                .setTitle("Wystąpił błąd!")
                .setMessage("Problem z dostępem do internetu. Sprawdź połączenie i spróbuj ponownie później.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean networkCheck = isOnline();
                if(!networkCheck){
                    Log.d("LOG", "Błąd połączenia z internetem.");
                    alertDialog.show();
                    return;
                }

                String editTextName_check = editTextName.getText().toString();
                String editTextSurname_check = editTextSurname.getText().toString();
                String editTextAge_check = editTextAge.getText().toString();
                String editTextPassword_check = editTextPassword.getText().toString();
                String editTextTelephone_check = editTextTelephone.getText().toString();
                String editTextEmail_check = editTextEmail.getText().toString();

                if (TextUtils.isEmpty(editTextSurname_check) || TextUtils.isEmpty(editTextName_check) || TextUtils.isEmpty(editTextAge_check) || TextUtils.isEmpty(editTextPassword_check)
                        || TextUtils.isEmpty(editTextTelephone_check) || TextUtils.isEmpty(editTextEmail_check)) {

                    if (TextUtils.isEmpty(editTextName_check)) {
                        editTextName.setError("Pole nie może być puste!");
                    }
                    if (TextUtils.isEmpty(editTextSurname_check)) {
                        editTextSurname.setError("Pole nie może być puste!");
                    }
                    if (TextUtils.isEmpty(editTextAge_check)) {
                        editTextAge.setError("Pole nie może być puste!");
                    }
                    if (TextUtils.isEmpty(editTextPassword_check)) {
                        editTextPassword.setError("Pole nie może być puste!");
                    }
                    if (TextUtils.isEmpty(editTextTelephone_check)) {
                        editTextTelephone.setError("Pole nie może być puste!");
                    }
                    if (TextUtils.isEmpty(editTextEmail_check)) {
                        editTextEmail.setError("Pole nie może być puste!");
                    }

                    Toast.makeText(getApplicationContext(), "Wszystkie pola muszą zostać uzupełnione.", Toast.LENGTH_SHORT).show();

                } else {
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
            }
        });

        tvDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean networkCheck = isOnline();
                if(!networkCheck){
                    Log.d("LOG", "Błąd połączenia z internetem.");
                    alertDialog.show();
                    return;
                }

                new AlertDialog.Builder(new ContextThemeWrapper(UserAreaActivity.this, R.style.Dialog_Theme))
                        .setTitle("Potwierdzenie").setMessage("Czy na pewno chcesz usunąć konto?")
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String type = "deleteUser";
                                BackgroundWorker backgroundWorker = new BackgroundWorker(UserAreaActivity.this);
                                backgroundWorker.execute(type, userId);
                                finish();

                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });

        editTextName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextName.setText("");
            }
        });

        editTextSurname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextSurname.setText("");
            }
        });

        editTextAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextAge.setText("");
            }
        });

        editTextPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextPassword.setText("");
            }
        });

        editTextEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextEmail.setText("");
            }
        });

        editTextTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextTelephone.setText("");
            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}