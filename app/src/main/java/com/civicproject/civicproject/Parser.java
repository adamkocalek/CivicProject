package com.civicproject.civicproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Parser extends AsyncTask<Void, Integer, Integer> {
    Context context;
    ListView listView;
    String data;
    ArrayList<String> projects = new ArrayList<>(), subjects = new ArrayList<>(), descriptions = new ArrayList<>(),
            locations = new ArrayList<>(), dates = new ArrayList<>(), authors = new ArrayList<>(), authors_keys = new ArrayList<>(),
            images = new ArrayList<>();
    ProgressDialog progressDialog;

    public Parser() {

    }

    public Parser(Context context, String data, ListView listView) {
        this.context = context;
        this.data = data;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Parser");
        progressDialog.setMessage("Parsing ....Please wait");
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
            //ADAPTER
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, projects);
            //ADAPT TO LISTVIEW
            listView.setAdapter(adapter);
            //LISTENET
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(context, ProjectActivity.class);

                    //ProjectActivity.textViewAuthor.setText("");
                    if (subjects.contains(listView.getItemAtPosition(position).toString())) {
                        position = subjects.indexOf(listView.getItemAtPosition(position).toString());
                    } else {
                        position = -1;
                    }
                    if (position != -1) {
                        intent.putExtra("subject", subjects.get(position));
                        intent.putExtra("description", descriptions.get(position));
                        intent.putExtra("location", locations.get(position));
                        intent.putExtra("date", dates.get(position));
                        intent.putExtra("author", authors.get(position));
                        intent.putExtra("author_key", authors_keys.get(position));
                        intent.putExtra("image", images.get(position));

                        context.startActivity(intent);
                    }
                }

            });
        } else {
            Toast.makeText(context, "Unable to Parse", Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
    }

    //PARSE RECEIVED DATA
    private int parse() {
        try {
            //ADD THAT DATA TO JSON ARRAY FIRST
            JSONArray ja = new JSONArray(data);
            //CREATE JO OBJ TO HOLD A SINGLE ITEM
            JSONObject jo = null;
            projects.clear();
            //LOOP THRU ARRAY
            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                //RETRIOEVE DATA
                String subject = jo.getString("subject");
                String description = jo.getString("description");
                String location = jo.getString("location");
                String date = jo.getString("date");
                String author = jo.getString("author");
                String author_key = jo.getString("author_key");
                String image = jo.getString("image");
                //ADD IT TO OUR ARRAYLIST
                projects.add(subject);
                subjects.add(subject);
                descriptions.add(description);
                locations.add(location);
                dates.add(date);
                authors.add(author);
                authors_keys.add(author_key);
                images.add(image);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}