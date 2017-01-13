package com.civicproject.civicproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Parser extends AsyncTask<Void, Integer, Integer> {
    Context context;
    String data;
    String taskName;
    public static ArrayList<String> locations = new ArrayList<>(), comments = new ArrayList<>(), likesNames = new ArrayList<>(), likes = new ArrayList<>(), likesids = new ArrayList<>(), ids = new ArrayList<>(), descriptions = new ArrayList<>(), subjects = new ArrayList<>(), projects = new ArrayList<>(), dates = new ArrayList<>(), authors = new ArrayList<>(), authors_keys = new ArrayList<>(),
            images = new ArrayList<>();

    ProgressDialog progressDialog;
    DateParser dateParser = null;

    public Parser() {

    }

    public Parser(Context context, String data) {
        this.context = context;
        this.data = data;
    }

    public Parser(Context context, String data, String taskName) {
        this.context = context;
        this.data = data;
        this.taskName = taskName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Przetwarzanie danych");
//        progressDialog.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return this.parse();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer == 1) {
            if (taskName != null) {
                switch (taskName) {
                    case "RootActivityIntent":
                        Intent intent = new Intent(context, RootActivity.class);
                        context.startActivity(intent);
                        break;

                    default:
                }
            }
        } else {
            Toast.makeText(context, "Nie można przetworzyć danych.", Toast.LENGTH_SHORT).show();
        }
//        progressDialog.dismiss();
    }

    // Parsowanie pobranego JSON Array
    private int parse() {
        try {
            // Dodanie danych do JSON Array
            JSONArray ja = new JSONArray(data);

            // Tworzenie pojedynczego JSON Object to przechowywania pojedynczego elementu
            JSONObject jo = null;
            projects.clear();
            subjects.clear();
            descriptions.clear();
            locations.clear();
            dates.clear();
            authors.clear();
            authors_keys.clear();
            ids.clear();
            likes.clear();
            likesids.clear();
            images.clear();
            comments.clear();

            dateParser = new DateParser();

            for (int i = ja.length() - 1; i > -1; i--) {
                jo = ja.getJSONObject(i);

                // Odbieranie danych
                String subject = jo.getString("subject");
                String description = jo.getString("description");
                String location = jo.getString("location");
                String date = jo.getString("date");
                String author = jo.getString("author");
                String author_key = jo.getString("author_key");
                String image = jo.getString("image");
                String id = jo.getString("id");
                String like = jo.getString("likes");
                String likeids = jo.getString("likesids");
                String likesnames = jo.getString("likesnames");
                String comment = jo.getString("comments");

                // Dodanie danych do normalnej listy
                projects.add(subject);
                subjects.add(subject);
                descriptions.add(description);
                locations.add(location);
                date = dateParser.getDate(date);
                dates.add(date);
                authors.add(author);
                authors_keys.add(author_key);
                ids.add(id);
                likes.add(like);
                likesids.add(likeids);
                images.add(image);
                likesNames.add(likesnames);
                comments.add(comment);
            }
            return 1;

        } catch (JSONException e) {
            Log.d("LOG Parser", e + "");
        }
        return 0;
    }
}