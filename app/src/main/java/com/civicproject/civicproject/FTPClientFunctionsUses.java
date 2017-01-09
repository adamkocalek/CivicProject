package com.civicproject.civicproject;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;


public class FTPClientFunctionsUses extends AppCompatActivity {

    private FTPClientFunctions ftpclient = new FTPClientFunctions();
    private String host = "serwer1633804.home.pl", username = "serwer1633804", password = "33murs0tKiby";
    int port = 21;

    private String output;

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

    public String ftpUploadImage(String desFileName, String TAG) {
        //final String desFileName = tempAuthorKey + "_" + textViewDate.getText() + ".jpg";
        boolean status;
        status = ftpclient.ftpConnect("serwer1633804.home.pl", "serwer1633804", "33murs0tKiby", 21);
        if (status) {
            Log.d(TAG, "Połączenie udane");
        } else {
            Log.d(TAG, "Połączenie nieudane");
        }

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Civic Project/IMG_CP_COMPRESSED" + ".jpg");
        String srcFilePath = file.toString();
        ftpclient.ftpChangeDirectory("/images/");
        ftpclient.ftpUpload(srcFilePath, desFileName);

        ftpclient.ftpChangeDirectory("/public_html/");
        ftpclient.ftpUpload(srcFilePath, "CheckImageNudity.jpg");
        ftpclient.ftpRemoveFile("CheckImageNudity.jpg");

        status = ftpclient.ftpDisconnect();
        if (status) {
            Log.d(TAG, "Połączenie zakończone");
        } else {
            Log.d(TAG, "Połączenie nie mogło zostać zakończone");
        }

        return desFileName;
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

    public String ftpUploadImageNudity(String TAG) {

        boolean status;
        status = ftpclient.ftpConnect("serwer1633804.home.pl", "serwer1633804", "33murs0tKiby", 21);
        if (status) {
            Log.d(TAG, "Połączenie udane");
        } else {
            Log.d(TAG, "Połączenie nieudane");
        }

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Civic Project/IMG_CP_COMPRESSED" + ".jpg");
        String srcFilePath = file.toString();

        ftpclient.ftpChangeDirectory("/public_html/");
        ftpclient.ftpUpload(srcFilePath, "CheckImageNudity.jpg");

        //ftpclient.ftpRemoveFile("CheckImageNudity.jpg");

        status = ftpclient.ftpDisconnect();
        if (status) {
            Log.d(TAG, "Połączenie zakończone");
        } else {
            Log.d(TAG, "Połączenie nie mogło zostać zakończone");
        }
        return output;
    }
}
