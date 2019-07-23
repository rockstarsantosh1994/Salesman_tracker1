package com.example.xxovek.salesman_tracker1;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
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
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class MyService extends Service implements LocationListener {

    private static final String LOG_TAG = "";
    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    long notify_interval = 1000;
    public static String str_receiver = "servicetutorial.service.receiver";
    Intent intent;
    ArrayList<Object> object, object1;
    String data, shopid1 = "";
    int flag = 0, flag3 = 0;
    private LatLng latLng;
    private String uid;
    int flag5 = 0;
    private Bundle args;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String startId1 = String.valueOf(startId);
        Toast.makeText(MyService.this, startId1.toString(), Toast.LENGTH_LONG).show();


        args = intent.getBundleExtra("BUNDLE");
        object = (ArrayList<Object>) args.getSerializable("ARRAYLIST");
        object1 = (ArrayList<Object>) args.getSerializable("ARRAYLIST1");

        Toast.makeText(getApplicationContext(), "o1: " + object.toString(), Toast.LENGTH_SHORT).show();

        SharedPreferences prf = getApplicationContext().getSharedPreferences("Options", getApplicationContext().MODE_PRIVATE);
        uid = prf.getString("uid", "");
        String uname = prf.getString("uname", "");
        Toast.makeText(getApplicationContext(), "user is: " + uid.toString(), Toast.LENGTH_SHORT).show();

//        if (intent.getAction().equals(SyncStateContract.Constants.ACTION.STARTFOREGROUND_ACTION)) {
//            Log.i(LOG_TAG, "Received Start Foreground Intent ");


//        } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
//            Log.i(LOG_TAG, "Clicked Previous");
//        } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
//            Log.i(LOG_TAG, "Clicked Play");
//        } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
//            Log.i(LOG_TAG, "Clicked Next");
//        } else if (intent.getAction().equals(
//                Constants.ACTION.STOPFOREGROUND_ACTION)) {
//            Log.i(LOG_TAG, "Received Stop Foreground Intent");
//            stopForeground(true);
//            stopSelf();
//        }

        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable) {

        } else {


            if (isGPSEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    //return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60 * 1000, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        Log.e("latitude", location.getLatitude() + "");
                        Log.e("longitude", location.getLongitude() + "");
                        latitude = location.getLatitude();
                        String latitude1 = String.valueOf(latitude);
                        longitude = location.getLongitude();
                        String longitude1 = String.valueOf(longitude);


                        Toast.makeText(getApplicationContext(), "nice " + latitude1.toString() + " on row number ", Toast.LENGTH_SHORT).show();

                        try {

                            shopid1 = "";
                            flag = 0;
                            latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            double lat = location.getLatitude();
                            String lat1 = Double.toString(lat);
                            double lon = location.getLongitude();
                            String lon1 = Double.toString(lon);


//                    mMap.addMarker(place1);
//                    mMap.addMarker(place2);


//                    Toast.makeText(getApplicationContext(), "Location changed: Lat: " + latLng.toString(), Toast.LENGTH_SHORT).show();


                            for (int i = 0; i < object.size(); i++) {

                                String str = object.get(i).toString();
                                String[] splitStr = str.split("\\s+");
                                String str1 = splitStr[0];
                                String str2 = splitStr[1];


                                double d1 = Double.parseDouble(str1);
                                double d2 = Double.parseDouble(str2);

//                        Toast.makeText(getApplicationContext(), "o1: " + object.get(i).toString(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(), "o2: " + object1.get(i).toString(), Toast.LENGTH_SHORT).show();


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


                            }


                            if (flag == 1) {

                                MapsActivity11 newMapsActivity11 = new MapsActivity11(MyService.this);
                                newMapsActivity11.execute(uid, lat1, lon1, shopid1);
                            } else {

                                Toast.makeText(getApplicationContext(), "latitude " + lat1.toString() + "and longitude  " + lon1.toString(), Toast.LENGTH_LONG).show();

                                MapsActivity11 newMapsActivity11 = new MapsActivity11(MyService.this);
                                newMapsActivity11.execute(uid, lat1, lon1, "0");
                            }


                        } catch (SecurityException e) {
                            Toast.makeText(getApplicationContext(), "VIKAS: ", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }
            }//
//             intent = new Intent(getApplicationContext(), MapsActivity.class);
////
//                intent.putExtra("BUNDLE",args);
//                startActivity(intent);

        }

        Intent notificationIntent = new Intent(this, MyService.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);


//            Bitmap icon = BitmapFactory.decodeResource(getResources(),
//                    R.drawable.truiton_short);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Salesman Tracker")
                .setTicker("Salesman Tracker Runing")
                .setContentText("Background Service")
                .setSmallIcon(R.drawable.ic_launcher)
//                    .setLargeIcon(
//                            Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.common_google_signin_btn_text_disabled, getString(R.string.common_google_play_services_enable_button), pendingIntent)

                .setOngoing(true).build();

        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                notification);

        super.onStartCommand(intent, flags, startId);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        super.onCreate();


