package com.civicproject.civicproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

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
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

        etRegisterAge = (EditText) findViewById(R.id.etRegisterAge);
        etRegisterName = (EditText) findViewById(R.id.etRegisterName);
        etRegisterUsername = (EditText) findViewById(R.id.etRegisterUsername);
        etRegisterPassword = (EditText) findViewById(R.id.etRegisterPassword);
        etRegisterSurname = (EditText) findViewById(R.id.etRegisterSurname);
        editTextTelephone = (EditText) findViewById(R.id.editTextTelephone);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        tvRegisterRules = (TextView) findViewById(R.id.tvRegisterRules);

        validator = new Validator();

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


        if (!validator.isEmpty(str_name) && !validator.isEmpty(str_surname) && !validator.isEmpty(str_age) && !validator.isEmpty(str_username) && !validator.isEmpty(str_password) && !validator.isEmpty(str_telephone) && !validator.isEmpty(str_email)) {
            if (validator.passwordValidator(etRegisterPassword.getText() + "")) {
                if (validator.emailValidator(editTextEmail.getText() + "")) {
                    if (validator.phoneNumberValidator(editTextTelephone.getText() + "")) {
                        int i = 0;
                        Scanner scanner = new Scanner(loginsDownloaded);
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            if (line.equals(str_username)) {
                                i++;
                            }
                        }
                        scanner.close();

                        if (i == 0) {
                            loginsUptaded = loginsDownloaded + "\n" + str_username;
                            ftpUploadFileWithLogins();
                            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                            backgroundWorker.execute(type, validator.trimSpaces(str_name), validator.trimSpaces(str_surname), validator.trimSpaces(str_age), validator.trimSpaces(str_username), validator.trimSpaces(str_password), validator.trimSpaces(str_telephone), validator.trimSpaces(str_email));
                            Toast toast = Toast.makeText(getApplicationContext(), "Zarejestrowano, możesz się zalogować ; )", Toast.LENGTH_LONG);
                            toast.show();
                            Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                            RegisterActivity.this.startActivity(myIntent);

                        } else {
                            etRegisterUsername.setError("Wybrany login już istnieje.");
                            Toast.makeText(getApplicationContext(), "Wybrany login już istnieje.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        editTextTelephone.setError("Numer telefonu musi być 9 cyfrowy (XXXXXXXXX).");
                        Toast.makeText(getApplicationContext(), "Numer telefonu musi być 9 cyfrowy (XXXXXXXXX).", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    editTextEmail.setError("Niepoprawny adres email.");
                    Toast.makeText(getApplicationContext(), "Niepoprawny adres email.", Toast.LENGTH_SHORT).show();
                }
            } else {
                etRegisterPassword.setError("Hasło musi mieć długość od 6 do 20 znaków i zawierać przynajmniej jedną cyfrę.");
                Toast.makeText(getApplicationContext(), "Hasło musi mieć długość od 6 do 20 znaków i zawierać przynajmniej jedną cyfrę.", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (TextUtils.isEmpty(validator.trimSpaces(str_name))) {
                etRegisterName.setError("Pole nie może być puste!");
            }
            if (TextUtils.isEmpty(validator.trimSpaces(str_surname))) {
                etRegisterSurname.setError("Pole nie może być puste!");
            }
            if (TextUtils.isEmpty(validator.trimSpaces(str_age))) {
                etRegisterAge.setError("Pole nie może być puste!");
            }
            if (TextUtils.isEmpty(validator.trimSpaces(str_username))) {
                etRegisterUsername.setError("Pole nie może być puste!");
            }
            if (TextUtils.isEmpty(validator.trimSpaces(str_password))) {
                etRegisterPassword.setError("Pole nie może być puste!");
            }
            if (TextUtils.isEmpty(validator.trimSpaces(str_telephone))) {
                editTextTelephone.setError("Pole nie może być puste!");
            }
            if (TextUtils.isEmpty(validator.trimSpaces(str_email))) {
                editTextEmail.setError("Pole nie może być puste!");
            }

            Toast.makeText(getApplicationContext(), "Wszystkie pola muszą zostać uzupełnione.", Toast.LENGTH_SHORT).show();
        }
    }

    public void ftpUploadFileWithLogins() {
        new Thread(new Runnable() {
            public void run() {
                boolean status;
                status = ftpclient.ftpConnect("serwer1633804.home.pl", "serwer1633804", "33murs0tKiby", 21);
                if (status) {
                    Log.d(TAG, "Połączenie udane");
                } else {
                    Log.d(TAG, "Połączenie nieudane");
                }

                InputStream input = new ByteArrayInputStream(loginsUptaded.getBytes());

                ftpclient.ftpChangeDirectory("/important_data/");
                ftpclient.ftpUploadString(input, "Logins.txt");

                status = ftpclient.ftpDisconnect();
                if (status) {
                    Log.d(TAG, "Połączenie zakończone.");
                } else {
                    Log.d(TAG, "Połączenie nie mogło zostać zakończone.");
                }
            }
        }).start();
    }

    public void ftpDownloadFileWithLogins() {
        new Thread(new Runnable() {
            public void run() {
                boolean status;
                status = ftpclient.ftpConnect("serwer1633804.home.pl", "serwer1633804", "33murs0tKiby", 21);
                if (status) {
                    Log.d(TAG, "Połączenie udane.");
                } else {
                    Log.d(TAG, "Połączenie nieudane.");
                }

                ftpclient.ftpChangeDirectory("/important_data/");
                loginsDownloaded = ftpclient.ftpDownloadString("Logins.txt");

                status = ftpclient.ftpDisconnect();
                if (status) {
                    Log.d(TAG, "Połączenie zakończone.");
                } else {
                    Log.d(TAG, "Połączenie nie mogło zostać zakończone.");
                }
            }
        }).start();
    }
}