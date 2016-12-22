package com.civicproject.civicproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.civicproject.civicproject.Fragments.Tab2Fragment;

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
        mFragmentTransaction.replace(R.id.containerView1, new Tab2Fragment()).commit();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

//                if (menuItem.getItemId() == R.id.nav_main) {
//                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.containerView1, new PopularItems_Fragment()).commit();
//                }

                if(menuItem.getItemId() == R.id.nav_main){
                    finish();
                    Intent intent = new Intent(RootActivity.this, RootActivity.class);
                    startActivity(intent);
                }

                else if(menuItem.getItemId() == R.id.nav_idea){
                    Intent intent = new Intent(RootActivity.this, AddProjectActivity.class);
                    startActivity(intent);
                }

                else if(menuItem.getItemId() == R.id.nav_old){
                    Intent intent = new Intent(RootActivity.this, ProjectsActivity.class);
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
                    finish();
                    Intent intent = new Intent(RootActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                return false;
            }

        });

        //android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar1);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
    }
}