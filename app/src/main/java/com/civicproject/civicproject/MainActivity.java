package com.civicproject.civicproject;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.civicproject.civicproject.Layout_Szymon.TabFragment;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //MainActivity.this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                // Dodaj pomysł
                if (menuItem.getItemId() == R.id.nav_idea) {
                    Intent i = new Intent(MainActivity.this, ProjectActivity.class);
                    startActivity(i);
                    //finish();

                // Stary wygląd
                } else if (menuItem.getItemId() == R.id.nav_old) {
                    Intent i = new Intent(MainActivity.this, ProjectsActivity.class);
                    startActivity(i);
                    //finish();

                // Layout Szymona
                } else if (menuItem.getItemId() == R.id.nav_szymon) {
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(i);
                    //finish();

                // Layout Patryka
                } else if (menuItem.getItemId() == R.id.nav_patryk) {
                    Intent i = new Intent(MainActivity.this, HubActivity.class);
                    startActivity(i);
                    //finish();

                // Layout Adama
                } else if (menuItem.getItemId() == R.id.nav_adam) {
                    Intent i = new Intent(MainActivity.this, RootActivity.class);
                    startActivity(i);
                    //finish();

                // Mapa
                } else if (menuItem.getItemId() == R.id.nav_map) {
                    Intent i = new Intent(MainActivity.this, Map.class);
                    startActivity(i);
                    //finish();

                // Dodaj pomysł
                } else if (menuItem.getItemId() == R.id.nav_idea) {
                    Intent i = new Intent(MainActivity.this, ProjectActivity.class);
                    startActivity(i);
                    //finish();
                }

                return false;
            }

        });

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
    }
}
