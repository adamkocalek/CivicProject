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

    String localizationList;
    String locations;
    Parser parser = new Parser();
    Button buttonAddProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buttonAddProject = (Button) findViewById(R.id.buttonAddProject);

        SharedPreferences myprefs = getSharedPreferences("user", MODE_PRIVATE);
        locations = myprefs.getString("location", null);

        ArrayList<String> myProjects = new ArrayList<String>();
        final ArrayList<Integer> indexs = new ArrayList<Integer>();

        for (int i = 0; i < parser.locations.size(); i++) {
                myProjects.add(parser.locations.get(i));
                indexs.add(parser.locations.indexOf(parser.locations.get(i)));

        }



        Toast.makeText(getApplicationContext(),locations, Toast.LENGTH_LONG).show();
    }

}
