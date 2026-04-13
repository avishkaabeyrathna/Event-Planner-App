package com.example.personaleventplanner.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.personaleventplanner.MainActivity;
import com.example.personaleventplanner.R;
import com.example.personaleventplanner.adapter.EventAdapter;
import com.example.personaleventplanner.model.Event;

import java.util.List;


//Displays all saved events in a RecyclerView layout
public class EventListFragment extends Fragment {

    RecyclerView recyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Populate the layout
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        //Connect RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewEvents);

        //Set layout manager for vertical list display
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Load the events form the database
        loadEvents();

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

        //Reload events when resuming  this fragment
        loadEvents();
    }

    private void loadEvents() {
        //get all events from the Room database
        List<Event> eventList = MainActivity.database.eventDao().getAllEvents();

        //Set adapter for RecyclerView
        EventAdapter adapter = new EventAdapter(eventList);
        recyclerView.setAdapter(adapter);
    }
}