package com.example.flexdaily.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flexdaily.R;
import com.example.flexdaily.adapter.ExerciseAdapter;
import com.example.flexdaily.model.Exercise;

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
    private TextView exerciseTitle;
    private String workoutOrMuscle = "";
    private String exerciseType = "";
    private static final String API_KEY = "HHtkzEQk9usKBeGORj7BSw==6QTqvmtBxvs8JpwN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        recyclerView = findViewById(R.id.exerciseRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        exerciseTitle = findViewById(R.id.exerciseTitle);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            exerciseType = bundle.getString("exerciseType");
            workoutOrMuscle = bundle.getString("workoutOrMuscle");
            String text = exerciseType + " Exercises";
            exerciseTitle.setText(text);
        }


        if (exerciseType.equals("Olympic Weightlifting")) {
            exerciseType = "olympic_weightlifting";
        } else if (exerciseType.equals("Lower Back")) {
            exerciseType = "lower_back";
        } else if (exerciseType.equals("Middle Back")) {
            exerciseType = "middle_back";
        }
        else {
            exerciseType = exerciseType.toLowerCase();
        }

        fetchExercises(workoutOrMuscle, exerciseType);
    }

    private void fetchExercises(String workoutOrMuscle, String exerciseType) {
        OkHttpClient client = new OkHttpClient();

        String API_URL = "https://api.api-ninjas.com/v1/exercises";


        if (workoutOrMuscle.equals("muscle")) {
            API_URL = API_URL + "?muscle=" + exerciseType;
        }
        else{
            API_URL = API_URL + "?type=" + exerciseType;
        }

        Log.d("API_URL", API_URL);
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
