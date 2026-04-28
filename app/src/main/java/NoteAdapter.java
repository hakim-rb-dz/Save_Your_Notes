package com.example.saveyournotes ;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    List<Note> notes;

    // constructor
    public NoteAdapter(List<Note> notes) {

        this.notes = notes;
    }


    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteAdapter.NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.tvTitle.setText(note.getTitle());
        holder.tvContent.setVisibility(View.GONE);
        holder.imgNote.setVisibility(View.GONE);
        holder.videoNote.setVisibility(View.GONE);

        String type = note.getnotetype();
//if the type of note saved is an text
        if (type.equals("text")) {
            holder.tvContent.setVisibility(View.VISIBLE);
            holder.tvContent.setText(note.getContent());
        }
// if the type is an image
        else if (type.equals("photo")) {
            holder.imgNote.setVisibility(View.VISIBLE);
            holder.imgNote.setImageURI(Uri.parse(note.getContent()));
        }
// if the type is video
        else if (type.equals("video")) {
            holder.videoNote.setVisibility(View.VISIBLE);
            holder.videoNote.setVideoURI(Uri.parse(note.getContent()));

        }
// audio

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvContent;
        ImageView imgNote;
        VideoView videoNote;
        public NoteViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvContent = itemView.findViewById(R.id.tvNoteContent);
            imgNote = itemView.findViewById(R.id.imgNote);
            videoNote = itemView.findViewById(R.id.videoNote);
        }


    }
}