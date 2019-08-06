package com.example.xxovek.salesman_tracker1.user;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxovek.salesman_tracker1.ConfigUrls;
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

public class UserProfile extends AppCompatActivity {
    TextView textView7,textView8,textView9,textView10;
    String username="vikas123",passsword="vikas";
    String alq,alq1,alq2,alq3,uid,cid;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        SharedPreferences prf = getApplicationContext().getSharedPreferences("Options", MODE_PRIVATE);
        String uid=prf.getString("uid", "");
        String uname=prf.getString("uname","");

        Toast.makeText(getApplicationContext(), "hhhhhh" + uid.toString(), Toast.LENGTH_SHORT).show();




        textView7 = (TextView) findViewById(R.id.textView7);
        textView7.startAnimation(animation);
        textView8 = (TextView) findViewById(R.id.textView8);
        textView8.startAnimation(animation);
        textView9 = (TextView) findViewById(R.id.textView9);
        textView9.startAnimation(animation);
        textView10 = (TextView) findViewById(R.id.textView10);
        textView10.startAnimation(animation);







        String type = "user_profile";

        UserProfile1 userProfile = new UserProfile1(this);
        userProfile.execute(uid, username);

    }

    public class UserProfile1 extends AsyncTask<String, Void, String> {

        public UserProfile1(UserProfile userProfile) {


        }

        @Override
        protected String doInBackground(String... params) {
            String countries_url = "http://track.xxovek.com/public_html/salesandroid/clientsinfo";

            try {
                String uid = params[0];
                String cid = params[1];


                URL url = new URL(ConfigUrls.CLIENTS_INFO);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(uid, "UTF-8")
                        +"&"+URLEncoder.encode("cid","UTF-8")+"="+URLEncoder.encode(cid,"UTF-8");



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
                Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "empttttyyy", Toast.LENGTH_SHORT).show();


            } else {
                try {
                    List<String> al = new ArrayList<String>();
                    List<String> al1 = new ArrayList<String>();
                    List<String> al2 = new ArrayList<String>();
                    List<String> al3 = new ArrayList<String>();

                    Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();

                    JSONArray json_data = new JSONArray(result);
                    int len = json_data.length();
                    Toast.makeText(getApplicationContext(), json_data.toString(), Toast.LENGTH_SHORT).show();

                    for(int i=0; i<json_data.length();i++){
                        JSONObject json = json_data.getJSONObject(i);
                        alq =(json.getString("fname").concat(" ").concat(json.getString("lname")));
                        alq1 = (json.getString("created_at"));
                        alq2 = (json.getString("email"));
                        alq3 = (json.getString("address"));




                        // a= a + "Age : "+json.getString("c_phone")+"\n";
                        //j= j + "Job : "+json.getString("Job")+"\n";
                    }
                    String result1 = result.replace("\"", "");
                    result1 = result1.replaceAll("[\\[\\]\\(\\)]", "");
                    String str[] = result1.split(",");
                    Toast.makeText(getApplicationContext(), alq.toString(), Toast.LENGTH_SHORT).show();

                    textView7.setText(alq.toString());
                    textView8.setText(alq1.toString());
                    textView9.setText(alq2.toString());
                    textView10.setText(alq3.toString());





                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }

}
