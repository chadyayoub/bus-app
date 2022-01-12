package com.prohacker.busapp.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

import static android.content.ContentValues.TAG;

public class FirebaseAuthentication {
    private FirebaseAuth mAuth;
    private Context mContext;

    public FirebaseAuthentication(Context context){
        mContext = context;
    }

    public void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Executor)this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(mContext, "Login success.",
                            Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(mContext, "Login failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void register(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    Toast.makeText(mContext, "Signup success.",
                            Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(mContext, "Signup failed.",
                            Toast.LENGTH_SHORT).show();
                }

                // ...
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
