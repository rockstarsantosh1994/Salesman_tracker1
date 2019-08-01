package com.example.xxovek.salesman_tracker1.admin.routes;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.xxovek.salesman_tracker1.admin.tabs.RoutesFragmentTab;

import java.util.HashMap;
import java.util.Map;

public class AddRoutesFragment extends Fragment {

    public EditText et_source,et_destination;
    public Button btn_submit,btn_reset;
    String admin_id,r_id="",st_source,st_dest;


    public AddRoutesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_routes, container, false);
        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        admin_id=prf.getString("admin_id", "");
        Log.d("mytag", "onCreateView:Admin_id in AddRoutesFragment "+admin_id);

        try{
            r_id=getArguments().getString("data");
            st_source=getArguments().getString("source");
            st_dest=getArguments().getString("dest");
            Log.d("mytag", "onCreateView:Admin_id in AddRoutesFragment "+r_id);}
        catch (NullPointerException e){e.printStackTrace();}

        et_source=view.findViewById(R.id.et_source);
        et_destination=view.findViewById(R.id.et_destination);
        //when we click on update via Recycler then it will be filled....
        et_source.setText(st_source);
        et_destination.setText(st_dest);

        btn_submit=view.findViewById(R.id.btn_submit);
        btn_reset=view.findViewById(R.id.btn_reset);





        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrls.ADD_ROUTE,
                        new Response.Listener<String>() {


                            @Override
                            public void onResponse(String response) {


                                Toast.makeText(getContext(), "Response\n\n"+response, Toast.LENGTH_SHORT).show();
                                Log.d("mytag", "ADD_ROUTE onResponse: "+response);
                                //converting response to json object
                                // JSONObject obj = new JSONObject(response);
                                // JSONArray obj = new JSONArray(response);
                                //getting the user from the response
                                //JSONObject userJson = obj.getJSONObject("tax_info");
                                // int len = obj.length();

                                Fragment fragment = new RoutesFragmentTab();
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

                        final String source= et_source.getText().toString();
                        final String destination= et_destination.getText().toString();
                        params.put("admin_id", admin_id);
                        params.put("source",source);
                        params.put("dest",destination);
                        params.put("r_id",r_id);

                        Log.d("mytag", "getParams:ADD_ROUTE \nadmin_id "+admin_id+"\n source "+source+
                                "\n destination "+destination+ "\n r_id "+r_id);

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
                et_source.getText().clear();
                et_destination.getText().clear();
            }
        });
        return view;
    }

}
