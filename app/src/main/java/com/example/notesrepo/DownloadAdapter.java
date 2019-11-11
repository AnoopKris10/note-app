package com.example.notesrepo;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.DownloadViewHolder> {

    private Context mContext;
    private ArrayList<DownloadModel> downloadModels;

    public DownloadAdapter(Context mContext,ArrayList<DownloadModel> downloadModels) {
        this.mContext = mContext;
        this.downloadModels = downloadModels;
    }


    @NonNull
    @Override
    public DownloadAdapter.DownloadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.notesviewholder,null);
        return new DownloadAdapter.DownloadViewHolder(view);
    }

    @Override
    public void  onBindViewHolder(@NonNull final DownloadViewHolder holder, final int position) {

        holder.mDocName.setText(downloadModels.get(position).getDocName());
        holder.mUri.setText(downloadModels.get(position).getUri());
        holder.mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadFile(mContext,downloadModels.get(position).getDocName(),"pdf", DIRECTORY_DOWNLOADS, downloadModels.get(position).getUri());
            }
        });

    }



    @Override
    public int getItemCount() {
        return downloadModels.size();
    }


    public class DownloadViewHolder extends RecyclerView.ViewHolder {

        TextView mDocName;
        TextView mUri;
        Button mDownload;

        public DownloadViewHolder(@NonNull View itemView) {
            super(itemView);
            mDocName = itemView.findViewById(R.id.DocNameTextView);
            mDownload = itemView.findViewById(R.id.DownloadButton);
            mUri = itemView.findViewById(R.id.UriTextView);


        }



    }

    void downloadFile(Context context,String filename,String fileExtension ,String destinationDirectory,String url){


        DownloadManager downloadManager = (DownloadManager) context
                .getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,filename+fileExtension);

        downloadManager.enqueue(request);

    }

}

