package com.example.notee;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.notee.model.Note;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {
    private final NoteDatabaseHelper databaseHelper;
    private final ExecutorService executor;

    public NoteRepository(Context context) {
        Log.d("NoteRepository", "Creating new instance of NoteDatabaseHelper in constructor");
        databaseHelper = new NoteDatabaseHelper(context);
        executor = Executors.newSingleThreadExecutor();
    }

    public void addNoteToDatabase(Note note) {
        Log.d("NoteRepository", "addNoteToDatabase() method in NoteRepository called");
        executor.execute(() -> databaseHelper.addNote(note));
    }

    public LiveData<List<Note>> getAllNotes() {
        MutableLiveData<List<Note>> notes = new MutableLiveData<>();
        executor.execute(() -> {
            List<Note> noteList = databaseHelper.getAllNotes();
            notes.postValue(noteList);
        });
        return notes;
    }

    public void deleteNoteById(int noteID) {
        databaseHelper.deleteNoteById(noteID);
    }

    public void updateNote(Note note) {
        databaseHelper.updateNote(note);
    }
}
