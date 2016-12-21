package com.civicproject.civicproject.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.civicproject.civicproject.Downloader;
import com.civicproject.civicproject.R;


public class NewestFragment extends Fragment {

    String url = "http://188.128.220.60/projects.php";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.newestfragment_layout, null);
        /*
        String[] listItems = {"1", "2", "3", "4", "4", "4", "4", "4", "4", "4", "4", "4", "4", "4", "4", "4", "4"};

        ListView nf_list = (ListView) view.findViewById(R.id.nf_list);



        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                listItems
        );

        nf_list.setAdapter(listAdapter);

//      nf_button1.setOnClickListener(this);
        */

        /*
        final ListView nf_list = (ListView) view.findViewById(R.id.nf_list);

        final Downloader downloader = new Downloader(getContext(), url, getActivity(), nf_list);
        downloader.execute();
        */

        return view;


    }

//    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(getActivity(), ProjectActivity.class);
//        startActivity(intent);
//    }
}