package com.example.flexdaily;

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
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private EditText textEmail, textPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;

    public LoginFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize UI components
        textEmail = view.findViewById(R.id.textEmail);
        textPassword = view.findViewById(R.id.textPassword);
        btnLogin = view.findViewById(R.id.btnLogin);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();

        // Login button click listener
        btnLogin.setOnClickListener(v -> loginUser());

        return view;
    }

    private void loginUser() {
        String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            textEmail.setError("Email is required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            textPassword.setError("Password is required");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Login Successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), UserTabsActivity.class));
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Login Failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
