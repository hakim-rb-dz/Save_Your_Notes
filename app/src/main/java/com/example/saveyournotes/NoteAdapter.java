package com.example.saveyournotes ;
import android.app.AlertDialog;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    List<Note> notes;
    List<Note> AllNotes;
    // constructor
    public NoteAdapter(List<Note> notes) {

        this.notes = notes;
        this.AllNotes = new ArrayList<>(notes);
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
            holder.tvContent.setText(note.getContent());
        }
// if the type is video
        else if (type.equals("video")) {
            holder.videoNote.setVisibility(View.VISIBLE);
            holder.videoNote.setVideoURI(Uri.parse(note.getContent()));
            holder.tvContent.setText(note.getContent());
        }
        Button Edit = holder.itemView.findViewById(R.id.Edit);
        Button Delete = holder.itemView.findViewById(R.id.Delete);

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();

                Note note = notes.get(position);

                View dialogView = LayoutInflater.from(v.getContext())
                        .inflate(R.layout.edit_ui, null);

                EditText editTitle = dialogView.findViewById(R.id.editTextText2);
                EditText editContent = dialogView.findViewById(R.id.editTextTextMultiLine);
                Button btnUpdate = dialogView.findViewById(R.id.button);
                Button btnCancel = dialogView.findViewById(R.id.button4);

                // Pre-fill data
                editTitle.setText(note.getTitle());
                editContent.setText(note.getContent());

                AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                        .setView(dialogView)
                        .setTitle("Edit Note")
                        .create();

                dialog.show();

                // 🔥 UPDATE BUTTON
                btnUpdate.setOnClickListener(view -> {

                    String newTitle = editTitle.getText().toString();
                    String newContent = editContent.getText().toString();

                    if (newTitle.isEmpty()) {
                        editTitle.setError("Required");
                        return;
                    }

                    com.google.firebase.firestore.FirebaseFirestore.getInstance()
                            .collection("notes")
                            .document(note.getnoteid())
                            .update(
                                    "title", newTitle,
                                    "content", newContent
                            )
                            .addOnSuccessListener(unused -> {

                                // update UI
                                note.setTitle(newTitle);
                                note.setContent(newContent);

                                notifyItemChanged(position);

                                android.widget.Toast.makeText(v.getContext(),
                                        "Updated", android.widget.Toast.LENGTH_SHORT).show();

                                dialog.dismiss();
                            });
                });

                btnCancel.setOnClickListener(view -> dialog.dismiss());
            }
        });
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Note x = notes.get(position);
                com.google.firebase.firestore.FirebaseFirestore.getInstance()
                        .collection("notes")
                        .document(x.getnoteid())
                        .delete()
                        .addOnSuccessListener(unused -> {

                            notes.remove(position);
                            AllNotes.remove(x);

                            notifyItemRemoved(position);

                            android.widget.Toast.makeText(v.getContext(), "Deleted", android.widget.Toast.LENGTH_SHORT).show();
                        });
            }
        });


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void Search(String text){
        if (text.isEmpty()){
            notes.clear();
            notes.addAll(AllNotes);
        }
        else{
            notes.clear();
            for (Note note : AllNotes){
               if (note.getTitle().toLowerCase().trim().contains(text.toLowerCase()) || note.getContent().toLowerCase().trim().contains(text.toLowerCase())) {
                   notes.add(note);
               }
            }

        }
        notifyDataSetChanged();
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



