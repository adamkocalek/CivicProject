package com.civicproject.civicproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserProjectsActivity extends AppCompatActivity {
    ListView listViewMyProjects;
    String author_key;

    Parser parser = new Parser();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_projects);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_user_projects);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        listViewMyProjects = (ListView) findViewById(R.id.listViewMyProjects);

        SharedPreferences myprefs = getSharedPreferences("user", MODE_PRIVATE);
        author_key = myprefs.getString("author_key", null);

        final ArrayList<String> myProjects = new ArrayList<>(), ids = new ArrayList<>(), authors = new ArrayList<>(), likes = new ArrayList<>(), dates = new ArrayList<>();
        final ArrayList<Integer> indexs = new ArrayList<>();

        for (int i = 0; i < parser.subjects.size(); i++) {
            if (parser.authors_keys.get(i).equals(author_key)) {
                ids.add(parser.ids.get(i));
                myProjects.add(parser.subjects.get(i));
                authors.add(parser.authors.get(i));
                likes.add(parser.likes.get(i));
                dates.add(parser.dates.get(i));
                indexs.add(parser.subjects.indexOf(parser.subjects.get(i)));
            }
        }

        for (int i = 0; i < indexs.size(); i++) {
            System.out.println(indexs.get(i));
        }


        ListViewAdapter lviewAdapter;
        lviewAdapter = new ListViewAdapter(this, ids, myProjects, authors, likes, dates);
        listViewMyProjects.setAdapter(lviewAdapter);

        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ids);
        //listViewMyProjects.setAdapter(arrayAdapter);
        listViewMyProjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

<<<<<<< HEAD
                Intent intent = new Intent(UserProjectsActivity.this, UserProjectActivity.class);
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
                UserProjectsActivity.this.startActivity(intent);

=======
                TextView textView = (TextView) view.findViewById(R.id.textViewIds);
                if (ids.contains(textView.getText() + "")) {
                    position = ids.indexOf(textView.getText() + "");
                } else {
                    position = -1;
                }
                if (position != -1) {
                    Intent intent = new Intent(UserProjectsActivity.this, ProjectActivity.class);
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
                    UserProjectsActivity.this.startActivity(intent);
                }
>>>>>>> origin/master
            }
        });
    }
}
