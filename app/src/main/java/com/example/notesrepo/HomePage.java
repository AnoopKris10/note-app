package com.example.notesrepo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<String> Semesterlist = new ArrayList<>();


    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mAuth = FirebaseAuth.getInstance();

        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("HOME");
        actionBar.setDisplayHomeAsUpEnabled(false);

        mDrawerLayout = findViewById(R.id.drawerlayout);
        mNavigationView = findViewById(R.id.navigationview);
        mRecyclerView = findViewById(R.id.HomeRecyclerView);


        final FirebaseUser user = mAuth.getCurrentUser();

        FirebaseFirestore mFirestoreDB = FirebaseFirestore.getInstance();

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                switch (menuItem.getItemId()) {

                    case R.id.navlogout:
                        menuItem.setChecked(true);
                        mAuth.getInstance().signOut();
                        startActivity(new Intent(HomePage.this, LoginPage.class));
                        finish();
                        return true;


                }

                return false;

            }


        });


        final RecyclerView SemesterHomeRecyclerView = (RecyclerView)findViewById(R.id.HomeRecyclerView);
        SemesterHomeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SemesterHomeRecyclerView.setHasFixedSize(true);


        CollectionReference mCollectionReference = mFirestoreDB.collection("Notes");
        mCollectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){

                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()) {

                                String SemString = documentSnapshot.getId();
                                Semesterlist.add(SemString);
                            }
                                HomeRecyclerAdapter mHomeRecyclerAdapter = new HomeRecyclerAdapter(HomePage.this,Semesterlist);
                                SemesterHomeRecyclerView.setAdapter(mHomeRecyclerAdapter);
                            }

                        }
                    });



    }
}







