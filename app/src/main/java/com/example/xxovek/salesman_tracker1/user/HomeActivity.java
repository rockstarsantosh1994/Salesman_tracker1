package com.example.xxovek.salesman_tracker1.user;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.xxovek.salesman_tracker1.R;

public class HomeActivity extends AppCompatActivity {
    public static TodaysVisitsFragment myActivity;
    Toolbar toolbar, toolbar1;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    public static final String mypreference = "prf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        SharedPreferences prf = getApplicationContext().getSharedPreferences("Options", MODE_PRIVATE);
        String uid = prf.getString("uid", "");
        String uname = prf.getString("uname", "");

        checkLocationPermission();

        final ImageButton signout = (ImageButton) findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new TodaysVisitsFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("TODAYS VISITS");
                drawerLayout.closeDrawers();
                //item.setChecked(true);
            }
        });
        Toast.makeText(getApplicationContext(), uid.toString(), Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), uname.toString(), Toast.LENGTH_LONG).show();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new UserProfileFragment());
        fragmentTransaction.commit();
        signout.setVisibility(View.INVISIBLE);
        getSupportActionBar().setTitle("USER PROFILE");
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int newfrag = item.getItemId();
                switch (newfrag) {
                    case R.id.user_profile_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new UserProfileFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("USER PROFILE");

                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        signout.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.admin_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new AdminDetailsFragment()).addToBackStack(null).commit();
                        //fragmentTransaction.commit();
                        getSupportActionBar().setTitle("ADMIN DETAILS");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        signout.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.todays_visits_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new TodaysVisitsFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("TODAYS VISITS");

                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        signout.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.visit_history_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new VisitsHistoryFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("VISITS HISTORY");

                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        signout.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.sign_out_id:
                        SharedPreferences prf = getApplicationContext().getSharedPreferences("Options", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prf.edit();
                        editor.clear();
                        editor.commit();
                        finish();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(),
                                "LOGGED OUT",
                                Toast.LENGTH_LONG).show();
                        break;


                }


                return false;
            }
        });

//        FragmentManager fm = getSupportFragmentManager();
//        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                if(getSupportFragmentManager().getBackStackEntryCount() == 0) finish();
//            }
//        });

    }


    @Override
    public void onBackPressed() {
        Toast.makeText(HomeActivity.this, "helloppp", Toast.LENGTH_SHORT).show();


        new AlertDialog.Builder(this)
                .setTitle("EXIT !!!")
                .setMessage("Are you sure you want to exit !!!")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation

                        SharedPreferences prf = getApplicationContext().getSharedPreferences("Options", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prf.edit();
                        editor.clear();
                        editor.commit();
                        finish();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(),
                                "LOGGED OUT",
                                Toast.LENGTH_LONG).show();

                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("GIVE LOCATION PERMISSION")
                        .setMessage("press ok and set the location permissions")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(HomeActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstance) {
        super.onPostCreate(savedInstance);
        actionBarDrawerToggle.syncState();
    }

}