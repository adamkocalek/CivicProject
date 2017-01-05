package com.civicproject.civicproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class LocationActivity extends AppCompatActivity  {


    Double distance;
    Parser parser = new Parser();
    Button buttonAddProject;
    Double locationX = Double.NaN, locationY = Double.NaN;
    String splited[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrolling_addproject);

        final ArrayList<String> locations = new ArrayList<>();
        final ArrayList<String> X = new ArrayList<>();
        final ArrayList<String> Y = new ArrayList<>();
      //  locations = parser.locations;

        for (int i = 0; i < parser.locations.size(); i++) {
            splited = parser.locations.get(i).split("\\s");
            locations.add(parser.locations.get(i));
            X.add(splited[0]);
            Y.add(splited[1]);
            double lat1 = 55, lon1 = 19.529735, lat2 = 51.730011, lon2 = 19.532406;
            distance = haversine(lat1, lon1, lat2, lon2);
            distance *= 1000;
        }




        buttonAddProject = (Button) findViewById(R.id.buttonAddProject);
        buttonAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),locations.get(3), Toast.LENGTH_LONG).show();
            }
        });

    }
    public static double  haversine(double lat1, double lng1, double lat2, double lng2) {
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

    private String getAddress(double LATITUDE, double LONGITUDE) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }


}

