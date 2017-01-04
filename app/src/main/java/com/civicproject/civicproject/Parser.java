package com.civicproject.civicproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class Parser extends AsyncTask<Void, Integer, Integer> {
    Context context;
    String data;
    public static ArrayList<String> locations = new ArrayList<>(), likesNames = new ArrayList<>(), likes = new ArrayList<>(), likesids = new ArrayList<>(), ids = new ArrayList<>(), descriptions = new ArrayList<>(), subjects = new ArrayList<>(), projects = new ArrayList<>(), dates = new ArrayList<>(), authors = new ArrayList<>(), authors_keys = new ArrayList<>(),
            images = new ArrayList<>();

    SpotsDialog progressDialog;
    DateParser dateParser = null;

    public Parser() {

    }

    public Parser(Context context, String data) {
        this.context = context;
        this.data = data;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new SpotsDialog(context, R.style.CustomDialogParse);
        progressDialog.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return this.parse();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer == 1) {
            dateParser = new DateParser();
            for (int i = 0; i < dates.size(); i++) {
                dates.set(i, dateParser.getDate(dates.get(i)));
            }
        } else {
            Toast.makeText(context, "Unable to Parse", Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
    }

    // PARSE RECEIVED DATA
    private int parse() {
        try {
            // ADD THAT DATA TO JSON ARRAY FIRST
            JSONArray ja = new JSONArray(data);

            //CREATE JO OBJ TO HOLD A SINGLE ITEM
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

            // LOOP THROUGH ARRAY
            for (int i = ja.length() - 1; i > -1; i--) {
                jo = ja.getJSONObject(i);

                // RETRIEVE DATA
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
                // ADD IT TO OUR ARRAYLIST
                projects.add(subject);
                subjects.add(subject);
                descriptions.add(description);
                locations.add(location);
                dates.add(date);
                authors.add(author);
                authors_keys.add(author_key);
                ids.add(id);
                likes.add(like);
                likesids.add(likeids);
                images.add(image);
                likesNames.add(likesnames);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}