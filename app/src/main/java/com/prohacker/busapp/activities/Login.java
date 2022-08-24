package com.prohacker.busapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.location.LocationListenerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.prohacker.busapp.R;
import com.prohacker.busapp.fragments.AddPhoneFragment;
import com.prohacker.busapp.fragments.SmsCodeFragment;
import com.prohacker.busapp.fragments.ViewPagerLoginFragment;
import com.prohacker.busapp.services.CodeGenerator;

import java.util.Calendar;
import java.util.List;

public class Login extends AppCompatActivity implements  AddPhoneFragment.Listener, LocationListenerCompat {
    FrameLayout container;
    Button confirm;
    ConstraintLayout constraintLayout;
    int currentStep = 0;
    SmsManager smsManager;
    ValueAnimator slideAnimator;
    CodeGenerator codeGenerator;
    String number;
    int SEND_SMS = 5;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }



    void initView() {

        confirm = findViewById(R.id.confirm_button);
        container = findViewById(R.id.container);
        constraintLayout = findViewById(R.id.constraintLayout);
        codeGenerator = new CodeGenerator();
        // register activity as listener
        AddPhoneFragment.mListener=this;

        setFragment(new ViewPagerLoginFragment());


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentStep == 0) {
                    setFragment(new SmsCodeFragment());
                    // Gets the layout params that will allow you to resize the layout
                    // Changes the height and width to the specified *pixels*

                    sendSMS(number);

                    animateLayout(300);
                    currentStep = 1;
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if(currentStep==1){

                }
            }
        });
    }

    void sendSMS(String phoneNumber){
        if (ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    Login.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    SEND_SMS
            );
            return;
        }
        smsManager = SmsManager.getDefault();
        String message = "Your verification code is " + codeGenerator.getCodeMessage();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Toast.makeText(this, "message sent", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {


    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListenerCompat.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListenerCompat.super.onFlushComplete(requestCode);
    }

    void animateLayout(int height){
        int newHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics());

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) constraintLayout.getLayoutParams();

        slideAnimator = ValueAnimator
                .ofInt(params.height, newHeight)
                .setDuration(300);
        slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // get the value the interpolator is at
                Integer value = (Integer) animation.getAnimatedValue();
                // I'm going to set the layout's height 1:1 to the tick
                constraintLayout.getLayoutParams().height = value.intValue();
                // force all layouts to see which ones are affected by
                // this layouts height change
                constraintLayout.requestLayout();

            }
        });
        AnimatorSet set = new AnimatorSet();
        // since this is the only animation we are going to run we just use
        // play
        set.play(slideAnimator);
        // this is how you set the parabola which controls acceleration
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        // start the animation
        set.start();
    }



    void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(currentStep!=0) {
            setFragment(new ViewPagerLoginFragment());
            animateLayout(450);
            currentStep=0;
        }
        return;
    }

    @Override
    public void onPhoneNumberChanged(String s) {
        Toast.makeText(Login.this, s, Toast.LENGTH_SHORT ).show();
        number = "+961"+s;
    }
}
