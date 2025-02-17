package com.example.flexdaily.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flexdaily.R;
import com.example.flexdaily.activity.ExercisesActivity;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private final List<String> exerciseList;
    private final Context context;

    private final String[] muscles = {"abdominals", "abductors", "adductors",
            "biceps", "calves", "chest", "forearms", "glutes", "hamstrings", "lats",
            "lower_back", "middle_back", "neck", "quadriceps", "traps", "triceps"
    };

    public SearchAdapter(Context context, List<String> exerciseList) {
        this.exerciseList = exerciseList;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_exercise, parent, false);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        holder.searchName.setText(exerciseList.get(position));

        holder.searchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exercise = findExercise(holder.searchName.getText().toString());
                String exerciseType = findExerciseType(exercise);

                Intent intent = new Intent(context, ExercisesActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("workoutOrMuscle", exerciseType);
                bundle.putString("exerciseType", exercise);

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView searchName;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            searchName = itemView.findViewById(R.id.tvSearchName);
        }
    }

    public String findExercise(String exerciseType){
        if(exerciseType.equals("Lower Back")){
            return "lower_back";
        }
        else if(exerciseType.equals("Middle Back")){
            return "middle_back";
        }
        else if(exerciseType.equals("Olympic Weightlifting")){
            return "olympic_weightlifting";
        }
        else{
            return exerciseType.toLowerCase();
        }
    }

    public String findExerciseType(String exercise){
        for(int i = 0; i < muscles.length; i++){
            if(muscles[i].equals(exercise)){
                return "muscle";
            }
        }
        return "type";
    }
}
