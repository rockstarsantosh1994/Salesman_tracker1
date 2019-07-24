package com.example.xxovek.salesman_tracker1.user;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.xxovek.salesman_tracker1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * A simple {@link Fragment} subclass.
 */
public class TodaysVisits1Fragment extends Fragment implements View.OnClickListener{
    public int i;
    public MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    Button button77;
    String n [] ;
    String a="";
    List<String> al = new ArrayList<String>();
    List<String> al1 = new ArrayList<String>();
    List<String> al2 = new ArrayList<String>();
    List<String> al3 = new ArrayList<String>();
    List<String> al4 = new ArrayList<String>();
    public String st_assignid;


    public TodaysVisits1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todays_visits1, container, false);

        final String cid = getArguments().getString("data");
        st_assignid = getArguments().getString("data1");

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview1);
        button77 = (Button) view.findViewById(R.id.button77);



        final ImageButton myButton=(ImageButton) getActivity().findViewById(R.id.signout);
        myButton.setVisibility(View.VISIBLE);

        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Fragment fragment = new TodaysVisitsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        button77.setOnClickListener(this);

//        TodaysVisits12 newTodaysVisits12 = new TodaysVisits12(this);
//        newTodaysVisits12.execute(cid);

        final String LOGIN_URL = "http://track.xxovek.com/public_html/salesandroid/clients1";

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(TextUtils.isEmpty(response)){
                            //Creating a shared preference
                            Toast.makeText(TodaysVisits1Fragment.this.getContext(), "No Shops"+response.toString(), Toast.LENGTH_LONG).show();

                        }else{

                            JSONArray json_data = null;
                            try {


                                json_data = new JSONArray(response);
                                int len = json_data.length();
                                //Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                                for(int i=0; i<json_data.length();i++){
                                    JSONObject json = json_data.getJSONObject(i);
                                    al.add(json.getString("latitude").concat(" ").concat(json.getString("longitude")));
                                    al1.add("CONTACT PERSON : ".concat(json.getString("contactPerson").toUpperCase()));
                                    al2.add(json.getString("shopKeeperId"));
                                    al3.add("ADDRESS : ".concat(json.getString("address")));
                                    //  Toast.makeText(getContext(), "nice " + al.toString() + " on row number " , Toast.LENGTH_SHORT).show();



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
                                adapter = new MyRecyclerViewAdapter(getContext(), al,al1,al3,al2,"1");
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
                params.put("uid", cid);
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
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.button77:
                /*comment on 4 july 2019
                Intent intent = new Intent(getActivity(), MyService.class);*/
                //intent.putExtra("latlong1",al.toString());
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)al);
                args.putSerializable("ARRAYLIST1",(Serializable)al2);


                /*intent.putExtra("BUNDLE",args);
                getContext().startService(intent);*/

                Intent intent1 = new Intent(getActivity(), MapsActivity.class);

//                Bundle args1 = new Bundle();
//                args.putSerializable("ARRAYLIST",(Serializable)al);
//                args.putSerializable("ARRAYLIST1",(Serializable)al2);

                intent1.putExtra("BUNDLE",args);
                intent1.putExtra("data1",st_assignid);
                startActivity(intent1);

                break;
        }
    }

    public class TodaysVisits12 extends AsyncTask<String, Void, String> implements MyRecyclerViewAdapter.ItemClickListener {

        public TodaysVisits12(TodaysVisits1Fragment todaysVisits) {

        }

        @Override
        protected String doInBackground(String... params) {
            String countries_url = "http://track.xxovek.com/public_html/salesandroid/clients1";

            try {
                String uid = params[0];


                URL url = new URL(countries_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(uid, "UTF-8");


                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String result1 = "";
                String line = "";
                ArrayList<String> categories1 = new ArrayList<String>();

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;

                }


                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(final String result) {

            if (result.isEmpty() || result.equals(null)) {
               // Toast.makeText(getContext(), result.toString(), Toast.LENGTH_SHORT).show();

            } else {
                try {


                    JSONArray json_data = new JSONArray(result);
                    int len = json_data.length();
                    //Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                    for(int i=0; i<json_data.length();i++){
                        JSONObject json = json_data.getJSONObject(i);
                        al.add(json.getString("latitude").concat(" ").concat(json.getString("longitude")));
                        al1.add("CONTACT PERSON : ".concat(json.getString("contactPerson").toUpperCase()));
                        al2.add(json.getString("shopKeeperId"));
                        al3.add("ADDRESS : ".concat(json.getString("address")));
                      //  Toast.makeText(getContext(), "nice " + al.toString() + " on row number " , Toast.LENGTH_SHORT).show();



                        // a= a + "Age : "+json.getString("c_phone")+"\n";
                        //j= j + "Job : "+json.getString("Job")+"\n";
                    }
//                    //Toast.makeText(getContext(), n.toString(), Toast.LENGTH_SHORT).show();



                    String result1 = result.replace("\"", "");
                    result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                    String str[] = result1.split(",");


                    //al = Arrays.asList(n);

                    final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

                    recyclerView.setLayoutManager(mLayoutManager);

                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    adapter = new MyRecyclerViewAdapter(getContext(), al,al1,al3,al2,"1");
//                    adapter.setClickListener(this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                            DividerItemDecoration.VERTICAL));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }

        @Override
        public void onItemClick(View view, int position) {
           // Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
            String user_id1 = adapter.getItem(position);
          //  Toast.makeText(getContext(), "getitem is  " + user_id1.toString() + " on row number " + position, Toast.LENGTH_SHORT).show();


             Intent intent = new Intent(getContext(), MapsActivity.class);
             startActivity(intent);

//            Fragment fragment = new VisitsHistoryFragment();
//            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            Bundle data = new Bundle();//Use bundle to pass data
//            data.putString("data", user_id1);//put string, int, etc in bundle with a key value
//            fragment.setArguments(data);
//            fragmentTransaction.replace(R.id.main_container, fragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();

        }
    }

}
