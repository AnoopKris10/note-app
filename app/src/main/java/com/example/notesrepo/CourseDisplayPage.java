package com.example.notesrepo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

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

public class CourseDisplayPage extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<String> Courselist = new ArrayList<>();

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_display_page);

        mAuth = FirebaseAuth.getInstance();

        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("COURSES");
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = findViewById(R.id.drawerlayout2);
        mNavigationView = findViewById(R.id.navigationview2);
        mRecyclerView = findViewById(R.id.CourseRecyclerView);


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
                        startActivity(new Intent(CourseDisplayPage.this, LoginPage.class));
                        finish();
                        return true;


                }

                return false;

            }


        });

        final Bundle bundle = this.getIntent().getExtras();
        String selectedSemester = bundle.getString("Semester");

        final RecyclerView CourseRecyclerView = (RecyclerView)findViewById(R.id.CourseRecyclerView);
        CourseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CourseRecyclerView.setHasFixedSize(true);

        CollectionReference mCollectionReference = mFirestoreDB.collection("Notes")
                .document(selectedSemester)
                .collection("Subjects");
        mCollectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){

                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()) {

                                String CourseString = documentSnapshot.getId();
                                Courselist.add(CourseString);
                            }
                            CourseRecyclerAdapter mCourseRecyclerAdapter = new CourseRecyclerAdapter(CourseDisplayPage.this,Courselist,bundle);
                            CourseRecyclerView.setAdapter(mCourseRecyclerAdapter);
                        }

                    }
                });

    }
}
