package com.example.egra2atapp.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.egra2atapp.R;
import com.example.egra2atapp.app.App;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder>{

    List<String> steps;
    private Context context = App.getContext();

    public StepsAdapter(List<String> steps){
        this.steps = steps;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.steps_item, viewGroup, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StepsViewHolder holder, final int i) {
        holder.stepTV.setText("  " +  steps.get(i) + "  ");
        holder.stepNo.setText(String.valueOf(i+1));
    }

    @Override
    public int getItemCount() {
        if (steps!=null)
            return steps.size();
        return 0;
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.stepName)
        TextView stepTV;
        @BindView(R.id.stepNo)
        TextView stepNo;
        public StepsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
