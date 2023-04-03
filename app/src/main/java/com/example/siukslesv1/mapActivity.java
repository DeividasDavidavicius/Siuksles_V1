package com.example.siukslesv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveCanceledListener;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveListener;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class mapActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        OnCameraMoveStartedListener,
        OnCameraMoveCanceledListener,
        OnCameraIdleListener,
        OnCameraMoveListener {

    Button settingsButton;
    Button profileButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.map);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId())
            {
                case R.id.camera:
                    startActivity(new Intent(getApplicationContext(),CameraActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.map:
                    return true;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
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
        profileButton = findViewById(R.id.Profile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToProfile();
            }
        });
    }
    private void switchToSettings() {
        Intent switchActivityIntent = new Intent(this, settingsActivity.class);
        startActivity(switchActivityIntent);
    }
    private void switchToProfile() {
        Intent switchActivityIntent = new Intent(this, profileActivity.class);
        startActivity(switchActivityIntent);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        map.setOnCameraIdleListener(this);
        map.setOnCameraMoveStartedListener(this);
        map.setOnCameraMoveListener(this);
        map.setOnCameraMoveCanceledListener(this);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(54.8985, 23.9036), 6));


        firebaseDatabase = FirebaseDatabase.getInstance("https://siuksliu-programele-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference("posts");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot markerSnap : snapshot.getChildren()){
                    Post post = markerSnap.getValue(Post.class);
                    assert post != null;
                    String[] arrayOfStr = post.getLocation().split(" ", 5);
                    for (int i = 0; i < arrayOfStr.length; i++)
                    {
                        arrayOfStr[i] = arrayOfStr[i].replace(',', '.');
                    }
                    double lat = Double.parseDouble(arrayOfStr[0]);
                    double lon = Double.parseDouble(arrayOfStr[1]);
                    LatLng position = new LatLng(lat, lon);
                    map.addMarker(new MarkerOptions()
                            .position(position)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .title(post.getName()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onCameraMoveStarted(int i) {

    }

    @Override
    public void onCameraIdle() {

    }

    @Override
    public void onCameraMoveCanceled() {

    }

    @Override
    public void onCameraMove() {

    }
}