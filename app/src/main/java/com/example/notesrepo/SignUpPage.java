package com.example.notesrepo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;


/*
 * code by:Anoop Krishna Y
 *
 * Date:13th sept 2019
 *
 * Referred from : https://www.youtube.com/watch?v=7hwlMKUgTQc&t=821s
 *
 * */

public class SignUpPage extends AppCompatActivity {


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        mAuth = FirebaseAuth.getInstance();

        final EditText mEmailEditText = (EditText)findViewById(R.id.EmailSignUpEditText);
        final EditText mPasswordEditTest = (EditText)findViewById(R.id.PasswordSignUpEditText);
        final EditText mReEnterPasswordEditText = (EditText)findViewById(R.id.RePasswordEditText);
        final TextView mLoginNow = findViewById(R.id.LoginNowTextView);


        Button mSignUpButton = (Button)findViewById(R.id.SignUpButton);


        mLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpPage.this,LoginPage.class));
            }
        });
        //Sign Up functionality

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditTest.getText().toString();
                String confirmedPassword = mReEnterPasswordEditText.getText().toString();

                if(isValidFields(email,password,confirmedPassword)){

                    if(isPasswordStrong(password)){
                        if(isMatching(password,confirmedPassword)){

                            mAuth.createUserWithEmailAndPassword(email,password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            if(task.isSuccessful()){

                                                Toast.makeText(SignUpPage.this,"Account created",Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SignUpPage.this,UserDetailsInput.class));
                                            }else{

                                                Toast.makeText(SignUpPage.this,"Failed..!! Try again",Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                        }
                    }

                }
            }
        });

    }



    //Function to check if two  input passwords are matching
    private boolean isMatching(String password,String confirmedPassword){
        if( password.equals(confirmedPassword)){
            return true;
        }
        else{
            Toast.makeText(SignUpPage.this,"Passwords are not matching",Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    /*
     * We need strong passwords , so as a starter condition, I am

     * adding a condition of minimum 8 chars , can be optimized later on
     * */

    private boolean isPasswordStrong(String password){

        if(password.length() > 7){
            return true;
        }
        else{
            Toast.makeText(SignUpPage.this,"Password must contain a minimum of 8 characters",Toast.LENGTH_SHORT).show();
            return false;
        }

    }


    /*
     * Function to check if all the fields are filled,
     * Function is called every time , when 'SignUpButton' is pressed
     * */
    private boolean isValidFields(String email,String password,String confirmedPassword){

        if(email != "" && password != "" && confirmedPassword != ""){
            return true;
        }
        else{
            Toast.makeText(SignUpPage.this,"You cannot leave the fields empty!!",Toast.LENGTH_SHORT).show();
            return false;
        }


    }


    }
