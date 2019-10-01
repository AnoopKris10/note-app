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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
*
* Code By :Anoop Krishna Y
* DATE : 10th sept 2019
*
* Referred from https://firebase.google.com/docs/auth/android/password-auth#sign_in_a_user_with_an_email_address_and_password
*
*
* */
public class LoginPage extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        final EditText mEmailEditText = (EditText)findViewById(R.id.EmailLoginEditText);
        final EditText mPasswordEditText = (EditText)findViewById(R.id.PasswordLoginEditText);
        final TextView mForgotPasswordTextView = (TextView)findViewById(R.id.ForgotPasswordTextView);
        mAuth = FirebaseAuth.getInstance();

        Button mLogInButton = (Button)findViewById(R.id.LoginButton);
        final TextView mSignUpTextView = (TextView)findViewById(R.id.RegNowTextView);

        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                //Check if user has entered email and password fields , if 'YES' proceed ,otherwise toast error messages

                if(validateEntries(email,password)){
                    mAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    }
                                    else{
                                        Toast.makeText(LoginPage.this,"Authentication failed",Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }

                                }
                            });
                }
                else
                {
                    if(email.equals("")){
                        Toast.makeText(LoginPage.this,"Please Enter Email-ID",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginPage.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        mForgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,ForgotPasswordPage.class));
            }
        });


        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,SignUpPage.class));
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private boolean validateEntries(String email,String password){
        if(!email.equals("") && !password.equals("")){
            return true;
        }
        else {
            return false;
        }
    }

    private void updateUI(FirebaseUser user){
        if(user != null){
            // mSharedPreferenceConfig.writeUserUID(user.getUid());
            startActivity(new Intent(this,HomePage.class));
        }
        else{
            Toast.makeText(LoginPage.this, "Login here", Toast.LENGTH_SHORT).show();
        }

    }
}

