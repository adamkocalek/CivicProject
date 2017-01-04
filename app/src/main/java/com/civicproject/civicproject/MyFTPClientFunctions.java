package com.civicproject.civicproject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by Patrycjusz on 2016-12-14.
 */

public class MyFTPClientFunctions {

    // Now, declare a public FTP client object.

    private static final String TAG = "MyFTPClientFunctions";
    public FTPClient mFTPClient = null;

    // Method to connect to FTP server:
    public boolean ftpConnect(String host, String username, String password,
                              int port) {
        try {
            mFTPClient = new FTPClient();
            // connecting to the host
            mFTPClient.connect(host, port);

            // now check the reply code, if positive mean connection success
            if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {
                // login using username & password
                boolean status = mFTPClient.login(username, password);

				/*
                 * Set File Transfer Mode
				 *
				 * To avoid corruption issue you must specified a correct
				 * transfer mode, such as ASCII_FILE_TYPE, BINARY_FILE_TYPE,
				 * EBCDIC_FILE_TYPE .etc. Here, I use BINARY_FILE_TYPE for
				 * transferring text, image, and compressed files.
				 */
                mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
                mFTPClient.enterLocalPassiveMode();

                return status;
            }
        } catch (Exception e) {
            Log.d(TAG, "Error: nie można się połączyć z hostem " + host);
        }

        return false;
    }

    // Method to disconnect from FTP server:

    public boolean ftpDisconnect() {
        try {
            mFTPClient.logout();
            mFTPClient.disconnect();
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error wystąpił podczas rozłączania z serwerem ftp.");
        }

        return false;
    }

    // Method to get current working directory:

    public String ftpGetCurrentWorkingDirectory() {
        try {
            String workingDir = mFTPClient.printWorkingDirectory();
            return workingDir;
        } catch (Exception e) {
            Log.d(TAG, "Error: nie można pobrać akutalnie działającego folderu.");
        }

        return null;
    }

    // Method to change working directory:

    public boolean ftpChangeDirectory(String directory_path) {
        try {
            mFTPClient.changeWorkingDirectory(directory_path);
        } catch (Exception e) {
            Log.d(TAG, "Error: nie można zmienic folderu na " + directory_path);
        }

        return false;
    }

    // Method to list all files in a directory:

    public String[] ftpPrintFilesList(String dir_path) {
        String[] fileList = null;
        try {
            FTPFile[] ftpFiles = mFTPClient.listFiles(dir_path);
            int length = ftpFiles.length;
            fileList = new String[length];
            for (int i = 0; i < length; i++) {
                String name = ftpFiles[i].getName();
                boolean isFile = ftpFiles[i].isFile();

                if (isFile) {
                    fileList[i] = "Folder :: " + name;
                    Log.i(TAG, "Folder : " + name);
                } else {
                    fileList[i] = "Folder :: " + name;
                    Log.i(TAG, "Folder : " + name);
                }
            }
            return fileList;
        } catch (Exception e) {
            e.printStackTrace();
            return fileList;
        }
    }

    // Method to create new directory:

    public boolean ftpMakeDirectory(String new_dir_path) {
        try {
            boolean status = mFTPClient.makeDirectory(new_dir_path);
            return status;
        } catch (Exception e) {
            Log.d(TAG, "Error: nie można utowrzyć nowego folderu o nazwie "
                    + new_dir_path);
        }

        return false;
    }

    // Method to delete/remove a directory:

    public boolean ftpRemoveDirectory(String dir_path) {
        try {
            boolean status = mFTPClient.removeDirectory(dir_path);
            return status;
        } catch (Exception e) {
            Log.d(TAG, "Error: nie można usunąć folderu o nazwie " + dir_path);
        }

        return false;
    }

    // Method to delete a file:

    public boolean ftpRemoveFile(String filePath) {
        try {
            boolean status = mFTPClient.deleteFile(filePath);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Method to rename a file:

    public boolean ftpRenameFile(String from, String to) {
        try {
            boolean status = mFTPClient.rename(from, to);
            return status;
        } catch (Exception e) {
            Log.d(TAG, "Nie można zmienić nazwy: " + from + " na: " + to);
        }

        return false;
    }

    // Method to download a file from FTP server:

    /**
     * mFTPClient: FTP client connection object (see FTP connection example)
     * srcFilePath: path to the source file in FTP server desFilePath: path to
     * the destination file to be saved in sdcard
     */
    public boolean ftpDownload(String srcFilePath, String desFilePath) {
        boolean status = false;
        try {
            FileOutputStream desFileStream = new FileOutputStream(desFilePath);

            status = mFTPClient.retrieveFile(srcFilePath, desFileStream);
            desFileStream.close();

            return status;
        } catch (Exception e) {
            Log.d(TAG, "pobieranie nieudane");
        }

        return status;
    }

    public Bitmap ftpDownloadBitmap(String srcFilePath) {
        Bitmap image = null;
        try {
            InputStream srcFileStream = mFTPClient.retrieveFileStream(srcFilePath);;
            image = BitmapFactory.decodeStream(srcFileStream);

            return image;
        } catch (Exception e) {
            Log.d(TAG, "pobieranie nieudane");
        }

        return image;
    }

    // Method to upload a file to FTP server:

    /**
     * mFTPClient: FTP client connection object (see FTP connection example)
     * srcFilePath: source file path in sdcard desFileName: file name to be
     * stored in FTP server desDirectory: directory path where the file should
     * be upload to
     */
    public boolean ftpUpload(String srcFilePath, String desFileName) {
        boolean status = false;
        try {
            FileInputStream srcFileStream = new FileInputStream(srcFilePath);

            // change working directory to the destination directory
            // if (ftpChangeDirectory(desDirectory)) {
            status = mFTPClient.storeFile(desFileName, srcFileStream);
            // }

            srcFileStream.close();

            return status;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "dodawanie nieudane: " + e);
        }

        return status;
    }

    public boolean ftpUploadString(InputStream srcFileStream, String desFileName) {
        boolean status = false;
        try {

            // change working directory to the destination directory
            // if (ftpChangeDirectory(desDirectory)) {
            status = mFTPClient.storeFile(desFileName, srcFileStream);
            // }

            srcFileStream.close();

            return status;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "dodawanie nieudane: " + e);
        }

        return status;
    }

    public String ftpDownloadString(String srcFilePath) {
        String logins = null;
        try {
            InputStream srcFileStream = mFTPClient.retrieveFileStream(srcFilePath);

            Scanner s = new Scanner(srcFileStream).useDelimiter("\\A");
            logins = s.hasNext() ? s.next() : "";

            return logins;
        } catch (Exception e) {
            Log.d(TAG, "pobieranie nieudane");
        }

        return logins;
    }


}