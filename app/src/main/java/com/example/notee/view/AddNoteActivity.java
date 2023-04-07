package com.example.notee.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notee.NoteDatabaseHelper;
import com.example.notee.R;
import com.example.notee.model.Note;
import com.example.notee.viewmodel.NoteActivityViewModel;

import java.util.List;

public class AddNoteActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText contentEditText;
    private Button addButton;
    private NoteActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        NoteDatabaseHelper db = new NoteDatabaseHelper(this);

        viewModel = new ViewModelProvider(this).get(NoteActivityViewModel.class);

        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addNote(new Note("ze title", "ze content"));
                Log.d("NotesActivity", "the new note has been added");
//                String title = titleEditText.getText().toString().trim();
//                String content = contentEditText.getText().toString().trim();
//                if (!title.isEmpty() && !content.isEmpty()) {
//                    Note note = new Note(0, title, content);
//                    Log.d("NotesActivity", "Adding note: " + note.getTitle() + " - " + note.getContent());
//                    viewModel.addNoteToDatabase(note);
//                    // clear the EditText fields to prepare for a new note
//                    titleEditText.setText("");
//                    contentEditText.setText("");
//                } else {
//                    Toast.makeText(NotesActivity.this, "Please enter a title and content for the note.", Toast.LENGTH_SHORT).show();
//                }
                List<Note> notes = db.getAllNotes();

                for (Note n : notes) {
                    String log = n.getTitle() + n.getContent();
                    Log.d("note is: ", log);
                }
            }
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