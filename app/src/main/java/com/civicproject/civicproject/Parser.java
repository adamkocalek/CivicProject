package com.civicproject.civicproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class Parser extends AsyncTask<Void,Integer,Integer> {
    Context context;
    ListView listView;
    String data;
    ArrayList<String> players=new ArrayList<>();
    ProgressDialog progressDialog;
    public Parser(Context context, String data, ListView listView) {
        this.context = context;
        this.data = data;
        this.listView = listView;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Parser");
        progressDialog.setMessage("Parsing ....Please wait");
        progressDialog.show();
    }
    @Override
    protected Integer doInBackground(Void... params) {
        return this.parse();
    }
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer == 1)
        {
            //ADAPTER
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,players);
            //ADAPT TO LISTVIEW
            listView.setAdapter(adapter);
            //LISTENET
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            });
        }else
        {
            Toast.makeText(context,"Unable to Parse",Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
    }
    //PARSE RECEIVED DATA
    private int parse()
    {
        try
        {
            //ADD THAT DATA TO JSON ARRAY FIRST
            JSONArray ja=new JSONArray(data);
            //CREATE JO OBJ TO HOLD A SINGLE ITEM
            JSONObject jo=null;
            players.clear();
            //LOOP THRU ARRAY
            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                //RETRIOEVE NAME
                String subject = jo.getString("subject");
                //ADD IT TO OUR ARRAYLIST
                players.add(subject);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}