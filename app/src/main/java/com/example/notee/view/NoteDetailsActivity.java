package com.example.notee.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.notee.R;
import com.example.notee.model.Note;
import com.example.notee.viewmodel.NoteViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class NoteDetailsActivity extends AppCompatActivity {
    TextInputEditText noteDetailsTitle, noteDetailsContent;
    MaterialButton deleteNoteButton, editNoteButton, saveNoteButton;
    int noteID;
    private NoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteDetailsTitle = findViewById(R.id.note_details_title);
        noteDetailsContent = findViewById(R.id.note_details_content);
        deleteNoteButton = findViewById(R.id.delete_note_button);
        editNoteButton = findViewById(R.id.edit_note_button);
        saveNoteButton = findViewById(R.id.save_note_button);

        noteDetailsTitle.setEnabled(false);
        noteDetailsContent.setEnabled(false);

        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        noteID = getIntent().getIntExtra("id", -1);

        noteDetailsTitle.setText(title);
        noteDetailsContent.setText(content);

        deleteNoteButton.setOnClickListener(v -> {
            viewModel.deleteNoteById(noteID);
            finish();
        });

        editNoteButton.setOnClickListener(v -> {
            noteDetailsTitle.setEnabled(true);
            noteDetailsContent.setEnabled(true);

            saveNoteButton.setVisibility(View.VISIBLE);
            editNoteButton.setVisibility(View.GONE);
        });

        saveNoteButton.setOnClickListener(v -> {
            String newTitle = noteDetailsTitle.getText().toString();
            String newContent = noteDetailsContent.getText().toString();

            Note note = new Note(noteID, newTitle, newContent);
            viewModel.updateNote(note);

            noteDetailsTitle.setEnabled(false);
            noteDetailsContent.setEnabled(false);

            saveNoteButton.setVisibility(View.GONE);
            editNoteButton.setVisibility(View.VISIBLE);
            finish();
        });
    }
}