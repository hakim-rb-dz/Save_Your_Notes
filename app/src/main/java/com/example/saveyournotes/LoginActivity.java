package com.example.saveyournotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity);
        FirebaseAuth auth2;
        auth2 = FirebaseAuth.getInstance();
        Button btn = findViewById(R.id.button3);
        EditText email2=findViewById(R.id.editTextTextEmailAddress3);
        EditText password2=findViewById(R.id.editTextTextPassword2);
        Button sign= findViewById(R.id.buttonsignup);
         sign.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent =new Intent(LoginActivity.this,CreateAccountActivity.class);
                 startActivity(intent);
             }
         });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eml2= email2.getText().toString();
                String psw2= password2.getText().toString();
                auth2.signInWithEmailAndPassword(eml2,psw2).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

    }
}