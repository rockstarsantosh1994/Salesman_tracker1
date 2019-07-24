package com.example.xxovek.salesman_tracker1.admin.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.xxovek.salesman_tracker1.user.MainActivity;
import com.example.xxovek.salesman_tracker1.R;

public class AdminLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
    }



    public void UserLogin(View view) {
        Intent intent=new Intent(AdminLoginActivity.this, MainActivity.class);
        startActivity(intent);
    }


    public void loginAsAdmin(View view) {
        //stuff of admin login
        Intent intent=new Intent(AdminLoginActivity.this, AdminHomeActivity.class);
        startActivity(intent);
    }
}
