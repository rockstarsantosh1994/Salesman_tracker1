package com.example.xxovek.salesman_tracker1.admin.assignsaleswork;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
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
import com.example.xxovek.salesman_tracker1.admin.tabs.AssignSalesmanWorkTab;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignSalesManFragment extends Fragment {

    public EditText et_ondate,et_waitingtime;
    public Spinner spin_route,spin_salesman;
    public Button btn_add,btn_reset;
    private int mYear, mMonth, mDay;
    DatePickerDialog datePickerDialog;
    String[] spinnerArray;
    HashMap<Integer,String> routeId_hashmap,salesmanId_hashmap;
    String admin_id,st_route_id,st_salesman_id,w_id="",st_Rid,st_wid,st_route,st_sid,st_sname,st_adate,st_status,st_wtime;
    public static final String TAG="mytag";

    public AssignSalesManFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_assign_sales_man, container, false);

        et_ondate=view.findViewById(R.id.et_ondate);
        et_waitingtime=view.findViewById(R.id.et_waitingtime);
        spin_route=view.findViewById(R.id.spin_route);
        spin_salesman=view.findViewById(R.id.spin_salesman);
        btn_add=view.findViewById(R.id.btn_add);
        btn_reset=view.findViewById(R.id.btn_reset);

        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        admin_id=prf.getString("admin_id", "");
        Log.d("mytag", "onCreateView:Admin_id in AddRoutesFragment "+admin_id);

        try{
            w_id=getArguments().getString("data");
            Log.d("mytag", "onCreateView:w_id AssignSalesManFragment "+w_id);}
        catch (NullPointerException e){e.printStackTrace();}

        //Loading data of Routes in spinner using below function.....
        getRoute();

        //Loading data of Salesman name in spinner using below function.....
        getSalesman();

        //....After RecyclerView Click Event Operation perform....
        if(!w_id.equals("")) {
            fetchWorkDetails();
        }


        et_ondate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Datest_
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                               // et_ondate.setText((dayOfMonth) + "/" + (monthOfYear + 1)  + "/" + year);
                                et_ondate.setText((year) + "/" + (monthOfYear + 1)  + "/" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                //It Will Disable all Past Date...
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrls.ADD_SALES_WORK,
                        new Response.Listener<String>() {


                            @Override
                            public void onResponse(String response) {


                                //.makeText(getContext(), "Response\n\n"+response, //.LENGTH_SHORT).show();
                                Log.d("mytag", "ADD_SALES_WORK onResponse: "+response);

                                Fragment fragment = new AssignSalesmanWorkTab();
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
                        params.put("source",st_route_id);
                        params.put("salesmansid",st_salesman_id);
                        params.put("assigndate",et_ondate.getText().toString());
                        params.put("wtime",et_waitingtime.getText().toString());
                        params.put("w_id",w_id);

                        Log.d(TAG, "ADD_SALES_WORK getParams: admin_id "+admin_id+"\nsource "+st_route_id+
                                "\nsalesmansid "+st_salesman_id+"\n assigndate "+et_ondate.getText().toString()
                        +"\nwtime "+et_waitingtime.getText().toString()+"\nw_id "+w_id);

                        return params;
                    }
                };

                RequestQueue requestQueue6 = Volley.newRequestQueue(getContext());
                requestQueue6.add(stringRequest);
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    public void fetchWorkDetails(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrls.FETCH_WORK_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            //.makeText(AssignSalesManFragment.this.getContext(), "No Shops"+response.toString(), //.LENGTH_LONG).show();

                        }else{

                            try {
                                Log.d("mytag", "onResponse:FETCH_WORK_DETAILS "+response);


                               JSONObject json = new JSONObject(response);
                                Log.d("mytag", "onResponse:json Bhau"+json);
                                st_Rid = json.getString("Rid");
                                st_wid = json.getString("wid");
                                st_route = json.getString("route");
                                st_sid = json.getString("sid");
                                st_sname = json.getString("sname");
                                st_adate = json.getString("adate");
                                st_status = json.getString("status");
                                st_wtime = json.getString("wtime");
                                Log.d("mytag", "onResponse:\n rid "+st_Rid+"\nsid "+st_wid+"\nroute "+st_route+"\nsid "+st_sid+"\nsname"
                                +st_sname+"\nadate "+st_adate+"\nstatus "+st_status+"\nwtime "+st_wtime);

                                et_ondate.setText(st_adate);
                                et_waitingtime.setText(st_wtime);

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
                params.put("w_id",String.valueOf(70));
                params.put("admin_id",admin_id);
                Log.d(TAG, "getParams: w_id"+w_id);


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
                //params.put("ItemId", "ItemId");
                //params.put("ItemName", "ItemName");
                params.put("admin_id",admin_id);
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        requestQueue1.add(stringRequest);
    }


    public void getSalesman(){
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrls.GET_SALESMAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            //.makeText(getContext(), "Unable to fetch size data"+response.toString(), //.LENGTH_LONG).show();

                        }else{

                            try {

                              //  //.makeText(getContext(), "Salesman Response \n\n\n"+response.toString(), //.LENGTH_SHORT).show();
                                Log.d("mytag", "Salesman data onResponse: "+response);


                                List<String> al1 = new ArrayList<String>();
                                List<String> al2 = new ArrayList<String>();



                                JSONArray json_data = new JSONArray(response);
                                int len = json_data.length();
                                String len1 = String.valueOf(len);
                                // //.makeText(getContext(), json_data.toString(), //.LENGTH_SHORT).show();

                                for (int i = 0; i < json_data.length(); i++) {
                                    JSONObject json = json_data.getJSONObject(i);
                                    al1.add(json.getString("salesManId"));
                                    al2.add(json.getString("salesManName"));
                                }


                                Integer a1 = al1.size();
                                String a2 = String.valueOf(a1);
                                spinnerArray = new String[al1.size()];
                                salesmanId_hashmap = new HashMap<Integer, String>();

                                // //.makeText(getContext(), "the size is" + a2.toString(), //.LENGTH_SHORT).show();


                                for (int i = 0; i <al1.size(); i++)
                                {
                                    salesmanId_hashmap.put(i,al1.get(i));
                                    spinnerArray[i] = al1.get(i);
                                }

                                    /*ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddNewSchemesInformationFragment.this.getActivity(),
                                            android.R.layout.simple_spinner_dropdown_item, al2);
*/
                                ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, al2);
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                                spin_salesman.setAdapter(dataAdapter);

                                spin_salesman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        st_salesman_id = (String) salesmanId_hashmap.get(spin_salesman.getSelectedItemPosition());


                                        //.makeText(getContext(), "SalesmanId Value "+st_route_id, //.LENGTH_SHORT).show();
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
                //params.put("ItemId", "ItemId");
                //params.put("ItemName", "ItemName");
                params.put("admin_id",admin_id);
//                params.put("password", password);

                //returning parameter
                return params;
            }
        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        requestQueue1.add(stringRequest);
    }

}
