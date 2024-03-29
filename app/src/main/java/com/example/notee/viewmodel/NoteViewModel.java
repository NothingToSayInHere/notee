package com.example.notee.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.AndroidViewModel;

import com.example.notee.NoteRepository;
import com.example.notee.model.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private final NoteRepository repository;
    private final MutableLiveData<Boolean> isNoteAdded = new MutableLiveData<>(false);

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application.getApplicationContext());
    }

    public LiveData<Boolean> getIsNoteAdded() {
        return isNoteAdded;
    }

    public void addNoteToDatabase(Note note) {
        if (note == null) {
            throw new IllegalArgumentException("Note cannot be null");
        }

        if (repository != null) {
            repository.addNoteToDatabase(note);
            repository.getAllNotes();
            isNoteAdded.setValue(true);
        }
    }

    public void deleteNoteById(int noteID) {
        repository.deleteNoteById(noteID);
    }

    public void updateNote(Note note) {
        repository.updateNote(note);
    }

    public LiveData<List<Note>> getAllNotes() {
        return repository.getAllNotes();
    }

    public void setIsNoteAdded(boolean value) {
        isNoteAdded.setValue(value);
    }

}