package com.example.notee.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.notee.NoteRepository;
import com.example.notee.model.Note;

public class NoteActivityViewModel extends ViewModel {
    private NoteRepository repository;
    private MutableLiveData<Boolean> isNoteAdded = new MutableLiveData<>(false);

    public NoteActivityViewModel() {}

    public NoteActivityViewModel(Application application) {
        NoteRepository repo = new NoteRepository(application);
        if (repo != null) {
            repository = repo;
        } else {
            throw new IllegalStateException("NoteRepository cannot be null");
        }
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
            isNoteAdded.setValue(true);
            Log.d("NoteActivityViewModel", "Note added to database: " + note.toString());
        }
    }


    public void setIsNoteAdded(boolean value) {
        isNoteAdded.setValue(value);
    }
}
