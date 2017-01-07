package com.civicproject.civicproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class BackgroundWorker extends AsyncTask<String, Void, String> {

    private Context context;
    private Activity activity;
    private ListView listView;
    private String output;
    private AlertDialog alertDialog;
    private ArrayList<String> myProjects = new ArrayList<>(), ids = new ArrayList<>(), likes = new ArrayList<>(), dates = new ArrayList<>(), images = new ArrayList<>();
    private ArrayList<Integer> indexs = new ArrayList<>();
    private ArrayList<Bitmap> imagesBitmaps = new ArrayList<>();

    BackgroundWorker(Context context) {
        this.context = context;
    }

    BackgroundWorker(Context context, Activity activity, ListView listView) {
        this.context = context;
        this.activity = activity;
        this.listView = listView;
    }

    String tempJSON;
    String tempAuthor;
    private String projects_url = "http://188.128.220.60/projects.php";
    private Parser parser = new Parser();
    private FTPClientFunctionsUses useFTP = new FTPClientFunctionsUses();
    private String NetworkException = "Błąd połączenia z internetem";
    private String IOException = "Nieoczekiwany błąd, IOException";
    private String TAG = "+++++ TAG +++++";

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://188.128.220.60/login.php";
        String register_url = "http://188.128.220.60/register.php";
        String addProject_url = "http://188.128.220.60/addProject.php";
        String getUser_url = "http://188.128.220.60/getUser.php";
        String update_url = "http://188.128.220.60/updateUser.php";
        String updateProject_url = "http://188.128.220.60/updateProject.php";
        String updateLikes_url = "http://188.128.220.60/updateProjectLikes.php";
        String updatePermission_url = "http://188.128.220.60/updateProjectPermission.php";
        String deleteUser_url = "http://188.128.220.60/deleteUser.php";
        String deleteProject_url = "http://188.128.220.60/deleteProject.php";

        if (type.equals("login")) {
            String result = "";
            try {
                String user_name = params[1];
                String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));   //było iso-8859-1
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                System.out.println(result);

            } catch (MalformedURLException e) {
                Log.d(TAG, NetworkException);
                result = "null";
            } catch (IOException e) {
                Log.d(TAG,IOException);
                result = "null";
            }
            return result;


        } else if (type.equals("register")) {
            String result = "";
            try {
                String name = params[1];
                String surname = params[2];
                String age = params[3];
                String usename = params[4];
                String password = params[5];
                String telephone = params[6];
                String email = params[7];
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("surname", "UTF-8") + "=" + URLEncoder.encode(surname, "UTF-8") + "&"
                        + URLEncoder.encode("age", "UTF-8") + "=" + URLEncoder.encode(age, "UTF-8") + "&"
                        + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(usename, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                        + URLEncoder.encode("telephone", "UTF-8") + "=" + URLEncoder.encode(telephone, "UTF-8") + "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));   ///było iso-8859-1
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                System.out.println(result);

            } catch (MalformedURLException e) {
                Log.d(TAG,NetworkException);
                result = "null";
            } catch (IOException e) {
                Log.d(TAG,IOException);
                result = "null";
            }
            return result;


        } else if (type.equals("addProject")) {
            try {
                String author = params[1];
                String subject = params[2];
                String description = params[3];
                String location = params[4];
                String date = params[5];
                String author_key = params[6];
                String image = params[7];
                URL url = new URL(addProject_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("author", "UTF-8") + "=" + URLEncoder.encode(author, "UTF-8") + "&"
                        + URLEncoder.encode("subject", "UTF-8") + "=" + URLEncoder.encode(subject, "UTF-8") + "&"
                        + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8") + "&"
                        + URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(location, "UTF-8") + "&"
                        + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&"
                        + URLEncoder.encode("author_key", "UTF-8") + "=" + URLEncoder.encode(author_key, "UTF-8") + "&"
                        + URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(image, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));    //było iso-8859-1
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                System.out.println(result);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (type.equals("updateProject")) {
            try {
                String subject = params[1];
                String description = params[2];
                String id = params[3];
                URL url = new URL(updateProject_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("subject", "UTF-8") + "=" + URLEncoder.encode(subject, "UTF-8") + "&"
                        + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8") + "&"
                        + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));   //było iso-8859-1
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                System.out.println(result);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (type.equals("updateProjectLikes")) {
            try {
                String likesids = params[1];
                String likes = params[2];
                String id = params[3];
                String likesnames = params[4];
                URL url = new URL(updateLikes_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("likesids", "UTF-8") + "=" + URLEncoder.encode(likesids, "UTF-8") + "&"
                        + URLEncoder.encode("likes", "UTF-8") + "=" + URLEncoder.encode(likes, "UTF-8") + "&"
                        + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&"
                        + URLEncoder.encode("likesnames", "UTF-8") + "=" + URLEncoder.encode(likesnames, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                System.out.println(result);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("updateProjectPermission")) {
            try {
                String permited = params[1];
                String id = params[2];
                URL url = new URL(updatePermission_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("permited", "UTF-8") + "=" + URLEncoder.encode(permited, "UTF-8") + "&"
                        + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                System.out.println(result);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("updateUser")) {
            try {
                String name = params[1];
                String surname = params[2];
                String age = params[3];
                String usename = params[4];
                String password = params[5];
                String telephone = params[6];
                String email = params[7];
                URL url = new URL(update_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("surname", "UTF-8") + "=" + URLEncoder.encode(surname, "UTF-8") + "&"
                        + URLEncoder.encode("age", "UTF-8") + "=" + URLEncoder.encode(age, "UTF-8") + "&"
                        + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(usename, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                        + URLEncoder.encode("telephone", "UTF-8") + "=" + URLEncoder.encode(telephone, "UTF-8") + "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));   //było iso-8859-1
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                System.out.println(result);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("getUser")) {
            try {
                String user_name = params[1];
                URL url = new URL(getUser_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                tempJSON = result;
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("deleteUser")) {
            try {
                String id = params[1];
                URL url = new URL(deleteUser_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));    //było iso-8859-1
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                tempJSON = result;
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (type.equals("deleteProject")) {
            try {
                String id = params[1];
                URL url = new URL(deleteProject_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));   //było iso-8859-1
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                tempJSON = result;
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("getMyProjects")) {
            String result;
            String author_key = params[1];

            for (int i = 0; i < parser.subjects.size(); i++) {
                if (parser.authors_keys.get(i).equals(author_key)) {
                    ids.add(parser.ids.get(i));
                    myProjects.add(parser.subjects.get(i));
                    likes.add(parser.likes.get(i));
                    dates.add(parser.dates.get(i));
                    images.add(parser.images.get(i));
                    indexs.add(parser.subjects.indexOf(parser.subjects.get(i)));
                }
            }

            for (int i = 0; i < images.size(); i++) {
                imagesBitmaps.add(useFTP.ftpDownloadImage(images.get(i), "UserProjectsActivity"));
            }

            result = "MyProjects";

            tempJSON = result;
            return result;
        } else if (type.equals("getLogins")) {
            String result;

            output = useFTP.ftpDownloadFileWithLogins("RegisterActivity");

            result = "Logins";

            tempJSON = result;
            return result;
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        String temp = "Login success. Welcome!";

        if (result.equals(temp)) {
            final Downloader downloader = new Downloader(context, projects_url);
            downloader.execute();

            Intent intent = new Intent(context, RootActivity.class);
            context.startActivity(intent);
            Toast.makeText(context, "Zalogowano poprawnie.", Toast.LENGTH_SHORT).show();

        } else if (result.equals("null")) {
            new android.support.v7.app.AlertDialog.Builder(new ContextThemeWrapper(context, R.style.Dialog_Theme))
                    .setTitle("Wystąpił błąd!").setMessage("Sprawdź swoje połączenie internetowe i spróbuj ponownie.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    }).show();

        } else if (result.contains("[{")) {

        } else if (result.equals("Logins")) {
            //Toast.makeText(context, input, Toast.LENGTH_SHORT).show();
            ((RegisterActivity)context).editTextEmail.setText(output);

        } else if (result.equals("MyProjects")) {
            ListViewAdapterUser lviewAdapter;
            lviewAdapter = new ListViewAdapterUser(activity, ids, myProjects, likes, dates, imagesBitmaps);
            listView.setAdapter(lviewAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView textView = (TextView) view.findViewById(R.id.textViewIds);
                    if (ids.contains(textView.getText() + "")) {
                        position = ids.indexOf(textView.getText() + "");
                    } else {
                        position = -1;
                    }
                    if (position != -1) {
                        Intent intent = new Intent(activity, UserProjectActivity.class);
                        intent.putExtra("subject", parser.subjects.get(indexs.get(position)));
                        intent.putExtra("description", parser.descriptions.get(indexs.get(position)));
                        intent.putExtra("location", parser.locations.get(indexs.get(position)));
                        intent.putExtra("date", parser.dates.get(indexs.get(position)));
                        intent.putExtra("image", parser.images.get(indexs.get(position)));
                        intent.putExtra("id", parser.ids.get(indexs.get(position)));
                        intent.putExtra("likes", parser.likes.get(indexs.get(position)));
                        intent.putExtra("likesids", parser.likesids.get(indexs.get(position)));
                        intent.putExtra("author", parser.authors.get(indexs.get(position)));
                        intent.putExtra("author_key", parser.authors_keys.get(indexs.get(position)));
                        intent.putExtra("likesnames", parser.likesNames.get(indexs.get(position)));
                        activity.startActivity(intent);
                    }
                }
            });

        } else {
            result = result.replaceAll("<", "");
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}