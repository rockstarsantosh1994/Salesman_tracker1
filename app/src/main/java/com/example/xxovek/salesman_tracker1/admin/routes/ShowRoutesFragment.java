package com.example.xxovek.salesman_tracker1.admin.routes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import com.example.xxovek.salesman_tracker1.user.MyRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowRoutesFragment extends Fragment {

    RecyclerView recyclerView;
    public int i;
    public MyRecyclerViewAdapter adapter;
    Button button77;
    String n [] ;
    String a="";
    List<String> al = new ArrayList<String>();
    List<String> al1 = new ArrayList<String>();
    List<String> al2 = new ArrayList<String>();
    List<String> al3 = new ArrayList<String>();
    List<String> al4 = new ArrayList<String>();
    List<String> al5 = new ArrayList<String>();

    public String st_assignid;
    private String cid="3";

    public ShowRoutesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_route_details, container, false);

        recyclerView = view.findViewById(R.id.admin_show_recycle_id);


        final String LOGIN_URL = "http://track.xxovek.com/src/display_routes";

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(ShowRoutesFragment.this.getContext(), "No Shops"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            JSONArray json_data = null;
                            try {


                                json_data = new JSONArray(response);
                                int len = json_data.length();
                                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();

                                for(int i=0; i<json_data.length();i++){
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add((json.getString("RouteId")));
                                    al2.add("Source : ".concat(json.getString("source")));
                                    al3.add("Destination : ".concat(json.getString("dest")));
                                    al4.add("Created At : ".concat(json.getString("creattime")));
                                    al5.add("Updated At : ".concat(json.getString("updatetime")));

                                    // st_assignid =(json.getString("status"));


                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                }
//                    //Toast.makeText(getContext(), n.toString(), Toast.LENGTH_SHORT).show();



                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");

                                Toast.makeText(getContext(), al.toString(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(getContext(), al2.toString(), Toast.LENGTH_SHORT).show();

                                //al = Arrays.asList(n);

                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

                                recyclerView.setLayoutManager(mLayoutManager);

                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                adapter = new MyRecyclerViewAdapter(getContext(), al1,al2,al3,al4,al5,al5,al3,al2,al1,al1,"2");
//                    adapter.setClickListener(this);
                                recyclerView.setAdapter(adapter);
                                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                                        DividerItemDecoration.VERTICAL));
                            } catch (JSONException e) {
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
                params.put("admin_id", cid);

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
