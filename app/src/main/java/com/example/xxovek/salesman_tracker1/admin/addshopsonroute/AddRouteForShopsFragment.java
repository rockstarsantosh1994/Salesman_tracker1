package com.example.xxovek.salesman_tracker1.admin.addshopsonroute;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddRouteForShopsFragment extends Fragment {
    public Spinner spin_route,spin_shop;
    String[] spinnerArray;
    HashMap<Integer,String> routeId_hashmap,shopkeeperId_hashmap;
    String admin_id,st_route_id,st_shopkeeper_id;
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

        //Loading data of Routes in spinner using below function.....
        getRoute();

        //Loading data of Shops name in spinner using below function.....
        getShops();


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrls.ADD_ROUTE_DETAILS,
                        new Response.Listener<String>() {


                            @Override
                            public void onResponse(String response) {


                                Toast.makeText(getContext(), "Response\n\n"+response, Toast.LENGTH_SHORT).show();
                                Log.d("mytag", "ADD_ROUTE_DETAILS onResponse: "+response);

                                Fragment fragment = new ShowRouteDetailsFragment();
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
                                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("admin_id", admin_id);
                        params.put("source", st_route_id);
                        params.put("shop", st_shopkeeper_id);


                        return params;
                    }
                };

                RequestQueue requestQueue6 = Volley.newRequestQueue(getContext());
                requestQueue6.add(stringRequest);
            }
        });

        return view;
    }

    public void getRoute(){
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrls.GET_ROUTE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Unable to fetch size data"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {

                                Toast.makeText(getContext(), "getSizeValue Response \n\n\n"+response.toString(), Toast.LENGTH_SHORT).show();


                                List<String> al1 = new ArrayList<String>();
                                List<String> al2 = new ArrayList<String>();



                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("RouteId"));
                                    al2.add(json.getString("RouteName"));


                                }
                                Integer a1 = al1.size();
                                String a2 = String.valueOf(a1);
                                spinnerArray = new String[al1.size()];
                                routeId_hashmap = new HashMap<Integer, String>();

                                // Toast.makeText(getContext(), "the size is" + a2.toString(), Toast.LENGTH_SHORT).show();


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

                                spin_route.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        st_route_id = (String) routeId_hashmap.get(spin_route.getSelectedItemPosition());


                                        Toast.makeText(getContext(), "RouteId Value"+st_route_id, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

//                    Toast.makeText(getContext(), n.toString(), Toast.LENGTH_SHORT).show();


                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");


                                //al = Arrays.asList(n);


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
                            Toast.makeText(getContext(), "Unable to fetch size data"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {

                                Toast.makeText(getContext(), "getShops Value Response \n\n\n"+response.toString(), Toast.LENGTH_SHORT).show();


                                List<String> al1 = new ArrayList<String>();
                                List<String> al2 = new ArrayList<String>();



                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("shopKeeperId"));
                                    al2.add(json.getString("shops"));


                                }
                                Integer a1 = al1.size();
                                String a2 = String.valueOf(a1);
                                spinnerArray = new String[al1.size()];
                                shopkeeperId_hashmap = new HashMap<Integer, String>();

                                // Toast.makeText(getContext(), "the size is" + a2.toString(), Toast.LENGTH_SHORT).show();


                                for (int i = 0; i <al1.size(); i++)
                                {
                                    shopkeeperId_hashmap.put(i,al1.get(i));
                                    spinnerArray[i] = al1.get(i);
                                }

                                    /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddNewSchemesInformationFragment.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);
*/
                                ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, al2);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                                spin_shop.setAdapter(dataAdapter);

                                spin_shop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        st_shopkeeper_id = (String) shopkeeperId_hashmap.get(spin_route.getSelectedItemPosition());


                                        Toast.makeText(getContext(), "ShopKeeperId Value"+st_shopkeeper_id, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

//                    Toast.makeText(getContext(), n.toString(), Toast.LENGTH_SHORT).show();


                                String result1 = response.replace("\"", "");
                                result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                                String str[] = result1.split(",");


                                //al = Arrays.asList(n);


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
                params.put("admin_id",admin_id);


                //returning parameter
                return params;
            }
        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        requestQueue1.add(stringRequest);

    }

}
