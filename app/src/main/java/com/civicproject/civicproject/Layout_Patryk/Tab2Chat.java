package com.civicproject.civicproject.Layout_Patryk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.civicproject.civicproject.Downloader;
import com.civicproject.civicproject.HubActivity;
import com.civicproject.civicproject.R;

public class Tab2Chat extends Fragment {

    String url = "http://188.128.220.60/projects.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((HubActivity) getActivity()).setActionBarTitle("Contacts");

        View view = inflater.inflate(R.layout.tab2chat, container,false);

        final ListView p2_list = (ListView) view.findViewById(R.id.p2_list);
        final Downloader downloader = new Downloader(getContext(), url, getActivity(), p2_list);
        downloader.execute();

        return view;
    }
}
