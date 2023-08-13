package com.example.notee.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notee.R;
import com.example.notee.model.Note;
import com.example.notee.viewmodel.NoteViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {
    FloatingActionButton floatingAddNote;
    List<Note> notesItems;
    private NoteViewModel viewModel;
    private NotesAdapter notesAdapter;
    private Animation fabReappearAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        floatingAddNote = findViewById(R.id.floating_add_note);
        fabReappearAnimation = AnimationUtils.loadAnimation(this, R.anim.fab_reappear);
        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(NotesActivity.this));
        rv.hasFixedSize();

        notesItems = new ArrayList<>();

        notesAdapter = new NotesAdapter(notesItems, (note, position) -> {
            if (!notesItems.isEmpty() && position < notesItems.size()) {
                Intent intent = new Intent(NotesActivity.this, NoteDetailsActivity.class);

                Note clickedNote = notesItems.get(position);
                intent.putExtra("title", clickedNote.getTitle());
                intent.putExtra("content", clickedNote.getContent());
                intent.putExtra("id", clickedNote.getId());
                startActivity(intent);
            }
        });

        int paddingStart = getResources().getDimensionPixelOffset(R.dimen.divider_padding_start);
        int paddingEnd = getResources().getDimensionPixelOffset(R.dimen.divider_padding_end);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, R.drawable.divider, paddingStart, paddingEnd);
        rv.addItemDecoration(dividerItemDecoration);

        rv.setAdapter(notesAdapter);

        floatingAddNote.setOnClickListener(v -> startActivity(new Intent(NotesActivity.this, AddNoteActivity.class)));

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
        viewModel.getAllNotes().observe(this, notes -> {
            notesItems.clear();
            notesItems.addAll(notes);
            notesAdapter.notifyDataSetChanged();

            if (!notes.isEmpty()) {
                floatingAddNote.show();
            } else {
                floatingAddNote.startAnimation(fabReappearAnimation);
            }
        });
    }
}