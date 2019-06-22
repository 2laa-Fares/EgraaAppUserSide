package com.example.egra2atapp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.egra2atapp.R;
import com.example.egra2atapp.app.App;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SectorsAdapter extends RecyclerView.Adapter<SectorsAdapter.SectorsViewHolder> {

    private List<String> sectors;
    private String ministryName;
    private Context context = App.getContext();
    public SectorsAdapter(List<String> sectors, String ministryName) {
        this.sectors = sectors;
        this.ministryName = ministryName;
    }

    public void AddSector(String sectorName){
        sectors.add(sectors.size(), sectorName);
        notifyItemInserted(sectors.size()-1);
    }
    @NonNull
    @Override
    public SectorsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.sector_item, viewGroup, false);
        return new SectorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SectorsViewHolder holder, int i) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(context.getString(R.string.ministries));
        holder.sectorNameTV.setText("  " + sectors.get(i) + "  " );
        holder.SectorItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ServicesActivity.class);
                intent.putExtra("ministryName", ministryName);
                intent.putExtra("sectorName", sectors.get(holder.getAdapterPosition()));
                App.getContext().startActivity(intent);
                ((Activity) context).finish();
            }
        });


    }

    @Override
    public int getItemCount() {
        if(sectors!=null)
            return sectors.size();
        return 0;
    }

    public class SectorsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.sectorName)
        TextView sectorNameTV;
        @BindView(R.id.sectorItemCardView)
        CardView SectorItemCardView;
        public SectorsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
