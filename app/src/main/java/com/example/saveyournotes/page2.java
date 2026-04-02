package com.example.saveyournotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.saveyournotes.page3;
import com.example.saveyournotes.page4;
import com.example.saveyournotes.page5;
import com.example.saveyournotes.page6;

public class page2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        LinearLayout lay1=findViewById(R.id.second2);
        LinearLayout lay2=findViewById(R.id.first1);
        LinearLayout lay3=findViewById(R.id.thered3);
        LinearLayout lay4=findViewById(R.id.zir4);
        lay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(page2.this, page4.class);
                startActivity(intent);
            }
        });
        lay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(page2.this, page3.class);
                startActivity(intent);
            }
        });
        lay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(page2.this, page5.class);
                startActivity(intent);
            }
        });
        lay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(page2.this, page6.class);
                startActivity(intent);
            }
        });


    }
}