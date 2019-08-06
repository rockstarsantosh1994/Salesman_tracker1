package com.example.xxovek.salesman_tracker1.user;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.example.xxovek.salesman_tracker1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {
    TextView textView7,textView8,textView9,textView10;
    ImageView imageView;
    String username="vikas123",passsword="vikas";
    String alq,alq1,alq2,alq3,alq4,uid,cid;
    Context mcontext;
    ProgressDialog progress;
    Animation animation;





    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        animation = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        final String uid=prf.getString("uid", "");
        String uname=prf.getString("uname","");
        mcontext = getContext();


        imageView = (ImageView) view.findViewById(R.id.imageView);

        textView7 = (TextView) view.findViewById(R.id.textView7);
        textView7.startAnimation(animation);
        textView8 = (TextView) view.findViewById(R.id.textView8);
        textView8.startAnimation(animation);
        textView9 = (TextView) view.findViewById(R.id.textView9);
        textView9.startAnimation(animation);
        textView10 = (TextView) view.findViewById(R.id.textView10);
        textView10.startAnimation(animation);

        String type = "user_profile";



        //final String LOGIN_URL = "http://track.xxovek.com/public_html/salesandroid/clientsinfo";

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrls.CLIENTS_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(UserProfileFragment.this.getContext(), "No User Listed"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            JSONArray json_data = null;
                            try {
                                List<String> al = new ArrayList<String>();
                                List<String> al1 = new ArrayList<String>();
                                List<String> al2 = new ArrayList<String>();
                                List<String> al3 = new ArrayList<String>();
                                List<String> al4 = new ArrayList<String>();


                                //  Toast.makeText(getContext(), result.toString(), Toast.LENGTH_SHORT).show();

                                json_data = new JSONArray(response);
                                int len = json_data.length();
                                //  Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                                for(int i=0; i<json_data.length();i++){
                                    JSONObject json = json_data.getJSONObject(i);
                                    alq =(json.getString("fname").concat(" ").concat(json.getString("lname")));
                                    alq1 = (json.getString("created_at"));
                                    alq2 = (json.getString("email"));
                                    alq3 = (json.getString("address"));
                                    alq4 = (json.getString("profilePic"));


                                    String image_url = "http://track.xxovek.com/".concat(alq4);

//                        new DownloadImageTask((ImageView) getActivity().findViewById(R.id.imageView))
//                                .execute(image_url);


                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                }
                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");
                                // Toast.makeText(getContext(), alq.toString(), Toast.LENGTH_SHORT).show();

                                textView7.setText(alq.toString());
                                textView8.setText(alq1.toString());
                                textView9.setText(alq2.toString());
                                textView10.setText(alq3.toString());

                                //Starting profile activity

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
                params.put("uid", uid);
                params.put("cid", uid);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);



        return  view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getActivity(), "service done", Toast.LENGTH_SHORT).show();
    }



}
