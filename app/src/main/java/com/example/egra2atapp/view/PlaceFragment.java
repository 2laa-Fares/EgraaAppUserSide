package com.example.egra2atapp.view;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.egra2atapp.R;
import com.example.egra2atapp.app.App;
import com.example.egra2atapp.model.Service;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceFragment extends Fragment {

    @BindView(R.id.locationTV)
    TextView location;
    @BindView(R.id.webTV)
    TextView website;
    @BindView(R.id.from)
    TextView hourFrom;
    @BindView(R.id.to)
    TextView hourTo;
    Service service;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        ButterKnife.bind(this, view);

        service = (Service) getArguments().getSerializable(
                "service");

        if(service.getLocation() != null) {
            location.setText(service.getLocation());

            location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isGoogleMapsInstalled()) {
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("google.navigation:q=" + service.getLocation()));
                        //mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(intent);
                    } else {
                        Toast.makeText(App.getContext(), "لاستخدام خدمة تحديد الموقع وجب وجود خرائط جوجل على الهاتف", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        if(service.getWebsite() != null)
            website.setText(service.getWebsite());

        if(service.getWorkingHours() != null){
            String[] separated = service.getWorkingHours().split("-");
            hourFrom.setText(separated[0] + "ص");
            hourTo.setText(separated[1] + " م ");
        }

        return view;
    }

    public boolean isGoogleMapsInstalled()
    {
        try
        {
            ApplicationInfo info = App.getContext().getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0 );
            return true;
        }
        catch(PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }
}
