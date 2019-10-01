package com.example.notesrepo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class UserDetailsInput extends AppCompatActivity {


    boolean validNumber = false;
    boolean otpRecieved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_input);

        final EditText mFullNameEditText = (EditText)findViewById(R.id.FullNameEditText);
        final EditText mMobileNumberEditText = (EditText)findViewById(R.id.MobilenoEditText);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final FirebaseFirestore mFirestoreDB = FirebaseFirestore.getInstance();
        final FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();


        final Button mDoneButton = (Button)findViewById(R.id.DoneButton);

        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String fullName = mFullNameEditText.getText().toString();
                String mobile = mMobileNumberEditText.getText().toString();

                // Running  the inputs on some verification functions

                if(isValidFields(fullName,mobile)){
                    if(isValidMobileNumber(mobile)){


                        Map<String,Object> profileDetails = new HashMap<>();
                        profileDetails.put("Full Name",fullName);
                        profileDetails.put("Email",user.getEmail());
                        profileDetails.put("Mobile Number",mobile);

                        VerifiedProfileUpdater mVerifiedProfileUpdater = new VerifiedProfileUpdater(UserDetailsInput.this, UserDetailsInput.this, profileDetails, user, mAuth,mFirestoreDB,mFirebaseStorage);
                        mVerifiedProfileUpdater.start();

                    }
                }


            }
        });
    }

    /*
    * fn to check if mobile digits=10
    *
    * */
    private boolean isValidMobileNumber(String mobileNumber){

        if(mobileNumber.length() == 10){

            boolean numeric = true;

            numeric = mobileNumber.matches("-?\\d+(\\.\\d+)?");

            if(numeric){
                return true;
            }else{
                Toast.makeText(UserDetailsInput.this,"Mobile number contains only numbers from 0-9",Toast.LENGTH_SHORT).show();
                return  false;
            }

        }else
        {
            Toast.makeText(UserDetailsInput.this,"Mobile Number should be of 10 digits",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    /*
     * Function to check if all the fields are not empty
     * */
    private boolean isValidFields(String name,String mobileNumber){

        if(name != ""&& mobileNumber != ""){
            return true;
        }
        else{
            Toast.makeText(UserDetailsInput.this,"You cannot leave the fields empty",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    }

