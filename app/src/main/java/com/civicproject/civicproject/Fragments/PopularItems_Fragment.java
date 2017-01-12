package com.civicproject.civicproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.civicproject.civicproject.DateParser;
import com.civicproject.civicproject.ListViewAdapter;
import com.civicproject.civicproject.Parser;
import com.civicproject.civicproject.ProjectActivity;
import com.civicproject.civicproject.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopularItems_Fragment extends Fragment {
    ListView listView_popular;
    SwipeRefreshLayout swipeRefreshLayout;

    private Parser parser = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.popular_frag_layout, null);

        final HashMap<Integer, String> mapLikes = new HashMap<Integer, String>();
        final List sortedList;
        String getLike;
        String splitLike[];
        listView_popular = (ListView) view.findViewById(R.id.listView_popular);
        final ArrayList<Integer> indexs = new ArrayList<>(), tempList = new ArrayList<>();
        final ArrayList<String> popularList = new ArrayList<>(), ids = new ArrayList<>(), authors = new ArrayList<>(), subjects = new ArrayList<>(), likes = new ArrayList<>(), dates = new ArrayList<>(), images = new ArrayList<>();

        parser = new Parser();

        for (int i = 0; i < parser.subjects.size(); i++) {
            tempList.add(Integer.parseInt(parser.likes.get(i)));
            mapLikes.put(i, parser.likes.get(i));
        }

        if (mapLikes.size() > 1) {
            sortedList = sortByValue(mapLikes);

            for (int i = 0; i < 15; i++) {
                popularList.add(tempList.get(i) + "");
                getLike = sortedList.get(i) + "";
                splitLike = getLike.split("=");
                indexs.add(Integer.parseInt(splitLike[0]));
            }

            for (int i = 0; i < popularList.size(); i++) {
                ids.add(parser.ids.get(indexs.get(i)));
                subjects.add(parser.subjects.get(indexs.get(i)));
                authors.add(parser.authors.get(indexs.get(i)));
                likes.add(parser.likes.get(indexs.get(i)));
                dates.add(parser.dates.get(indexs.get(i)));
            }

            ListViewAdapter lviewAdapter;
            lviewAdapter = new ListViewAdapter(getActivity(), ids, subjects, authors, likes, dates);
            listView_popular.setAdapter(lviewAdapter);

            listView_popular.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView textView = (TextView) view.findViewById(R.id.textViewIds);
                    if (ids.contains(textView.getText() + "")) {
                        position = ids.indexOf(textView.getText() + "");
                    } else {
                        position = -1;
                    }
                    if (position != -1) {
                        Intent intent = new Intent(getContext(), ProjectActivity.class);
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
                        intent.putExtra("likesnames", parser.likesNames.get(indexs.get(position)));
                        intent.putExtra("comments", parser.comments.get(indexs.get(position)));
                        getContext().startActivity(intent);
                    }
                }
            });
        }

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.popular_frag_swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.progress, R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /*
                final ArrayList<String> locations = new ArrayList<>(), likesNames = new ArrayList<>(), likes = new ArrayList<>(), likesids = new ArrayList<>(),
                        ids = new ArrayList<>(), descriptions = new ArrayList<>(), subjects = new ArrayList<>(), dates = new ArrayList<>(),
                        authors = new ArrayList<>(), authors_keys = new ArrayList<>(), images = new ArrayList<>();


                String projects_url = "http://188.128.220.60/projects.php";

                AsyncHttpClient client = new AsyncHttpClient();
                client.get(projects_url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("Połączenie nawiązane", "HTTP Sucess");

                        try {
                            // ADD THAT DATA TO JSON ARRAY FIRST
                            JSONArray ja = new JSONArray(response);

                            //CREATE JO OBJ TO HOLD A SINGLE ITEM
                            JSONObject jo = null;
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

                            DateParser dateParser = new DateParser();

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

                        listView_popular.setAdapter(lviewAdapter);

                        listView_popular.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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



                        final HashMap<Integer, String> mapLikes = new HashMap<Integer, String>();
                        final List sortedList;
                        String getLike;
                        String splitLike[];
                        final ListView listView_popular = (ListView) view.findViewById(R.id.listView_popular);
                        final ArrayList<Integer> indexs = new ArrayList<>(), tempList = new ArrayList<>();
                        final ArrayList<String> popularList = new ArrayList<>(), idsTemp = new ArrayList<>(), authorsTemp = new ArrayList<>(), subjectsTemp = new ArrayList<>(),
                                likesTemp = new ArrayList<>(), datesTemp = new ArrayList<>();


                        for (int i = 0; i < subjects.size(); i++) {
                            tempList.add(Integer.parseInt(likes.get(i)));
                            mapLikes.put(i, likes.get(i));
                        }


                        sortedList = sortByValue(mapLikes);

                        for (int i = 0; i < 15; i++) {
                            popularList.add(tempList.get(i) + "");
                            getLike = sortedList.get(i) + "";
                            splitLike = getLike.split("=");
                            indexs.add(Integer.parseInt(splitLike[0]));
                        }

                        for (int i = 0; i < popularList.size(); i++) {
                            idsTemp.add(ids.get(indexs.get(i)));
                            subjectsTemp.add(subjects.get(indexs.get(i)));
                            authorsTemp.add(authors.get(indexs.get(i)));
                            likesTemp.add(likes.get(indexs.get(i)));
                            datesTemp.add(dates.get(indexs.get(i)));
                        }

                        ListViewAdapter lviewAdapter;
                        lviewAdapter = new ListViewAdapter(getActivity(), idsTemp, subjectsTemp, authorsTemp, likesTemp, datesTemp);

                        listView_popular.setAdapter(lviewAdapter);

                        listView_popular.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                    intent.putExtra("subject", subjects.get(indexs.get(position)));
                                    intent.putExtra("description", descriptions.get(indexs.get(position)));
                                    intent.putExtra("location", locations.get(indexs.get(position)));
                                    intent.putExtra("date", dates.get(indexs.get(position)));
                                    intent.putExtra("author", authors.get(indexs.get(position)));
                                    intent.putExtra("author_key", authors_keys.get(indexs.get(position)));
                                    intent.putExtra("image", images.get(indexs.get(position)));
                                    intent.putExtra("id", ids.get(indexs.get(position)));
                                    intent.putExtra("likes", likes.get(indexs.get(position)));
                                    intent.putExtra("likesids", likesids.get(indexs.get(position)));
                                    intent.putExtra("likesnames", likesNames.get(indexs.get(position)));
                                    getContext().startActivity(intent);
                                }
                            }
                        });

                    }
                });
                */
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    public static List sortByValue(Map unsortedMap) {
        List list = new ArrayList(unsortedMap.entrySet());

        Collections.sort(list, new Comparator() {
            public int compare(Object obj1, Object obj2) {
                return ((Comparable) ((Map.Entry) (obj1)).getValue

                        ()).compareTo(((Map.Entry) (obj2)).getValue());
            }
        });
        Collections.reverse(list);
        return list;
    }
}
