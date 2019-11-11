package com.example.notesrepo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.CourseViewHolder> {



    private ArrayList<String> CourseList = new ArrayList<>();
    private Context mContext;
    private Bundle bundle;

    public CourseRecyclerAdapter(Context context, ArrayList<String> CourseList, Bundle bundle){

        this.CourseList=CourseList;
        this.mContext= context;
        this.bundle = bundle;
    }


    @NonNull
    @Override
    public CourseRecyclerAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.courseviewholder,null);
        return new CourseRecyclerAdapter.CourseViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CourseRecyclerAdapter.CourseViewHolder holder, int position) {

        final String MyCourse = CourseList.get(position);
        holder.mCourseName.setText(CourseList.get(position));
        holder.mCourseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an intent next page !
                // pass extras like semester name , course name in intent to next page


                // snippet to retrieve all collections under selected
                // semester , add all the collections ( using model class)
                // query path should ../selectedSem/Subjects/ under documents
                // once the list is computed

                //Bundle bundle = new Bundle();

                bundle.putString("Course",MyCourse);

                Intent intent = new Intent(mContext,UploadDownloadPage.class);

                intent.putExtras(bundle);

                mContext.startActivity(intent);



            }
        });



    }

    @Override
    public int getItemCount() {
        return CourseList.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder{

        TextView mCourseName;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            mCourseName = itemView.findViewById(R.id.CourseTextView);
        }
    }
}
