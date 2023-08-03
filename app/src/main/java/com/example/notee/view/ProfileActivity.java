package com.example.notee.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

    BottomNavigationView bottomNavigationView;

    MaterialButton logoutButton, deleteProfileButton;
    ProgressBar deleteProfileProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize and assign variable
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Set profile item selected
        bottomNavigationView.setSelectedItemId(R.id.profileItem);

        logoutButton = findViewById(R.id.logoutButton);
        deleteProfileButton = findViewById(R.id.deleteProfileButton);
        deleteProfileProgressBar = findViewById(R.id.deleteProfileProgressBar);

        setupFirebaseListener();

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {
                    case R.id.profileItem:
                        return true;
                    case R.id.shoppingListItem:
                        startActivity(new Intent(getApplicationContext(), ShoppingListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.notesItem:
                        startActivity(new Intent(getApplicationContext(), NotesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        logoutButton.setOnClickListener(v -> FirebaseAuth.getInstance().signOut());

        // Getting the user from Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Deleting a user from database
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

    }

    private void setupFirebaseListener() {
        mAuthStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // Displays all the data
            } else {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }

}
