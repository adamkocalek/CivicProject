package com.civicproject.civicproject.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.civicproject.civicproject.R;
import com.civicproject.civicproject.RootActivity;

public class TabHost_Fragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab2_layout, null);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs2);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager2);

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                //tabLayout.getTabAt(0).setIcon(R.drawable.home_icon_tab);
                //tabLayout.getTabAt(1).setIcon(R.drawable.flame_icon_tab);
            }
        });

        return view;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return new NewItems_Fragment();
                case 1: return new PopularItems_Fragment();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Najnowsze";
                case 1:
                    return "Popularne";
            }
            return null;
        }
    }
}
