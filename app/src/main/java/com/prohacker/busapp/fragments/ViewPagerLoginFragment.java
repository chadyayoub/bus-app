package com.prohacker.busapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.prohacker.busapp.R;
import com.prohacker.busapp.adapters.LoginAdapter;

import org.jetbrains.annotations.NotNull;

public class ViewPagerLoginFragment extends Fragment {

    TabLayout tabLayout;
    Button confirm;
    ViewPager2 viewPager;

    public ViewPagerLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_login_viewpager, container, false);

        tabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewPager2);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        LoginAdapter loginAdapter = new LoginAdapter(getActivity());
        viewPager.setAdapter(loginAdapter);


        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("Add contact number")
        ).attach();
    }
}
