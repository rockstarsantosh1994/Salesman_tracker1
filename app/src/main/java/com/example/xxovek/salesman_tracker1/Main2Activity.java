package com.example.xxovek.salesman_tracker1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    ArrayList<Object> object,object1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();

        Bundle args = intent.getBundleExtra("BUNDLE");


        object = (ArrayList<Object>) args.getSerializable("ARRAYLIST");
        object1 = (ArrayList<Object>) args.getSerializable("ARRAYLIST1");

         Toast.makeText(getApplicationContext(), "disance is: " + object.toString(), Toast.LENGTH_SHORT).show();

    }
}
