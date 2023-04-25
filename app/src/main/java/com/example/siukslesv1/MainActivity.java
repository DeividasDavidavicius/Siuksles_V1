package com.example.siukslesv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siukslesv1.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;
    Button settingsButton;
    Button votedPostsButton;
    Button profileButton;
    FirebaseUser user;

    PostAdapter postAdapter;

    RecyclerView PostRecyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference timerReference;
    DatabaseReference eventReference;
    List<Post> postList;
    TextView timer;
    Date currentTime;
    private String email;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if(user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_main);

        PostRecyclerView = findViewById(R.id.postRV);

        //TIMERIS (REIKES PAKEIST, DABAR LAIKINAS)
        timer = findViewById(R.id.timer);
        long timerDuration = TimeUnit.MINUTES.toMillis(120);
        new CountDownTimer(timerDuration, 1000)
        {

            @Override
            public void onTick(long l) {
                int hours = (int)(l/1000/60/60);
                int minutes = (int) (l - hours * 60 * 60 * 1000)/(1000 * 60);
                int seconds = (int) (l - minutes * 60 * 1000 - hours * 60 * 60 * 1000) / 1000;
                timer.setText(hours + "h "+ minutes + "min " + seconds + "s");
            }

            @Override
            public void onFinish() {
                timer.setText("Done");
            }
        }.start();

        PostRecyclerView.setHasFixedSize(true);
        PostRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        firebaseDatabase = FirebaseDatabase.getInstance("https://siuksliu-programele-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference("posts");
        eventReference = firebaseDatabase.getReference("events");

        // TIMER LEFT
        currentTime = Calendar.getInstance().getTime();
        timerReference = firebaseDatabase.getReference("timer");
        timerReference.child("timerDate").setValue(currentTime.toString());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList = new ArrayList<>();

                for (DataSnapshot postsnap : snapshot.getChildren()){
                    Post post = postsnap.getValue(Post.class);
                    postList.add(post);
                }
                postAdapter = new PostAdapter(MainActivity.this,postList);
                Collections.reverse(postList);
                PostRecyclerView.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId())
            {
                case R.id.camera:
                    startActivity(new Intent(getApplicationContext(),CameraActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.map:
                    startActivity(new Intent(getApplicationContext(),mapActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });
        settingsButton = findViewById(R.id.Settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToSettings();
            }
        });

        votedPostsButton = findViewById(R.id.VotedPosts);
        votedPostsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToVotedPosts();
            }
        });


        profileButton = findViewById(R.id.Profile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToProfile();
            }
        });

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if(user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }


        // NUOTRAUKOS

        //selectedImage = findViewById(R.id.displayImageView);
        //cameraBtn = findViewById(R.id.cameraBtn);
/*
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Camera Btn is Clicked", Toast.LENGTH_SHORT).show();
            }
        }); */

        email = user.getEmail();
        String eventID = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        long currentEventTime = calendar.getTimeInMillis();
        event = new Event(email, "Test1223", "Location", "https://firebasestorage.googleapis.com/v0/b/siuksliu-programele.appspot.com/o/images%2FJPEG_20230319_192028.jpg?alt=media&token=ec74a9ca-5983-453e-8c9f-93b0c7f6f656", 1, currentEventTime, eventID);
        String keyID = eventReference.push().getKey();
        eventReference.child(keyID).setValue(event);
    }
    private void switchToSettings() {
        Intent switchActivityIntent = new Intent(this, settingsActivity.class);
        startActivity(switchActivityIntent);
    }
    private void switchToProfile() {
        Intent switchActivityIntent = new Intent(this, profileActivity.class);
        startActivity(switchActivityIntent);
    }

    private void switchToVotedPosts() {
        Intent switchActivityIntent = new Intent(this, VotedPosts.class);
        startActivity(switchActivityIntent);
    }
}