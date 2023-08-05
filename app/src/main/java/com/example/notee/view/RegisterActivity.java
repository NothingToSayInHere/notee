package com.example.notee.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.notee.R;
import com.example.notee.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputEditText firstNameField, emailField, passwordField;
    MaterialButton registerButton;
    private ProgressBar registerProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        registerButton = findViewById(R.id.register_button);

        firstNameField = findViewById(R.id.first_name_field);
        emailField = findViewById(R.id.email_field);
        passwordField = findViewById(R.id.password_field);

        registerProgressBar = findViewById(R.id.register_progress_bar);

        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String firstNameString = firstNameField.getText() != null ? firstNameField.getText().toString().trim() : "";
        String emailString = emailField.getText() != null ? emailField.getText().toString().trim() : "";
        String passwordString = passwordField.getText() != null ? passwordField.getText().toString().trim() : "";

        if (firstNameString.isEmpty()) {
            firstNameField.setError("First name is required");
            firstNameField.requestFocus();
            return;
        }

        if (emailString.isEmpty()) {
            emailField.setError("Email is required");
            emailField.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
            emailField.setError("Please provide a valid email");
            emailField.requestFocus();
            return;
        }

        if (passwordString.isEmpty()) {
            passwordField.setError("Password is required");
            passwordField.requestFocus();
            return;
        }

        if (passwordString.length() < 6) {
            passwordField.setError("The password must be at least 6 characters");
            passwordField.requestFocus();
            return;
        }

        registerProgressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User user = new User(firstNameString, emailString);

                FirebaseDatabase.getInstance().getReference("users")
                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                        .setValue(user)
                        .addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "User has been successfully registered!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                                registerProgressBar.setVisibility(View.GONE);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Failed to register. Try again later.", Toast.LENGTH_LONG).show();
                                registerProgressBar.setVisibility(View.GONE);
                            }
                        });

            } else {
                Toast.makeText(RegisterActivity.this, "No internet connection. Connect to the internet.", Toast.LENGTH_LONG).show();
                registerProgressBar.setVisibility(View.GONE);
            }

        });
    }
}