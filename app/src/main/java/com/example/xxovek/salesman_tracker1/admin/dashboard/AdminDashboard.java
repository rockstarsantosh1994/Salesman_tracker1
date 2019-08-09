package com.example.xxovek.salesman_tracker1.admin.dashboard;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.xxovek.salesman_tracker1.ConfigUrls;
import com.example.xxovek.salesman_tracker1.R;
import com.example.xxovek.salesman_tracker1.user.MapsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.LOCATION_SERVICE;

public class AdminDashboard extends Fragment implements OnMapReadyCallback ,View.OnClickListener {

    public String admin_id,lattitude="",longitude="",sellname;
    private LatLng latLng;
    private GoogleMap mMap;
    public LocationListener locationListener;
    public LocationManager locationManager;
    public SupportMapFragment mapFragment;

    public AdminDashboard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_admin_dashboard, container, false);

        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        admin_id=prf.getString("admin_id", "");
        Log.d("mytag", "onCreateView:Admin_id in AddRoutesFragment "+admin_id);


        //We will get Lattitude ,longitude and name.....by below function.........
        Timer timer=new Timer();
        TimerTask hourlyTask=new TimerTask() {
            @Override
            public void run() {
                try{
                    fetchLiveTrackPosition();
                }catch (NullPointerException e){e.printStackTrace();}
            }
        };
        //schedule the task to run starting now and then every minute.....
        timer.schedule(hourlyTask,01,1000*60);

        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.admin_map);


       return view;

    }




    public void fetchLiveTrackPosition(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrls.FETCH_LIVE_TRACK_POSITION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            //.makeText(AddSalesmanFragment.this.getContext(), "No Shops"+response.toString(), //.LENGTH_LONG).show();

                        }else{

                            try {
                                Log.d("mytag", "\n\nonResponse:FETCH_SALE_INFO "+response);



                                JSONObject json = new JSONObject(response);

                                lattitude=json.getString("latitude");
                                longitude=json.getString("longitude");
                                sellname=json.getString("SellName");
                                Log.d("mytag", "onResponse:json Bhau"+json);
                                Log.d("mytag", "onResponse:lattitude "+lattitude+"\n longitude "+longitude+
                                        "\n sell Name"+sellname);



                                mapFragment.getMapAsync(AdminDashboard.this);


                            }catch (JSONException e){e.printStackTrace();}
                            catch(IndexOutOfBoundsException e){e.printStackTrace();}

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("admin_id",admin_id);


                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("mytag1", "\nonMapReady: "+lattitude+"\nlongitue"+longitude+"\nname"+sellname);
            mMap=googleMap;


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try {



          /*  Double longitude1 = Double.parseDouble(longitude);
            Double lattitude1 = Double.parseDouble(lattitude);*/



           /* Double longitude1 = 73.84953666666667;
            Double lattitude1 =18.533088333333335;*/
           if(!longitude.equals("") && !lattitude.equals("")) {
               Double longitude1 = Double.parseDouble(longitude);
               Double lattitude1 = Double.parseDouble(lattitude);

               Log.d("mytag", "onMapReady:Double Longitude1 " + longitude1 + "\n lattitude1" + lattitude1);
               latLng = new LatLng(lattitude1, longitude1);

               mMap.addMarker(new MarkerOptions().position(latLng).title(sellname));
               mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
           }
        }catch (NullPointerException e){e.printStackTrace();}
        catch (NumberFormatException e){e.printStackTrace();}
        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        locationListener =new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Double longitude1 = Double.parseDouble(longitude);
                Double lattitude1 = Double.parseDouble(lattitude);
                latLng = new LatLng(lattitude1,longitude1);
                 Toast.makeText(getContext(), "latlong"+latLng, Toast.LENGTH_SHORT).show();
                mMap.addMarker(new MarkerOptions().position(latLng).title("Aniket Shinde"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16f));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        try {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000*60, 0, locationListener);


        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {

    }
}
