package com.example.xxovek.salesman_tracker1.admin.addshopsonroute;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.xxovek.salesman_tracker1.admin.tabs.AddShopOnRoutesTab;
import com.example.xxovek.salesman_tracker1.user.MyRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShowRouteDetailsFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener{

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
    List<String> al6 = new ArrayList<String>();
    public String st_assignid;
    private String cid="3";

    public ShowRouteDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_route_details, container, false);

        recyclerView = view.findViewById(R.id.admin_show_recycle_id);

        final String LOGIN_URL = "http://track.xxovek.com/src/display_route_details";

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(ShowRouteDetailsFragment.this.getContext(), "No Shops"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            JSONArray json_data = null;
                            try {


                                json_data = new JSONArray(response);
                                int len = json_data.length();
                                //Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                                for(int i=0; i<json_data.length();i++){
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add((json.getString("RdId")));
                                    al2.add("RouteId : ".concat(json.getString("RouteId")));
                                    al3.add("Route : ".concat(json.getString("route")));
                                    al4.add("Shopk Id : ".concat(json.getString("shopkid")));
                                    al5.add("Name: ".concat(json.getString("sname")));
                                    al6.add("Phone : ".concat(json.getString("sphone")));


                                    // a= a + "Age : "+json.getString("c_phone")+"\n";
                                    //j= j + "Job : "+json.getString("Job")+"\n";
                                }
//                    //Toast.makeText(getContext(), n.toString(), Toast.LENGTH_SHORT).show();



                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");

                                Toast.makeText(getContext(), al1.toString(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(getContext(), al2.toString(), Toast.LENGTH_SHORT).show();

                                //al = Arrays.asList(n);

                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

                                recyclerView.setLayoutManager(mLayoutManager);

                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                adapter = new MyRecyclerViewAdapter(getContext(), al1,al2,al3,al4,al5,al6,al2,al1,al1,al1,"3");
                                adapter.setClickListener(ShowRouteDetailsFragment.this);
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
                params.put("cid", cid);

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

        int id=view.getId();
        String user_id1 = adapter.getItem(position);
        // Toast.makeText(getContext(), "getitem is  " + user_id1.toString() + " on row number " + position, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), "On Item Clicked"+id, Toast.LENGTH_SHORT).show();

        switch (id){
            case R.id.t2:Fragment fragment = new AddRouteForShopsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle data = new Bundle();//Use bundle to pass data
                data.putString("data", user_id1);//put string, int, etc in bundle with a key value
                fragment.setArguments(data);
                Toast.makeText(getContext(), "Userid"+user_id1, Toast.LENGTH_SHORT).show();
                fragmentTransaction.replace(R.id.main_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;

            case R.id.imageButton: final String st_delid= adapter.getItem(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setIcon(android.R.drawable.ic_lock_power_off);
                builder.setTitle("Delete");
                builder.setMessage("Do you really want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getContext(), "st_delid\n"+st_delid, Toast.LENGTH_SHORT).show();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrls.REMOVE_DETAILS,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        //If we are getting success from server
                                        if(TextUtils.isEmpty(response)){
                                            //Creating a shared preference
                                            Toast.makeText(getContext(), "Unable to delete product data"+response.toString(), Toast.LENGTH_LONG).show();

                                        }else{

                                            Toast.makeText(getContext(), "Customer Deleted Successfully"+response, Toast.LENGTH_SHORT).show();
                                            Log.d("mytag", "onResponse:REMOVE_DETAILS "+response);
                                            Fragment fragment = new AddShopOnRoutesTab();
                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.main_container, fragment);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                            adapter.notifyDataSetChanged();
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

                                String tblname="RouteDetails";
                                String colname="routeDetailId";
                                params.put("id", st_delid);
                                params.put("tblName", tblname);
                                params.put("colName", colname);
//                params.put("password", password);

                                Log.d("mytag", "\ngetParams: ID "+st_delid+"\ntblname "+tblname+"\ncolname "+colname);

                                //returning parameter
                                return params;
                            }
                        };

                        //Adding the string request to the queue
                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        requestQueue.add(stringRequest);

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Delete Operation Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


                break;
        }

        // Intent intent = new Intent(getContext(), Clientsinfo.class);
        // startActivity(intent);


    }
}
