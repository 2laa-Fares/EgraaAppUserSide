package com.example.egra2atapp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.egra2atapp.R;
import com.example.egra2atapp.app.App;
import com.example.egra2atapp.model.Service;
import com.example.egra2atapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Egra2atFragment extends Fragment {

    @BindView(R.id.nationalityTV)
    TextView nationality;
    @BindView(R.id.durationTV)
    TextView duration;
    @BindView(R.id.feesTV)
    TextView fees;
    @BindView(R.id.onlineServiceTV)
    TextView onlineService;
    @BindView(R.id.negotiabilityTV)
    TextView negotiability;
    @BindView(R.id.stepsRecyclerView)
    RecyclerView stepsRecyclerView;
    @BindView(R.id.NoStepsTV)
    TextView noStepsTV;
    @BindView(R.id.StepsTV)
    TextView stepsTV;
    Service service;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_egraa, container, false);
        ButterKnife.bind(this, view);

        service = (Service) getArguments().getSerializable(
                "service");

       // toolbar_title.setText(service.getServiceName());

        if(service.getNationality() != null)
            nationality.setText(service.getNationality());
        if(service.getDuration() != null)
            duration.setText(service.getDuration());
        fees.setText(service.getFees() + "ريال");
        onlineService.setText(service.getOnlineService() != null && service.getOnlineService().equals("نعم") ? "التقديم أونلاين متاح" : "التقديم أونلاين غير متاح" );
        negotiability.setText(service.getNegotiability() != null && service.getNegotiability().equals("نعم") ? "يمكن التفويض" : "لا يمكن التفويض" );

        RecyclerView.LayoutManager manager = new LinearLayoutManager(App.getContext());
        stepsRecyclerView.setLayoutManager(manager);
        if (Utils.isOnline(App.getContext())){
                            if (service.getServiceSteps() !=null){
                                List<String> steps = new ArrayList<>(service.getServiceSteps().values());
                                StepsAdapter adapter = new StepsAdapter(steps);
                                stepsRecyclerView.setAdapter(adapter);
                            }
                            else {
                                noStepsTV.setVisibility(View.VISIBLE);
                                stepsRecyclerView.setVisibility(View.GONE);
                                stepsTV.setVisibility(View.GONE);
                                noStepsTV.setText("لا يوجد خدمات متاحة");
                            }
        }else {
            noStepsTV.setVisibility(View.VISIBLE);
            stepsRecyclerView.setVisibility(View.GONE);
            stepsTV.setVisibility(View.GONE);
            noStepsTV.setText("لا يوجد اتصال بالانترنت");
        }



        return view;
    }
}

