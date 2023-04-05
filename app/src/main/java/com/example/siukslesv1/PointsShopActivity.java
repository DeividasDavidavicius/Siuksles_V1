package com.example.siukslesv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PointsShopActivity extends AppCompatActivity {

    private TextView pointCountView;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private int pointCount;
    private DataSnapshot currentSnapshot;
    private static final String USERS = "user";
    String email;
    FirebaseAuth mAuth;

    Button settingsButton;
    Button confirmButton;
    Button buyButton1;
    Button buyButton2;
    Button buyButton3;
    Button buyButton4;
    Button buyButton5;
    Button buyButton6;
    Button buyButton7;
    Button buyButton8;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_shop);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        email = mUser.getEmail();

        database = FirebaseDatabase.getInstance("https://siuksliu-programele-default-rtdb.europe-west1.firebasedatabase.app/");
        userRef = database.getReference(USERS);

        pointCountView = findViewById(R.id.pointCountView);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()){

                    if(ds.child("email").getValue().equals(email)){
                        pointCount = ds.child("points").getValue(int.class);
                        String pointCountText = " " + String.valueOf(pointCount);
                        pointCountView.setText(pointCountText);
                        currentSnapshot = ds;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId())
            {
                case R.id.camera:
                    startActivity(new Intent(getApplicationContext(),CameraActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
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
        buyButton1 = findViewById(R.id.buyButton1);
        buyButton2 = findViewById(R.id.buyButton2);
        buyButton3 = findViewById(R.id.buyButton3);
        buyButton4 = findViewById(R.id.buyButton4);
        buyButton5 = findViewById(R.id.buyButton5);
        buyButton6 = findViewById(R.id.buyButton6);
        buyButton7 = findViewById(R.id.buyButton7);
        buyButton8 = findViewById(R.id.buyButton8);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToSettings();
            }
        });

        buyButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = currentSnapshot.getKey();

                if(pointCount > 0){
                    pointCount -= 1;
                    userRef.child(key).child("points").setValue(pointCount);
                }
                else{
                    Toast.makeText(PointsShopActivity.this, "You don't have enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = currentSnapshot.getKey();

                if(pointCount > 0){
                    pointCount -= 1;
                    userRef.child(key).child("points").setValue(pointCount);
                }
                else{
                    Toast.makeText(PointsShopActivity.this, "You don't have enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = currentSnapshot.getKey();

                if(pointCount > 0){
                    pointCount -= 1;
                    userRef.child(key).child("points").setValue(pointCount);
                }
                else{
                    Toast.makeText(PointsShopActivity.this, "You don't have enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = currentSnapshot.getKey();

                if(pointCount > 0){
                    pointCount -= 1;
                    userRef.child(key).child("points").setValue(pointCount);
                }
                else{
                    Toast.makeText(PointsShopActivity.this, "You don't have enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = currentSnapshot.getKey();

                if(pointCount > 0){
                    pointCount -= 1;
                    userRef.child(key).child("points").setValue(pointCount);
                }
                else{
                    Toast.makeText(PointsShopActivity.this, "You don't have enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = currentSnapshot.getKey();

                if(pointCount > 0){
                    pointCount -= 1;
                    userRef.child(key).child("points").setValue(pointCount);
                }
                else{
                    Toast.makeText(PointsShopActivity.this, "You don't have enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = currentSnapshot.getKey();

                if(pointCount > 0){
                    pointCount -= 1;
                    userRef.child(key).child("points").setValue(pointCount);
                }
                else{
                    Toast.makeText(PointsShopActivity.this, "You don't have enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = currentSnapshot.getKey();

                if(pointCount > 0){
                    pointCount -= 1;
                    userRef.child(key).child("points").setValue(pointCount);
                }
                else{
                    Toast.makeText(PointsShopActivity.this, "You don't have enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = currentSnapshot.getKey();

                if(pointCount > 0){
                    pointCount -= 1;
                    userRef.child(key).child("points").setValue(pointCount);
                }
                else{
                    Toast.makeText(PointsShopActivity.this, "You don't have enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = currentSnapshot.getKey();

                if(pointCount > 0){
                    pointCount -= 1;
                    userRef.child(key).child("points").setValue(pointCount);
                }
                else{
                    Toast.makeText(PointsShopActivity.this, "You don't have enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void switchToSettings() {
        Intent switchActivityIntent = new Intent(this, settingsActivity.class);
        startActivity(switchActivityIntent);
    }

    private void switchToThis() {
        Intent switchActivityIntent = new Intent(this, PointsShopActivity.class);
        startActivity(switchActivityIntent);
    }
}