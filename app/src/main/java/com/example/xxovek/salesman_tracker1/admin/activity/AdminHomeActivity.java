package com.example.xxovek.salesman_tracker1.admin.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.xxovek.salesman_tracker1.admin.dashboard.AdminDashboard;
import com.example.xxovek.salesman_tracker1.user.Dashboard;
import com.example.xxovek.salesman_tracker1.user.MainActivity;
import com.example.xxovek.salesman_tracker1.R;
import com.example.xxovek.salesman_tracker1.user.TodaysVisitsFragment;
import com.example.xxovek.salesman_tracker1.user.UserProfileFragment;

import com.example.xxovek.salesman_tracker1.admin.tabs.AddShopOnRoutesTab;
import com.example.xxovek.salesman_tracker1.admin.tabs.AssignSalesmanWorkTab;
import com.example.xxovek.salesman_tracker1.admin.tabs.RoutesFragmentTab;
import com.example.xxovek.salesman_tracker1.admin.tabs.SalesPersonFragmentTab;
import com.example.xxovek.salesman_tracker1.admin.tabs.ShopKeepersTab;

public class AdminHomeActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_admin_home);

        SharedPreferences prf = getApplicationContext().getSharedPreferences("Options", MODE_PRIVATE);
        String uid = prf.getString("uid", "");
        String uname = prf.getString("uname", "");


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
        fragmentTransaction.add(R.id.main_container, new AdminDashboard());
        fragmentTransaction.commit();
        signout.setVisibility(View.INVISIBLE);
        getSupportActionBar().setTitle("DASHBOARD");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#004D40")));


        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int newfrag = item.getItemId();
                switch (newfrag) {

                    case R.id.menu_dashboard:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new AdminDashboard());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("DASHBOARD");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        signout.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.sales_person_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new SalesPersonFragmentTab());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("SALES PERSON");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        signout.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.routes_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new RoutesFragmentTab());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("ROUTES");
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        signout.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.shop_keepers_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new ShopKeepersTab());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("SHOP KEEPERS");

                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        signout.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.add_shop_on_routes_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new AddShopOnRoutesTab());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("VISITS HISTORY");

                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        signout.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.assign_salesman_work_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new AssignSalesmanWorkTab());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("VISITS HISTORY");
                        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
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
    }



}
