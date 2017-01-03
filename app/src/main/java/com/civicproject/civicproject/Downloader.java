package com.civicproject.civicproject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
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

import dmax.dialog.SpotsDialog;

public class Downloader extends AsyncTask<Void, Integer, String> {
    Context context;
    String address;
    SpotsDialog progressDialog;
    public static boolean run = false;

    public Downloader(Context context, String address) {
        this.context = context;
        this.address = address;
    }

    //B4 JOB STARTS
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new SpotsDialog(context, R.style.CustomDialogDownload);
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

        if (s != null) {
            Parser p = new Parser(context, s);
            p.execute();
        } else {
            Toast.makeText(context, "Nie można pobrać danych.", Toast.LENGTH_SHORT).show();
        }
        run = true;
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