package com.example.notee.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.notee.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    MaterialButton logoutButton, deleteProfileButton;
    ProgressBar deleteProfileProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logoutButton = findViewById(R.id.logout_button);
        deleteProfileButton = findViewById(R.id.delete_profile_button);
        deleteProfileProgressBar = findViewById(R.id.delete_profile_progress_bar);

        setupFirebaseListener();

        logoutButton.setOnClickListener(v -> FirebaseAuth.getInstance().signOut());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        deleteProfileButton.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(ProfileActivity.this);
            dialog.setTitle("Are you sure?");
            dialog.setMessage("Deleting this account will result in completely removing all data.");
            dialog.setPositiveButton("Delete", (dialog1, which) -> {
                deleteProfileButton.setVisibility(View.VISIBLE);
                assert user != null;
                user.delete().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ProfileActivity.this, "Account successfully deleted!",
                                Toast.LENGTH_LONG).show();
                        FirebaseAuth.getInstance().signOut(); // Sign out after profile deletion
                        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        deleteProfileProgressBar.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(ProfileActivity.this, Objects.requireNonNull(task.getException()).getMessage(),
                                Toast.LENGTH_LONG).show();
                        deleteProfileProgressBar.setVisibility(View.GONE);
                    }
                });
            });

            dialog.setNegativeButton("Dismiss", (dialog12, which) -> dialog12.dismiss());

            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_notes) {
                startActivity(new Intent(this, NotesActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (item.getItemId() == R.id.navigation_shopping_list) {
                startActivity(new Intent(this, ShoppingListActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else return item.getItemId() == R.id.navigation_profile;
        });

    }

    private void setupFirebaseListener() {
        mAuthStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }

}