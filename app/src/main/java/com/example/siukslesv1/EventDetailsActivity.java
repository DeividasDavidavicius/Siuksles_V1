package com.example.siukslesv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventDetailsActivity extends AppCompatActivity {
    private TextView mTitleTextView;
    private TextView locationTextView;
    private TextView startTextView;
    private TextView endTextView;
    private ImageView imageEventView;
    FirebaseDatabase firebaseDatabase;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        // Get the event data from the Intent
        String eventTitle = getIntent().getStringExtra("event_title");
        String eventLocation = getIntent().getStringExtra("event_location");
        String imageEvent = getIntent().getStringExtra("event_image");
        Long eventStart = getIntent().getLongExtra("event_start", 0);
        Long eventEnd = getIntent().getLongExtra("event_end", 0);

        // Initialize the views in the layout
        mTitleTextView = findViewById(R.id.row_event_title);
        locationTextView = findViewById(R.id.event_location);
        imageEventView = findViewById(R.id.row_event_image);
        startTextView = findViewById(R.id.row_event_start);
        endTextView = findViewById(R.id.row_event_end);

        // Transform data

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTimeInMillis(eventStart);
        Date startDate = startCalendar.getTime();
        String startDateText = formatter.format(startDate);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTimeInMillis(eventEnd);
        Date endDate = endCalendar.getTime();
        String endDateString = formatter.format(endDate);

        // Set the views with the event data
        startTextView.setText(startDateText);
        endTextView.setText(endDateString);
        mTitleTextView.setText(eventTitle);
        locationTextView.setText(eventLocation);
        Picasso.get().load(imageEvent).into(imageEventView);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.camera:
                    startActivity(new Intent(getApplicationContext(), CameraActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.map:
                    startActivity(new Intent(getApplicationContext(), mapActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }
}