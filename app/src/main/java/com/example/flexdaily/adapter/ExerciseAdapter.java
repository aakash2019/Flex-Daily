package com.example.flexdaily.adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flexdaily.R;
import com.example.flexdaily.model.Exercise;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private final List<Exercise> exerciseList;
    private final FirebaseFirestore firestore;

    private final String userId;

    public ExerciseAdapter(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
        this.firestore = FirebaseFirestore.getInstance();
        this.userId = FirebaseAuth.getInstance().getCurrentUser() != null ? FirebaseAuth.getInstance().getCurrentUser().getUid() : null;
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
        holder.instructions.setText("Instructions: " + exercise.getInstructions());

        firestore.collection("users").document(userId)
            .collection("favorites")
            .document(exercise.getName())
            .get()
            .addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                holder.favoriteImage.setVisibility(View.GONE);
                holder.favouriteFilledImage.setVisibility(View.VISIBLE);
                } else {
                holder.favoriteImage.setVisibility(View.VISIBLE);
                holder.favouriteFilledImage.setVisibility(View.GONE);
                }
            })
            .addOnFailureListener(e -> {
                holder.favoriteImage.setVisibility(View.VISIBLE);
                holder.favouriteFilledImage.setVisibility(View.GONE);
                Log.d("ExerciseAdapter", "Error checking favorite status: " + e.getMessage());
            });

        holder.favoriteImage.setOnClickListener(v -> {
            holder.favoriteImage.setVisibility(View.GONE);
            holder.favouriteFilledImage.setVisibility(View.VISIBLE);
            if (addExerciseToFavorites(exercise)) {
            Toast.makeText(holder.itemView.getContext(), "Exercise added to favorites", Toast.LENGTH_SHORT).show();
            } else {
            Toast.makeText(holder.itemView.getContext(), "Failed to add exercise to favorites", Toast.LENGTH_SHORT).show();
            }
        });

        holder.favouriteFilledImage.setOnClickListener(v -> {
            holder.favoriteImage.setVisibility(View.VISIBLE);
            holder.favouriteFilledImage.setVisibility(View.GONE);
            if (removeExerciseFromFavorites(exercise)) {
            Toast.makeText(holder.itemView.getContext(), "Exercise removed from favorites", Toast.LENGTH_SHORT).show();
            } else {
            Toast.makeText(holder.itemView.getContext(), "Failed to remove exercise from favorites", Toast.LENGTH_SHORT).show();
            }
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

    private boolean addExerciseToFavorites(Exercise exercise) {
        if (userId == null) return false;


        Map<String, Object> favoriteExercise = new HashMap<>();
        favoriteExercise.put("name", exercise.getName());
        favoriteExercise.put("type", exercise.getType());
        favoriteExercise.put("muscle", exercise.getMuscle());
        favoriteExercise.put("equipment", exercise.getEquipment());
        favoriteExercise.put("difficulty", exercise.getDifficulty());
        favoriteExercise.put("instructions", exercise.getInstructions());

        Log.d("ExerciseAdapter", "UserID: " + userId);

        try{
            firestore.collection("users").document(userId)
                    .collection("favorites")
                    .document(exercise.getName())
                    .set(favoriteExercise)
                    .addOnSuccessListener(aVoid -> {
                        Log.d("ExerciseAdapter", "Exercise added to favorites successfully");
                    })
                    .addOnFailureListener(e -> Log.d("ExerciseAdapter", "Error uploading transaction: " + e.getMessage()));

            return true;
        }
        catch(Exception e){
            return false;
        }


    }


    private boolean removeExerciseFromFavorites(Exercise exercise) {
        if (userId == null) return false;


        try{
            firestore.collection("users").document(userId)
                    .collection("favorites")
                    .document(exercise.getName())
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Log.d("ExerciseAdapter", "Exercise deleted from favorites successfully");
                    })
                    .addOnFailureListener(e -> Log.d("ExerciseAdapter", "Error deleting transaction: " + e.getMessage()));

            return true;
        }
        catch(Exception e){
            return false;
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
