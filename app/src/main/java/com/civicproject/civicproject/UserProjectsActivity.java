package com.civicproject.civicproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class UserProjectsActivity extends AppCompatActivity {
    ListView listViewMyProjects;
    String author_key;

    Parser parser = new Parser();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_projects);
        overridePendingTransition(R.anim.right_in,R.anim.left_out);
        listViewMyProjects = (ListView)findViewById(R.id.listViewMyProjects);
        SharedPreferences myprefs = getSharedPreferences("user", MODE_PRIVATE);
        author_key = myprefs.getString("author_key", null);

        for(int i = 0; i < parser.subjects.size(); i++){
            if(parser.authors_keys.get(i) == author_key){
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX   " + parser.subjects.get(i) + "   " + parser.descriptions.get(i) + "   " + parser.likes.get(i) + "   "+ parser.locations.get(i) + "   " + parser.images.get(i) + "   " + parser.likesids.get(i) + "   " + parser.dates.get(i));
            }
        }

    }

}
