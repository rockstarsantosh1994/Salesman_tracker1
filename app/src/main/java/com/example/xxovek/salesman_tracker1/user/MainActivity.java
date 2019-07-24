package com.example.xxovek.salesman_tracker1.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.xxovek.salesman_tracker1.R;
import com.example.xxovek.salesman_tracker1.admin.activity.AdminLoginActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {

    Button button;
    SharedPreferences prf;
    SharedPreferences.Editor editor;
    EditText editText1, editText2;
    AlertDialog alertDialog;
    TextView textView2, textView5, textView6;
    MenuItem item;
    int year=0,monthi=0,day=0, databaseyear=0, databasemonth=0, databaseday=0;
    //String databasedate;
    ArrayList<String> xyz = new ArrayList<String>();
    String xyz1, day1, month1, year1, currentmonth1, currentmonth2, frag, date, date1;
    String[] seprated, seprated1;
    FragmentTransaction fragmentTransaction;
    ViewPager mViewPager;
    ImageView iv_icon;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prf = getApplicationContext().getSharedPreferences("Options", Context.MODE_PRIVATE);
        editor = prf.edit();

        if(prf.getBoolean("logged",false)){
            goToMainActivity();
        }

        iv_icon=(ImageView)findViewById(R.id.iv_icon);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        iv_icon.startAnimation(animation);



        button = (Button) findViewById(R.id.btn_login);
        button.startAnimation(animation);


        editText1 = (EditText) findViewById(R.id.et_username);
        editText1.startAnimation(animation);
        editText2 = (EditText) findViewById(R.id.et_password);
        editText2.startAnimation(animation);

    }

    public void goToMainActivity(){
        Intent i = new Intent(this,HomeActivity.class);
        startActivity(i);
    }

    public void LoginNow(View view) {
        String username = editText1.getText().toString();
        String passsword = editText2.getText().toString();
        String type = "login";

        final String email = editText1.getText().toString().trim();
        final String password = editText2.getText().toString().trim();
        final String LOGIN_URL = "http://track.xxovek.com/public_html/salesandroid/login";

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(MainActivity.this, "Invalid username or password"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            JSONArray json_data = null;
                            try {
                                json_data = new JSONArray(response);

                                String uid = (String) json_data.get(0);
                                String uname = (String) json_data.get(1);
                                String password = (String) json_data.get(2);

                                editor.putBoolean("logged",true).apply();
                                editor.putString("uid", uid);
                                editor.putString("uname", uname);// Storing string
                                editor.commit();


                                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                                //Starting profile activity
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                params.put("username", email);
                params.put("password", password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void AdminLogin(View view) {
                Intent intent=new Intent(MainActivity.this, AdminLoginActivity.class);
                startActivity(intent);
    }

   /* @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {

            case R.id.btn_login:

                String username = editText1.getText().toString();
                String passsword = editText2.getText().toString();
                String type = "login";

                final String email = editText1.getText().toString().trim();
                final String password = editText2.getText().toString().trim();
                final String LOGIN_URL = "http://track.xxovek.com/public_html/salesandroid/login";

                //Creating a string request
                StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //If we are getting success from server
                                if(TextUtils.isEmpty(response)){
                                    //Creating a shared preference
                                    Toast.makeText(MainActivity.this, "Invalid username or password"+response.toString(), Toast.LENGTH_LONG).show();

                                }else{

                                    JSONArray json_data = null;
                                    try {
                                        json_data = new JSONArray(response);

                                        String uid = (String) json_data.get(0);
                                        String uname = (String) json_data.get(1);
                                        String password = (String) json_data.get(2);

                                        editor.putBoolean("logged",true).apply();
                                        editor.putString("uid", uid);
                                        editor.putString("uname", uname);// Storing string
                                        editor.commit();


                                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                                        //Starting profile activity
                                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }catch (JSONException e) {
                                        e.printStackTrace();
                                    }
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
                        params.put("username", email);
                        params.put("password", password);

                        //returning parameter
                        return params;
                    }
                };

                //Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);


                break;
        }

    }*/
}
