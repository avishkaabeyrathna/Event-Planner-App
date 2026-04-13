package com.example.personaleventplanner;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.personaleventplanner.database.AppDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // Static database instance so fragments can access it easily
    public static AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Create the Room database
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "event_db")
                        .allowMainThreadQueries()
                        .build();

        // Find the BottomNavigationView from the layout
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Find the NavHostFragment
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        // Connect the BottomNavigationView to the NavController
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }
}