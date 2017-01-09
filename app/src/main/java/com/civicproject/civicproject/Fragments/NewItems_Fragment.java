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

import com.civicproject.civicproject.Downloader;
import com.civicproject.civicproject.ListViewAdapter;
import com.civicproject.civicproject.Parser;
import com.civicproject.civicproject.ProjectActivity;
import com.civicproject.civicproject.R;

public class NewItems_Fragment extends Fragment {
    private Parser parser = null;
    SwipeRefreshLayout swipeRefreshLayout;
    private String projects_url = "http://188.128.220.60/projects.php";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.new_frag_layout, null);

        final ListView listView_new = (ListView) view.findViewById(R.id.listView_new);
        parser = new Parser();

        ListViewAdapter lviewAdapter;
        lviewAdapter = new ListViewAdapter(getActivity(), parser.ids, parser.subjects, parser.authors, parser.likes, parser.dates);

        listView_new.setAdapter(lviewAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.new_frag_swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.progress,R.color.colorPrimary);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Downloader downloader = new Downloader(view.getContext(), projects_url);
                downloader.execute();

                parser = new Parser();

                ListViewAdapter lviewAdapter;
                lviewAdapter = new ListViewAdapter(getActivity(), parser.ids, parser.subjects, parser.authors, parser.likes, parser.dates);

                listView_new.setAdapter(lviewAdapter);

                swipeRefreshLayout.setRefreshing(false);
            }
        });

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
        return view;
    }
}
