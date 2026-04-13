package com.example.personaleventplanner.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.personaleventplanner.MainActivity;
import com.example.personaleventplanner.R;
import com.example.personaleventplanner.model.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

//Fragment to add new events and update existing events
public class AddEventFragment extends Fragment {

    //UI Components
    EditText etTitle, etLocation, etDate, etTime;
    Spinner spinnerCategory;
    Button btnSave;

    //Spinner categories
    String[] categories = {"Work", "Social", "Travel"};

    //Used in update mode
    int eventId = -1;
    Event existingEvent = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);

        // Link UI components
        etTitle = view.findViewById(R.id.etTitle);
        etLocation = view.findViewById(R.id.etLocation);
        etDate = view.findViewById(R.id.etDate);
        etTime = view.findViewById(R.id.etTime);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        btnSave = view.findViewById(R.id.btnSave);

        // Set category spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories
        );
        spinnerCategory.setAdapter(adapter);

        // Check if fragment opened for updating an event
        if (getArguments() != null) {
            eventId = getArguments().getInt("eventId", -1);
        }

        //If event id exists, load old event data into fields
        if (eventId != -1) {
            existingEvent = MainActivity.database.eventDao().getEventById(eventId);

            if (existingEvent != null) {
                etTitle.setText(existingEvent.title);
                etLocation.setText(existingEvent.location);
                etDate.setText(existingEvent.date);
                etTime.setText(existingEvent.time);

                // Set the category in the spinner to match the existing category
                for (int i = 0; i < categories.length; i++) {
                    if (categories[i].equals(existingEvent.category)) {
                        spinnerCategory.setSelection(i);
                        break;
                    }
                }

                //button text is changed to indicate update mode
                btnSave.setText("Update Event");
            }
        }

        // Save or update when button is clicked
        btnSave.setOnClickListener(v -> saveEvent());

        return view;
    }

    private void saveEvent() {

        //get user input
        String title = etTitle.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        String location = etLocation.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String time = etTime.getText().toString().trim();

        // Input validation
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(date)) {
            Toast.makeText(getContext(), "Title and Date are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Date & Time validation
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        sdf.setLenient(false);

        try {
            Date selectedDateTime = sdf.parse(date + " " + time);
            Date currentDateTime = new Date();

            if (selectedDateTime != null && selectedDateTime.before(currentDateTime)) {
                Toast.makeText(getContext(), "Date cannot be in the past", Toast.LENGTH_SHORT).show();
                return;
            }

        } catch (ParseException e) {
            Toast.makeText(getContext(), "Enter date as dd/MM/yyyy and time as HH:mm", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save event details to database if it is an existing event otherwise create a new event
        if (existingEvent != null) {
            existingEvent.title = title;
            existingEvent.category = category;
            existingEvent.location = location;
            existingEvent.date = date;
            existingEvent.time = time;

            MainActivity.database.eventDao().update(existingEvent);
            Toast.makeText(getContext(), "Event updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Event event = new Event();
            event.title = title;
            event.category = category;
            event.location = location;
            event.date = date;
            event.time = time;

            MainActivity.database.eventDao().insert(event);
            Toast.makeText(getContext(), "Event saved successfully", Toast.LENGTH_SHORT).show();
        }

        //navigate back to the Event list screen
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.eventListFragment);

        // Clear fields after saving to database
        etTitle.setText("");
        etLocation.setText("");
        etDate.setText("");
        etTime.setText("");
    }
}