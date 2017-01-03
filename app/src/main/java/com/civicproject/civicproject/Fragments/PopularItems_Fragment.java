package com.civicproject.civicproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.civicproject.civicproject.ListViewAdapter;
import com.civicproject.civicproject.Parser;
import com.civicproject.civicproject.ProjectActivity;
import com.civicproject.civicproject.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopularItems_Fragment extends Fragment {
    private Parser parser = null;
    int temp;
    public static int test = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.popular_frag_layout, null);

        final HashMap<Integer, String> mapLikes = new HashMap<Integer, String>();
        final List sortedList;
        String getLike;
        String splitLike[];
        final ListView listView_popular = (ListView) view.findViewById(R.id.listView_popular);
        final ArrayList<Integer> indexs = new ArrayList<>(), tempList = new ArrayList<>();
        final ArrayList<String> popularList = new ArrayList<>(), ids = new ArrayList<>(), authors = new ArrayList<>(), subjects = new ArrayList<>(), likes = new ArrayList<>(), dates = new ArrayList<>(), images = new ArrayList<>();

        parser = new Parser();

        for (int i = 0; i < parser.subjects.size(); i++) {
            tempList.add(Integer.parseInt(parser.likes.get(i)));
            mapLikes.put(i, parser.likes.get(i));
        }

        if (mapLikes.size() > 2) {
            sortedList = sortByValue(mapLikes);

            for (int i = 0; i < 10; i++) {
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
                        Intent intent = new Intent(getActivity(), ProjectActivity.class);
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
                        getActivity().startActivity(intent);
                    }
                }
            });
        }
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
