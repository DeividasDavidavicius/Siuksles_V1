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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.List;
import java.util.ArrayList;

public class EventDetailsActivity extends AppCompatActivity {
    private TextView mTitleTextView;
    private TextView locationTextView;
    private TextView startTextView;
    private TextView endTextView;
    private ImageView imageEventView;
    FirebaseDatabase firebaseDatabase;
    Context mContext;
    Button vote;
    DatabaseReference userReference;
    String message;
    long eventStart;
    long eventEnd;
    String eventLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        mContext =this;
        // Get the event data from the Intent
        String eventTitle = getIntent().getStringExtra("event_title");
        eventLocation = getIntent().getStringExtra("event_location");
        String imageEvent = getIntent().getStringExtra("event_image");
        eventStart = getIntent().getLongExtra("event_start", 0);
        eventEnd = getIntent().getLongExtra("event_end", 0);

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
        vote = findViewById(R.id.event);
        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the joinEvent method here
                joinEvent();
            }
        });

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
    private void joinEvent() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            // User is not logged in, show a message and return
            Toast.makeText(mContext, "Please log in to join the event", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the event data from the Intent
        String eventId = getIntent().getStringExtra("event_id");

        firebaseDatabase = FirebaseDatabase.getInstance("https://siuksliu-programele-default-rtdb.europe-west1.firebasedatabase.app/");
        // Add the current user to the event's participants
        DatabaseReference eventRef = firebaseDatabase.getReference().child("events");
        Query query = eventRef.orderByChild("eventid").equalTo(eventId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String eventID = dataSnapshot.getChildren().iterator().next().getKey();
                DatabaseReference RefRef = firebaseDatabase.getReference().child("events").child(eventID).child("participants");
                RefRef.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        List<String> participants = mutableData.getValue(new GenericTypeIndicator<List<String>>() {});
                        if (participants == null) {
                            participants = new ArrayList<>();
                        }


                            // Check if the user is already a participant
                        if (participants.contains(currentUser.getUid())) {
                            message = "You have already joined the event";
                            return Transaction.success(mutableData);
                        }


                        long currentTimeMillis = Calendar.getInstance().getTimeInMillis();

                        if(currentTimeMillis > eventEnd || currentTimeMillis < eventStart) {
                            message = "Event hasn't started yet";
                            return Transaction.success(mutableData);
                        }

                        if(2==1)
                        {
                            message = "You arent close enough to the event";
                            return Transaction.success(mutableData);
                        }

                        userReference = firebaseDatabase.getReference("user");
                        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@android.support.annotation.NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                                    String userId = postSnapShot.getKey();
                                    DatabaseReference userRef = firebaseDatabase.getReference().child("user").child(userId);
                                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@android.support.annotation.NonNull DataSnapshot dataSnapshot) {
                                            User user = dataSnapshot.getValue(User.class);
                                            if(user.getEmail().equals(currentUser.getEmail()))
                                            {
                                                userRef.child("points").setValue(user.getPoints() + 5);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@android.support.annotation.NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@android.support.annotation.NonNull DatabaseError databaseError) {
                            }
                        });

                        message = "You have joined the event";
                        // Add the user to the participants list
                        participants.add(currentUser.getUid());
                        mutableData.setValue(participants);

                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                        if (databaseError == null) {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "Failed to join the event: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled if needed
            }
        });
    }
    private String getCurrentUserId() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            return user.getUid();
        } else {
            return "";
        }
    }
}