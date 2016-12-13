package com.civicproject.civicproject;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.R.attr.id;
import static com.civicproject.civicproject.R.id.buttonCamera;
import static com.civicproject.civicproject.R.id.textViewLike;
import static com.google.android.gms.analytics.internal.zzy.C;
import static com.google.android.gms.analytics.internal.zzy.i;

public class ProjectActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonEditProject, buttonLikeProject;
    TextView textViewLocation, textViewDate, textViewAuthor, textViewLike;
    LocationManager locationManager;
    LocationListener locationListener;
    EditText editTextSubject, editTextDesctiption;
    ImageView imageViewPicture;
    String id, author_key;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        textViewLike = (TextView) findViewById(R.id.textViewLike);
        textViewLocation = (TextView) findViewById(R.id.textViewLocation);
        textViewAuthor = (TextView) findViewById(R.id.textViewAuthor);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextDesctiption = (EditText) findViewById(R.id.editTextDesctiption);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        imageViewPicture = (ImageView) findViewById(R.id.imageViewPictureExist);
        buttonEditProject = (Button) findViewById(R.id.buttonEditProject);
        buttonLikeProject = (Button) findViewById(R.id.buttonLikeProject);
        textViewDate = (TextView) findViewById(R.id.textViewDate);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        editTextSubject.setText(intent.getStringExtra("subject"));
        editTextDesctiption.setText(intent.getStringExtra("description"));
        textViewAuthor.setText(intent.getStringExtra("author"));
        textViewLike.setText(intent.getStringExtra("likes"));
        author_key = intent.getStringExtra("author_key");
        image = intent.getStringExtra("image").replaceAll("\\s","");
        imageViewPicture.setImageBitmap(convertStringToBitMap(image));
        //imageViewPicture.setRotation(90);


        //SharedPreferences myprefs = getSharedPreferences("user", MODE_WORLD_READABLE);
        SharedPreferences myprefs = getSharedPreferences("user", MODE_PRIVATE);

        String author_id = myprefs.getString("author_key", null);

//        CZY JEST AUTOREM??
        if(Integer.parseInt(author_id) != Integer.parseInt(author_key)){
            buttonEditProject.setVisibility(View.INVISIBLE);
            String location = intent.getStringExtra("location");
            String[] splited = location.split("\\s+");
            if(splited.length > 1) {
                textViewLocation.setText(getCompleteAddressString(Double.parseDouble(splited[0]), Double.parseDouble(splited[1])));
            } else {
                textViewLocation.setText("Brak lokalizacji.");
            }
            textViewDate.setText("Data: " + intent.getStringExtra("date"));
        } else {
            buttonEditProject.setVisibility(View.VISIBLE);
            String location = intent.getStringExtra("location");
            DateFormat df = new SimpleDateFormat("EEE d-MMM-yyyy, HH:mm");
            textViewDate.setText(df.format(Calendar.getInstance().getTime()));
        }
    }


    public void onLikeProjectButtonClick(View view) {
        int x = Integer.parseInt((String)textViewLike.getText()) + 1;
        textViewLike.setText(x + "");
    }

    public void onEditProjectButtonClick(View view) {
        String subject = editTextSubject.getText().toString();
        String description = editTextDesctiption.getText().toString();
        String author = textViewAuthor.getText().toString();
        String date = textViewDate.getText().toString();
        String location = textViewLocation.getText().toString();
        String type = "updateProject";
//        String image = camera.convertBitMapToString(imageBitmap);
        BackgroundWorker backgroundWorker = new BackgroundWorker(ProjectActivity.this);
        backgroundWorker.execute(type, author, subject, description, location, date, author_key, image, id);
        editTextSubject.setText("");
        editTextDesctiption.setText("");
        if (textViewLocation == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "Twoj projekt został dodany bez lokalizacji, nie wyświetli się na mapie...", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public Bitmap convertStringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
    }

}
