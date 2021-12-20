package com.prohacker.busapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.prohacker.busapp.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}