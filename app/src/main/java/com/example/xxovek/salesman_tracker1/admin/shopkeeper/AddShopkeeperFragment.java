package com.example.xxovek.salesman_tracker1.admin.shopkeeper;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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


public class AddShopkeeperFragment extends Fragment {

    public EditText et_contactpersonname,et_mailid,et_mobileno,et_alternateno,et_pincode,et_permanentadd,et_residentialadd;
    public Spinner spin_country,spin_state,spin_city;
    public CheckBox cb_sameaspermanentabove;
    public Button btn_add,btn_reset;
    String st_country_id,st_state_id,st_city_id,address,st_state_name;
    HashMap<Integer, String> spinnerMap3;

    public AddShopkeeperFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_shopkeeper, container, false);

        //EditText.....
        et_contactpersonname=view.findViewById(R.id.et_contactpersonname);
        et_mailid=view.findViewById(R.id.et_personemailid);
        et_mobileno=view.findViewById(R.id.et_personmobilenumber);
        et_alternateno=view.findViewById(R.id.et_alternatemobilenumber);
        et_pincode=view.findViewById(R.id.et_shopaddresszipcode);
        et_permanentadd=view.findViewById(R.id.et_permanentaddress);
        et_residentialadd=view.findViewById(R.id.et_resendtialaddress);
        btn_add=view.findViewById(R.id.btn_add);
        btn_reset=view.findViewById(R.id.btn_reset);

        //Spinner.....
        spin_country=view.findViewById(R.id.spin_country);
        spin_state=view.findViewById(R.id.spin_state);
        spin_city=view.findViewById(R.id.spin_city);

        //Checkbox.....
        cb_sameaspermanentabove=view.findViewById(R.id.cb_sameaspermanetaddress);


        //Getting COuntry Data in Spinner......
        getCountrySpin();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String permanent_address=et_permanentadd.getText().toString();
                String pincode=et_pincode.getText().toString();
                address=permanent_address+" "+st_city_id+" "+st_state_name+" "+st_country_id+" "+pincode;
                Log.d("mytag", "onClick:String of Address "+address);
                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(address,
                        getContext(), new GeocoderHandler());
            }

        });

        return view;
    }

    private class GeocoderHandler extends Handler {
    @Override
    public void handleMessage(Message message) {
        String locationAddress;
        switch (message.what) {
            case 1:
                Bundle bundle = message.getData();
                locationAddress = bundle.getString("address");
                break;
            default:
                locationAddress = null;
        }
        Log.d("mytag", "handleMessage: Transferring Address to latlong"+locationAddress);
    }
}
    public void getCountrySpin(){
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, ConfigUrls.COUNTRY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "Country"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {

                                Toast.makeText(getContext(), "111"+response.toString(), Toast.LENGTH_SHORT).show();

                                List<String> al1 = new ArrayList<String>();
                                List<String> al2 = new ArrayList<String>();
                                List<String> al3 = new ArrayList<String>();
                                List<String> al4 = new ArrayList<String>();

                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("id"));
                                    al2.add(json.getString("sortname"));
                                    al3.add(json.getString("name"));
                                    al4.add(json.getString("phonecode"));
//

                                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                                            android.R.layout.simple_spinner_dropdown_item, al3);

                                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spin_country.setAdapter(dataAdapter);

                                   /* String[] spinnerArray = new String[al1.size()];
                                    spinnerMap1 = new HashMap<Integer, String>();

                                    for (i = 0; i < al1.size(); i++) {
                                        spinnerMap1.put(i, al1.get(i));
                                        spinnerArray[i] = al1.get(i);
                                    }*/
                                          /* int spinnerPosition = dataAdapter.getPosition(a10);
                                    sp_countrybill.setSelection(spinnerPosition);*/

                                   /* dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    countrys2.setAdapter(dataAdapter);
                                    int spinnerPosition1 = dataAdapter.getPosition(a13);
                                    countrys2.setSelection(spinnerPosition1);*/

                                    spin_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                            st_country_id = spin_country.getItemAtPosition(i).toString();
                                            if (!st_country_id .isEmpty()) {
                                                /*billing_country_id = spinnerMap1.get(sp_countrybill.getSelectedItemPosition());
                                                 */

                                                Toast.makeText(getContext(), "Country id\n" + st_country_id, Toast.LENGTH_SHORT).show();

                                                //Getting State Spinner....
                                                getStateSpin();

                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }

                                    });
                                }


                                // a= a + "Age : "+json.getString("c_phone")+"\n";
                                //j= j + "Job : "+json.getString("Job")+"\n
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
                /*params.put("pid", cid);*/
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest1);
    }

    public void getStateSpin(){
        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, ConfigUrls.STATES_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "State"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {

                                Toast.makeText(getContext(), "111"+response.toString(), Toast.LENGTH_SHORT).show();

                                List<String> al1 = new ArrayList<String>();
                                List<String> al2 = new ArrayList<String>();
                                List<String> al3 = new ArrayList<String>();



                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("id"));
                                    al2.add(json.getString("name"));
                                    al3.add(json.getString("name"));
                                    //al4.add(json.getString("phonecode"));
//

                                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);

                                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spin_state.setAdapter(dataAdapter);
                                    String[] spinnerStateArray = new String[al1.size()];
                                    spinnerMap3 = new HashMap<Integer, String>();

                                    for (i = 0; i < al1.size(); i++) {
                                        spinnerMap3.put(i, al1.get(i));
                                        spinnerStateArray[i] = al1.get(i);
                                    }
                                          /* int spinnerPosition = dataAdapter.getPosition(a10);
                                    sp_countrybill.setSelection(spinnerPosition);*/

                                   /* dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    countrys2.setAdapter(dataAdapter);
                                    int spinnerPosition1 = dataAdapter.getPosition(a13);
                                    countrys2.setSelection(spinnerPosition1);
*/
                                    spin_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                            st_state_name = spin_state.getItemAtPosition(i).toString();

                                                st_state_id= spinnerMap3.get(spin_state.getSelectedItemPosition());

                                                //getting City
                                                getCitySpin();

                                                Toast.makeText(getContext(), "State Id\n" + st_state_id, Toast.LENGTH_SHORT).show();


                                        }
                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }


                                // a= a + "Age : "+json.getString("c_phone")+"\n";
                                //j= j + "Job : "+json.getString("Job")+"\n
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
                params.put("countryname", String.valueOf(st_country_id));
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest3);

    }

    public void getCitySpin(){
        StringRequest stringRequest7 = new StringRequest(Request.Method.POST, ConfigUrls.CITY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(getContext(), "City"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            try {

                                Toast.makeText(getContext(), "111"+response.toString(), Toast.LENGTH_SHORT).show();

                                List<String> al1 = new ArrayList<String>();
                                List<String> al2 = new ArrayList<String>();
                                List<String> al3 = new ArrayList<String>();

                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
                                // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("id"));
                                    al2.add(json.getString("name"));
                                    al3.add(json.getString("name"));
                                    //al4.add(json.getString("phonecode"));
//



                                    //For Shipping City Spinner
                                    ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getContext(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);

                                    cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spin_city.setAdapter(cityAdapter);
/*
                                    String[] spinnercityArray2 = new String[al1.size()];
                                    spinnerMap6 = new HashMap<Integer, String>();

                                    for (i = 0; i < al1.size(); i++) {
                                        spinnerMap6.put(i, al1.get(i));
                                        spinnercityArray2[i] = al1.get(i);
                                    }*/

                                    spin_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                            st_city_id = spin_city.getItemAtPosition(i).toString();
                                            if (!st_city_id.isEmpty()) {
                                                //shipping_city_id = spinnerMap6.get(sp_cityship.getSelectedItemPosition());


                                                Toast.makeText(getContext(), "Shipping City id\n" + st_city_id, Toast.LENGTH_SHORT).show();



                                            }

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {


                                        }
                                    });
                                }
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
                }) {
            @Override
            protected Map<String,   String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("state_id", st_state_id);
//                params.put("password", password);

                //returning parameter
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest7);


    }
}