package com.example.egra2atapp.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.egra2atapp.MainActivity;
import com.example.egra2atapp.R;
import com.example.egra2atapp.app.App;
import com.example.egra2atapp.model.Ministry;
import com.example.egra2atapp.model.Sector;
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

public class SectorActivity extends AppCompatActivity {


    @BindView(R.id.sectorsRecyclerView)
    RecyclerView sectorsRecyclerView;
    @BindView(R.id.NoSectorsTV)
    TextView noSectorsTV;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_back)
    Button back;
    @BindView(R.id.reloader)
    ImageView loader;
    SectorsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sectors);
        ButterKnife.bind(this);
        App.setContext(this);
        toolbar_title.setText("القطاعات");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SectorActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loader.setVisibility(View.VISIBLE);
        Glide.with(SectorActivity.this).load(R.drawable.loader).into(loader);

        Bundle b = new Bundle();
        b = getIntent().getExtras();
        final String ministryName = b.getString("ministryName");

        final RecyclerView.LayoutManager manager = new LinearLayoutManager(SectorActivity.this);
        sectorsRecyclerView.setLayoutManager(manager);
        if (Utils.isOnline(SectorActivity.this)) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(getString(R.string.ministries)).child(ministryName);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Ministry ministry = dataSnapshot.getValue(Ministry.class);
                    if (ministry!=null && ministry.getSectors()!=null) {
                        loader.setVisibility(View.GONE);
                        List<Sector> sectors = new ArrayList<>(ministry.getSectors().values());
                        List<String> sectorsName = new ArrayList<>();
                        for (Sector sector : sectors){
                            sectorsName.add(sector.getSectorName());
                        }
                        SectorsAdapter adapter = new SectorsAdapter(sectorsName, ministry.getMinistryName());
                        sectorsRecyclerView.setAdapter(adapter);
                    }
                    else {
                        loader.setVisibility(View.GONE);
                        noSectorsTV.setVisibility(View.VISIBLE);
                        sectorsRecyclerView.setVisibility(View.GONE);
                        noSectorsTV.setText("لا يوجد قطاعات");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    loader.setVisibility(View.GONE);
                    noSectorsTV.setVisibility(View.VISIBLE);
                    sectorsRecyclerView.setVisibility(View.GONE);
                    noSectorsTV.setText("لا يوجد قطاعات");

                }
            });
        } else {
            loader.setVisibility(View.GONE);
            noSectorsTV.setVisibility(View.VISIBLE);
            sectorsRecyclerView.setVisibility(View.GONE);
            noSectorsTV.setText("لا يوجد اتصال بالانترنت");
        }
    }


    public void onBackPressed() {
        Intent intent = new Intent(SectorActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
