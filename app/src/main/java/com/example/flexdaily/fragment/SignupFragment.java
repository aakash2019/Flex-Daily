package com.example.flexdaily.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.example.flexdaily.R;
import com.example.flexdaily.activity.UserTabsActivity;
import com.example.flexdaily.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupFragment extends Fragment {

    private EditText textUsername, textEmail, textPassword;
    private Button btnSignup;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public SignupFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        textUsername = view.findViewById(R.id.textUsername);
        textEmail = view.findViewById(R.id.textEmail);
        textPassword = view.findViewById(R.id.textPassword);
        btnSignup = view.findViewById(R.id.btnSignup);

        // Signup button click listener
        btnSignup.setOnClickListener(v -> registerUser());

        return view;
    }

    private void registerUser() {
        String username = textUsername.getText().toString().trim();
        String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase Authentication - Create User
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            String uid = firebaseUser.getUid();
                            saveUserToDatabase(uid, username, email);
                            Toast.makeText(getContext(), "Signup successful!", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getActivity(), UserTabsActivity.class));
                            getActivity().finish();
                        }
                    } else {
                        Toast.makeText(getContext(), "Signup Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserToDatabase(String uid, String username, String email) {
        User user = new User(uid, username, email);

        // Store user details in Firestore
        db.collection("users").document(uid)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "User Registered Successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
