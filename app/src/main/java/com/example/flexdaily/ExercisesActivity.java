package com.example.flexdaily;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ExercisesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;
    private List<Exercise> exerciseList = new ArrayList<>();
    private static final String API_URL = "https://api.api-ninjas.com/v1/exercises?muscle=biceps";
    private static final String API_KEY = "HHtkzEQk9usKBeGORj7BSw==6QTqvmtBxvs8JpwN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchExercises();
    }

    private void fetchExercises() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("X-Api-Key", API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
                });
                Log.e("API_ERROR", "Error fetching data", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) return;

                try {
                    String jsonData = response.body().string();
                    JSONArray jsonArray = new JSONArray(jsonData);
                    exerciseList.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        Exercise exercise = new Exercise(
                                item.getString("name"),
                                item.getString("type"),
                                item.getString("muscle"),
                                item.getString("equipment"),
                                item.getString("difficulty"),
                                item.getString("instructions")
                        );
                        exerciseList.add(exercise);
                    }

                    runOnUiThread(() -> {
                        adapter = new ExerciseAdapter(exerciseList);
                        recyclerView.setAdapter(adapter);
                    });

                } catch (Exception e) {
                    Log.e("JSON_ERROR", "Error parsing JSON", e);
                }
            }
        });
    }
}
