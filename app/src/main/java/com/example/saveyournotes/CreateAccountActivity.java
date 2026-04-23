package com.example.saveyournotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);

            EditText email =findViewById(R.id.editTextTextEmailAddress2);
            EditText password =findViewById(R.id.editTextTextPassword);
            Button create =findViewById(R.id.button5);
            ImageButton retour =findViewById(R.id.imageButton2);
            FirebaseAuth auth;
            auth= FirebaseAuth.getInstance();
            retour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(CreateAccountActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
            });
       create.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String eml =email.getText().toString();
               String psw =password.getText().toString();
               auth.createUserWithEmailAndPassword(eml,psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task .isSuccessful()){
                           Intent intent =new Intent(CreateAccountActivity.this,HomeActivity.class);
                           startActivity(intent);
                       } else {
                           String error = task.getException().getMessage();
                           Toast.makeText(CreateAccountActivity.this, error, Toast.LENGTH_LONG).show();
                       }
                   }
               });
           }
       });
    }
}