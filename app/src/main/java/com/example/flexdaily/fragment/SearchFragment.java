package com.example.flexdaily.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flexdaily.R;
import com.example.flexdaily.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {

    private EditText etSearch;
    private RecyclerView recyclerViewExercises;
    private SearchAdapter searchAdapter;
    private List<String> exerciseList, filteredList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        etSearch = view.findViewById(R.id.etSearch);
        recyclerViewExercises = view.findViewById(R.id.recyclerViewSearchExercises);
        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(getContext()));

        // List of exercise categories
        exerciseList = Arrays.asList(
                "Cardio", "Olympic Weightlifting", "Powerlifting", "Strength", "Stretching", "Strongman",
                "Abdominals", "Abductors", "Adductors", "Biceps", "Calves", "Chest", "Forearms", "Glutes",
                "Hamstrings", "Lats", "Lower Back", "Middle Back", "Neck", "Quadriceps", "Traps", "Triceps"
        );

        filteredList = new ArrayList<>(exerciseList);
        searchAdapter = new SearchAdapter(getContext(), filteredList);
        recyclerViewExercises.setAdapter(searchAdapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterExercises(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void filterExercises(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(exerciseList);
        } else {
            for (String exercise : exerciseList) {
                if (exercise.toLowerCase().startsWith(query)) {
                    filteredList.add(exercise);
                }
            }
        }
        searchAdapter.notifyDataSetChanged();
    }
}
