package com.example.xxovek.salesman_tracker1.user;

import android.Manifest;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.xxovek.salesman_tracker1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private static final String TAG = "";
    private GoogleMap mMap, mMap1;
    private LocationListener locationListener, locationListener1;
    private LocationManager locationManager, locationManager1;
    public String st_assignid;

    Location location;

    ProgressDialog dialog;
    Button stop1;

    private String uid;
    int flag = 0, flag3 = 0;
    private LatLng latLng;
    Context context;

    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 500;
    ArrayList<Object> object, object1;

    private Polyline currentPolyline;
    String data, shopid1 = "";

    MarkerOptions place1, place2, place;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_maps);
            // ... rest of body of onCreateView() ...
        } catch (Exception e) {
            Log.e(TAG, "onCreateView", e);
            throw e;
        }
        // setContentView(R.layout.activity_maps);

        stop1 = (Button) findViewById(R.id.stop1);
        stop1.setOnClickListener(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait!");
        dialog.show();

        Intent intent1 = getIntent();
        Bundle args1 = intent1.getBundleExtra("BUNDLE");
        object = (ArrayList<Object>) args1.getSerializable("ARRAYLIST");
        object1 = (ArrayList<Object>) args1.getSerializable("ARRAYLIST1");

        st_assignid = getIntent().getExtras().getString("data1", "data1");
        Log.d("MYTAG", "onCreateView_MAPS: " + st_assignid);


        data = getIntent().getExtras().getString("latlong1", "defaultKey");


        SharedPreferences prf = getApplicationContext().getSharedPreferences("Options", getApplicationContext().MODE_PRIVATE);
        uid = prf.getString("uid", "");
        String uname = prf.getString("uname", "");
        Toast.makeText(getApplicationContext(), "user is: " + uid.toString(), Toast.LENGTH_SHORT).show();



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);


    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap1 = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        // when you need location
        // if inside activity context = this;

        // Add a marker in Sydney and move the cameralass 'MainActivity' must either be declared abstra


        locationListener = new LocationListener() {


            @Override
            public void onLocationChanged(Location location) {


                try {

                    shopid1 = "";
                    flag = 0;
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    double lat = location.getLatitude();
                    String lat1 = Double.toString(lat);
                    double lon = location.getLongitude();
                    String lon1 = Double.toString(lon);

                    mMap.clear();

//                    mMap.addMarker(place1);
//                    mMap.addMarker(place2);


                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);

//                    Toast.makeText(getApplicationContext(), "Location changed: Lat: " + latLng.toString(), Toast.LENGTH_SHORT).show();
                    mMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16f));

                    for (int i = 0; i < object.size(); i++) {

                        String str = object.get(i).toString();
                        String[] splitStr = str.split("\\s+");
                        String str1 = splitStr[0];
                        String str2 = splitStr[1];


                        double d1 = Double.parseDouble(str1);
                        double d2 = Double.parseDouble(str2);

//                        Toast.makeText(getApplicationContext(), "o1: " + object.get(i).toString(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(), "o2: " + object1.get(i).toString(), Toast.LENGTH_SHORT).show();

                        place = new MarkerOptions().position(new LatLng(d1, d2)).title("SHOPS " + i);
                        mMap1.addMarker(place);

//                        double earthRadius = 3958.75;
//
//                        double dLat = Math.toRadians(d1-lat);
//                        double dLng = Math.toRadians(d2-lon);
//                        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
//                                Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(d1)) *
//                                        Math.sin(dLng/2) * Math.sin(dLng/2);
//                        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
//                        double dist = earthRadius * c;
//
//                        String dist1 = String.valueOf(dist);
                        // Toast.makeText(getApplicationContext(), "disance is: " + dist1.toString(), Toast.LENGTH_SHORT).show();

                        Location startPoint = new Location("locationA");
                        startPoint.setLatitude(d1);
                        startPoint.setLongitude(d2);

                        Location endPoint = new Location("locationB");
                        endPoint.setLatitude(lat);
                        endPoint.setLongitude(lon);

                        double distance = startPoint.distanceTo(endPoint);
                        String dist12 = String.valueOf(distance);
                        Toast.makeText(getApplicationContext(), "disance from shop is: " + dist12.toString(), Toast.LENGTH_SHORT).show();

                        if (distance <= 150) {
                            if (!(shopid1.isEmpty() || shopid1.equals(null))) {
                                shopid1 += "," + object1.get(i).toString();
//                                Toast.makeText(getApplicationContext(), "first if: " + shopid1.toString(), Toast.LENGTH_SHORT).show();
                                flag = 1;
                            } else {
                                shopid1 = object1.get(i).toString();
//                                Toast.makeText(getApplicationContext(), "second if: " + object1.get(i).toString(), Toast.LENGTH_SHORT).show();
                                flag = 1;

                            }


                        }
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }

                    }



                    if (flag == 1) {

                        MapsActivity11 newMapsActivity11 = new MapsActivity11(MapsActivity.this);
                        newMapsActivity11.execute(st_assignid, lat1, lon1, shopid1);
                    }
                    else{

                        Toast.makeText(getApplicationContext(), "latitude " + lat1.toString()+ "and longitude  "+ lon1.toString(), Toast.LENGTH_LONG).show();

                        MapsActivity11 newMapsActivity11 = new MapsActivity11(MapsActivity.this);
                        newMapsActivity11.execute(st_assignid, lat1, lon1, "0");
                    }




                } catch (SecurityException e) {
                    Toast.makeText(getApplicationContext(), "VIKAS: ", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

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



        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        try {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60*1000, 0, locationListener);


        } catch (SecurityException e) {
            e.printStackTrace();
        }


    }

    private boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }


    // Use like this:
   // boolean foregroud = new ForegroundCheckTask().execute(context).get();


    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_options, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.stop1:
                //locationManager=null;
                locationManager.removeUpdates((locationListener));
                //super.onPause();
                Toast.makeText(getApplicationContext(), " stopped ", Toast.LENGTH_SHORT).show();

                break;

        }

    }


    public class MapsActivity11 extends AsyncTask<String, Void, String> {

        public MapsActivity11(MapsActivity todaysVisits) {

        }

        @Override
        protected String doInBackground(String... params) {
            String countries_url = "http://track.xxovek.com/public_html/salesandroid/droplatlong";

            try {
                String uid = params[0];
                String lan = params[1];
                String lon = params[2];
                String shopid = params[3];




                URL url = new URL(countries_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("lan", "UTF-8") + "=" + URLEncoder.encode(lan, "UTF-8")
                        +"&"+ URLEncoder.encode("lon","UTF-8")+"="+ URLEncoder.encode(lon,"UTF-8")
                        +"&"+ URLEncoder.encode("uid","UTF-8")+"="+ URLEncoder.encode(uid,"UTF-8")
                        +"&"+ URLEncoder.encode("shopid","UTF-8")+"="+ URLEncoder.encode(shopid,"UTF-8");




                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String result1 = "";
                String line = "";
                ArrayList<String> categories1 = new ArrayList<String>();

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;

                }


                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(final String result) {

            if (result.isEmpty() || result.equals(null)) {
                Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();

            } else {
                try {

                    Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();

                    List<String> al = new ArrayList<String>();
                    List<String> al1 = new ArrayList<String>();
                    List<String> al2 = new ArrayList<String>();
                    List<String> al3 = new ArrayList<String>();





                    JSONArray json_data = new JSONArray(result);
                    int len = json_data.length();
                    Toast.makeText(getApplicationContext(), json_data.toString(), Toast.LENGTH_SHORT).show();



                    String result1 = result.replace("\"", "");
                    result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                    String str[] = result1.split(",");


                    //al = Arrays.asList(n);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }

    }


}
