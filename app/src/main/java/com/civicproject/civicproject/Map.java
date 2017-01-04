package com.civicproject.civicproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Random;

import static com.civicproject.civicproject.Parser.locations;
import static com.civicproject.civicproject.R.id.map;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    Parser parser = new Parser();
    private GoogleMap mMap;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);

        mapFragment.getMapAsync(this);


//        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                Intent intent = new Intent(Map.this, AboutActivity.class);
//                startActivity(intent);
//            }
//        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Random random = new Random();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.75883047028454, 19.456186294555664), 12.0f));
        for (int i = 0; i < locations.size(); i++) {
            String location = parser.locations.get(i);

            String[] splited = location.split("\\s+");
            if (splited.length > 1) {
                LatLng xy = new LatLng(Double.parseDouble(splited[0]), Double.parseDouble(splited[1]));
                int color = random.nextInt(360 + 1);
                mMap.addMarker(new MarkerOptions().position(xy).title(parser.ids.get(i) + " " + parser.subjects.get(i)).icon(BitmapDescriptorFactory.defaultMarker(color)));
            }
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String[] splited = marker.getTitle().split("\\s+");
                if (splited.length > 1) {
                    String id = splited[0];
                    for (int i = 0; i < parser.ids.size(); i++) {
                        if (parser.ids.get(i).equals(id)) {
                            Intent intent = new Intent(Map.this, ProjectActivity.class);
                            intent.putExtra("subject", parser.subjects.get(i));
                            intent.putExtra("description", parser.descriptions.get(i));
                            intent.putExtra("location", parser.locations.get(i));
                            intent.putExtra("date", parser.dates.get(i));
                            intent.putExtra("author", parser.authors.get(i));
                            intent.putExtra("author_key", parser.authors_keys.get(i));
                            intent.putExtra("image", parser.images.get(i));
                            intent.putExtra("id", parser.ids.get(i));
                            intent.putExtra("likes", parser.likes.get(i));
                            intent.putExtra("likesids", parser.likesids.get(i));
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

}