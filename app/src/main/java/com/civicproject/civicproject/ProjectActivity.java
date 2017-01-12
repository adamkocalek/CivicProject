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

public class ProjectActivity extends AppCompatActivity implements View.OnClickListener {
    Parser parser = new Parser();

    Button buttonEditProject, buttonDeleteProject, buttonPermission, buttonAddComment;
    ImageButton buttonLikeProject;
    TextView textViewLocation, textViewDate, textViewAuthor, textViewLike, editTextSubject, editTextDesctiption, textViewComment;
    LocationManager locationManager;
    LocationListener locationListener;
    ImageView imageViewPicture;
    EditText editTextComment;
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

        setContentView(R.layout.scrolling_project);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_project);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        textViewComment = (TextView) findViewById(R.id.textViewComments);
        textViewLike = (TextView) findViewById(R.id.textViewLike);
        textViewLocation = (TextView) findViewById(R.id.textViewLocation);
        textViewAuthor = (TextView) findViewById(R.id.textViewAuthor);
        editTextSubject = (TextView) findViewById(R.id.editTextSubject);
        editTextDesctiption = (TextView) findViewById(R.id.editTextDesctiption);
        editTextComment = (EditText) findViewById(R.id.editTextAddComment);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        imageViewPicture = (ImageView) findViewById(R.id.imageViewPictureExist);
        buttonEditProject = (Button) findViewById(R.id.buttonEditProject);
        buttonLikeProject = (ImageButton) findViewById(R.id.buttonLikeProject);
        buttonDeleteProject = (Button) findViewById(R.id.buttonDeleteProject);
        buttonAddComment = (Button) findViewById(R.id.buttonAddComment);
        textViewDate = (TextView) findViewById(R.id.textViewDate);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        editTextSubject.setText(intent.getStringExtra("subject"));
        editTextDesctiption.setText(intent.getStringExtra("description"));
        textViewAuthor.setText(intent.getStringExtra("author"));
        textViewDate.setText(intent.getStringExtra("date"));
        textViewLike.setText(intent.getStringExtra("likes"));
        textViewComment.setText(intent.getStringExtra("comments"));
        likesidss = intent.getStringExtra("likesids");
        author_key = intent.getStringExtra("author_key");
        likesnamestemp = intent.getStringExtra("likesnames");
        toolbar.setTitle(intent.getStringExtra("subject"));

        image = intent.getStringExtra("image");
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute("getImage", image, "ProjectActivity");

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

    public void onAddCommentButtonClick(View view) {
        String comment = textViewComment.getText() + "";
        comment = comment + editTextComment.getText() + "; ";
        String type = "updateProjectComments";
        BackgroundWorker backgroundWorker = new BackgroundWorker(ProjectActivity.this);
        backgroundWorker.execute(type, id, comment);
        Toast.makeText(getApplicationContext(), "Dodałeś komentarz", Toast.LENGTH_LONG).show();
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
