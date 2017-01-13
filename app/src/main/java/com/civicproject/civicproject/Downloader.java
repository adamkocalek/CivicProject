package com.civicproject.civicproject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader extends AsyncTask<Void, Integer, String> {
    Context context;
    String address;
    String taskName;
//    ProgressDialog progressDialog;

    public Downloader(Context context, String address) {
        this.context = context;
        this.address = address;
    }

    public Downloader(Context context, String address, String taskName) {
        this.context = context;
        this.address = address;
        this.taskName = taskName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Pobieranie danych");
//        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        String data = downloadData();
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        progressDialog.dismiss();

        if (s != null) {
            if (taskName == null) {
                Parser p = new Parser(context, s);
                p.execute();
            } else {
                Parser p = new Parser(context, s, taskName);
                p.execute();
            }
        } else {
            Toast.makeText(context, "Nie można pobrać danych.", Toast.LENGTH_SHORT).show();
        }
    }

    public String downloadData() {

        // Połączenie i pobranie ciągu danych w postaci JSON Array
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

        } catch (Exception e) {
            Log.d("LOG Downloader", e + "");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.d("LOG Downloader", e + "");
                }
            }
        }
        return null;
    }
}