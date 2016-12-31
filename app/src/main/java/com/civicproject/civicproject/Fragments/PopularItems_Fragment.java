package com.civicproject.civicproject.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.civicproject.civicproject.Downloader;
import com.civicproject.civicproject.ListViewAdapter;
import com.civicproject.civicproject.ListViewAdapterUser;
import com.civicproject.civicproject.MyFTPClientFunctions;
import com.civicproject.civicproject.Parser;
import com.civicproject.civicproject.ProjectActivity;
import com.civicproject.civicproject.R;
import com.civicproject.civicproject.RootActivity;
import com.civicproject.civicproject.UserProjectsActivity;

import java.util.ArrayList;
import java.util.Collections;

import static com.civicproject.civicproject.R.id.listViewMyProjects;

public class PopularItems_Fragment extends Fragment {
    private Parser parser = null;
    int temp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.popular_frag_layout, null);

        final ListView listView_popular = (ListView) view.findViewById(R.id.listView_popular);
        final ArrayList<Integer> indexs = new ArrayList<>(), tempList2 = new ArrayList<>();
        final ArrayList<String> popularList = new ArrayList<String>(), ids = new ArrayList<>(), authors = new ArrayList<>(), likes = new ArrayList<>(), dates = new ArrayList<>(), images = new ArrayList<>();
        parser = new Parser();

        for (int i = 0; i < parser.subjects.size(); i++) {
            tempList2.add(Integer.parseInt(parser.likes.get(i)));
        }

        Collections.sort(tempList2);
        Collections.reverse(tempList2);
        if(tempList2.size() > 2) {
            for (int i = 0; i < 10; i++) {
                popularList.add(tempList2.get(i) + "");
            }
        }

        /*
        final ArrayList<String> subjects = new ArrayList<>(), subjectsNew = new ArrayList<>(), ids = new ArrayList<>(), idsNew = new ArrayList<>(),
                authors = new ArrayList<>(), authorsNew = new ArrayList<>(), likes = new ArrayList<>(), likesOld = new ArrayList<>(), dates = new ArrayList<>(), datesNew = new ArrayList<>();
        final ArrayList<Integer> indexs = new ArrayList<>();

        for (int i = 0; i < parser.subjects.size(); i++) {
            ids.add(parser.ids.get(i));
            subjects.add(parser.subjects.get(i));
            authors.add(parser.authors.get(i));
            likes.add(parser.likes.get(i));
            dates.add(parser.dates.get(i));
        }
        for (int i = 0; i < likes.size(); i++) {
            likesOld.add(likes.get(i));
        }
        Collections.sort(likes, Collections.reverseOrder());
        for (int i = 0; i < likes.size(); i++) {
            indexs.add(likesOld.indexOf(likes.get(i)));
        }
        for (int i = 0; i < indexs.size(); i++) {
            subjectsNew.add(subjects.get(indexs.get(i)));
            idsNew.add(ids.get(indexs.get(i)));
            authorsNew.add(authors.get(indexs.get(i)));
            datesNew.add(dates.get(indexs.get(i)));
        }

        ListViewAdapter lviewAdapter;
        lviewAdapter = new ListViewAdapter(getActivity(), ids, subjects, authors, likes, dates);
        listView_popular.setAdapter(lviewAdapter);
        */

        /*


        for (int i = 0; i < parser.subjects.size(); i++) {
            ids.add(parser.ids.get(i));
        }


        //Parser parser = new Parser();
        //Toast.makeText(getContext(), parser.subjects.size() + "", Toast.LENGTH_LONG).show();

        ids.add("1");
        ids.add("2");
        ids.add("3");
        ids.add("4");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, ids);
        */
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, popularList);
        listView_popular.setAdapter(arrayAdapter);
        return view;
    }
}
