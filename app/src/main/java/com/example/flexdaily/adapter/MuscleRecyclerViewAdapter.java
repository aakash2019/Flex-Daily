package com.example.flexdaily.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flexdaily.R;
import com.example.flexdaily.activity.ExercisesActivity;
import com.example.flexdaily.model.Muscle;

import java.util.ArrayList;

public class MuscleRecyclerViewAdapter extends RecyclerView.Adapter<MuscleRecyclerViewAdapter.ViewHolder> {
    ArrayList<Muscle> muscleList = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public MuscleRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.muscle_type_card_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public MuscleRecyclerViewAdapter(Context context, ArrayList<Muscle> muscleList) {
        this.muscleList = muscleList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull MuscleRecyclerViewAdapter.ViewHolder holder, int position) {
        Muscle muscle = muscleList.get(position);
        holder.muscle.setText(muscle.getMuscle());

        holder.muscle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExercisesActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("workoutOrMuscle", "muscle");
                bundle.putString("exerciseType", muscle.getMuscle());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return muscleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView muscle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            muscle = itemView.findViewById(R.id.muscle_type);
        }
    }
}
