package com.prohacker.busapp.fragments;

import android.animation.TimeInterpolator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.prohacker.busapp.R;

import org.jetbrains.annotations.NotNull;


public class LoginFragment extends Fragment {

   public EditText password, phoneNumber;
   TextView forgotPasswordButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_login, container, false);

        phoneNumber = view.findViewById(R.id.phone_number_editText);
        password = view.findViewById(R.id.password_editText);
        forgotPasswordButton = view.findViewById(R.id.forgot_password_button);

        password.setAlpha(0);
        forgotPasswordButton.setAlpha(0);
        phoneNumber.setAlpha(0);

        password.setTranslationX(-1200);
        forgotPasswordButton.setTranslationX(600);
        phoneNumber.setTranslationX(-1200);

        password.animate().alpha(1).translationX(0).setStartDelay(200).setDuration(500).start();
        forgotPasswordButton.animate().alpha(1).translationX(0).setStartDelay(200).setDuration(700).start();
        phoneNumber.animate().alpha(1).translationX(0).setStartDelay(0).setDuration(500).start();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Dabber rasak mech mechkelte",Toast.LENGTH_SHORT).show();
            }
        });
    }
}