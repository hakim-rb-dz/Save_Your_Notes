package com.example.saveyournotes;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.saveyournotes.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menuBtn;
    FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        menuBtn = findViewById(R.id.menuBtn);
        fabAdd = findViewById(R.id.fabAdd);

        // Open the drawer when menu button clicked
        menuBtn.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));

        fabAdd.setOnClickListener(v -> {
            // TODO: Open add note screen
        });
    }
}