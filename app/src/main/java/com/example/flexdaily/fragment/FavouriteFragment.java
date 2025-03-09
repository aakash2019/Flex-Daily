package com.example.flexdaily.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flexdaily.R;
import com.example.flexdaily.adapter.FavouriteAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {
    private FirebaseFirestore db;
    private ArrayList<String> favouriteExercises = new ArrayList<>();
    private FavouriteAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    public static FavouriteFragment newInstance(String param1, String param2) {
        FavouriteFragment fragment = new FavouriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        fetchFavouriteExercises();
        adapter = new FavouriteAdapter(favouriteExercises);
        recyclerView = view.findViewById(R.id.recyclerViewFavouriteExercises);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void fetchFavouriteExercises() {
        db = FirebaseFirestore.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("users").document(userID).collection("favorites")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        favouriteExercises.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            favouriteExercises.add(document.getId());
                        }
                    } else {
                        // Handle the error
                    }
                });
    }
}