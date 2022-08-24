package com.prohacker.busapp.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.prohacker.busapp.fragments.AddPhoneFragment;

import org.jetbrains.annotations.NotNull;

public class LoginAdapter extends FragmentStateAdapter {
    private int tabCount;
    public LoginAdapter(FragmentActivity fragmentActivity){
        super(fragmentActivity);
        tabCount = 1;
    }


    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                AddPhoneFragment addPhoneFragment = new AddPhoneFragment();
                return addPhoneFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return tabCount;
    }
}
