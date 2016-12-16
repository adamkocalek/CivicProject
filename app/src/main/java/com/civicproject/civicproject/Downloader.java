package com.civicproject.civicproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader extends AsyncTask<Void, Integer, String> {
    Context context;
    String address;
    Activity activity;
    ListView listView;
    ProgressDialog progressDialog;

    public Downloader(Context context, String address, Activity activity, ListView listView) {
        this.context = context;
        this.address = address;
        this.activity = activity;
        this.listView = listView;
    }

    //B4 JOB STARTS
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Fetch Data");
        progressDialog.setMessage("Fetching Data...Please wait");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        String data = downloadData();
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        ;
        if (s != null) {
            Parser p = new Parser(context, s, activity, listView);
            p.execute();
        } else {
            Toast.makeText(context, "Unable to download data", Toast.LENGTH_SHORT).show();
        }
    }

    public String downloadData() {
        //connect and get a stream
        InputStream is = null;
        String line = null;
        try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(con.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            if (br != null) {
                while ((line = br.readLine()) != null) {
                    sb.append(line + "n");
                }
            } else {
                return null;
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}