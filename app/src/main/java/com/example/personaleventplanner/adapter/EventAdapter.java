package com.example.personaleventplanner.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personaleventplanner.MainActivity;
import com.example.personaleventplanner.R;
import com.example.personaleventplanner.model.Event;

import java.util.List;

//RecyclerView adapter used to display each event in the event list
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    //List of events to show
    List<Event> eventList;

    public EventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Populate one event item layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        //Get the current event from the list
        Event event = eventList.get(position);

        //Display event details
        holder.tvTitle.setText(event.title);
        holder.tvCategory.setText("Category: " + event.category);
        holder.tvLocation.setText("Location: " + event.location);
        holder.tvDateTime.setText("Date: " + event.date + "  Time: " + event.time);

        //Delete button logic
        holder.btnDelete.setOnClickListener(v -> {
            MainActivity.database.eventDao().delete(event);
            eventList.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(v.getContext(), "Event deleted", Toast.LENGTH_SHORT).show();
        });

        //Update button logic
        holder.btnUpdate.setOnClickListener(v -> {
            Bundle bundle = new Bundle();

            //Pass the event ID to AddEventFragment
            bundle.putInt("eventId", event.id);

            //Navigate to AddEventFragment for editing
            Navigation.findNavController(v).navigate(R.id.addEventFragment, bundle);
        });
    }

    @Override
    //Return total number of events
    public int getItemCount() {
        return eventList.size();
    }

    //ViewHolder class holds the view for one event item
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCategory, tvLocation, tvDateTime;
        Button btnDelete, btnUpdate;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            //Connect item layout views
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }
}