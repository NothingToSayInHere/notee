package com.example.notee.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.notee.R;
import com.example.notee.model.Note;
import com.example.notee.viewmodel.NoteActivityViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class NoteDetailsActivity extends AppCompatActivity {
    TextInputEditText noteDetailsTitle, noteDetailsContent;
    MaterialButton deleteNoteButton, editNoteButton, saveNoteButton;
    int noteID;
    private NoteActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        viewModel = new ViewModelProvider(this).get(NoteActivityViewModel.class);
        noteDetailsTitle = findViewById(R.id.note_details_title);
        noteDetailsContent = findViewById(R.id.note_details_content);
        deleteNoteButton = findViewById(R.id.delete_note_button);
        editNoteButton = findViewById(R.id.edit_note_button);
        saveNoteButton = findViewById(R.id.save_note_button);

        // Disable the fields by default
        noteDetailsTitle.setEnabled(false);
        noteDetailsContent.setEnabled(false);

        // Get the note data from the intent extras
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        noteID = getIntent().getIntExtra("id", -1);

        // Populate the EditText fields with the note data
        noteDetailsTitle.setText(title);
        noteDetailsContent.setText(content);

        // Set up the DELETE button click listener
        deleteNoteButton.setOnClickListener(v -> {
            viewModel.deleteNoteById(noteID);
            finish(); // Close the activity and return to the previous screen
        });

        // Set up the edit button click listener
        editNoteButton.setOnClickListener(v -> {

            // Enable the fields for editing
            noteDetailsTitle.setEnabled(true);
            noteDetailsContent.setEnabled(true);

            // Show the "save" button and hide the "edit" button
            saveNoteButton.setVisibility(View.VISIBLE);
            editNoteButton.setVisibility(View.GONE);
        });

        // Set up the save button click listener
        saveNoteButton.setOnClickListener(v -> {
            // Save the changes to the note
            String newTitle = noteDetailsTitle.getText().toString();
            String newContent = noteDetailsContent.getText().toString();

            Note note = new Note(noteID, newTitle, newContent);
            viewModel.updateNote(note);

            // Disable the fields again
            noteDetailsTitle.setEnabled(false);
            noteDetailsContent.setEnabled(false);

            // Hide the "save" button and show the "edit" button
            saveNoteButton.setVisibility(View.GONE);
            editNoteButton.setVisibility(View.VISIBLE);
            finish();
        });
    }
}