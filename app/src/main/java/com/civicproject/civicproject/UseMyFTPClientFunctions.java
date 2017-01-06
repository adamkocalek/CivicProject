package com.civicproject.civicproject;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class UseMyFTPClientFunctions extends AppCompatActivity {

    private MyFTPClientFunctions ftpclient = new MyFTPClientFunctions();
    private static final String TAG = "ProjectActivity";
    private String host = "serwer1633804.home.pl", username = "serwer1633804", password = "33murs0tKiby";
    int port = 21;


    public Bitmap ftpDownloadImage(String srcFilePath) {
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
