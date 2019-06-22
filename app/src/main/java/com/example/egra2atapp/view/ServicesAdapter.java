package com.example.egra2atapp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.egra2atapp.R;
import com.example.egra2atapp.app.App;
import com.example.egra2atapp.model.Service;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder>{

    List<Service> services;
    String ministryName;
    String sectorName;
    private Context context = App.getContext();

    public ServicesAdapter(List<Service> services, String ministryName, String sectorName){
        this.services = services;
        this.ministryName = ministryName;
        this.sectorName = sectorName;
    }

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.service_item, viewGroup, false);
        return new ServicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServicesViewHolder holder, final int i) {
        holder.serviceNameTV.setText("  " +  services.get(i).getServiceName() + "  ");
        holder.seeService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserActivity.class);
                intent.putExtra("service", (Serializable) services.get(i));
                intent.putExtra("ministryName", ministryName);
                intent.putExtra("sectorName", sectorName);
                App.getContext().startActivity(intent);
                ((Activity) context).finish();
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserActivity.class);
                intent.putExtra("service", (Serializable) services.get(i));
                intent.putExtra("ministryName", ministryName);
                intent.putExtra("sectorName", sectorName);
                App.getContext().startActivity(intent);
                ((Activity) context).finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        if (services!=null)
            return services.size();
        return 0;
    }

    public class ServicesViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.serviceName)
        TextView serviceNameTV;
        @BindView(R.id.showService)
        Button seeService;
        @BindView(R.id.serviceItemCardView)
        CardView cardView;
        public ServicesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
