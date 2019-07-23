package com.example.xxovek.salesman_tracker1;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard extends Fragment {

    double latitude = 18.526110;
    double longitude = 73.844131;

    public Dashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

        Toast.makeText(getContext(), address.toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), city.toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), state.toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), country.toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), postalCode.toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), knownName.toString(), Toast.LENGTH_SHORT).show();


        return view;
    }

}
