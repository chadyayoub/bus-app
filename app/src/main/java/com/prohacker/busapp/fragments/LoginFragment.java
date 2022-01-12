package com.prohacker.busapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.prohacker.busapp.R;

import org.jetbrains.annotations.NotNull;


public class LoginFragment extends Fragment {

   public EditText firstName, lastName, phoneNumber;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        firstName = view.findViewById(R.id.first_name_editText);
        lastName = view.findViewById(R.id.last_name_editText);
        phoneNumber = view.findViewById(R.id.phone_number_editText);
    }
}