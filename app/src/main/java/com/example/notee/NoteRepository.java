package com.example.notee;

import android.app.Application;

public class NoteRepository {

    private NoteDatabaseHelper databaseHelper;

    public NoteRepository(Application context) {
        databaseHelper = new NoteDatabaseHelper(context);
    }

    public void addNoteToDatabase(Note note) {
        databaseHelper.addNote(note);
    }
}
