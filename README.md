# Personal Event Planner App

## Overview
This is an Android application developed using Java in Android Studio.  
The app allows users to manage their personal events such as appointments, trips, and schedules.

Users can add, view, update, and delete events. All data is stored locally using the Room database and persists even after the app is closed.

## Features
- Add new events with title, category, location, date, and time
- View all events in a list sorted by date and time
- Update existing events
- Delete events
- Input validation to ensure required fields are filled
- Prevents users from entering dates in the past

## Technologies Used
- Java
- Android Studio
- Room Database
- RecyclerView
- Navigation Component (Fragments and Bottom Navigation)

## App Structure
- MainActivity: Hosts navigation and initializes the database
- EventListFragment: Displays all events
- AddEventFragment: Used for both adding and updating events
- EventAdapter: Handles RecyclerView display
- Room Database: Stores event data locally

## Notes
- The app uses simple text input for date and time
- Validation ensures correct format and prevents past dates
- Designed as a simple implementation to meet assignment requirements
