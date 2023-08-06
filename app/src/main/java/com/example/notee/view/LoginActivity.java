package com.example.notee.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.notee.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputEditText emailField, passwordField;
    private ProgressBar loginProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MaterialButton forgotPasswordButton = findViewById(R.id.forgot_password_button);
        MaterialButton loginButton = findViewById(R.id.login_button);

        forgotPasswordButton.setOnClickListener(view -> startActivity(new Intent(
                LoginActivity.this, ForgotPasswordActivity.class)));

        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.email_field);
        passwordField = findViewById(R.id.password_field);

        loginButton.setOnClickListener(view -> userLogin());

        loginProgressBar = findViewById(R.id.login_progress_bar);
    }

    private void userLogin() {
        String emailString = emailField.getText() != null ? emailField.getText().toString().trim() : "";
        String passwordString = passwordField.getText() != null ? passwordField.getText().toString().trim() : "";

        if (emailString.isEmpty()) {
            emailField.setError("Email is required");
            emailField.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
            emailField.setError("Please enter a valid email");
            emailField.requestFocus();
            return;
        }

        if (passwordString.isEmpty()) {
            passwordField.setError("Password is required");
            passwordField.requestFocus();
            return;
        }

        if (passwordString.length() < 6) {
            passwordField.setError("Minimum password length is 6 characters");
            passwordField.requestFocus();
            return;
        }

        loginProgressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(LoginActivity.this, NotesActivity.class));
            } else {
                Toast.makeText(LoginActivity.this,
                        "Failed to log in. Please check your credentials.",
                        Toast.LENGTH_LONG).show();
                loginProgressBar.setVisibility(View.GONE);
            }
        });
    }
}