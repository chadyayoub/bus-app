package com.prohacker.busapp.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prohacker.busapp.R;
import com.prohacker.busapp.fragments.LoginFragment;

public class Login extends AppCompatActivity {

    int selectedId=0;
    // signup id is 0 login id is 1
    TextView signupBtn, loginBtn;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.login_button);
        signupBtn = findViewById(R.id.signup_button);

        swapFragment();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if(selectedId==0)
                {
                    selectedId = 1;
                    swapFragment();
                }
            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if(selectedId==1)
                {
                    selectedId = 0;
                    swapFragment();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void swapFragment(){
        if(selectedId == 0) {
            LoginFragment myf = new LoginFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, myf);
            transaction.commit();
            signupBtn.setBackground(getDrawable(R.drawable.tab_selected));
            loginBtn.setBackground(getDrawable(R.drawable.tab_not_selected));
            loginBtn.setLayoutParams(new LinearLayout.LayoutParams(R.dimen.tab_height, R.dimen.tab_width, 0.4f));
            signupBtn.setLayoutParams(new LinearLayout.LayoutParams(R.dimen.tab_height, R.dimen.tab_width, 0.6f));
            return;
        }
        LoginFragment myf = new LoginFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, myf);
        transaction.commit();
        signupBtn.setBackground(getDrawable(R.drawable.tab_not_selected));
        loginBtn.setBackground(getDrawable(R.drawable.tab_selected));
        loginBtn.setLayoutParams(new LinearLayout.LayoutParams(R.dimen.tab_height, R.dimen.tab_width, 0.6f));
        signupBtn.setLayoutParams(new LinearLayout.LayoutParams(R.dimen.tab_height, R.dimen.tab_width, 0.4f));
    }

    @Override
    public void onBackPressed() {
        return;
    }
}