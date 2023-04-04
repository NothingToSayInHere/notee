package com.example.notee;

import android.app.Application;

import com.example.notee.model.Note;

public class NoteRepository {

    private NoteDatabaseHelper databaseHelper;

    public NoteRepository(Application context) {
        databaseHelper = new NoteDatabaseHelper(context);
    }

    public void addNoteToDatabase(Note note) {
        databaseHelper.addNote(note);
    }
}
