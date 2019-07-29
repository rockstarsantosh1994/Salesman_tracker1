package com.example.xxovek.salesman_tracker1.admin.salesperson;


import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.xxovek.salesman_tracker1.admin.shopkeeper.GeocodingLocation;
import com.example.xxovek.salesman_tracker1.admin.tabs.AssignSalesmanWorkTab;
import com.example.xxovek.salesman_tracker1.admin.tabs.SalesPersonFragmentTab;
import com.example.xxovek.salesman_tracker1.admin.tabs.ShopKeepersTab;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddSalesmanFragment extends Fragment {

    public TextView tv_personaccountnumber,tv_ifsc,tv_branch;
    public EditText et_firstname,et_lastname,et_email,et_birtdate,et_permanentno,et_alternateno,
                        et_zipcode,et_permanantadd,et_residentialadd,et_personaccountnumber,et_ifsc,et_branch;
    public Spinner  spin_gender,spin_personstatus,spin_country,spin_state,spin_city;
    public Button btn_add,btn_reset;
    public CheckBox cb_bankdetails,cb_sameasabove;
    public String admin_id,st_country_id,st_state_id,st_city_id,address,st_state_name,st_gender,st_personstatus;
    HashMap<Integer, String> spinnerMap3;
    private int mYear, mMonth, mDay;
    DatePickerDialog datePickerDialog;



    public AddSalesmanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_salesman, container, false);

        //admin_id is Stored in SharedPreference....
        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        admin_id=prf.getString("admin_id", "");
        Log.d("mytag", "onCreateView:Admin_id in AddRoutesFragment "+admin_id);

        //TextView Declaration....
        tv_personaccountnumber= view.findViewById(R.id.tv_personaccountnumber);
        tv_ifsc= view.findViewById(R.id.tv_bankifsc);
        tv_branch= view.findViewById(R.id.tv_branch);

        //EditText Declaration....
        et_firstname=view.findViewById(R.id.et_firstname);
        et_lastname=view.findViewById(R.id.et_lastname);
        et_email=view.findViewById(R.id.et_personemailid);
        et_birtdate=view.findViewById(R.id.et_birtdate);
        et_permanentno=view.findViewById(R.id.et_personmobilenumber);
        et_alternateno=view.findViewById(R.id.et_alternatemobilenumber);
        et_zipcode=view.findViewById(R.id.et_shopaddresszipcode);
        et_permanantadd=view.findViewById(R.id.et_permanentaddress);
        et_residentialadd=view.findViewById(R.id.et_resendtialaddress);
        et_personaccountnumber= view.findViewById(R.id.et_personaccountnumber);
        et_ifsc= view.findViewById(R.id.et_bankifsc);
        et_branch= view.findViewById(R.id.et_branch);

        //Checkbox Declaration....
        cb_bankdetails=view.findViewById(R.id.cb_bankdetails);
        cb_sameasabove=view.findViewById(R.id.cb_sameaspermanetaddress);

        //Spinner Declaration....
        spin_gender=view.findViewById(R.id.spin_gender);
        spin_personstatus=view.findViewById(R.id.spin_personstatus);
        spin_country=view.findViewById(R.id.spin_country);
        spin_state=view.findViewById(R.id.spin_state);
        spin_city=view.findViewById(R.id.spin_city);

        //Button Declaration....
        btn_add=view.findViewById(R.id.btn_add);
        btn_reset=view.findViewById(R.id.btn_reset);

        //Getting Country Data in Spinner......
        getCountrySpin();

        //Getting Gender Data in Spinner.....
        getGenderSpin();

        //Getting Person Status Data in Spinner....
        getPersonStatusSpin();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String permanent_address=et_permanantadd.getText().toString();
                String pincode=et_zipcode.getText().toString();
                address=permanent_address+" "+st_city_id+" "+st_state_name+" "+st_country_id+" "+pincode;
                Log.d("mytag", "onClick:String of Address "+address);
                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(address,
                        getContext(), new GeocoderHandler());

                //Registering add Salesman...
                salesRegistration();
            }
        });
        cb_bankdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean checkBoxState = cb_bankdetails.isChecked();
                if(checkBoxState.equals(true)) {
                    tv_personaccountnumber.setVisibility(View.VISIBLE);
                    tv_ifsc.setVisibility(View.VISIBLE);
                    tv_branch.setVisibility(View.VISIBLE);
                    et_personaccountnumber.setVisibility(View.VISIBLE);
                    et_ifsc.setVisibility(View.VISIBLE);
                    et_branch.setVisibility(View.VISIBLE);
                }else{
                    tv_personaccountnumber.setVisibility(View.GONE);
                    tv_ifsc.setVisibility(View.GONE);
                    tv_branch.setVisibility(View.GONE);
                    et_personaccountnumber.setVisibility(View.GONE);
                    et_ifsc.setVisibility(View.GONE);
                    et_branch.setVisibility(View.GONE);
                }
            }
        });

        cb_sameasabove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checkBoxState = cb_sameasabove.isChecked();
                if(checkBoxState.equals(true)){
                    et_residentialadd.setText(et_permanantadd.getText().toString());
                }
                else{
                    et_residentialadd.getText().clear();
                }
            }
        });

        et_birtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                et_birtdate.setText((dayOfMonth) + "/" + (monthOfYear + 1)  + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                //It Will Disable all Past Date...
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
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

    public void salesRegistration(){
        final StringRequest salesRegistration = new StringRequest(Request.Method.POST, ConfigUrls.ADD_SALES_REGISTRATION,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {


                        Toast.makeText(getContext(), "ADD_SALES_REGISTRATION onResponse\n\n"+response, Toast.LENGTH_SHORT).show();
                        Log.d("mytag", "ADD_SALES_REGISTRATION onResponse: "+response);

                        Fragment fragment = new SalesPersonFragmentTab();
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
                params.put("firstname", et_firstname.getText().toString());
                params.put("lastname", et_lastname.getText().toString());
                params.put("emailid", et_email.getText().toString());
                params.put("mobileno", et_permanentno.getText().toString());
                params.put("mobileno1", et_alternateno.getText().toString());
                params.put("birthdate", et_birtdate.getText().toString());
                params.put("gender", st_gender);
                params.put("country", st_country_id);
                params.put("state", st_state_name);
                params.put("city", st_city_id);
                params.put("salespincode", et_zipcode.getText().toString());
                params.put("address", et_permanantadd.getText().toString());
                params.put("address2", et_residentialadd.getText().toString());
                params.put("status", st_personstatus);
                params.put("account", et_personaccountnumber.getText().toString());
                params.put("ifsc", et_ifsc.getText().toString());
                params.put("branch", et_branch.getText().toString());


                params.put("lat", String.valueOf(19.000));
                params.put("long", String.valueOf(73.00));

                Log.d("mytag", "getParams: Firstname"+et_firstname.getText().toString()+"\nLastname"+et_lastname.getText().toString()+"\nEmail-id"+et_email.getText().toString()+
                        "\nPermanent Number"+et_permanentno.getText().toString()+"\nAlternate Number"+et_alternateno.getText().toString()+"\nBirthDate"+ et_birtdate.getText().toString()+"\nGender "+st_gender+"\nCountry "+st_country_id+
                        "\nState Name"+ st_state_name+"\n City "+st_city_id+"\nPinCode "+ et_zipcode.getText().toString()+"\n Permanent Address"+ et_permanantadd.getText().toString()+
                        "\nResidential Address"+et_residentialadd.getText().toString()+"\n Person Status"+st_personstatus+"\nAccount Number"+et_personaccountnumber.getText().toString()+
                        "\nIFSC code"+et_ifsc.getText().toString()+"\nBranch Code"+et_branch.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue6 = Volley.newRequestQueue(getContext());
        requestQueue6.add(salesRegistration );
    }


    public void getGenderSpin(){
        List<String> gender = new ArrayList<String>();
        gender.add("Female");
        gender.add("Male");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, gender);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin_gender.setAdapter(dataAdapter);

        spin_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                st_gender = spin_gender.getItemAtPosition(i).toString();
                if (!st_gender .isEmpty()) {
                    Toast.makeText(getContext(), "Gender\n" + st_gender, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    public void getPersonStatusSpin(){
        List<String> status = new ArrayList<String>();
        status.add("Active");
        status.add("Inactive");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, status);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin_personstatus.setAdapter(dataAdapter);

        spin_personstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                st_personstatus = spin_personstatus.getItemAtPosition(i).toString();
                if (!st_personstatus .isEmpty()) {
                    Toast.makeText(getContext(), "st_personstatus\n" + st_personstatus, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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

                               // Toast.makeText(getContext(), "111"+response.toString(), Toast.LENGTH_SHORT).show();

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
                                    spin_country.setSelection(100);

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

                                //Toast.makeText(getContext(), "111"+response.toString(), Toast.LENGTH_SHORT).show();

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

                                //Toast.makeText(getContext(), "111"+response.toString(), Toast.LENGTH_SHORT).show();
//
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
