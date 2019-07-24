package com.example.xxovek.salesman_tracker1.user;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class VisitsHistoryFragment extends Fragment {
    public int i;
    public MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    String n [] ;
    String a="",cid="2";


    public VisitsHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todays_visits, container, false);

        SharedPreferences prf = getContext().getSharedPreferences("Options", getContext().MODE_PRIVATE);
        String uid=prf.getString("uid", "");
        String uname=prf.getString("uname","");

        //Toast.makeText(getContext(), "1111111111" + uid.toString(), Toast.LENGTH_SHORT).show();



        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        VisitsHistory11 newVisitHistory11 = new VisitsHistory11(this);
        newVisitHistory11.execute(uid);

        return view;
    }


    public class VisitsHistory11 extends AsyncTask<String, Void, String> implements MyRecyclerViewAdapter.ItemClickListener {

        public VisitsHistory11(VisitsHistoryFragment visitsHistory) {

        }

        @Override
        protected String doInBackground(String... params) {
            String countries_url = "http://track.xxovek.com/public_html/salesandroid/historyclients";

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
                    List<String> al = new ArrayList<String>();
                    List<String> al1 = new ArrayList<String>();
                    List<String> al2 = new ArrayList<String>();
                    List<String> al3 = new ArrayList<String>();
                    List<String> al4 = new ArrayList<String>();






                    JSONArray json_data = new JSONArray(result);
                    int len = json_data.length();
                    String len1 = String.valueOf(len);
                    // Toast.makeText(getContext(), len1.toString(), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(getContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                    for(int i=0; i<json_data.length();i++){
                        JSONObject json = json_data.getJSONObject(i);
                        al.add((json.getString("RouteId")));
                        al1.add("ROUTE NAME : ".concat(json.getString("routeName").toUpperCase()));
                        al2.add("TOTAL SHOPS : ".concat(json.getString("shops")));
                        al3.add("ASSIGN DATE : ".concat(json.getString("assignDate")));



                        // a= a + "Age : "+json.getString("c_phone")+"\n";
                        //j= j + "Job : "+json.getString("Job")+"\n";
                    }
//                    Toast.makeText(getContext(), n.toString(), Toast.LENGTH_SHORT).show();



                    String result1 = result.replace("\"", "");
                    result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                    String str[] = result1.split(",");


                    //al = Arrays.asList(n);

                    final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

                    recyclerView.setLayoutManager(mLayoutManager);

                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    adapter = new MyRecyclerViewAdapter(getContext(), al,al1,al2,al3,"1");
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

            //Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
            String user_id1 = adapter.getItem(position);
            // Toast.makeText(getContext(), "getitem is  " + user_id1.toString() + " on row number " + position, Toast.LENGTH_SHORT).show();


            // Intent intent = new Intent(getContext(), Clientsinfo.class);
            // startActivity(intent);
            Fragment fragment = new TodaysVisits1Fragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Bundle data = new Bundle();//Use bundle to pass data
            data.putString("data", user_id1);//put string, int, etc in bundle with a key value
            fragment.setArguments(data);
            fragmentTransaction.replace(R.id.main_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
    }
}
