package com.example.xxovek.salesman_tracker1.admin.shopkeeper;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xxovek.salesman_tracker1.R;

public class ShowShopkeeperFragment extends Fragment {

    public ShowShopkeeperFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_route_details, container, false);
    }

}
