package com.example.flexdaily.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flexdaily.R;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {
    private final ArrayList<String> favouriteExercises;

    @NonNull
    @Override
    public FavouriteAdapter.FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite_exercise, parent, false);
        return new FavouriteViewHolder(view);
    }
    public FavouriteAdapter(ArrayList<String> favouriteExercises) {
        this.favouriteExercises = favouriteExercises;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.FavouriteViewHolder holder, int position) {
        String exerciseName;
        if (favouriteExercises.isEmpty()) {
            exerciseName = "No favorite exercise";
        } else {
            exerciseName = favouriteExercises.get(position);
        }
        holder.name.setText(exerciseName);
    }

    @Override
    public int getItemCount() {
        return favouriteExercises.isEmpty() ? 1 : favouriteExercises.size();
    }

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvExercise);
        }
    }
}
