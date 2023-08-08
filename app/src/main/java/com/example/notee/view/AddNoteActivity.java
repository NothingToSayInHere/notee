package com.example.notee.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.notee.NoteDatabaseHelper;
import com.example.notee.R;
import com.example.notee.model.Note;
import com.example.notee.viewmodel.NoteActivityViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddNoteActivity extends AppCompatActivity {
    private TextInputEditText addNoteTitle, addNoteContent;
    private MaterialButton addNoteButton;
    private NoteActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        NoteDatabaseHelper db = new NoteDatabaseHelper(this);

        viewModel = new ViewModelProvider(this).get(NoteActivityViewModel.class);

        addNoteTitle = findViewById(R.id.add_note_title);
        addNoteContent = findViewById(R.id.add_note_content);
        addNoteButton = findViewById(R.id.add_note_button);

        addNoteButton.setOnClickListener(v -> {
            String titleString = addNoteTitle.getText().toString().trim();
            String contentString = addNoteContent.getText().toString().trim();
            if (!titleString.isEmpty() && !contentString.isEmpty()) {
                Note note = new Note(0, titleString, contentString);
                Log.d("NotesActivity", "Adding note: " + note.getTitle() + " - " + note.getContent());
                viewModel.addNoteToDatabase(note);
                // clear the EditText fields to prepare for a new note
                addNoteTitle.setText("");
                addNoteContent.setText("");
            } else {
                Toast.makeText(AddNoteActivity.this, "Please enter a title and content for the note.", Toast.LENGTH_SHORT).show();
            }

            finish();
        });

        // Observe isNoteAdded LiveData
        viewModel.getIsNoteAdded().observe(this, isNoteAdded -> {
            if (isNoteAdded) {
                Toast.makeText(AddNoteActivity.this, "Note added successfully.", Toast.LENGTH_LONG).show();
                viewModel.setIsNoteAdded(false); // reset isNoteAdded to false
            }
        });

    }
}