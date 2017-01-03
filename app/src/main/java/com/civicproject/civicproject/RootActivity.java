package com.civicproject.civicproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.civicproject.civicproject.Fragments.TabHost_Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RootActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    public Toolbar toolbar;

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        overridePendingTransition(R.anim.right_in,R.anim.left_out);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout1);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff1);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView1, new TabHost_Fragment()).commit();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if(menuItem.getItemId() == R.id.nav_main){
                    finish();
                    Intent intent = new Intent(RootActivity.this, RootActivity.class);
                    startActivity(intent);
                }

                if(menuItem.getItemId() == R.id.nav_idea){
                    Intent intent = new Intent(RootActivity.this, AddProjectActivity.class);
                    startActivity(intent);
                }

                else if(menuItem.getItemId() == R.id.nav_map){
                    Intent intent = new Intent(RootActivity.this, Map.class);
                    startActivity(intent);
                }

                else if(menuItem.getItemId() == R.id.nav_my_account){
                    Intent intent = new Intent(RootActivity.this, UserAreaActivity.class);
                    startActivity(intent);
                }

                else if(menuItem.getItemId() == R.id.nav_my_projects){
                    Intent intent = new Intent(RootActivity.this, UserProjectsActivity.class);
                    startActivity(intent);
                }

                else if(menuItem.getItemId() == R.id.nav_rules){
                    Intent intent = new Intent(RootActivity.this, RulesActivity.class);
                    startActivity(intent);
                }

                else if(menuItem.getItemId() == R.id.nav_help){
                    Intent intent = new Intent(RootActivity.this, HelpActivity.class);
                    startActivity(intent);
                }

                else if(menuItem.getItemId() == R.id.nav_about){
                    Intent intent = new Intent(RootActivity.this, AboutActivity.class);
                    startActivity(intent);
                }

                else if(menuItem.getItemId() == R.id.nav_logout){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Wylogowano poprawnie.",Toast.LENGTH_SHORT).show();
                }

                return false;
            }

        });

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

        SharedPreferences myprefs = getSharedPreferences("user", MODE_PRIVATE);

        String username = myprefs.getString("username", null);
        String type = "getUser";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        JSONArray ja = null;
        try {
            ja = new JSONArray(backgroundWorker.tempJSON);
            JSONObject jo1 = null;
            for (int i = 0; i < ja.length(); i++) {
                SharedPreferences sharedPreferences = this.getSharedPreferences("user", MODE_PRIVATE);

                jo1 = ja.getJSONObject(i);
                String name = jo1.getString("name");
                String surname = jo1.getString("surname");
                String age = jo1.getString("age");
                String password = jo1.getString("password");
                String author_key = jo1.getString("id");
                String telephone = jo1.getString("telephone");
                String email = jo1.getString("email");

                sharedPreferences.edit().putString("name", name).apply();
                sharedPreferences.edit().putString("surname", surname).apply();
                sharedPreferences.edit().putString("age", age).apply();
                sharedPreferences.edit().putString("password", password).apply();
                sharedPreferences.edit().putString("author_key", author_key).apply();
                sharedPreferences.edit().putString("telephone", telephone).apply();
                sharedPreferences.edit().putString("email", email).apply();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}