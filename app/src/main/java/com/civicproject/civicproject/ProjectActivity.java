package com.civicproject.civicproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class ProjectActivity extends AppCompatActivity implements View.OnClickListener {
    Parser parser = new Parser();

    Button buttonEditProject, buttonDeleteProject, buttonPermission;
    ImageButton buttonLikeProject;
    TextView textViewLocation, textViewDate, textViewAuthor, textViewLike, editTextSubject, editTextDesctiption;
    LocationManager locationManager;
    LocationListener locationListener;
    ImageView imageViewPicture;
    String id, author_key, image, likesidss, author_id, author, likesnamestemp;
    Bitmap imageBitmap;
    private MyFTPClientFunctions ftpclient = null;
    private static final String TAG = "ProjectActivity";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrolling_project);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_project);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        textViewLike = (TextView) findViewById(R.id.textViewLike);
        textViewLocation = (TextView) findViewById(R.id.textViewLocation);
        textViewAuthor = (TextView) findViewById(R.id.textViewAuthor);
        editTextSubject = (TextView) findViewById(R.id.editTextSubject);
        editTextDesctiption = (TextView) findViewById(R.id.editTextDesctiption);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        imageViewPicture = (ImageView) findViewById(R.id.imageViewPictureExist);
        buttonEditProject = (Button) findViewById(R.id.buttonEditProject);
        buttonLikeProject = (ImageButton) findViewById(R.id.buttonLikeProject);
        buttonDeleteProject = (Button) findViewById(R.id.buttonDeleteProject);
        textViewDate = (TextView) findViewById(R.id.textViewDate);

        ftpclient = new MyFTPClientFunctions();

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        editTextSubject.setText(intent.getStringExtra("subject"));
        editTextDesctiption.setText(intent.getStringExtra("description"));
        textViewAuthor.setText(intent.getStringExtra("author"));
        textViewDate.setText(intent.getStringExtra("date"));
        textViewLike.setText(intent.getStringExtra("likes"));
        likesidss = intent.getStringExtra("likesids");
        author_key = intent.getStringExtra("author_key");
        likesnamestemp = intent.getStringExtra("likesnames");
        toolbar.setTitle(intent.getStringExtra("subject"));

        image = intent.getStringExtra("image");
        ftpDownloadImage(image);

        String location = intent.getStringExtra("location");
        String[] splited = location.split("\\s+");
        if (splited.length > 1) {
            String fullAddress = getCompleteAddressString(Double.parseDouble(splited[0]), Double.parseDouble(splited[1]));

            String splitLocation[];
            splitLocation = fullAddress.split("\n");
            fullAddress = splitLocation[0] + ", " + splitLocation[1];
            textViewLocation.setText(fullAddress);
        } else {
            textViewLocation.setText("Brak lokalizacji");
        }

        SharedPreferences myprefs = getSharedPreferences("user", MODE_PRIVATE);
        String name = myprefs.getString("name", null);
        String surname = myprefs.getString("surname", null);
        author = name + " " + surname;
        author_id = myprefs.getString("author_key", null);
        if (likesidss.contains(author_id)) {
            buttonLikeProject.setVisibility(View.INVISIBLE);
        } else {
            buttonLikeProject.setVisibility(View.VISIBLE);
        }

        buttonPermission = (Button) findViewById(R.id.buttonPermission);
        buttonPermission.setVisibility(View.INVISIBLE);
        buttonPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "updateProjectPermission";
                String permited = "1";
                BackgroundWorker backgroundWorker = new BackgroundWorker(ProjectActivity.this);
                backgroundWorker.execute(type, permited, id);
            }
        });
    }

    public void onLikeProjectButtonClick(View view) {
        int likes = Integer.parseInt((String) textViewLike.getText()) + 1;
        textViewLike.setText(likes + "");
        likesidss = likesidss + author_id + ",";
        likesnamestemp = likesnamestemp + author + ", ";
        String type = "updateProjectLikes";
        BackgroundWorker backgroundWorker = new BackgroundWorker(ProjectActivity.this);
        backgroundWorker.execute(type, likesidss, likes + "", id, likesnamestemp);
        buttonLikeProject.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {

    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder
                    .getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                android.location.Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress
                            .append(returnedAddress.getAddressLine(i)).append(
                            "\n");
                }
                strAdd = strReturnedAddress.toString();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configureButton();
                break;
            default:
                break;
        }
    }

    private void configureButton() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
    }

    public void ftpDownloadImage(final String srcFilePath) {
        new Thread(new Runnable() {
            public void run() {
                boolean status = false;
                status = ftpclient.ftpConnect("serwer1633804.home.pl", "serwer1633804", "33murs0tKiby", 21);
                if (status == true) {
                    Log.d(TAG, "Połączenie udane");
                } else {
                    Log.d(TAG, "Połączenie nieudane");
                }

                ftpclient.ftpChangeDirectory("/images/");
                imageBitmap = ftpclient.ftpDownloadBitmap(srcFilePath);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageViewPicture.setImageBitmap(imageBitmap);
                    }
                });

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
