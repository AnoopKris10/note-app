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
import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder> {


    private ArrayList<String> Semesterlist = new ArrayList<>();
    private Context mContext;

    public HomeRecyclerAdapter(Context context,ArrayList<String> Semesterlist){

        this.Semesterlist=Semesterlist;
        this.mContext= context;
    }


    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.homeviewholder,null);
        return new HomeViewHolder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {

        final String Mysemester = Semesterlist.get(position);
        holder.mSemesterName.setText(Semesterlist.get(position));
        holder.mSemesterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an intent next page !
                // pass extras like semester name , course name in intent to next page


                // snippet to retrieve all collections under selected
                // semester , add all the collections ( using model class)
                // query path should ../selectedSem/Subjects/ under documents
                // once the list is computed

                Bundle bundle = new Bundle();
                bundle.putString("Semester",Mysemester);

                //bundle.putString("Semester",sem.getSemester());

                Intent intent = new Intent(mContext,CourseDisplayPage.class);

                intent.putExtras(bundle);

                mContext.startActivity(intent);



            }
        });

    }

    @Override
    public int getItemCount() {

        return Semesterlist.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder{

        TextView mSemesterName;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            mSemesterName = itemView.findViewById(R.id.HomeSemTextView);
        }
    }
}
