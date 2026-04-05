package com.example.saveyournotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.saveyournotes.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menuBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FloatingActionButton fab =findViewById(R.id.fabAdd);
        drawerLayout = findViewById(R.id.drawer_layout);
        menuBtn = findViewById(R.id.menuBtn);


        // Open the drawer when menu button clicked
        menuBtn.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,page2.class);
                startActivity(intent);
            }
        });
    }
}