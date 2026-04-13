package com.example.personaleventplanner.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.personaleventplanner.model.Event;

import java.util.List;

// DAO = Data Access Object contains all database operations for the Event table
@Dao
public interface EventDao {

    @Insert
    void insert(Event event);

    @Update
    void update(Event event);

    @Delete
    void delete(Event event);

    // Get all events sorted by date and time
    @Query("SELECT * FROM events ORDER BY date ASC, time ASC")
    List<Event> getAllEvents();

    // Get one specific event by its ID
    @Query("SELECT * FROM events WHERE id = :eventId")
    Event getEventById(int eventId);
}