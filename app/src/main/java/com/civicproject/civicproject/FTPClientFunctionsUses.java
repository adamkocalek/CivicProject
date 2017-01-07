package com.civicproject.civicproject;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class FTPClientFunctionsUses extends AppCompatActivity {

    private FTPClientFunctions ftpclient = new FTPClientFunctions();
    private String host = "serwer1633804.home.pl", username = "serwer1633804", password = "33murs0tKiby";
    int port = 21;

    public String ftpDownloadFileWithLogins(String TAG) {
        String loginsDownloaded;
        boolean status;
        status = ftpclient.ftpConnect(host, username, password, port);
        if (status) {
            Log.d(TAG, "Połączenie udane.");
        } else {
            Log.d(TAG, "Połączenie nieudane.");
        }

        ftpclient.ftpChangeDirectory("/important_data/");
        loginsDownloaded = ftpclient.ftpDownloadString("Logins.txt");

        status = ftpclient.ftpDisconnect();
        if (status) {
            Log.d(TAG, "Połączenie zakończone.");
        } else {
            Log.d(TAG, "Połączenie nie mogło zostać zakończone.");
        }

        return loginsDownloaded;
    }

    public void ftpUploadFileWithLogins(String loginsUptaded, String TAG) {
        boolean status;
        status = ftpclient.ftpConnect(host, username, password, port);
        if (status) {
            Log.d(TAG, "Połączenie udane");
        } else {
            Log.d(TAG, "Połączenie nieudane");
        }

        InputStream input = new ByteArrayInputStream(loginsUptaded.getBytes());

        ftpclient.ftpChangeDirectory("/important_data/");
        ftpclient.ftpUploadString(input, "Logins.txt");

        status = ftpclient.ftpDisconnect();
        if (status) {
            Log.d(TAG, "Połączenie zakończone.");
        } else {
            Log.d(TAG, "Połączenie nie mogło zostać zakończone.");
        }

    }

    public Bitmap ftpDownloadImage(String srcFilePath, String TAG) {
        boolean status;
        status = ftpclient.ftpConnect(host, username, password, port);
        if (status) {
            Log.d(TAG, "Połączenie udane");
        } else {
            Log.d(TAG, "Połączenie nieudane");
        }

        ftpclient.ftpChangeDirectory("/images/");
        Bitmap imageBitmap = ftpclient.ftpDownloadBitmap(srcFilePath);

        status = ftpclient.ftpDisconnect();
        if (status) {
            Log.d(TAG, "Połączenie zakończone");
        } else {
            Log.d(TAG, "Połączenie nie mogło zostać zakończone");
        }

        return imageBitmap;
    }


}
