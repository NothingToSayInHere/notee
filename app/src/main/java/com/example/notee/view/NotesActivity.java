package com.example.notee.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notee.R;
import com.example.notee.model.Note;
import com.example.notee.viewmodel.NoteActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        floatingAddNote = findViewById(R.id.floating_add_note);
        viewModel = new ViewModelProvider(this).get(NoteActivityViewModel.class);

        // Finding Recycler View
        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(NotesActivity.this));
        rv.hasFixedSize();

        notesItems = new ArrayList<>();

        notesAdapter = new NotesAdapter(this, notesItems, new NotesAdapter.OnNoteItemClickListener() {
            @Override
            public void onNoteItemClick(Note note, int position) {
                if (!notesItems.isEmpty() && position < notesItems.size()) {
                    Intent intent = new Intent(NotesActivity.this, NoteDetailsActivity.class);
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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.navigation_notes);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_notes) {
                return true;
            } else if (item.getItemId() == R.id.navigation_shopping_list) {
                startActivity(new Intent(this, ShoppingListActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (item.getItemId() == R.id.navigation_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
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




















