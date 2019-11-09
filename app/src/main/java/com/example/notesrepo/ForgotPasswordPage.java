package com.example.notesrepo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordPage extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page);

        mAuth = FirebaseAuth.getInstance();
        final EditText mEmailEditText = (EditText)findViewById(R.id.EmailForgotPasswordEditText);
        final Button mResetPasswordButton = (Button)findViewById(R.id.ForgotPasswordButton);
        //final ProgressBar mResetPasswordProgressBar = (ProgressBar)findViewById(R.id.resetPasswordProgressBar);

        mResetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String userEmail = mEmailEditText.getText().toString();

                if(isValidEntry(userEmail)){
                    mAuth.sendPasswordResetEmail(userEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ForgotPasswordPage.this,"Please check your mail for recovery",Toast.LENGTH_SHORT).show();
                                        //startActivity(new Intent(ForgotPassword.this,LogIn.class));
                                    }
                                    else{
                                        Toast.makeText(ForgotPasswordPage.this,"Oops! Some Error Occurred....Please Try Again",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }



    /*
     * Function to check if email field is not empty
     * */

    private boolean isValidEntry(String mail){
        if(mail!=null ){
            if(mail.indexOf('@')>=0){
                return true;
            }
            else{
                Toast.makeText(ForgotPasswordPage.this,"Invalid email",Toast.LENGTH_SHORT).show();
                return false;
            }

        }
        else{
            Toast.makeText(ForgotPasswordPage.this,"Please enter email",Toast.LENGTH_SHORT).show();
            return false;
        }
        }

    }

