package com.example.notesrepo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class UploadDownloadPage extends AppCompatActivity{

    FirebaseFirestore db;
    RecyclerView mRecyclerView;
    ArrayList<DownloadModel> downloadModelArrayList = new ArrayList<>();
    DownloadAdapter downloadAdapter;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_download_page);

        setUpRV();
        setUpFB();
        dataFromFirebase();

    }
    private void dataFromFirebase() {

        final Bundle bundle = this.getIntent().getExtras();
        String selectedCourse = bundle.getString("Course");

        if(downloadModelArrayList.size() > 0)
            downloadModelArrayList.clear();

       CollectionReference collectionReference = db.collection("Notes")
                                    .document(bundle.getString("Semester"))
                                    .collection("Subjects")
                                    .document(selectedCourse)
                                    .collection("Files");
                                     collectionReference.get()

        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    for(QueryDocumentSnapshot documentSnapshot: task.getResult()) {

                        DownloadModel downloadModel = new DownloadModel(documentSnapshot.getString("Doc Name"),
                                documentSnapshot.getString("Uri"));
                        downloadModelArrayList.add(downloadModel);

                    }
                    DownloadAdapter mDownloadAdapter = new DownloadAdapter(UploadDownloadPage.this,downloadModelArrayList);
                    mRecyclerView.setAdapter(mDownloadAdapter);
                }

            }
        });

    }
    private void setUpFB() {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
    }

    private void setUpRV() {

   mRecyclerView=findViewById(R.id.DownloadRecyclerView);
   mRecyclerView.setHasFixedSize(true);
   mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


}
