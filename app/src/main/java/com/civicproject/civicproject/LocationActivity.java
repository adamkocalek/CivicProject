package com.civicproject.civicproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity {

    Double distance;
    Parser parser = new Parser();
    Button buttonAddAnyway;
    Double locationX = Double.NaN, locationY = Double.NaN;
    String splited[];
    private ArrayList<String> ids = new ArrayList<>(), subjects = new ArrayList<>(), authors = new ArrayList<>(), likes = new ArrayList<>(), dates = new ArrayList<>();
    private ArrayList<Integer> indexs = new ArrayList<>();
    String subject, description, image, date, author, type, location, tempAuthorKey;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LocationActivity.this, RootActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_locations);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        buttonAddAnyway = (Button) findViewById(R.id.AddAnyway);
        ListView locations_nearby = (ListView) findViewById(R.id.listViewMyProjects);

        Intent receiveIntent = this.getIntent();
        locationX = receiveIntent.getDoubleExtra("doubleValue_e1", locationX);
        locationY = receiveIntent.getDoubleExtra("doubleValue_e2", locationY);
        subject = receiveIntent.getStringExtra("subject");
        description = receiveIntent.getStringExtra("description");
        author = receiveIntent.getStringExtra("author");
        date = receiveIntent.getStringExtra("date");
        image = receiveIntent.getStringExtra("image");
        type = receiveIntent.getStringExtra("type");
        location = receiveIntent.getStringExtra("location");
        tempAuthorKey = receiveIntent.getStringExtra("tempAuthorKey");

        final ArrayList<String> locations = new ArrayList<>();
        final ArrayList<String> X = new ArrayList<>();
        final ArrayList<String> Y = new ArrayList<>();
        final ArrayList<Double> distance_temp = new ArrayList<>();
        final ArrayList<String> subjects_temp = new ArrayList<>();
        final ArrayList<String> array_subjects = new ArrayList<>();

        for (int i = 0; i < parser.locations.size(); i++) {
            splited = parser.locations.get(i).split("\\s");
            locations.add(parser.locations.get(i));
            X.add(splited[0]);
            Y.add(splited[1]);
        }

        // double lat1 = 51.731916, lon1 = 19.529735;
        //distance = haversine(lat1, lon1, lat2, lon2);
        int word = 0;
        for (int i = 0; i < parser.locations.size(); i++) {
            distance = haversine(locationX, locationY, Double.parseDouble(X.get(i)), Double.parseDouble(Y.get(i)));
            distance *= 1000;
            //sprawdzanie odległości
            if (distance <= 1000) {

                //sprawdzanie podobnych tematów
                word = Levenshtein(subject, parser.subjects.get(i));

                if (word <= 3) {
                    distance_temp.add(distance);
                    indexs.add(i);
                }
            }

        }


        for (int i = 0; i < distance_temp.size(); i++) {
            ids.add(parser.ids.get(indexs.get(i)));
            subjects.add(parser.subjects.get(indexs.get(i)));
            authors.add(parser.authors.get(indexs.get(i)));
            likes.add(parser.likes.get(indexs.get(i)));
            dates.add(parser.dates.get(indexs.get(i)));
        }

        ListViewAdapter lviewAdapter;
        lviewAdapter = new ListViewAdapter(LocationActivity.this, ids, subjects, authors, likes, dates);
        locations_nearby.setAdapter(lviewAdapter);

        buttonAddAnyway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackgroundWorker backgroundWorker = new BackgroundWorker(LocationActivity.this);
                backgroundWorker.execute(type, author, subject, description, location, date, tempAuthorKey, image);

                Toast.makeText(getApplicationContext(), "Projekt dodany do poczekalni, będzie widoczny po zatwierdzeniu przez moderatora.", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(LocationActivity.this, RootActivity.class);
                startActivity(intent);
                finish();
            }
        });

        locations_nearby.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProjectActivity.class);
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXX  " + position + "  xxxxx   " + indexs.get(position));
                intent.putExtra("subject", parser.subjects.get(indexs.get(position)));
                intent.putExtra("description", parser.descriptions.get(indexs.get(position)));
                intent.putExtra("location", parser.locations.get(indexs.get(position)));
                intent.putExtra("date", parser.dates.get(indexs.get(position)));
                intent.putExtra("image", parser.images.get(indexs.get(position)));
                intent.putExtra("id", parser.ids.get(indexs.get(position)));
                intent.putExtra("likes", parser.likes.get(indexs.get(position)));
                intent.putExtra("likesids", parser.likesids.get(indexs.get(position)));
                intent.putExtra("author", parser.authors.get(indexs.get(position)));
                intent.putExtra("author_key", parser.authors_keys.get(indexs.get(position)));
                intent.putExtra("likesnames", parser.likesNames.get(indexs.get(position)));
                startActivity(intent);

            }
        });
        Toast.makeText(getApplicationContext(), "Sprawdź, czy ktoś nie ma takiego samego pomysłu ! ", Toast.LENGTH_LONG).show();
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

    public static int Levenshtein(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }


}



