package com.example.notee;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.notee.model.Note;
import com.example.notee.view.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notes_db";
    private static final String TABLE_NOTES = "notes";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_NOTE_TEXT = "note_text";
    private static final String KEY_CREATION_DATE = "creation_date";
    private static final String KEY_USER_ID = "user_id";
    private final Context context;

    public NoteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_NOTE_TEXT + " TEXT,"
                + KEY_CREATION_DATE + " INTEGER,"
                + KEY_USER_ID + " TEXT"
                + ")";
        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_NOTE_TEXT, note.getContent());
        values.put(KEY_USER_ID, getCurrentUserId());
        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

    public List<Note> getAllNotes() {
        List<Note> noteList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES +
                " WHERE " + KEY_USER_ID + " = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{getCurrentUserId()});
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(0));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return noteList;
    }

    public void updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_NOTE_TEXT, note.getContent());
        db.update(TABLE_NOTES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNoteById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    private String getCurrentUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        } else {
            Intent loginIntent = new Intent(context, LoginActivity.class);
            context.startActivity(loginIntent);

            return "not_authenticated";
        }
    }

}