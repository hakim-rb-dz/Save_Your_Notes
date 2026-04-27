package com.example.saveyournotes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.saveyournotes.Note ;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class page3 extends AppCompatActivity {
    //firebase variables
    FirebaseFirestore database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.savetext);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText editTitle   = findViewById(R.id.edit2);
        EditText editContent = findViewById(R.id.maniline1);
        Button   savebutton  = findViewById(R.id.button2);
        database = FirebaseFirestore.getInstance();
        auth     = FirebaseAuth.getInstance();

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();

                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(page3.this, "pls enter the title or the content", Toast.LENGTH_SHORT).show();
                    return;
                }
                String userid = auth.getCurrentUser().getUid();
                Note note = new Note(title, content, userid, "text");
                database.collection("notes").add(note).addOnSuccessListener(documentReference -> {
                            Toast.makeText(page3.this, "saved successfully", Toast.LENGTH_SHORT).show();

                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(page3.this, "failed saving: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

    }
}