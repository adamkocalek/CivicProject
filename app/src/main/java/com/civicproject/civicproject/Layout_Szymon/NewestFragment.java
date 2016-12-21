package com.civicproject.civicproject.Layout_Szymon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.civicproject.civicproject.Downloader;
import com.civicproject.civicproject.R;


public class NewestFragment extends Fragment {

    String url = "http://188.128.220.60/projects.php";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.newestfragment_layout, null);

        final ListView nf_list = (ListView) view.findViewById(R.id.nf_list);
        final Downloader downloader = new Downloader(getContext(), url, getActivity(), nf_list);
        downloader.execute();

        return view;
    }
}