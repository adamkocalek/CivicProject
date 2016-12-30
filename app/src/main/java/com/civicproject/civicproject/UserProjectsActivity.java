package com.civicproject.civicproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    private Parser parser = null;

    final ArrayList<String> myProjects = new ArrayList<>(), ids = new ArrayList<>(), authors = new ArrayList<>(), likes = new ArrayList<>(), dates = new ArrayList<>(), images = new ArrayList<>();
    final ArrayList<Integer> indexs = new ArrayList<>();
    final ArrayList<Bitmap> imagesBitmaps = new ArrayList<>();

    private MyFTPClientFunctions ftpclient = null;
    private static final String TAG = "ProjectActivity";

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

        parser = new Parser();
        ftpclient = new MyFTPClientFunctions();

        for (int i = 0; i < parser.subjects.size(); i++) {
            if (parser.authors_keys.get(i).equals(author_key)) {
                ids.add(parser.ids.get(i));
                myProjects.add(parser.subjects.get(i));
                authors.add(parser.authors.get(i));
                likes.add(parser.likes.get(i));
                dates.add(parser.dates.get(i));
                images.add(parser.images.get(i));
                indexs.add(parser.subjects.indexOf(parser.subjects.get(i)));
            }
        }

        for (int i = 0; i < images.size(); i++) {
            ftpDownloadImage(images.get(i));
            do {
            } while (imagesBitmaps.size() != i + 1);
        }

        if (imagesBitmaps.size() == images.size()) {
            ListViewAdapterUser lviewAdapter;
            lviewAdapter = new ListViewAdapterUser(this, ids, myProjects, likes, dates, imagesBitmaps);
            listViewMyProjects.setAdapter(lviewAdapter);

            listViewMyProjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                }
            });
        }
    }

    public void ftpDownloadImage(final String srcFilePath) {
        new Thread(new Runnable() {
            public void run() {
                boolean status = false;
                status = ftpclient.ftpConnect("serwer1633804.home.pl", "serwer1633804", "33murs0tKiby", 21);
                if (status == true) {
                    Log.d(TAG, "Połączenie udane");
                } else {
                    Log.d(TAG, "Połączenie nieudane");
                }

                ftpclient.ftpChangeDirectory("/images/");
                imagesBitmaps.add(ftpclient.ftpDownloadBitmap(srcFilePath));

                status = ftpclient.ftpDisconnect();
                if (status == true) {
                    Log.d(TAG, "Połączenie zakończone");
                } else {
                    Log.d(TAG, "Połączenie nie mogło zostać zakończone");
                }
            }
        }).start();
    }
}
