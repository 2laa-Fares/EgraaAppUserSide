package com.example.egra2atapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.egra2atapp.app.App;
import com.example.egra2atapp.model.Ministry;
import com.example.egra2atapp.utils.Utils;
import com.example.egra2atapp.view.MinistriesAdapter;
import com.example.egra2atapp.view.SectorActivity;
import com.example.egra2atapp.view.ServicesActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ministriesRecyclerView)
    RecyclerView ministriesRecyclerView;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_back)
    Button back;
    @BindView(R.id.noMinistriesTV)
    TextView noMinistriesTV;
    RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        App.setContext(this);
        toolbar_title.setText("الوزارات");
        back.setVisibility(View.GONE);

        Map<String, Integer> ministries = new HashMap<>();
        ministries.put("وزارة النقل", R.mipmap.nakl);
        ministries.put("وزارة الإتصالات وتقنية المعلومات", R.mipmap.etsalat);
        ministries.put("وزارة الإسكان", R.mipmap.eskan);
        ministries.put("وزارة الإعلام", R.mipmap.media);
        ministries.put("وزارة الإقتصاد والتخطيط", R.mipmap.economy);
        ministries.put("وزارة البيئة والمياه والزراعة", R.mipmap.environment);
        ministries.put("وزارة التجارة والاستثمار", R.mipmap.tegara);
        ministries.put("وزارة التعليم", R.mipmap.education);
        ministries.put("وزارة الثقافة", R.mipmap.culture);
        ministries.put("وزارة الحج والعمرة", R.mipmap.haj);
        ministries.put("وزارة الحرس الوطنى", R.mipmap.haraswatany);
        ministries.put("وزارة الخارجية", R.mipmap.khargia);
        ministries.put("وزارة الخدمة المدنية", R.mipmap.khdmamadnia);
        ministries.put("وزارة الداخلية", R.mipmap.dakhlya);
        ministries.put("وزارة الدفاع", R.mipmap.deffaa);
        ministries.put("وزارة الشئون الإسلامية والدعوة والارشاء", R.mipmap.dawaa);
        ministries.put("وزارة الشئون البلدية والقروية", R.mipmap.baladya);
        ministries.put("وزارة الصحة", R.mipmap.health);
        ministries.put("وزارة الطاقة والصناعة والثروة المعدنية", R.mipmap.taka);
        ministries.put("وزارة العدل", R.mipmap.adl);
        ministries.put("وزارة العمل والتنمية الاجتماعية", R.mipmap.alamal);
        ministries.put("وزارة المالية", R.mipmap.malia);

        if (checkIsTablet())
        {
            manager = new GridLayoutManager(MainActivity.this, 4);
        }
        else {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // In landscape
                manager = new GridLayoutManager(MainActivity.this, 4);
            } else {
                // In portrait
                manager = new GridLayoutManager(MainActivity.this, 2);
            }
        }
        ministriesRecyclerView.setLayoutManager(manager);
        MinistriesAdapter adapter = new MinistriesAdapter(ministries);
        ministriesRecyclerView.setAdapter(adapter);

        /*final RecyclerView.LayoutManager manager = new LinearLayoutManager(MainActivity.this);
        ministriesRecyclerView.setLayoutManager(manager);

        loader.setVisibility(View.VISIBLE);
        Glide.with(MainActivity.this).load(R.drawable.loader).into(loader);


        if (Utils.isOnline(MainActivity.this)) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(getString(R.string.ministries));
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<String> ministries = new ArrayList<>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Ministry ministry = dataSnapshot1.getValue(Ministry.class);
                        ministries.add(ministry.getMinistryName());
                    }
                    if (ministries.size() != 0) {
                        loader.setVisibility(View.GONE);
                        MinistriesAdapter adapter = new MinistriesAdapter(ministries);
                        ministriesRecyclerView.setAdapter(adapter);
                    } else {
                        loader.setVisibility(View.GONE);
                        noMinistriesTV.setVisibility(View.VISIBLE);
                        ministriesRecyclerView.setVisibility(View.GONE);
                        noMinistriesTV.setText("لا يوجد وزارات");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    loader.setVisibility(View.GONE);
                    noMinistriesTV.setVisibility(View.VISIBLE);
                    ministriesRecyclerView.setVisibility(View.GONE);
                    noMinistriesTV.setText("لا يوجد وزارات");
                }
            });
        }else {
            loader.setVisibility(View.GONE);
            noMinistriesTV.setVisibility(View.VISIBLE);
            ministriesRecyclerView.setVisibility(View.GONE);
            noMinistriesTV.setText("لا يوجد اتصال بالانترنت");
        }*/
    }


    public void onBackPressed() {
        finish();
    }

    private boolean checkIsTablet() {
        Display display = MainActivity.this.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        float widthInches = metrics.widthPixels / metrics.xdpi;
        float heightInches = metrics.heightPixels / metrics.ydpi;
        double diagonalInches = Math.sqrt(Math.pow(widthInches, 2) + Math.pow(heightInches, 2));
        return diagonalInches >= 7.0;
    }
}
