package com.civicproject.civicproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ListView;

public class ProjectsActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonAddProject, buttonSettings;

    String url="http://192.168.1.109/projects.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        buttonAddProject = (Button)findViewById(R.id.buttonAddProject);
        buttonSettings = (Button)findViewById(R.id.buttonSettings);

        final ListView lv = (ListView) findViewById(R.id.lv);
        final Downloader d = new Downloader(this,url,lv);

        d.execute();

        buttonAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddProjectActivity.class));
            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserAreaActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
