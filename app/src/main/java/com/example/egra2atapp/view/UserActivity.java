package com.example.egra2atapp.view;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import com.example.egra2atapp.R;
import com.example.egra2atapp.app.App;
import com.example.egra2atapp.model.Service;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_back)
    Button back;

    String ministryName, sectorName;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        App.setContext(this);

        Bundle b = new Bundle();
        b = getIntent().getExtras();
        service = (Service) b.getSerializable("service");
        ministryName = b.getString("ministryName");
        sectorName = b.getString("sectorName");

        if(service.getServiceName() != null) {
            toolbar_title.setText(service.getServiceName());
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, ServicesActivity.class);
                intent.putExtra("ministryName", ministryName);
                intent.putExtra("sectorName", sectorName);
                startActivity(intent);
                finish();
            }
        });
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        mViewPager.setCurrentItem(2);

    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                PlaceFragment fragment = new PlaceFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("service", (Serializable) service);
                fragment.setArguments(bundle);
                return fragment;
            }else{
                if(position == 1) {
                    AttachmentFragment fragment = new AttachmentFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("service", (Serializable) service);
                    fragment.setArguments(bundle);
                    return fragment;
                }else {
                    if (position == 2) {
                        Egra2atFragment fragment = new Egra2atFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("service", (Serializable) service);
                        fragment.setArguments(bundle);
                        return fragment;
                    }
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(UserActivity.this, ServicesActivity.class);
        intent.putExtra("ministryName", ministryName);
        intent.putExtra("sectorName", sectorName);
        startActivity(intent);
        finish();
    }
}
