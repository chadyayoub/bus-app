package com.prohacker.busapp.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.prohacker.busapp.R;
import com.prohacker.busapp.adapters.LoginAdapter;
import com.prohacker.busapp.fragments.LoginFragment;
import com.prohacker.busapp.fragments.SmsCodeFragment;
import com.prohacker.busapp.fragments.ViewPagerLoginFragment;
import com.prohacker.busapp.services.FirebaseAuthentication;

public class Login extends AppCompatActivity {
    FrameLayout container;
    Button confirm;
    ConstraintLayout constraintLayout;
    int currentStep = 0;
    ValueAnimator slideAnimator;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                SafetyNetAppCheckProviderFactory.getInstance());

        initView();
    }



    void initView() {

        confirm = findViewById(R.id.confirm_button);
        container = findViewById(R.id.container);
        constraintLayout = findViewById(R.id.constraintLayout);

        setFragment(new ViewPagerLoginFragment());


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuthentication firebaseAuthentication = new FirebaseAuthentication(Login.this);
                firebaseAuthentication.getAuthState();
                firebaseAuthentication.register("+961070636281","Chady","Ayoub");
                if (currentStep == 0) {
                    setFragment(new SmsCodeFragment());
                    // Gets the layout params that will allow you to resize the layout
                    // Changes the height and width to the specified *pixels*
                    animateLayout(300);
                    currentStep = 1;
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
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
}
