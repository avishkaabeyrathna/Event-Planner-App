package com.example.personaleventplanner.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Class represents one event in the Room Database
@Entity(tableName = "events")
public class Event {

    //Primary Key for each event
    @PrimaryKey(autoGenerate = true)
    public int id;

    //Features of the event
    public String title;
    public String category;
    public String location;
    public String date;
    public String time;
}