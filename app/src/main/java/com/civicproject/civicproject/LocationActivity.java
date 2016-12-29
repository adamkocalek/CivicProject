package com.civicproject.civicproject;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class LocationActivity extends AppCompatActivity  {


    String locations;
    Parser parser = new Parser();
    Button buttonAddProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrolling_addproject);

        final ArrayList<String> locations = new ArrayList<>();
      //  locations = parser.locations;

        for (int i = 0; i < parser.locations.size(); i++) {
            locations.add(parser.locations.get(i));
        }


        buttonAddProject = (Button) findViewById(R.id.buttonAddProject);
        buttonAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),locations.get(3), Toast.LENGTH_LONG).show();
            }
        });

    }

}

