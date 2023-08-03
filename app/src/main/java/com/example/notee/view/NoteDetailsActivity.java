package com.example.notee.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.notee.R;
import com.example.notee.model.Note;
import com.example.notee.viewmodel.NoteActivityViewModel;

public class NoteDetailsActivity extends AppCompatActivity {

    EditText editTitle, editContent;
    Button btnDelete, btnEdit, btnSave;
    int noteID;
    private NoteActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        viewModel = new ViewModelProvider(this).get(NoteActivityViewModel.class);

        editTitle = findViewById(R.id.edit_title);
        editContent = findViewById(R.id.edit_content);
        btnDelete = findViewById(R.id.btn_delete);
        btnEdit = findViewById(R.id.btn_edit);
        btnSave = findViewById(R.id.btn_save);

        // Disable the fields by default
        editTitle.setEnabled(false);
        editContent.setEnabled(false);

        // Get the note data from the intent extras
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        noteID = getIntent().getIntExtra("id", -1);

        // Populate the EditText fields with the note data
        editTitle.setText(title);
        editContent.setText(content);

        // Set up the DELETE button click listener
        btnDelete.setOnClickListener(v -> {
            viewModel.deleteNoteById(noteID);
            finish(); // Close the activity and return to the previous screen
        });

        // Set up the edit button click listener
        btnEdit.setOnClickListener(v -> {
            // Enable the fields for editing
            editTitle.setEnabled(true);
            editContent.setEnabled(true);

            // Show the "save" button and hide the "edit" button
            btnSave.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.GONE);
        });

        // Set up the save button click listener
        btnSave.setOnClickListener(v -> {
            // Save the changes to the note
            //NoteDatabaseHelper databaseHelper = new NoteDatabaseHelper(NoteDetailActivity.this);
            String newTitle = editTitle.getText().toString();
            String newContent = editContent.getText().toString();
            Note note = new Note(noteID, newTitle, newContent);
            viewModel.updateNote(note);

            // Disable the fields again
            editTitle.setEnabled(false);
            editContent.setEnabled(false);

            // Hide the "save" button and show the "edit" button
            btnSave.setVisibility(View.GONE);
            btnEdit.setVisibility(View.VISIBLE);
            finish();
        });
    }
}