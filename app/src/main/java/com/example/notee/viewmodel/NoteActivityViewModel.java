package com.example.notee.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.AndroidViewModel;

import com.example.notee.NoteRepository;
import com.example.notee.model.Note;

import java.util.List;

public class NoteActivityViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private MutableLiveData<Boolean> isNoteAdded = new MutableLiveData<>(false);

    public NoteActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application.getApplicationContext());
    }

    public LiveData<Boolean> getIsNoteAdded() {
        return isNoteAdded;
    }



    public void addNoteToDatabase(Note note) {
        Log.d("NoteActivityViewModel", "addNoteToDatabase() method called");
        if (note == null) {
            throw new IllegalArgumentException("Note cannot be null");
        }
        if (repository != null) {

            repository.addNoteToDatabase(note);
                repository.getAllNotes();
            isNoteAdded.setValue(true);
            Log.d("NoteActivityViewModel", "Note added to database: " + note.toString());
        }
    }

    public void deleteNoteById(int noteID) {
        repository.deleteNoteById(noteID);

    }



    public LiveData<List<Note>> getAllNotes() {
        return repository.getAllNotes();
    }

    public void setIsNoteAdded(boolean value) {
        isNoteAdded.setValue(value);
    }
}
