package com.civicproject.civicproject.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.civicproject.civicproject.DateParser;
import com.civicproject.civicproject.Downloader;
import com.civicproject.civicproject.ListViewAdapter;
import com.civicproject.civicproject.Parser;
import com.civicproject.civicproject.ProjectActivity;
import com.civicproject.civicproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewItems_Fragment extends Fragment {
    private Parser parser = null;
    SwipeRefreshLayout swipeRefreshLayout;
    public static ArrayList<String> locations = new ArrayList<>(), likesNames = new ArrayList<>(), likes = new ArrayList<>(), likesids = new ArrayList<>(), ids = new ArrayList<>(), descriptions = new ArrayList<>(), subjects = new ArrayList<>(), projects = new ArrayList<>(), dates = new ArrayList<>(), authors = new ArrayList<>(), authors_keys = new ArrayList<>(),
            images = new ArrayList<>();
    DateParser dateParser = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.new_frag_layout, null);

        final ListView listView_new = (ListView) view.findViewById(R.id.listView_new);
        parser = new Parser();

        ListViewAdapter lviewAdapter;
        lviewAdapter = new ListViewAdapter(getActivity(), parser.ids, parser.subjects, parser.authors, parser.likes, parser.dates);

        listView_new.setAdapter(lviewAdapter);

        listView_new.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), ProjectActivity.class);
                TextView textView = (TextView) view.findViewById(R.id.textViewIds);

                if (parser.ids.contains(textView.getText() + "")) {
                    position = parser.ids.indexOf(textView.getText() + "");
                } else {
                    position = -1;
                }
                if (position != -1) {
                    intent.putExtra("subject", parser.subjects.get(position));
                    intent.putExtra("description", parser.descriptions.get(position));
                    intent.putExtra("location", parser.locations.get(position));
                    intent.putExtra("date", parser.dates.get(position));
                    intent.putExtra("author", parser.authors.get(position));
                    intent.putExtra("author_key", parser.authors_keys.get(position));
                    intent.putExtra("image", parser.images.get(position));
                    intent.putExtra("id", parser.ids.get(position));
                    intent.putExtra("likes", parser.likes.get(position));
                    intent.putExtra("likesids", parser.likesids.get(position));
                    intent.putExtra("likesnames", parser.likesNames.get(position));
                    getContext().startActivity(intent);
                }
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.new_frag_swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.progress, R.color.colorPrimary);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                String projects_url = "http://188.128.220.60/projects.php";
                String data = downloadData(projects_url);

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

                    dateParser = new DateParser();

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
                        date = dateParser.getDate(date);
                        dates.add(date);
                        authors.add(author);
                        authors_keys.add(author_key);
                        ids.add(id);
                        likes.add(like);
                        likesids.add(likeids);
                        images.add(image);
                        likesNames.add(likesnames);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ListViewAdapter lviewAdapter;
                lviewAdapter = new ListViewAdapter(getActivity(), ids, subjects, authors, likes, dates);

                listView_new.setAdapter(lviewAdapter);

                listView_new.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(getContext(), ProjectActivity.class);
                        TextView textView = (TextView) view.findViewById(R.id.textViewIds);

                        if (ids.contains(textView.getText() + "")) {
                            position = ids.indexOf(textView.getText() + "");
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
                            intent.putExtra("id", ids.get(position));
                            intent.putExtra("likes", likes.get(position));
                            intent.putExtra("likesids", likesids.get(position));
                            intent.putExtra("likesnames", likesNames.get(position));
                            getContext().startActivity(intent);
                        }
                    }
                });

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    public String downloadData(String address) {
        //connect and get a stream
        InputStream is = null;
        String line = null;
        try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(con.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            if (br != null) {
                while ((line = br.readLine()) != null) {
                    sb.append(line + "n");
                }
            } else {
                return null;
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
