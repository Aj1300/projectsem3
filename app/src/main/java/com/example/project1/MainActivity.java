package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private String userno;  // To store the vehicle number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Disable the default title

        // Get the vehicle number from Intent
        Intent intent = getIntent();
        userno = intent.getStringExtra("USER_NO");

        // Bottom Navigation setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconSize(110);
        bottomNavigationView.setOnItemSelectedListener(navListener);

        // Load the default fragment (TollFragment) with vehicle number
        loadTollFragment();
    }

    private BottomNavigationView.OnItemSelectedListener navListener = new BottomNavigationView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_toll) {
                selectedFragment = new TollFragment();
            } else if (item.getItemId() == R.id.nav_toll_history) {
                selectedFragment = new TollHistoryFragment();
            } else if (item.getItemId() == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            } else {
                return false;
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }

            return true;
        }
    };

    // Helper method to load TollFragment with vehicle number
    private void loadTollFragment() {
        TollFragment tollFragment = new TollFragment();
        Bundle args = new Bundle();
        args.putString("USER_NO", userno);
        tollFragment.setArguments(args);

        // Load the fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, tollFragment)
                .commit();
    }
}