package com.example.flexdaily.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flexdaily.R;
import com.example.flexdaily.adapter.MuscleRecyclerViewAdapter;
import com.example.flexdaily.model.Muscle;

import java.util.ArrayList;


public class MuscleTypeFragment extends Fragment {

    ArrayList<Muscle> muscleList = new ArrayList<>();

    public MuscleTypeFragment() {
    }
    public static MuscleTypeFragment newInstance(String param1, String param2) {
        MuscleTypeFragment fragment = new MuscleTypeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_muscle_type, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.muscle_type_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        muscleList.add(new Muscle("Abdominals"));
        muscleList.add(new Muscle("Abductors"));
        muscleList.add(new Muscle("Adductors"));
        muscleList.add(new Muscle("Calves"));
        muscleList.add(new Muscle("Chest"));
        muscleList.add(new Muscle("Forearms"));
        muscleList.add(new Muscle("Glutes"));
        muscleList.add(new Muscle("Hamstrings"));
        muscleList.add(new Muscle("Lats"));
        muscleList.add(new Muscle("Lower Back"));
        muscleList.add(new Muscle("Middle Back"));
        muscleList.add(new Muscle("Neck"));
        muscleList.add(new Muscle("Quadriceps"));
        muscleList.add(new Muscle("Traps"));
        muscleList.add(new Muscle("Triceps"));

        MuscleRecyclerViewAdapter adapter = new MuscleRecyclerViewAdapter(getContext(), muscleList);
        recyclerView.setAdapter(adapter);

        return view;

    }
}