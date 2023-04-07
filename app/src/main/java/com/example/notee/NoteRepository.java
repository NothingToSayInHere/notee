package com.example.notee;

import android.app.Application;
import android.util.Log;

import com.example.notee.model.Note;

public class NoteRepository {

    private NoteDatabaseHelper databaseHelper;

    public NoteRepository(Application context) {
        Log.d("NoteRepository", "Creating new instance of NoteDatabaseHelper in constructor");
        databaseHelper = new NoteDatabaseHelper(context);
    }

    public void addNoteToDatabase(Note note) {
        Log.d("NoteRepository", "addNoteToDatabase() method called");
        databaseHelper.addNote(note);
    }
}
