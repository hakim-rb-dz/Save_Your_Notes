package com.example.saveyournotes;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.saveyournotes.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class HomeActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    ImageView menuBtn;
    RecyclerView recyclerNotes;
    FloatingActionButton fab;

    FirebaseFirestore database;
    FirebaseAuth auth;

    List<Note> notes;
    NoteAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fab = findViewById(R.id.fabAdd);
        drawerLayout = findViewById(R.id.drawer_layout);
        menuBtn = findViewById(R.id.menuBtn);
        recyclerNotes = findViewById(R.id.recyclerNotes);
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        notes = new ArrayList<>();
        adapter = new NoteAdapter(notes);
        EditText searchBar = findViewById(R.id.searchBar);


        recyclerNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerNotes.setAdapter(adapter);


        // Open the drawer when menu button clicked
        menuBtn.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, page2.class);
                startActivity(intent);
            }
        });
        loadNotes();
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.Search(s.toString());
                adapter.notifyDataSetChanged();
            }


            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadNotes() {

        String userid = auth.getCurrentUser().getUid();

        database.collection("notes").whereEqualTo("userId", userid).get().addOnSuccessListener(queryDocumentSnapshots -> {
                    notes.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Note note = document.toObject(Note.class);
                        note.setnoteid(document.getId());
                        notes.add(note);
                    }
                    adapter.notifyDataSetChanged();
                    adapter.AllNotes.clear();
                    for(Note note : notes){
                        adapter.AllNotes.add(note);
                    }

                    if (notes.isEmpty()) {
                        Toast.makeText(HomeActivity.this,
                                "no notes yet", Toast.LENGTH_SHORT).show();
                    }
                })

                .addOnFailureListener(e -> {
                    Toast.makeText(HomeActivity.this,
                            "failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });

    }



}



