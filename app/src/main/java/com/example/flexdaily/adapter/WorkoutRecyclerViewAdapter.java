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
import com.example.flexdaily.model.Workout;

import java.util.ArrayList;

public class WorkoutRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Workout> workoutList = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public WorkoutRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_type_card_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public WorkoutRecyclerViewAdapter(Context context, ArrayList<Workout> workoutList) {
        this.workoutList = workoutList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutRecyclerViewAdapter.ViewHolder holder, int position) {
        Workout workout = workoutList.get(position);
        holder.workoutType.setText(workout.getName());

        holder.workoutType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExercisesActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("workoutOrMuscle", "type");
                bundle.putString("exerciseType", workout.getName());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView workoutType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutType = itemView.findViewById(R.id.workout_type);
        }
    }
}
