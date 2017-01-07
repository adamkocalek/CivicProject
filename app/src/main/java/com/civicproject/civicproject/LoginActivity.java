package com.civicproject.civicproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.civicproject.civicproject.R.layout.dialog;

public class LoginActivity extends AppCompatActivity {
    EditText etLoginUsername, etLoginPassword;
    Button bLogin, bRegisterLink;
    TextView tvLoginAbout, tvLoginRules;
    int back;


    @Override
    public void onBackPressed() {
        if (back != 0) {
            finish();
            System.exit(0);
        } else {
            Snackbar snackbar = Snackbar.make(getCurrentFocus(), "Wciśnij jeszcze raz aby wyjść...", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        back = 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        back = 0;
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

        etLoginUsername = (EditText) findViewById(R.id.etLoginUsername);
        etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);
        bRegisterLink = (Button) findViewById(R.id.bRegisterLink);
        bLogin = (Button) findViewById(R.id.bSignIn);
        tvLoginAbout = (TextView) findViewById(R.id.tvLoginAbout);
        tvLoginRules = (TextView) findViewById(R.id.tvLoginRules);

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(LoginActivity.this, R.style.Dialog_Theme))
                .setTitle("Wystąpił błąd!")
                .setMessage("Problem z dostępem do internetu. Sprawdź połączenie i spróbuj ponownie później.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        bRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean networkCheck = isOnline();
                if(!networkCheck){
                    Log.d("LOG", "Błąd połączenia z internetem.");
                    alertDialog.show();
                    return;
                }

                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        tvLoginAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutIntent = new Intent(LoginActivity.this, AboutActivity.class);
                LoginActivity.this.startActivity(aboutIntent);
            }
        });

        tvLoginRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rulesIntent = new Intent(LoginActivity.this, RulesActivity.class);
                LoginActivity.this.startActivity(rulesIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean networkCheck = isOnline();
                if(!networkCheck){
                    Log.d("LOG", "Błąd połączenia z internetem.");
                    alertDialog.show();
                    return;
                }

                String username = etLoginUsername.getText().toString();
                String password = etLoginPassword.getText().toString();
                String type = "login";

                if (etLoginUsername.getText().toString().isEmpty() || etLoginPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Wszystkie pola muszą być wypełnione.", Toast.LENGTH_SHORT).show();

                } else {
                    SharedPreferences myprefs = LoginActivity.this.getSharedPreferences("user", MODE_PRIVATE);
                    myprefs.edit().putString("username", username).commit();

                    BackgroundWorker backgroundWorker = new BackgroundWorker(LoginActivity.this);
                    backgroundWorker.execute(type, username, password);

                }
            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
