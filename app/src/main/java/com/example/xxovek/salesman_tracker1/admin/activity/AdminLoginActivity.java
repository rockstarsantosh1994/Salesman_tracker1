package com.example.xxovek.salesman_tracker1.admin.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.xxovek.salesman_tracker1.ConfigUrls;
import com.example.xxovek.salesman_tracker1.user.HomeActivity;
import com.example.xxovek.salesman_tracker1.user.MainActivity;
import com.example.xxovek.salesman_tracker1.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminLoginActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_admin_login);

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
        Intent i = new Intent(this, AdminHomeActivity.class);
        startActivity(i);
    }

    public void UserLogin(View view) {
        Intent intent=new Intent(AdminLoginActivity.this, MainActivity.class);
        startActivity(intent);
    }


    public void loginAsAdmin(View view) {
        //stuff of admin login
        final String username = editText1.getText().toString();
        final String passsword = editText2.getText().toString();
        String type = "login";

        Log.d("mytag", "loginAsAdmin: Email"+username+"\nPassword"+passsword);
        final String email = editText1.getText().toString().trim();
        final String password = editText2.getText().toString().trim();


        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrls.AUTHENTICATE_ADMIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(AdminLoginActivity.this, "Invalid username or password"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{
                            try {
                                JSONArray json_data = null;
                                //json_data = new JSONArray(response);

                                String user_id = (String) json_data.get(0);
                                String username = (String) json_data.get(1);
                                String email = (String) json_data.get(2);
                                String admin_id = (String) json_data.get(3);

                                editor.putBoolean("logged", true).apply();
                                editor.putString("user_id", user_id);
                                editor.putString("username", username);// Storing string
                                editor.putString("email", email);// Storing string
                                editor.putString("admin_id", admin_id);// Storing string
                                editor.commit();


                                Toast.makeText(AdminLoginActivity.this, response, Toast.LENGTH_LONG).show();

                                //Starting profile activity
                                Intent intent = new Intent(AdminLoginActivity.this, AdminHomeActivity.class);
                                startActivity(intent);
                            }
                            catch (JSONException e){e.printStackTrace();}                        }
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
                params.put("username", username);
                params.put("pwd", passsword);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
