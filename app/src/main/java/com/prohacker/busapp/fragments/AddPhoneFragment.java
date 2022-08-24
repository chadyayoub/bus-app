package com.prohacker.busapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.prohacker.busapp.R;

import org.jetbrains.annotations.NotNull;


public class AddPhoneFragment extends Fragment {

   public EditText phoneNumber;

    public interface Listener {
        public void onPhoneNumberChanged(String s);
    }

    public static Listener mListener;

    public void setListener(Listener listener) {
        mListener = listener;
    }


    public AddPhoneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_login, container, false);

        phoneNumber = view.findViewById(R.id.phone_number_editText);
        phoneNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(mListener==null) {
                    Toast.makeText(getContext(), "listener is null", Toast.LENGTH_SHORT).show();
                    return;
                }else if(s.toString()==null){
                    Toast.makeText(getContext(), "s is null", Toast.LENGTH_SHORT).show();
                    return;
                }
            mListener.onPhoneNumberChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        phoneNumber.setAlpha(0);
        phoneNumber.setTranslationX(-1200);
        phoneNumber.animate().alpha(1).translationX(0).setStartDelay(0).setDuration(500).start();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

    }
}