package com.example.notee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.notee.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    // Initializing the instance variables
    private FirebaseAuth mAuth;
    private TextInputEditText firstNameField, emailField, passwordField;
    MaterialButton registerButton;
    private ProgressBar registerProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Getting the instance of the Firebase documentation: https://firebase.google.com/docs/auth/android/password-auth#create_a_password-based_account
        mAuth = FirebaseAuth.getInstance();

        registerButton = findViewById(R.id.registerButton);

        firstNameField = findViewById(R.id.firstNameField);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);

        registerProgressBar = findViewById(R.id.registerProgressBar);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        // Converting and trimming inputs to strings
        String firstNameString = firstNameField.getText().toString().trim();
        String emailString = emailField.getText().toString().trim();
        String passwordString = passwordField.getText().toString().trim();

        // Validating the inputs
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
            // Register user if validation passes
            if (task.isSuccessful()) {
                User user = new User(firstNameString, emailString);

                // Registering user to Firebase: https://firebase.google.com/docs/auth/android/password-auth#create_a_password-based_account
                FirebaseDatabase.getInstance().getReference("users")
                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                        .setValue(user)
                        .addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "User has been successfully registered!", Toast.LENGTH_LONG).show();
                                // Redirect to LoginActivity if the registration is successful
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                                registerProgressBar.setVisibility(View.GONE);
                                finish();
                            } else {
                                // Display a toast if there was an issue to register a user
                                Toast.makeText(RegisterActivity.this, "Failed to register. Try again later.", Toast.LENGTH_LONG).show();
                                registerProgressBar.setVisibility(View.GONE);
                            }
                        });

            } else {
                // Display a toast if the user could not be registered due to lack of internet connection
                Toast.makeText(RegisterActivity.this, "No internet connection. Connect to the internet.", Toast.LENGTH_LONG).show();
                registerProgressBar.setVisibility(View.GONE);
            }

        });
    }
}