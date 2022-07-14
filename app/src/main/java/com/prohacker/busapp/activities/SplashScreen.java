package com.prohacker.busapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.safetynet.SafetyNetApi.VerifyAppsUserResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.auth.FirebaseUser;
import com.prohacker.busapp.R;
import com.prohacker.busapp.services.FirebaseAuthentication;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseAuthentication firebaseAuthentication = new FirebaseAuthentication(this);


        Intent intent;
        if(firebaseAuthentication.getAuthState()==null)
            intent = new Intent(SplashScreen.this,Login.class);
        else
            intent = new Intent(SplashScreen.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}