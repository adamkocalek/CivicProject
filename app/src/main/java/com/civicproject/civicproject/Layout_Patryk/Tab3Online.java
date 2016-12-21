package com.civicproject.civicproject.Layout_Patryk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.civicproject.civicproject.HubActivity;
import com.civicproject.civicproject.R;

public class Tab3Online extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((HubActivity) getActivity()).setActionBarTitle("Contacts");
        View rootView = inflater.inflate(R.layout.tab1contacts, container, false);

        return rootView;
    }
}
