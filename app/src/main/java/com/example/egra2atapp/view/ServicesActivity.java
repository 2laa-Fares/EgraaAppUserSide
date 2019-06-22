package com.example.egra2atapp.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.egra2atapp.MainActivity;
import com.example.egra2atapp.R;
import com.example.egra2atapp.app.App;
import com.example.egra2atapp.model.Sector;
import com.example.egra2atapp.model.Service;
import com.example.egra2atapp.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServicesActivity extends AppCompatActivity {

    @BindView(R.id.servicesRecyclerView)
    RecyclerView servicesRecyclerView;
    @BindView(R.id.NoServicesTV)
    TextView noServicesTV;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_back)
    Button back;
    String ministryName, sectorName;
    @BindView(R.id.reloader)
    ImageView loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        ButterKnife.bind(this);
        App.setContext(this);
        toolbar_title.setText("الخدمات");


        Bundle b = new Bundle();
        b = getIntent().getExtras();
        ministryName = b.getString("ministryName");
        sectorName = b.getString("sectorName");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServicesActivity.this, SectorActivity.class);
                intent.putExtra("ministryName", ministryName);
                startActivity(intent);
                finish();
            }
        });

        loader.setVisibility(View.VISIBLE);
        Glide.with(ServicesActivity.this).load(R.drawable.loader).into(loader);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(ServicesActivity.this);
        servicesRecyclerView.setLayoutManager(manager);
        if (Utils.isOnline(ServicesActivity.this)){
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(getString(R.string.ministries)).child(ministryName);
            myRef.child(getString(R.string.sectors)).child(sectorName)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Sector sector = dataSnapshot.getValue(Sector.class);
                            if (sector!=null && sector.getServices()!=null){
                                loader.setVisibility(View.GONE);
                                List<Service> services = new ArrayList<>(sector.getServices().values());
                                ServicesAdapter adapter = new ServicesAdapter(services, ministryName, sectorName);
                                servicesRecyclerView.setAdapter(adapter);
                            }
                            else {
                                loader.setVisibility(View.GONE);
                                noServicesTV.setVisibility(View.VISIBLE);
                                servicesRecyclerView.setVisibility(View.GONE);
                                noServicesTV.setText("لا يوجد خدمات متاحة");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            loader.setVisibility(View.GONE);
                            noServicesTV.setVisibility(View.VISIBLE);
                            servicesRecyclerView.setVisibility(View.GONE);
                            noServicesTV.setText("لا يوجد خدمات متاحة");
                        }
                    });
        }else {
            loader.setVisibility(View.GONE);
            noServicesTV.setVisibility(View.VISIBLE);
            servicesRecyclerView.setVisibility(View.GONE);
            noServicesTV.setText("لا يوجد اتصال بالانترنت");
        }
    }


    public void onBackPressed() {
        Intent intent = new Intent(ServicesActivity.this, SectorActivity.class);
        intent.putExtra("ministryName", ministryName);
        startActivity(intent);
        finish();
    }
}
