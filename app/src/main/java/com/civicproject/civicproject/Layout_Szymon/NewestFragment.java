package com.civicproject.civicproject.Layout_Szymon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.civicproject.civicproject.ListViewAdapter;
import com.civicproject.civicproject.R;

import java.util.ArrayList;


public class NewestFragment extends Fragment {

    public static ArrayList<String> locations = new ArrayList<>(), likes = new ArrayList<>(), likesids = new ArrayList<>(), ids = new ArrayList<>(), descriptions = new ArrayList<>(), subjects = new ArrayList<>();
    ArrayList<String> projects = new ArrayList<>(), dates = new ArrayList<>(), authors = new ArrayList<>(), authors_keys = new ArrayList<>();
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.newestfragment_layout, null);

        final ListView nf_list = (ListView) view.findViewById(R.id.nf_list);

        ids.add("1");
        ids.add("2");
        ids.add("3");

        subjects.add("Plac zabaw.");
        subjects.add("Boisko dla dzieci.");
        subjects.add("Kosze na śmieci.");

        authors.add("Asia Koszycka");
        authors.add("Piotr Szczepanik");
        authors.add("Szymon Witkowski");

        likes.add("12");
        likes.add("57");
        likes.add("32");

        dates.add("7 GRUDZIEŃ");
        dates.add("9 GRUDZIEŃ");
        dates.add("11 GRUDZIEŃ");

        ListViewAdapter lviewAdapter;
        lviewAdapter = new ListViewAdapter(getActivity(), ids, subjects, authors, likes, dates);
        nf_list.setAdapter(lviewAdapter);

        return view;
    }
}