package com.civicproject.civicproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    String TAG = "LOG RootActivity";

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(RootActivity.this, R.style.Dialog_Theme))
                .setTitle("Wystąpił błąd!")
                .setMessage("Problem z dostępem do internetu. Sprawdź połączenie i spróbuj ponownie później.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout1);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff1);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView1, new TabHost_Fragment()).commit();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.nav_main) {
                    boolean networkCheck = isOnline();
                    if (!networkCheck) {
                        Log.d(TAG, "Błąd połączenia z internetem.");
                        alertDialog.show();
                        return false;
                    }

                    String projects_url = "http://188.128.220.60/projects.php";
                    Downloader downloader = new Downloader(RootActivity.this, projects_url, "RootActivityIntent");
                    downloader.execute();

                    //finish();
                    //Intent intent = new Intent(RootActivity.this, RootActivity.class);
                    //startActivity(intent);
                }

                if (menuItem.getItemId() == R.id.nav_idea) {
                    boolean networkCheck = isOnline();
                    if (!networkCheck) {
                        Log.d(TAG, "Błąd połączenia z internetem.");
                        alertDialog.show();
                        return false;
                    }
                    Intent intent = new Intent(RootActivity.this, AddProjectActivity.class);
                    startActivity(intent);

                } else if (menuItem.getItemId() == R.id.nav_map) {
                    boolean networkCheck = isOnline();
                    if (!networkCheck) {
                        Log.d(TAG, "Błąd połączenia z internetem.");
                        alertDialog.show();
                        return false;
                    }
                    Intent intent = new Intent(RootActivity.this, Map.class);
                    startActivity(intent);

                } else if (menuItem.getItemId() == R.id.nav_my_account) {
                    boolean networkCheck = isOnline();
                    if (!networkCheck) {
                        Log.d(TAG, "Błąd połączenia z internetem.");
                        alertDialog.show();
                        return false;
                    }
                    Intent intent = new Intent(RootActivity.this, UserAreaActivity.class);
                    startActivity(intent);

                } else if (menuItem.getItemId() == R.id.nav_my_projects) {
                    boolean networkCheck = isOnline();
                    if (!networkCheck) {
                        Log.d(TAG, "Błąd połączenia z internetem.");
                        alertDialog.show();
                        return false;
                    }
                    Intent intent = new Intent(RootActivity.this, UserProjectsActivity.class);
                    startActivity(intent);

                } else if (menuItem.getItemId() == R.id.nav_rules) {
                    Intent intent = new Intent(RootActivity.this, RulesActivity.class);
                    startActivity(intent);

                } else if (menuItem.getItemId() == R.id.nav_help) {
                    Intent intent = new Intent(RootActivity.this, HelpActivity.class);
                    startActivity(intent);

                } else if (menuItem.getItemId() == R.id.nav_about) {
                    Intent intent = new Intent(RootActivity.this, AboutActivity.class);
                    startActivity(intent);

                } else if (menuItem.getItemId() == R.id.nav_voting) {
                    boolean networkCheck = isOnline();
                    if (!networkCheck) {
                        Log.d(TAG, "Błąd połączenia z internetem.");
                        alertDialog.show();
                        return false;
                    }
                    Intent intent = new Intent(RootActivity.this, VoteActivity.class);
                    startActivity(intent);

                } else if (menuItem.getItemId() == R.id.nav_logout) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Wylogowano poprawnie.", Toast.LENGTH_SHORT).show();
                }

                return false;
            }

        });

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}