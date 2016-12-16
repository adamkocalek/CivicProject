package com.civicproject.civicproject;

/**
 * Created by Patrycjusz on 2016-12-16.
 */

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

    public ListViewAdapter(Activity activity, ArrayList<String> ids, ArrayList<String> subjects) {
        super();
        this.activity = activity;
        this.ids = ids;
        this.subjects = subjects;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return ids.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView textViewIds;
        TextView textViewSubjects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_projects, null);
            holder = new ViewHolder();
            holder.textViewIds = (TextView) convertView.findViewById(R.id.textViewIds);
            holder.textViewSubjects = (TextView) convertView.findViewById(R.id.textViewSubjects);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textViewIds.setText(ids.get(position));
        holder.textViewSubjects.setText(subjects.get(position));

        return convertView;
    }

}
