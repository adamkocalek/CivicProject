package com.civicproject.civicproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.civicproject.civicproject.R.id.buttonCamera;

public class LocationActivity extends AppCompatActivity {


    Double distance;
    Parser parser = new Parser();
    Button buttonAddProject;
    Double locationX = Double.NaN, locationY = Double.NaN;
    String splited[];
    LocationManager locationManager;
    LocationListener locationListener;
    public String Temp="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrolling_addproject);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationX = location.getLatitude();
                locationY = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }


        };


        final ArrayList<String> locations = new ArrayList<>();
        final ArrayList<String> X = new ArrayList<>();
        final ArrayList<String> Y = new ArrayList<>();
        final ArrayList<Double>  distance_temp = new ArrayList<>();

        //  locations = parser.locations;




        buttonAddProject = (Button) findViewById(R.id.buttonAddProject);
        buttonAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    for (int i = 0; i < parser.locations.size(); i++) {
                        splited = parser.locations.get(i).split("\\s");
                        locations.add(parser.locations.get(i));

                        X.add(splited[0]);
                        Y.add(splited[1]);


                    }
                    double lat1 = 51.731916, lon1 = 19.529735;
                    //distance = haversine(lat1, lon1, lat2, lon2);

                for(int i = 0; i < parser.locations.size(); i++) {
                    distance = haversine(lat1, lon1, Double.parseDouble(X.get(i)), Double.parseDouble(Y.get(i)));
                    distance *= 1000;
                    distance_temp.add(distance);
                    if(distance <= 1000){


                        Temp += "Nr projektu: " + i + " odl. " + distance.toString() + "\n";

                    }
                }

               Toast.makeText(getApplicationContext(), Temp, Toast.LENGTH_LONG).show();

            }
        });

    }



    public static double haversine(double lat1, double lng1, double lat2, double lng2) {
        int r = 6371; // promień ziemi w kilometrach
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = r * c;
        return d;
    }




}

