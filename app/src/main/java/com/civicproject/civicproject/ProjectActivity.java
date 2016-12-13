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
import static com.google.android.gms.analytics.internal.zzy.C;
import static com.google.android.gms.analytics.internal.zzy.i;

public class ProjectActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonEditProject;
    TextView textViewLocation, textViewDate, textViewAuthor;
    LocationManager locationManager;
    LocationListener locationListener;
    EditText editTextSubject, editTextDesctiption;
    ImageView imageViewPicture;
    Bitmap imageBitmap;
    Uri file;
    String id, author_key;
    Camera camera = new Camera(file);
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        textViewLocation = (TextView) findViewById(R.id.textViewLocation);
        textViewAuthor = (TextView) findViewById(R.id.textViewAuthor);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextDesctiption = (EditText) findViewById(R.id.editTextDesctiption);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        imageViewPicture = (ImageView) findViewById(R.id.imageViewPictureExist);
        buttonEditProject = (Button) findViewById(R.id.buttonEditProject);
        textViewDate = (TextView) findViewById(R.id.textViewDate);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        editTextSubject.setText(intent.getStringExtra("subject"));
        editTextDesctiption.setText(intent.getStringExtra("description"));
        textViewAuthor.setText(intent.getStringExtra("author"));
        author_key = intent.getStringExtra("author_key");
        image = intent.getStringExtra("image").replaceAll("\\s","");
        imageViewPicture.setImageBitmap(convertStringToBitMap(image));
        //imageViewPicture.setRotation(90);
        String location = intent.getStringExtra("location");
        String[] splited = location.split("\\s+");
        if(splited.length > 1) {
            textViewLocation.setText(getCompleteAddressString(Double.parseDouble(splited[0]), Double.parseDouble(splited[1])));
        } else {
            textViewLocation.setText("Brak lokalizacji.");
        }
        textViewDate.setText("Data: " + intent.getStringExtra("date"));

//        SharedPreferences myprefs = getSharedPreferences("user", MODE_WORLD_READABLE);
        SharedPreferences myprefs = getSharedPreferences("user", MODE_PRIVATE);

        String author_id = myprefs.getString("author_key", null);

//        CZY JEST AUTOREM??
        if(Integer.parseInt(author_id) != Integer.parseInt(author_key)){
            buttonEditProject.setVisibility(View.INVISIBLE);
        } else {
            buttonEditProject.setVisibility(View.VISIBLE);
        }
    }

    public void onEditProjectButtonClick(View view) {
        String subject = editTextSubject.getText().toString();
        String description = editTextDesctiption.getText().toString();
        System.out.println(subject + "       " + description + "          " + id);
        String type = "updateProject";
        BackgroundWorker backgroundWorker = new BackgroundWorker(ProjectActivity.this);
        backgroundWorker.execute(type, subject, description, id);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                //ivResult.setImageURI(file);
                Bitmap bitmap = camera.decodeSampledBitmapFromFile(file.getPath(), 200, 100);
                //imageOreintationValidator(bitmap, file.getPath());
                //rotateImage(bitmap, 90);
                imageBitmap = bitmap;
                imageViewPicture.setImageBitmap(bitmap);
                imageViewPicture.setRotation(90);
            }
        }
    }
}
