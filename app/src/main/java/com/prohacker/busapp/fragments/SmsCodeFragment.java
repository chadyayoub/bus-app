package com.prohacker.busapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.prohacker.busapp.R;

import org.jetbrains.annotations.NotNull;

public class SmsCodeFragment extends Fragment {

    public EditText phoneNumber;
    LinearLayout linearLayout;

    public SmsCodeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_sms_verification, container, false);

        linearLayout = view.findViewById(R.id.verification_layout);
        phoneNumber = view.findViewById(R.id.phone_number_editText);

        linearLayout.setTranslationY(-800);
        linearLayout.animate().translationY(0).setStartDelay(0).setDuration(500).start();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

    }
}
