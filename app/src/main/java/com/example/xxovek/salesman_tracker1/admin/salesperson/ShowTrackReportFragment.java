package com.example.xxovek.salesman_tracker1.admin.salesperson;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.xxovek.salesman_tracker1.ConfigUrls;
import com.example.xxovek.salesman_tracker1.R;
import com.example.xxovek.salesman_tracker1.admin.shopkeeper.ShowShopkeeperFragment;
import com.example.xxovek.salesman_tracker1.user.MyRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowTrackReportFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {

    RecyclerView recyclerView;
    public int i;
    public MyRecyclerViewAdapter adapter;
    public String st_salesid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_route_details, container, false);

        recyclerView = view.findViewById(R.id.admin_show_recycle_id);

        try{
            st_salesid=getArguments().getString("data");

            Log.d("mytag", "onCreateView:sales_id in ShowTrackReportFragment "+st_salesid);}
        catch (NullPointerException e){e.printStackTrace();}


        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrls.TRACK_REPORT_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            //.makeText(ShowShopkeeperFragment.this.getContext(), "No Shops"+response.toString(), //.LENGTH_LONG).show();

                        }else{

                            JSONArray json_data = null;
                            try {

                                List<String> al1 = new ArrayList<String>();
                                List<String> al2 = new ArrayList<String>();
                                List<String> al3 = new ArrayList<String>();
                                List<String> al4 = new ArrayList<String>();

                                json_data = new JSONArray(response);
                                int len = json_data.length();
                                //.makeText(getContext(), response.toString(), //.LENGTH_SHORT).show();

                                for(int i=0; i<json_data.length();i++){
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add((json.getString("assignId")));
                                    al2.add("Route Name : ".concat(json.getString("RouteName").toUpperCase()));
                                    al3.add("Date : ".concat(json.getString("AssignDate")));
                                    al4.add("Name : ".concat(json.getString("SallerName")));



                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                }
//                    ////.makeText(getContext(), n.toString(), //.LENGTH_SHORT).show();



                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");

                                //.makeText(getContext(), al1.toString(), //.LENGTH_SHORT).show();
                                //.makeText(getContext(), al2.toString(), //.LENGTH_SHORT).show();

                                //al = Arrays.asList(n);

                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

                                recyclerView.setLayoutManager(mLayoutManager);

                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                adapter = new MyRecyclerViewAdapter(getContext(), al1,al2,al3,al4,al4,al4,al2,al1,al1,al1,"5");
                                adapter.setClickListener(ShowTrackReportFragment.this);
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
                params.put("salesId", st_salesid);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        return view;
    }


    @Override
    public void onItemClick(View view, int position) {

    }
}
