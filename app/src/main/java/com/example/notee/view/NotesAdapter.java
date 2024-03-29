package com.example.notee.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notee.R;
import com.example.notee.model.Note;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private final List<Note> notesList;
    private final OnNoteItemClickListener onNoteItemClickListener;

    public NotesAdapter(List<Note> notesList, OnNoteItemClickListener onNoteItemClickListener) {
        this.notesList = notesList;
        this.onNoteItemClickListener = onNoteItemClickListener;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        holder.noteTitle.setText(notesList.get(holder.getAdapterPosition()).getTitle());
        holder.noteContent.setText(notesList.get(holder.getAdapterPosition()).getContent());

        holder.itemView.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            onNoteItemClickListener.onNoteItemClick(getItem(currentPosition), currentPosition);
        });
    }

    public Note getItem(int position) {
        return notesList.get(position);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView noteTitle, noteContent;

        ViewHolder(View itemView) {
            super(itemView);

            noteTitle = itemView.findViewById(R.id.displayed_note_title);
            noteContent = itemView.findViewById(R.id.displayed_note_content);
        }
    }

    public interface OnNoteItemClickListener {
        void onNoteItemClick(Note note, int position);
    }
}