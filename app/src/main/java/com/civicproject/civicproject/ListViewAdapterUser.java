package com.civicproject.civicproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapterUser extends BaseAdapter {
    Activity activity;
    ArrayList<String> ids;
    ArrayList<String> subjects;
    ArrayList<String> likes;
    ArrayList<String> dates;
    ArrayList<Bitmap> imagesBitmaps;

    public ListViewAdapterUser(Activity activity, ArrayList<String> ids, ArrayList<String> subjects, ArrayList<String> likes, ArrayList<String> dates, ArrayList<Bitmap> imagesBitmaps) {
        super();
        this.activity = activity;
        this.ids = ids;
        this.subjects = subjects;
        this.likes = likes;
        this.dates = dates;
        this.imagesBitmaps = imagesBitmaps;
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
        TextView textViewLikes;
        TextView textViewDates;
        ImageView imageViewProject;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_userprojectsimage, null);
            holder = new ViewHolder();
            holder.textViewIds = (TextView) convertView.findViewById(R.id.textViewIds);
            holder.textViewSubjects = (TextView) convertView.findViewById(R.id.textViewSubjects);
            holder.textViewLikes = (TextView) convertView.findViewById(R.id.textViewLikes);
            holder.textViewDates = (TextView) convertView.findViewById(R.id.textViewDates);
            holder.imageViewProject = (ImageView) convertView.findViewById(R.id.imageViewProject);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            holder.textViewDates.setText(dates.get(position));
            holder.textViewIds.setText(ids.get(position));
            holder.textViewSubjects.setText(subjects.get(position));
            holder.textViewLikes.setText(likes.get(position));
            holder.imageViewProject.setImageBitmap(imagesBitmaps.get(position));
        } catch (IndexOutOfBoundsException e) {
            Log.d("BŁĄD: ", "OutOfBound Exception w liście...");
        }

        return convertView;
    }
}
