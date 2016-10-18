package com.civicproject.civicproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddProjectActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonAddProjectFinal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        buttonAddProjectFinal = (Button)findViewById(R.id.buttonAddProjectFinal);
        buttonAddProjectFinal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
