package com.prohacker.busapp.services;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class


FirebaseAuthentication {

    private FirebaseAuth mAuth;
    private Activity mActivity;
    private String verificationCodeId;



    public FirebaseAuthentication(Activity context){
        mActivity = context;
    }



    public void login(String phoneNumber){
        phoneNumber = "+961"+phoneNumber;
    }



    public void register(String phoneNumber, String firstName, String lastName){
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder()
                .setPhoneNumber(phoneNumber)
                .setTimeout(60l,TimeUnit.SECONDS)
                .setCallbacks(mCallback)
                .setActivity(mActivity)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeId = s;
        }
        @Override
        public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                //signInWithCredential(phoneAuthCredential);
            }
        }
        @Override
        public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
            Toast.makeText(mActivity.getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };



    private void verifyCode(String code){
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationCodeId, code);
        signInWithCredential(phoneAuthCredential);
    }



    private void signInWithCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                }
                else{
                    Toast.makeText(mActivity.getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public void setAuthState(){
        mAuth = FirebaseAuth.getInstance();
    }



    public FirebaseAuth getAuthState(){
        return mAuth;
    }



    public int getDriverId(){
        return 0;
    }



    public int getUserId(){
        return 0;
    }
}
