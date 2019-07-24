package com.example.xxovek.salesman_tracker1.admin.routes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.xxovek.salesman_tracker1.R;

import java.util.HashMap;
import java.util.Map;

public class AddRoutesFragment extends Fragment {

    public EditText et_source,et_destination;
    public Button btn_submit,btn_reset;
    public String ADD_ROUTE="http://track.xxovek.com/src/add_route.php";

    public AddRoutesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_routes, container, false);
        et_source=view.findViewById(R.id.et_source);
        et_destination=view.findViewById(R.id.et_destination);
        btn_submit=view.findViewById(R.id.btn_submit);
        btn_reset=view.findViewById(R.id.btn_reset);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_ROUTE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                Toast.makeText(getContext(), "Response\n\n"+response, Toast.LENGTH_SHORT).show();
                                //converting response to json object
                                // JSONObject obj = new JSONObject(response);
                                // JSONArray obj = new JSONArray(response);
                                //getting the user from the response
                                //JSONObject userJson = obj.getJSONObject("tax_info");
                                // int len = obj.length();

                               /* Fragment fragment = new ProductsAndServices();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.main_container, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();*/
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
                        int adminid=3;
                        params.put("admin_id", String.valueOf(adminid));
                        params.put("source", et_source.getText().toString());
                        params.put("dest", et_destination.getText().toString());

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
