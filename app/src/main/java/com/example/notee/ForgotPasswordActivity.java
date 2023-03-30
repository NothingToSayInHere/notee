package com.example.notee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    // Instance variable from Firebase documentation
    FirebaseAuth mAuth;
    private TextInputEditText emailField;
    private ProgressBar forgotPasswordProgressBar;
    private MaterialButton resetPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Get the Firebase instance
        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.emailField);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        forgotPasswordProgressBar = findViewById(R.id.forgotPasswordProgressBar);

        resetPasswordButton.setOnClickListener(view -> resetPassword());
    }

    private void resetPassword() {
        String emailString = emailField.getText().toString().trim();

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

        forgotPasswordProgressBar.setVisibility(View.VISIBLE);

        // Sending a password reset email from Firebase documentation:
        // https://firebase.google.com/docs/auth/android/manage-users#send_a_password_reset_email
        mAuth.sendPasswordResetEmail(emailString).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset your password!",
                        Toast.LENGTH_LONG).show();
                forgotPasswordProgressBar.setVisibility(View.GONE);
            } else {
                Toast.makeText(ForgotPasswordActivity.this, "Something went wrong. Try again.",
                        Toast.LENGTH_LONG).show();
                forgotPasswordProgressBar.setVisibility(View.GONE);
            }
        });
    }
}