package com.example.xxovek.salesman_tracker1.admin.addshopsonroute;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xxovek.salesman_tracker1.R;


public class ShowRouteDetailsFragment extends Fragment {

    public ShowRouteDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_route_details, container, false);
    }


}
