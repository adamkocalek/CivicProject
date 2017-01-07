package com.civicproject.civicproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class UserProjectActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonEditProject, buttonDeleteProject;
    ImageButton buttonLikeProject;
    TextView textViewLocation, textViewDate, textViewLike, textViewLikesNames;
    LocationManager locationManager;
    LocationListener locationListener;
    EditText editTextSubject, editTextDesctiption;
    ImageView imageViewPicture;
    String id, author_key, image, likesidss, author_id, author, likesnamestemp;

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

        if (!isOnline()) {
            onBackPressed();
            Toast.makeText(getApplicationContext(), "Brak połączenia z internetem.", Toast.LENGTH_SHORT).show();
            return;
        }

        setContentView(R.layout.scrolling_userproject);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_userproject);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        textViewLike = (TextView) findViewById(R.id.textViewLike);
        textViewLocation = (TextView) findViewById(R.id.textViewLocation);
        textViewLikesNames = (TextView) findViewById(R.id.textViewLikesNames);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextDesctiption = (EditText) findViewById(R.id.editTextDesctiption);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        imageViewPicture = (ImageView) findViewById(R.id.imageViewPictureExist);
        buttonEditProject = (Button) findViewById(R.id.buttonEditProject);
        buttonLikeProject = (ImageButton) findViewById(R.id.buttonLikeProject);
        buttonDeleteProject = (Button) findViewById(R.id.buttonDeleteProject);
        textViewDate = (TextView) findViewById(R.id.textViewDate);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        editTextSubject.setText(intent.getStringExtra("subject"));
        editTextDesctiption.setText(intent.getStringExtra("description"));
        textViewDate.setText(intent.getStringExtra("date"));
        textViewLike.setText(intent.getStringExtra("likes"));
        textViewLikesNames.setText(intent.getStringExtra("likesnames"));
        likesidss = intent.getStringExtra("likesids");
        likesnamestemp = intent.getStringExtra("likesnames");
        author_key = intent.getStringExtra("author_key");
        toolbar.setTitle(intent.getStringExtra("subject"));

        image = intent.getStringExtra("image");
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute("getImage", image, "UserProjectActivity");

        SharedPreferences myprefs = getSharedPreferences("user", MODE_PRIVATE);

        String name = myprefs.getString("name", null);
        String surname = myprefs.getString("surname", null);
        author = name + " " + surname;

        String location = intent.getStringExtra("location");
        String[] splited = location.split("\\s+");
        if (splited.length > 1) {
            String fullAddress = getCompleteAddressString(Double.parseDouble(splited[0]), Double.parseDouble(splited[1]));

            String splitLocation[];
            splitLocation = fullAddress.split("\n");
            fullAddress = splitLocation[0] + ", " + splitLocation[1];
            textViewLocation.setText(fullAddress);

        } else {
            textViewLocation.setText("Brak lokalizacji.");
        }

        author_id = myprefs.getString("author_key", null);
        if (likesidss.contains(author_id)) {
            buttonLikeProject.setVisibility(View.INVISIBLE);
        } else {
            buttonLikeProject.setVisibility(View.VISIBLE);
        }

        // CZY JEST AUTOREM??
        if (Integer.parseInt(author_id) != Integer.parseInt(author_key)) {
            buttonEditProject.setVisibility(View.INVISIBLE);
            buttonDeleteProject.setVisibility(View.INVISIBLE);
        } else {
            buttonEditProject.setVisibility(View.VISIBLE);
            buttonDeleteProject.setVisibility(View.VISIBLE);
        }
    }

    public void onDeleteProjectButtonClick(View view) {
        String type = "deleteProject";
        BackgroundWorker backgroundWorker = new BackgroundWorker(UserProjectActivity.this);
        backgroundWorker.execute(type, id);
        Intent myIntent = new Intent(UserProjectActivity.this, LoginActivity.class);
        UserProjectActivity.this.startActivity(myIntent);
    }

    public void onLikeProjectButtonClick(View view) {
        int likes = Integer.parseInt((String) textViewLike.getText()) + 1;
        textViewLike.setText(likes + "");
        likesnamestemp = likesnamestemp + author + ",";
        likesidss = likesidss + author_id + ",";
        String type = "updateProjectLikes";
        BackgroundWorker backgroundWorker = new BackgroundWorker(UserProjectActivity.this);
        backgroundWorker.execute(type, likesidss, likes + "", id, likesnamestemp);
        buttonLikeProject.setVisibility(View.INVISIBLE);
    }

    public void onEditProjectButtonClick(View view) {
        String subject = editTextSubject.getText().toString();
        String description = editTextDesctiption.getText().toString();
        System.out.println(subject + "       " + description + "          " + id);
        String type = "updateProject";
        BackgroundWorker backgroundWorker = new BackgroundWorker(UserProjectActivity.this);
        backgroundWorker.execute(type, subject, description, id);
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

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
