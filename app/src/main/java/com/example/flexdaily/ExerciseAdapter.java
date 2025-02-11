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
        holder.type.setText("Type: " + exercise.getType());
        holder.muscle.setText("Muscle: " + exercise.getMuscle());
        holder.equipment.setText("Equipment: " + exercise.getEquipment());
        holder.difficulty.setText("Difficulty: " + exercise.getDifficulty());
        holder.instructions.setText(exercise.getInstructions());
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView name, type, muscle, equipment, difficulty, instructions;

        public ExerciseViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tvName);
            type = view.findViewById(R.id.tvType);
            muscle = view.findViewById(R.id.tvMuscle);
            equipment = view.findViewById(R.id.tvEquipment);
            difficulty = view.findViewById(R.id.tvDifficulty);
            instructions = view.findViewById(R.id.tvInstructions);
        }
    }
}

