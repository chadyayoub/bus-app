package com.prohacker.busapp.adapters;

import android.app.FragmentManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.prohacker.busapp.fragments.LoginFragment;
import com.prohacker.busapp.fragments.SignupFragment;

import org.jetbrains.annotations.NotNull;

public class LoginAdapter extends FragmentStateAdapter {
    private Context context;
    private int tabCount;
    public LoginAdapter(FragmentActivity fragmentActivity, Context context){
        super(fragmentActivity);
        this.context = context;
        tabCount = 2;
    }


    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                LoginFragment loginFragment = new LoginFragment();
                return loginFragment;
            case 1:
                SignupFragment signupFragment = new SignupFragment();
                return signupFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return tabCount;
    }
}
