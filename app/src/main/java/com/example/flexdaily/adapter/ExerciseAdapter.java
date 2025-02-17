package com.example.flexdaily.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flexdaily.R;
import com.example.flexdaily.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private final List<Exercise> exerciseList;

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

        String name = exercise.getName();
        String type = "Type: " + exercise.getType();
        String muscle = "Muscle: " + exercise.getMuscle();
        String equipment = "Equipment: " + exercise.getEquipment();
        String difficulty = "Difficulty: " + exercise.getDifficulty();
        String instructions = "Instructions: " + exercise.getInstructions();

        holder.name.setText(name);
        holder.type.setText(type);
        holder.muscle.setText(muscle);
        holder.equipment.setText(equipment);
        holder.difficulty.setText(difficulty);
        holder.instructions.setText(instructions);

        holder.favoriteImage.setOnClickListener(v -> {
            holder.favoriteImage.setVisibility(View.GONE);
            holder.favouriteFilledImage.setVisibility(View.VISIBLE);
        });

        holder.favouriteFilledImage.setOnClickListener(v -> {
            holder.favoriteImage.setVisibility(View.VISIBLE);
            holder.favouriteFilledImage.setVisibility(View.GONE);
        });

        holder.itemView.setOnClickListener(v -> showExercisePopup(holder.itemView.getContext(), exercise));
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView name, type, muscle, equipment, difficulty, instructions;
        ImageView favoriteImage, favouriteFilledImage;

        public ExerciseViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tvName);
            type = view.findViewById(R.id.tvType);
            muscle = view.findViewById(R.id.tvMuscle);
            equipment = view.findViewById(R.id.tvEquipment);
            difficulty = view.findViewById(R.id.tvDifficulty);
            instructions = view.findViewById(R.id.tvInstructions);
            favoriteImage = view.findViewById(R.id.favoriteImage);
            favouriteFilledImage = view.findViewById(R.id.favouriteFilledImage);
        }
    }

    private void showExercisePopup(Context context, Exercise exercise) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_exercise_details);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);


        TextView tvPopupTitle = dialog.findViewById(R.id.tvPopupTitle);
        ImageView imageClose = dialog.findViewById(R.id.imageClose);
        tvPopupTitle.setText(exercise.getName());

        List<String> details = new ArrayList<>();
        details.add("Type: " + exercise.getType());
        details.add("Muscle: " + exercise.getMuscle());
        details.add("Equipment: " + exercise.getEquipment());
        details.add("Difficulty: " + exercise.getDifficulty());
        details.add("Instructions: " + exercise.getInstructions());

        ListView listViewExerciseDetails = dialog.findViewById(R.id.listViewExerciseDetails);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, details);
        listViewExerciseDetails.setAdapter(adapter);

        imageClose.setOnClickListener(v -> dialog.dismiss());

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = 0.7f;
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }

        dialog.show();
    }

}

