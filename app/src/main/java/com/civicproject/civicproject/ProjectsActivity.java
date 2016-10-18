package com.civicproject.civicproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.R.attr.y;

public class ProjectsActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonAddProject, buttonSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        buttonAddProject = (Button)findViewById(R.id.buttonAddProject);
        buttonSettings = (Button)findViewById(R.id.buttonSettings);

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
