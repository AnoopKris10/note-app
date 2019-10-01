package com.example.notesrepo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class VerifiedProfileUpdater extends Thread{

    FirebaseUser user;
    FirebaseFirestore mFirestoreDB;
    FirebaseAuth mAuth;
    Activity mActivity;
    Context mContext;
    Map<String, Object> profileDetails = new HashMap<>();
    ProgressBar mProgressBar;
    FirebaseStorage mFirebaseStorage;


    VerifiedProfileUpdater(Context context, Activity activity, Map<String,Object> profileDetails, FirebaseUser user, FirebaseAuth auth, FirebaseFirestore db, FirebaseStorage mFirebaseStorage){
        this.mContext = context;
        this.profileDetails = profileDetails;
        this.user = user;
        this.mAuth = auth;
        this.mFirestoreDB = db;
        this.mActivity = activity;
        this.mFirebaseStorage = mFirebaseStorage;

        UploadDetails();
    }

    public void UploadDetails(){


        mFirestoreDB.collection("users")
                .document(user.getUid()).set(profileDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mContext,"Account created successfully",Toast.LENGTH_SHORT).show();
                        mContext.startActivity(new Intent(mContext,HomePage.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext,"Error ! Try after sometime",Toast.LENGTH_SHORT).show();
                       user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(!task.isSuccessful()){
                                            Toast.makeText(mContext,"Bug Report #1",Toast.LENGTH_SHORT).show();
                                       }
                                    }
                                });
                    }
                });


    }

}
