package com.example.flexdaily.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flexdaily.R;
import com.example.flexdaily.adapter.WorkoutRecyclerViewAdapter;
import com.example.flexdaily.model.Workout;

import java.util.ArrayList;


public class WorkoutTypeFragment extends Fragment {

    ArrayList<Workout> workoutList = new ArrayList<>();

    public WorkoutTypeFragment() {
    }

    public static WorkoutTypeFragment newInstance(String param1, String param2) {
        WorkoutTypeFragment fragment = new WorkoutTypeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_type, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.workout_type_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        workoutList.add(new Workout("Cardio"));
        workoutList.add(new Workout("Olympic Weightlifting"));
        workoutList.add(new Workout("Plyometrics"));
        workoutList.add(new Workout("Powerlifting"));
        workoutList.add(new Workout("Strength"));
        workoutList.add(new Workout("Stretching"));
        workoutList.add(new Workout("Strongman"));

        WorkoutRecyclerViewAdapter adapter = new WorkoutRecyclerViewAdapter(getContext(), workoutList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}