//        SharedPreferences prf = getApplicationContext().getSharedPreferences("Options", getApplicationContext().MODE_PRIVATE);
//        uid = prf.getString("uid", "");
//        String uname = prf.getString("uname", "");
//        Toast.makeText(getApplicationContext(), "user is: " + uid.toString(), Toast.LENGTH_SHORT).show();
//
//
//        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
//        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//        if (!isGPSEnable ) {
//
//        } else {
//
//
//            if (isGPSEnable) {
//                location = null;
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60*1000, 0, this);
//                if (locationManager!=null){
//                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                    if (location!=null){
//                        Log.e("latitude",location.getLatitude()+"");
//                        Log.e("longitude",location.getLongitude()+"");
//                        latitude = location.getLatitude();
//                        String latitude1 = String.valueOf(latitude);
//                        longitude = location.getLongitude();
//                        String longitude1 = String.valueOf(longitude);
//
//
//                        Toast.makeText(getApplicationContext(), "nice " + latitude1.toString() + " on row number " , Toast.LENGTH_SHORT).show();
//
//                        try {
//
//                            shopid1 = "";
//                            flag = 0;
//                            latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                            double lat = location.getLatitude();
//                            String lat1 = Double.toString(lat);
//                            double lon = location.getLongitude();
//                            String lon1 = Double.toString(lon);
//
//
////                    mMap.addMarker(place1);
////                    mMap.addMarker(place2);
//
//
////                    Toast.makeText(getApplicationContext(), "Location changed: Lat: " + latLng.toString(), Toast.LENGTH_SHORT).show();
//
//
//                            for (int i = 0; i < object.size(); i++) {
//
//                                String str = object.get(i).toString();
//                                String[] splitStr = str.split("\\s+");
//                                String str1 = splitStr[0];
//                                String str2 = splitStr[1];
//
//
//                                double d1 = Double.parseDouble(str1);
//                                double d2 = Double.parseDouble(str2);
//
////                        Toast.makeText(getApplicationContext(), "o1: " + object.get(i).toString(), Toast.LENGTH_SHORT).show();
////                        Toast.makeText(getApplicationContext(), "o2: " + object1.get(i).toString(), Toast.LENGTH_SHORT).show();
//
//
////                        double earthRadius = 3958.75;
////
////                        double dLat = Math.toRadians(d1-lat);
////                        double dLng = Math.toRadians(d2-lon);
////                        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
////                                Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(d1)) *
////                                        Math.sin(dLng/2) * Math.sin(dLng/2);
////                        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
////                        double dist = earthRadius * c;
////
////                        String dist1 = String.valueOf(dist);
//                                // Toast.makeText(getApplicationContext(), "disance is: " + dist1.toString(), Toast.LENGTH_SHORT).show();
//
//                                Location startPoint = new Location("locationA");
//                                startPoint.setLatitude(d1);
//                                startPoint.setLongitude(d2);
//
//                                Location endPoint = new Location("locationB");
//                                endPoint.setLatitude(lat);
//                                endPoint.setLongitude(lon);
//
//                                double distance = startPoint.distanceTo(endPoint);
//                                String dist12 = String.valueOf(distance);
//                                Toast.makeText(getApplicationContext(), "disance from shop is: " + dist12.toString(), Toast.LENGTH_SHORT).show();
//
//                                if (distance <= 150) {
//                                    if (!(shopid1.isEmpty() || shopid1.equals(null))) {
//                                        shopid1 += "," + object1.get(i).toString();
////                                Toast.makeText(getApplicationContext(), "first if: " + shopid1.toString(), Toast.LENGTH_SHORT).show();
//                                        flag = 1;
//                                    } else {
//                                        shopid1 = object1.get(i).toString();
////                                Toast.makeText(getApplicationContext(), "second if: " + object1.get(i).toString(), Toast.LENGTH_SHORT).show();
//                                        flag = 1;
//
//                                    }
//
//
//                                }
//
//
//                            }
//
//
//
//                            if (flag == 1) {
//
//                                MapsActivity11 newMapsActivity11 = new MapsActivity11(MyService.this);
//                                newMapsActivity11.execute(uid, lat1, lon1, shopid1);
//                            }
//                            else{
//
//                                Toast.makeText(getApplicationContext(), "latitude " + lat1.toString()+ "and longitude  "+ lon1.toString(), Toast.LENGTH_LONG).show();
//
//                                MapsActivity11 newMapsActivity11 = new MapsActivity11(MyService.this);
//                                newMapsActivity11.execute(uid, lat1, lon1, "0");
//                            }
//
//
//
//
//                        } catch (SecurityException e) {
//                            Toast.makeText(getApplicationContext(), "VIKAS: ", Toast.LENGTH_SHORT).show();
//                            e.printStackTrace();
//                        }
//
//                    }
//                }
//            }//
//
//
//        }

    }


    @Override
    public void onLocationChanged(Location location) {

        //location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            Log.e("latitude", location.getLatitude() + "");
            Log.e("longitude", location.getLongitude() + "");
            latitude = location.getLatitude();
            String latitude1 = String.valueOf(latitude);
            longitude = location.getLongitude();

            Toast.makeText(getApplicationContext(), "nice " + latitude1.toString() + " on row number ", Toast.LENGTH_SHORT).show();

            try {

                shopid1 = "";
                flag = 0;
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                double lat = location.getLatitude();
                String lat1 = Double.toString(lat);
                double lon = location.getLongitude();
                String lon1 = Double.toString(lon);


//                    mMap.addMarker(place1);
//                    mMap.addMarker(place2);


//                    Toast.makeText(getApplicationContext(), "Location changed: Lat: " + latLng.toString(), Toast.LENGTH_SHORT).show();


                for (int i = 0; i < object.size(); i++) {

                    String str = object.get(i).toString();
                    String[] splitStr = str.split("\\s+");
                    String str1 = splitStr[0];
                    String str2 = splitStr[1];


                    double d1 = Double.parseDouble(str1);
                    double d2 = Double.parseDouble(str2);

//                        Toast.makeText(getApplicationContext(), "o1: " + object.get(i).toString(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(), "o2: " + object1.get(i).toString(), Toast.LENGTH_SHORT).show();


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


                }


                if (flag == 1) {

                    MapsActivity11 newMapsActivity11 = new MapsActivity11(MyService.this);
                    newMapsActivity11.execute(uid, lat1, lon1, shopid1);
                } else {

                    Toast.makeText(getApplicationContext(), "latitude " + lat1.toString() + "and longitude  " + lon1.toString(), Toast.LENGTH_LONG).show();

                    MapsActivity11 newMapsActivity11 = new MapsActivity11(MyService.this);
                    newMapsActivity11.execute(uid, lat1, lon1, "0");
                }


            } catch (SecurityException e) {
                Toast.makeText(getApplicationContext(), "VIKAS: ", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

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


    public class MapsActivity11 extends AsyncTask<String, Void, String> {

        public MapsActivity11(MyService todaysVisits) {

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
                        + "&" + URLEncoder.encode("lon", "UTF-8") + "=" + URLEncoder.encode(lon, "UTF-8")
                        + "&" + URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(uid, "UTF-8")
                        + "&" + URLEncoder.encode("shopid", "UTF-8") + "=" + URLEncoder.encode(shopid, "UTF-8");


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
