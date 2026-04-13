package com.example.personaleventplanner.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.personaleventplanner.model.Event;

// The main Room database class
// tells Room which entity is used and which DAO belongs to it
@Database(entities = {Event.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    // Method to access EventDao
    public abstract EventDao eventDao();
}