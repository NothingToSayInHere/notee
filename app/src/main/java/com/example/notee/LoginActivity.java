package com.example.notee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    // Instance variable from Firebase documentation
    private FirebaseAuth mAuth;
    private TextInputEditText emailField, passwordField;
    private ProgressBar loginProgressBar;

    private MaterialButton forgotPasswordButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        loginButton = findViewById(R.id.loginButton);

        // Adding ForgotPasswordActivity to be viewed when clicked on the button
        forgotPasswordButton.setOnClickListener(view -> startActivity(new Intent(
                LoginActivity.this, ForgotPasswordActivity.class)));

        // Get the Firebase instance
        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);

        loginButton.setOnClickListener(view -> userLogin());

        loginProgressBar = findViewById(R.id.loginProgressBar);
    }

    private void userLogin() {
        String emailString = emailField.getText().toString().trim();
        String passwordString = passwordField.getText().toString().trim();

        // Validating the data
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

        // Retrieving login details and logging in user:
        // https://firebase.google.com/docs/auth/android/password-auth#sign_in_a_user_with_an_email_address_and_password
        mAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Redirect to NotesActivity
                startActivity(new Intent(LoginActivity.this, NotesActivity.class));
            } else {
                // Show a toast that the login attempt has not been successful
                Toast.makeText(LoginActivity.this,
                        "Failed to log in. Please check your credentials.",
                        Toast.LENGTH_LONG).show();
                loginProgressBar.setVisibility(View.GONE);
            }
        });
    }
}