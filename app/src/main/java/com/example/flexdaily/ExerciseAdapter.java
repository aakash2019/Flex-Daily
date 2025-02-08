package com.example.flexdaily;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private List<Exercise> exerciseList;

    public ExerciseAdapter(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.name.setText(exercise.getName());
        holder.equipment.setText("Equipment: " + exercise.getEquipment());
        holder.difficulty.setText("Difficulty: " + exercise.getDifficulty());
        holder.instructions.setText(exercise.getInstructions());
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView name, equipment, difficulty, instructions;

        public ExerciseViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tvName);
            equipment = view.findViewById(R.id.tvEquipment);
            difficulty = view.findViewById(R.id.tvDifficulty);
            instructions = view.findViewById(R.id.tvInstructions);
        }
    }
}

