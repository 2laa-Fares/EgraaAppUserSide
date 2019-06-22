package com.example.egra2atapp.view;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.egra2atapp.R;
import com.example.egra2atapp.app.App;
import com.example.egra2atapp.model.Service;
import com.example.egra2atapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttachmentFragment extends Fragment {

    Service service;

    @BindView(R.id.attachmentsRecyclerView)
    RecyclerView attachmentsRecyclerView;
    @BindView(R.id.NoAttachmentsTV)
    TextView noAttachmentsTV;
    @BindView(R.id.reloader)
    ImageView loader;

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attachment, container, false);
        ButterKnife.bind(this, view);

        service = (Service) getArguments().getSerializable(
                "service");


        if (ContextCompat.checkSelfPermission(App.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions((Activity) App.getContext(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }

        RecyclerView.LayoutManager manager = new LinearLayoutManager(App.getContext());
        attachmentsRecyclerView.setLayoutManager(manager);
        if (Utils.isOnline(App.getContext())){
            if (service.getServiceSteps() !=null){
                List<String> attachments = new ArrayList<>(service.getFiles().values());
                AttachmentsAdapter adapter = new AttachmentsAdapter(attachments, loader);
                attachmentsRecyclerView.setAdapter(adapter);
            }
            else {
                noAttachmentsTV.setVisibility(View.VISIBLE);
                attachmentsRecyclerView.setVisibility(View.GONE);
                noAttachmentsTV.setText("لا يوجد مرفقات متاحة");
            }
        }else {
            noAttachmentsTV.setVisibility(View.VISIBLE);
            attachmentsRecyclerView.setVisibility(View.GONE);
            noAttachmentsTV.setText("لا يوجد اتصال بالانترنت");
        }

        return view;
    }
}
