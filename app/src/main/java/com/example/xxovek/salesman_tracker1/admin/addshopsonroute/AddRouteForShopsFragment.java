package com.example.xxovek.salesman_tracker1.admin.addshopsonroute;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class AddRouteForShopsFragment extends Fragment {
    public Spinner spin_route,spin_shop;
    String[] spinnerArray,spinnerArray1;
    HashMap<Integer,String> routeId_hashmap,shopkeeperId_hashmap;
    public String admin_id,route_Detailid="",st_shopkeeper_id,st_route_id,rid,sid;
    Button btn_submit;

    public AddRouteForShopsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_route_for_shops, container, false);
        spin_route=view.findViewById(R.id.spin_route);
        spin_shop=view.findViewById(R.id.spin_shop);
        btn_submit=view.findViewById(R.id.btn_submit);

        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        admin_id=prf.getString("admin_id", "");
        Log.d("mytag", "onCreateView:Admin_id in AddRoutesFragment "+admin_id);

        try{
            route_Detailid=getArguments().getString("data");
        Log.d("mytag", "onCreateView:Admin_id in AddRoutesFragment "+route_Detailid);}
        catch (NullPointerException e){e.printStackTrace();}
        catch(IndexOutOfBoundsException e){e.printStackTrace();}

        //Loading data of Routes in spinner using below function.....
        getRoute();

        //Loading data of Shops name in spinner using below function.....
        getShops();

        //....After RecyclerView Click Event Operation perform....
        if(!route_Detailid.equals("")) {
            fetchRouteDetails();
        }


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Function Call When New Route Details are assign....
                 addRouteDetails();
            }
        });

        return view;
    }

    public void addRouteDetails(){
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrls.ADD_ROUTE_DETAILS,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {


                        //.makeText(getContext(), "Response\n\n"+response, //.LENGTH_SHORT).show();
                        Log.d("mytag", "ADD_ROUTE_DETAILS onResponse: "+response);

                        Fragment fragment = new AddShopOnRoutesTab();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //.makeText(getContext(), error.getMessage(), //.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("admin_id", admin_id);
                params.put("source", st_route_id);
                params.put("shop", st_shopkeeper_id);
                params.put("r_id", route_Detailid);

                Log.d("mytag", "getParams: ADD_ROUTE_DETAILS admin_id"+admin_id+"\nsource"+st_route_id+"\nshop"+st_shopkeeper_id+"\nr_id"+route_Detailid);

                return params;
            }
        };

        RequestQueue requestQueue6 = Volley.newRequestQueue(getContext());
        requestQueue6.add(stringRequest);
    }


    public void fetchRouteDetails(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrls.FETCH_ROUTE_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            //.makeText(AddRouteForShopsFragment.this.getContext(), "No Shops"+response.toString(), //.LENGTH_LONG).show();

                        }else{

                            try {
                                Log.d("mytag", "onResponse:FETCH_ROUTE_DETAILS "+response);


                                JSONObject json = new JSONObject(response);
                                Log.d("mytag", "onResponse:json Bhau"+json);
                                    rid = json.getString("rid");
                                    sid = json.getString("sid");
                                    Log.d("mytag", "onResponse: rid "+rid+"\nsid"+sid);

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
                params.put("rdid",route_Detailid);


                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void getRoute(){
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrls.GET_ROUTE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            //.makeText(getContext(), "Unable to fetch size data"+response.toString(), //.LENGTH_LONG).show();

                        }else{

                            try {

                                //.makeText(getContext(), "getSizeValue Response \n\n\n"+response.toString(), //.LENGTH_SHORT).show();


                                List<String> al1 = new ArrayList<String>();
                                List<String> al2 = new ArrayList<String>();



                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
                                // //.makeText(getContext(), json_data.toString(), //.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("RouteId"));
                                    al2.add(json.getString("RouteName"));


                                }
                                Integer a1 = al1.size();
                                String a2 = String.valueOf(a1);
                                spinnerArray = new String[al1.size()];
                                routeId_hashmap = new HashMap<Integer, String>();

                                // //.makeText(getContext(), "the size is" + a2.toString(), //.LENGTH_SHORT).show();


                                for (int i = 0; i <al1.size(); i++)
                                {
                                    routeId_hashmap.put(i,al1.get(i));
                                    spinnerArray[i] = al1.get(i);
                                }

                                    /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddNewSchemesInformationFragment.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);
*/
                                ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, al2);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                                spin_route.setAdapter(dataAdapter);


                                /*try{
                                    if(!rid.equals(null)){
                                        Log.d("mytag", "onResponse: rid"+rid);
                                        int rid1=Integer.parseInt(rid)-1;
                                        spin_route.setSelection(rid1);
                                    }
                                }catch(NullPointerException e){e.printStackTrace();}
*/
                                spin_route.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        st_route_id = (String) routeId_hashmap.get(spin_route.getSelectedItemPosition());


                                        //.makeText(getContext(), "RouteId Value"+st_route_id, //.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

//                    //.makeText(getContext(), n.toString(), //.LENGTH_SHORT).show();


                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");


                                //al = Arrays.asList(n);


                            } catch (JSONException e) {e.printStackTrace(); }
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

        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        requestQueue1.add(stringRequest);
    }

    public void getShops(){
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrls.GET_SHOPS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            //.makeText(getContext(), "Unable to fetch size data"+response.toString(), //.LENGTH_LONG).show();

                        }else{

                            try {

                                //.makeText(getContext(), "getShops Value Response \n\n\n"+response.toString(), //.LENGTH_SHORT).show();


                                List<String> al1 = new ArrayList<String>();
                                List<String> al2 = new ArrayList<String>();



                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
                                // //.makeText(getContext(), json_data.toString(), //.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("shopKeeperId"));
                                    al2.add(json.getString("shops"));


                                }
                                Integer a1 = al1.size();
                                String a2 = String.valueOf(a1);
                                spinnerArray1 = new String[al1.size()];
                                shopkeeperId_hashmap = new HashMap<Integer, String>();

                                // //.makeText(getContext(), "the size is" + a2.toString(), //.LENGTH_SHORT).show();


                                for (int i = 0; i <al1.size(); i++)
                                {
                                    shopkeeperId_hashmap.put(i,al1.get(i));
                                    spinnerArray1[i] = al1.get(i);
                                }

                                    /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddNewSchemesInformationFragment.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);
*/
                                ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, al2);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                                spin_shop.setAdapter(dataAdapter);


                              /*  try{
                                    Log.d("mytag", "onResponse: sid"+sid);
                                    if(!sid.equals(null)){
                                        int sid1=Integer.parseInt(sid)-1;
                                        spin_shop.setSelection(sid1);
                                    }
                                }catch(NullPointerException e){e.printStackTrace();}
*/

                                spin_shop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        st_shopkeeper_id = (String) shopkeeperId_hashmap.get(spin_shop.getSelectedItemPosition());


                                        //.makeText(getContext(), "ShopKeeperId Value"+st_shopkeeper_id, //.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

//                    //.makeText(getContext(), n.toString(), //.LENGTH_SHORT).show();


                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");


                                //al = Arrays.asList(n);


                            } catch (JSONException e) {e.printStackTrace(); }


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

        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        requestQueue1.add(stringRequest);

    }

}
