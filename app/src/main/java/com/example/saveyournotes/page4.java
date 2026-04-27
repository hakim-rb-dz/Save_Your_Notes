package com.example.saveyournotes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.saveyournotes.Note ;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class page4 extends AppCompatActivity {
    FirebaseFirestore database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    StorageReference storageRef;
    // intilia value cause the user didn,t select the photo
    Uri selectedImageUri = null;
    ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.savephoto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText editTitle = findViewById(R.id.edit2);
        EditText editDesc = findViewById(R.id.maniline1);
        Button saveButton = findViewById(R.id.button2);
        LinearLayout addPhotoArea = findViewById(R.id.lyn12);
        ImageView previewImage = findViewById(R.id.imageView5);
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK
                            && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        previewImage.setImageURI(selectedImageUri);
                    }
                });
        // when he click on add photo
        addPhotoArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                galleryLauncher.launch(intent);
            }
        });
        // when click on on save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = editTitle.getText().toString();
                String desc = editDesc.getText().toString();
                if (title.isEmpty()) {
                    Toast.makeText(page4.this, "please enter a title", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedImageUri == null) {
                    Toast.makeText(page4.this, "please select a photo", Toast.LENGTH_SHORT).show();
                    return;
                }
                // uplpoad the photo
                uploadPhotoAndSave(title, desc);
            }
        });
        private void uploadPhotoAndSave (String title, String desc){
            String userid = auth.getCurrentUser().getUid();
            StorageReference imageRef = storageRef.child("images").child(userid).child(System.currentTimeMillis() + ".jpg");
            imageRef.putFile(selectedImageUri).addOnSuccessListener(taskSnapshot -> {
                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            Note note = new Note(title, imageUrl, userid, "photo");
                            database.collection("notes").add(note).addOnSuccessListener(doc -> {
                                Toast.makeText(page4.this, "photo saved successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }).addOnFailureListener(e -> {
                                Toast.makeText(page4.this, "failed to save: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            });
                        });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(page4.this,
                                "failed to upload: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    });
        }
    }
