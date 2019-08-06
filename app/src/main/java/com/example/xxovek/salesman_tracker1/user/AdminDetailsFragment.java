package com.example.xxovek.salesman_tracker1.user;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
public class AdminDetailsFragment extends Fragment {

    TextView textView7a,textView8a,textView9a,textView10a;
    String username="vikas123",passsword="vikas";
    String alq,alq1,alq2,alq3,uid,cid;
    Animation animation;

    public AdminDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_details, container, false);

        animation = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        final String uid=prf.getString("uid", "");
        String uname=prf.getString("uname","");

//        Toast.makeText(getContext(), "hhhppplll" + uid.toString(), Toast.LENGTH_SHORT).show();


        textView7a = (TextView) view.findViewById(R.id.textView7a);
        textView7a.startAnimation(animation);
        textView8a = (TextView) view.findViewById(R.id.textView8a);
        textView8a.startAnimation(animation);
        textView9a = (TextView) view.findViewById(R.id.textView9a);
        textView9a.startAnimation(animation);
        textView10a = (TextView) view.findViewById(R.id.textView10a);
        textView10a.startAnimation(animation);

        String type = "user_profile";


        //final String LOGIN_URL = "http://track.xxovek.com/public_html/salesandroid/admininfo";

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrls.ADMIN_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(AdminDetailsFragment.this.getContext(), "No Admin assigned"+response.toString(), Toast.LENGTH_LONG).show();

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
                                    alq1 = (json.getString("email"));
                                    alq2 = (json.getString("email"));
                                    alq3 = (json.getString("contactNumber"));



//                        new DownloadImageTask((ImageView) getActivity().findViewById(R.id.imageView))
//                                .execute(image_url);


                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                }
                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");
                                // Toast.makeText(getContext(), alq.toString(), Toast.LENGTH_SHORT).show();

                                textView7a.setText(alq.toString());
                                textView8a.setText(alq1.toString());
                                textView9a.setText(alq2.toString());
                                textView10a.setText(alq3.toString());
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


        return view;
    }
}
