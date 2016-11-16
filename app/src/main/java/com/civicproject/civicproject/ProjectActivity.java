package com.civicproject.civicproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        TextView textViewProjectSubject = (TextView) findViewById(R.id.textViewProjectSubject);
        TextView textViewProjectDescription = (TextView) findViewById(R.id.textViewProjectDescription);
        TextView textViewProjectAuthor = (TextView) findViewById(R.id.textViewProjectAuthor);
        TextView textViewProjectLocalization = (TextView) findViewById(R.id.textViewProjectLocalization);
        TextView textViewProjectLikes = (TextView) findViewById(R.id.textViewProjectLikes);
        TextView textViewProjectDislikes = (TextView) findViewById(R.id.textViewProjectDislikes);
        TextView textViewProjectComments = (TextView) findViewById(R.id.textViewProjectComments);
        TextView textViewProjectDate = (TextView) findViewById(R.id.textViewProjectDate);


    }
}
