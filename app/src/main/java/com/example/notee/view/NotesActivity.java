package com.example.notee.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notee.NoteDatabaseHelper;
import com.example.notee.R;
import com.example.notee.model.Note;
import com.example.notee.viewmodel.NoteActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {
    FloatingActionButton floatingAddNote;
    List<Note> notesItems;

    private NoteActivityViewModel viewModel;
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        floatingAddNote = findViewById(R.id.floatingAddNote);

        viewModel = new ViewModelProvider(this).get(NoteActivityViewModel.class);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.notesItem);

        // Finding Recycler View
        RecyclerView rv = findViewById(R.id.rv);

        rv.setLayoutManager(new LinearLayoutManager(NotesActivity.this));
        rv.hasFixedSize();

        notesItems = new ArrayList<>();

        notesAdapter = new NotesAdapter(this, notesItems, new NotesAdapter.OnNoteItemClickListener() {
            @Override
            public void onNoteItemClick(Note note, int position) {
                if (!notesItems.isEmpty() && position < notesItems.size()) {
                    Intent intent = new Intent(NotesActivity.this, NoteDetailActivity.class);

                    // put the title and content of the clicked note as extras in the intent
                    Note clickedNote = notesItems.get(position);
                    intent.putExtra("title", clickedNote.getTitle());
                    intent.putExtra("content", clickedNote.getContent());
                    intent.putExtra("id", clickedNote.getId());

                    startActivity(intent);
                } else {
                    Log.e("NotesActivity", "Invalid position: " + position);
                }
            }
        });

        if (notesAdapter == null) {
            Log.e("NotesActivity", "notesAdapter instance is null");
        }

        rv.setAdapter(notesAdapter);

        floatingAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NotesActivity.this, AddNoteActivity.class));
            }
        });

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.notesItem:
                        return true;
                    case R.id.shoppingListItem:
                        startActivity(new Intent(getApplicationContext(), ShoppingListActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profileItem:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Observe the LiveData returned by ViewModel
        viewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                // Update the notesItems list with the new notes list
                notesItems.clear();
                notesItems.addAll(notes);

                // Notify the adapter that the data has changed
                notesAdapter.notifyDataSetChanged();
            }
        });
    }
}
