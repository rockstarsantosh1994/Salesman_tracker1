package com.example.xxovek.salesman_tracker1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

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
import java.util.Calendar;

/**
 * Created by AKASH PARMAR on 29-06-2018.
 */

public class BackgroundWorker extends AsyncTask<String,Void,String> {

    String type="";
    Context context;
    AlertDialog alertDialog;


    BackgroundWorker(Context ctx){
        context = ctx;

    }

    @Override
    protected String doInBackground(String... params) {
//        TextView textView26 = (TextView) company_information.findViewById(R.id.textView26);
        type = params[0];
  //      textView26.setText("akash");
        String login_url = "http://track.xxovek.com/public_html/salesandroid/login";
        String register_url = "http://192.168.0.112/registration";
        String countries_url = "http://192.168.0.112/countries";


        if(type.equals("login")){
            try {
                String username = params[1];
                String password = params[2];
                //String date = params[3];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+ URLEncoder.encode(username,"UTF-8")+"&"+ URLEncoder.encode("password","UTF-8")+"="+ URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!=null)
                {
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
        } else if(type.equals("register")){
            try {
                String fname = params[1];
                String uname = params[2];
                String email = params[3];
                String password = params[4];
                String reenterpassword = params[5];
                String contactno = params[6];
                String ac_created_at = params[7];
                String payment_date = params[8];
                String payment_due_date = params[9];


                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("fname","UTF-8")+"="+ URLEncoder.encode(fname,"UTF-8")
                        +"&"+ URLEncoder.encode("uname","UTF-8")+"="+ URLEncoder.encode(uname,"UTF-8")
                        +"&"+ URLEncoder.encode("email","UTF-8")+"="+ URLEncoder.encode(email,"UTF-8")
                        +"&"+ URLEncoder.encode("password","UTF-8")+"="+ URLEncoder.encode(password,"UTF-8")
                        +"&"+ URLEncoder.encode("reenterpassword","UTF-8")+"="+ URLEncoder.encode(reenterpassword,"UTF-8")
                        +"&"+ URLEncoder.encode("contactno","UTF-8")+"="+ URLEncoder.encode(contactno,"UTF-8")
                        +"&"+ URLEncoder.encode("ac_created_at","UTF-8")+"="+ URLEncoder.encode(ac_created_at,"UTF-8")
                        +"&"+ URLEncoder.encode("payment_date","UTF-8")+"="+ URLEncoder.encode(payment_date,"UTF-8")
                        +"&"+ URLEncoder.encode("payment_due_date","UTF-8")+"="+ URLEncoder.encode(payment_due_date,"UTF-8");


                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!=null)
                {
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

        }else if(type.equals("countries")){
            try {
                String username = params[1];


                URL url = new URL(countries_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+ URLEncoder.encode(username,"UTF-8");


                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!=null)
                {
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

        }
        return null;
    }

    @Override
    protected void onPreExecute(){
        alertDialog = new AlertDialog.Builder(context).create();
        //alertDialog.setTitle("Login Status");
        //super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int day = c.get(Calendar.DAY_OF_MONTH);
        String day1 = Integer.toString(day);
        int year = c.get(Calendar.YEAR);
        String year1 = Integer.toString(year);
        int monthi = c.get(Calendar.MONTH);
        String months = Integer.toString(monthi);
        String month1 = Integer.toString(monthi+1);
        String month2 = Integer.toString(monthi+2);
        String date = day1.concat("-").concat(month1).concat("-").concat(year1);
        String date1 = day1.concat("/").concat(month2).concat("/").concat(year1);


        if(type.equals("login")) {
            //JSONObject obj = null;

            if (result.isEmpty() || result.equals(null)) {
                Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show();
                alertDialog.setMessage("INVALID USERNAME AND PASSOWRD");
                alertDialog.show();
            } else {
                try {
                    Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show();

                    JSONArray json_data = new JSONArray(result);
                    String uid = (String) json_data.get(0);
                    String uname = (String) json_data.get(1);
                    String password = (String) json_data.get(2);
                   // String payment_due_date = (String) json_data.get(3);

                   // String id = (String) json_data.get(3);
                    SharedPreferences prf = context.getSharedPreferences("Options", context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prf.edit();
                    editor.putString("uid", uid);
                    editor.putString("uname",uname);// Storing string
                    editor.commit();

                    Intent intent = new Intent(context, HomeActivity.class);
                    context.startActivity(intent);

                    /*if(json_data.get(4).equals(null)){
                        //Toast.makeText(context, "kunal kapse", Toast.LENGTH_LONG).show();
                    }
                    else{
                        //Toast.makeText(context, "vikas pawarrrrrrrrrrrrrr", Toast.LENGTH_LONG).show();

                    }*/




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
//        else if(type.equals("register")){
//               Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show();
//               Intent intent = new Intent(context, Company_information.class);
//               context.startActivity(intent);
//
//        }
//        else if(type.equals("countries")){
//           // Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show();
//            if (result.isEmpty() || result.equals(null)) {
//                alertDialog.setMessage("INVALID USERNAME AND PASSOWRD");
//                alertDialog.show();
//            } else {
//                Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show();
//                JSONArray json_data1 = null;
//                try {
//                    JSONArray json_data = new JSONArray(result);
//                    for(int i=0;i<json_data.length();i++) {
//                        String payment_due_date = (String) json_data.get(i);
//                        Toast.makeText(context, payment_due_date.toString(), Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//                catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            //Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show();
//
//
//        }
    }

    @Override
    protected void onProgressUpdate(Void... values){
        super.onProgressUpdate(values);
    }

}