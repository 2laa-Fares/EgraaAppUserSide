package com.example.egra2atapp.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.egra2atapp.R;
import com.example.egra2atapp.app.App;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttachmentsAdapter extends RecyclerView.Adapter<AttachmentsAdapter.AttachmentsViewHolder>{

    List<String> attachments;
    ImageView image;

    StorageReference mStorageRef;
    private Context context = App.getContext();

    public AttachmentsAdapter(List<String> attachments, ImageView image){
        this.attachments = attachments;
        this.image = image;
    }

    @NonNull
    @Override
    public AttachmentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.attachment_item, viewGroup, false);
        return new AttachmentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AttachmentsViewHolder holder, final int i) {
        holder.attachmentName.setText("  " +  attachments.get(i) + "  ");

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    image.setVisibility(View.VISIBLE);
                    Glide.with(App.getContext()).load(R.drawable.loader).into(image);

                    mStorageRef = FirebaseStorage.getInstance().getReference();
                    StorageReference riversRef = mStorageRef.child(attachments.get(i));

                    //String root = Environment.getExternalStorageDirectory().toString();
                    File localFile = getStorageDir(attachments.get(i));

                    riversRef.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    image.setVisibility(View.GONE);
                                    Toast.makeText(App.getContext(), "لقد تم تنزيل الملف بنجاح", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            image.setVisibility(View.GONE);
                            Toast.makeText(App.getContext(), "تعذر تنزيل الملف من فضلك حاول بوقت آخر", Toast.LENGTH_LONG).show();
                        }
                    });
                }catch (Exception e){
                    image.setVisibility(View.GONE);
                    Toast.makeText(App.getContext(), "تعذر تنزيل الملف من فضلك حاول بوقت آخر", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (attachments!=null)
            return attachments.size();
        return 0;
    }

    public class AttachmentsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.attachmentName)
        TextView attachmentName;
        @BindView(R.id.downloadAttachment)
        Button download;
        public AttachmentsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public File getStorageDir(String fileName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), fileName);
        //if (!file.mkdirs()) {
          //  Log.e("Attachments", "Directory not created");
        //}
        return file;
    }
}
