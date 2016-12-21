package com.civicproject.civicproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<String> ids;
    ArrayList<String> subjects;
    ArrayList<String> authors;
    ArrayList<String> likes;
    ArrayList<String> dates;

    public ListViewAdapter(Activity activity, ArrayList<String> ids, ArrayList<String> subjects, ArrayList<String> authors, ArrayList<String> likes, ArrayList<String> dates) {
        super();
        this.activity = activity;
        this.ids = ids;
        this.subjects = subjects;
        this.authors = authors;
        this.likes = likes;
        this.dates = dates;
    }

    public int getCount() {
        return ids.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView textViewIds;
        TextView textViewSubjects;
        TextView textViewAuthors;
        TextView textViewLikes;
        TextView textViewDates;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_projects, null);
            holder = new ViewHolder();
            holder.textViewIds = (TextView) convertView.findViewById(R.id.textViewIds);
            holder.textViewSubjects = (TextView) convertView.findViewById(R.id.textViewSubjects);
            holder.textViewAuthors = (TextView) convertView.findViewById(R.id.textViewAuthors);
            holder.textViewLikes = (TextView) convertView.findViewById(R.id.textViewLikes);
            holder.textViewDates = (TextView) convertView.findViewById(R.id.textViewDates);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textViewIds.setText(ids.get(position));
        holder.textViewSubjects.setText(subjects.get(position));
        holder.textViewAuthors.setText(authors.get(position));
        holder.textViewLikes.setText(likes.get(position));
        holder.textViewDates.setText(dates.get(position));

        return convertView;
    }
}
