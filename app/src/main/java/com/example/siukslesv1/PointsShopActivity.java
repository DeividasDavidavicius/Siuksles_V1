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
    private DatabaseReference userRef, titleRef;
    private int pointCount;
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> userTitles = new ArrayList<>();
    private DataSnapshot currentSnapshot;
    private static final String USERS = "user";
    private static final String TITLES = "titles";
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

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_shop);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        email = mUser.getEmail();

        database = FirebaseDatabase.getInstance("https://siuksliu-programele-default-rtdb.europe-west1.firebasedatabase.app/");
        userRef = database.getReference(USERS);
        titleRef = database.getReference(TITLES);

        settingsButton = findViewById(R.id.Settings);
        buyButton1 = findViewById(R.id.buyButton1);
        buyButton2 = findViewById(R.id.buyButton2);
        buyButton3 = findViewById(R.id.buyButton3);
        buyButton4 = findViewById(R.id.buyButton4);
        buyButton5 = findViewById(R.id.buyButton5);
        buyButton6 = findViewById(R.id.buyButton6);
        buyButton7 = findViewById(R.id.buyButton7);
        buyButton8 = findViewById(R.id.buyButton8);

        textView1 = findViewById(R.id.buyView1);
        textView2 = findViewById(R.id.buyView2);
        textView3 = findViewById(R.id.buyView3);
        textView4 = findViewById(R.id.buyView4);
        textView5 = findViewById(R.id.buyView5);
        textView6 = findViewById(R.id.buyView6);
        textView7 = findViewById(R.id.buyView7);
        textView8 = findViewById(R.id.buyView8);

        pointCountView = findViewById(R.id.pointCountView);

        titleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    titles.add(ds.getValue(String.class));
                }

                textView1.setText(titles.get(1));
                textView2.setText(titles.get(2));
                textView3.setText(titles.get(3));
                textView4.setText(titles.get(4));
                textView5.setText(titles.get(5));
                textView6.setText(titles.get(6));
                textView7.setText(titles.get(7));
                textView8.setText(titles.get(8));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()){

                    if(ds.child("email").getValue().equals(email)){
                        pointCount = ds.child("points").getValue(int.class);
                        String pointCountText = " " + String.valueOf(pointCount);
                        pointCountView.setText(pointCountText);
                        currentSnapshot = ds;

                        userTitles.clear();

                        for (DataSnapshot dsTitle : ds.child("titles").getChildren()){
                            userTitles.add(dsTitle.getValue(String.class));
                        }
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
                boolean checkSame = false;
                String currentTitle = textView1.getText().toString();

                for(int i = 0; i < userTitles.size(); i++){
                    if(currentTitle.equals(userTitles.get(i))){
                        checkSame = true;
                    }
                }

                if(pointCount > 0 && !checkSame){
                    pointCount -= 1;
                    userTitles.add(currentTitle);
                    userRef.child(key).child("points").setValue(pointCount);
                    userRef.child(key).child("titles").setValue(userTitles);
                }
                else if (pointCount <= 0 && !checkSame){
                    Toast.makeText(PointsShopActivity.this, "You don't have enough points!", Toast.LENGTH_SHORT).show();
                }
                else if(pointCount > 0 && checkSame){
                    Toast.makeText(PointsShopActivity.this, "You already have this title!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(PointsShopActivity.this, "You already have this title!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = currentSnapshot.getKey();
                boolean checkSame = false;
                String currentTitle = textView2.getText().toString();

                for(int i = 0; i < userTitles.size(); i++){
                    if(currentTitle.equals(userTitles.get(i))){
                        checkSame = true;
                    }
                }

                if(pointCount > 0 && !checkSame){
                    pointCount -= 1;
                    userTitles.add(currentTitle);
                    userRef.child(key).child("points").setValue(pointCount);
                    userRef.child(key).child("titles").setValue(userTitles);
                }
                else if (pointCount <= 0 && !checkSame){
                    Toast.makeText(PointsShopActivity.this, "You don't have enough points!", Toast.LENGTH_SHORT).show();
                }
                else if(pointCount > 0 && checkSame){
                    Toast.makeText(PointsShopActivity.this, "You already have this title!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(PointsShopActivity.this, "You already have this title!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = currentSnapshot.getKey();
                boolean checkSame = false;
                String currentTitle = textView3.getText().toString();

                for(int i = 0; i < userTitles.size(); i++){
                    if(currentTitle.equals(userTitles.get(i))){
                        checkSame = true;
                    }
                }

                if(pointCount > 0 && !checkSame){
                    pointCount -= 1;
                    userTitles.add(currentTitle);
                    userRef.child(key).child("points").setValue(pointCount);
                    userRef.child(key).child("titles").setValue(userTitles);
                }
                else if (pointCount <= 0 && !checkSame){
                    Toast.makeText(PointsShopActivity.this, "You don't have enough points!", Toast.LENGTH_SHORT).show();
                }
                else if(pointCount > 0 && checkSame){
                    Toast.makeText(PointsShopActivity.this, "You already have this title!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(PointsShopActivity.this, "You already have this title!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = currentSnapshot.getKey();
                boolean checkSame = false;
                String currentTitle = textView4.getText().toString();

                for(int i = 0; i < userTitles.size(); i++){
                    if(currentTitle.equals(userTitles.get(i))){
                        checkSame = true;
                    }
                }

                if(pointCount > 0 && !checkSame){
                    pointCount -= 1;
                    userTitles.add(currentTitle);
                    userRef.child(key).child("points").setValue(pointCount);
                    userRef.child(key).child("titles").setValue(userTitles);
                }
                else if (pointCount <= 0 && !checkSame){
                    Toast.makeText(PointsShopActivity.this, "You don't have enough points!", Toast.LENGTH_SHORT).show();
                }
                else if(pointCount > 0 && checkSame){
                    Toast.makeText(PointsShopActivity.this, "You already have this title!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(PointsShopActivity.this, "You already have this title!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = currentSnapshot.getKey();
                boolean checkSame = false;
                String currentTitle = textView5.getText().toString();

                for(int i = 0; i < userTitles.size(); i++){
                    if(currentTitle.equals(userTitles.get(i))){
                        checkSame = true;
                    }
                }

                if(pointCount > 0 && !checkSame){
                    pointCount -= 1;
                    userTitles.add(currentTitle);
                    userRef.child(key).child("points").setValue(pointCount);
                    userRef.child(key).child("titles").setValue(userTitles);
                }
                else if (pointCount <= 0 && !checkSame){
                    Toast.makeText(PointsShopActivity.this, "You don't have enough points!", Toast.LENGTH_SHORT).show();
                }
                else if(pointCount > 0 && checkSame){
                    Toast.makeText(PointsShopActivity.this, "You already have this title!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(PointsShopActivity.this, "You already have this title!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = currentSnapshot.getKey();
                boolean checkSame = false;
                String currentTitle = textView6.getText().toString();

                for(int i = 0; i < userTitles.size(); i++){
                    if(currentTitle.equals(userTitles.get(i))){
                        checkSame = true;
                    }
                }

                if(pointCount > 0 && !checkSame){
                    pointCount -= 1;
                    userTitles.add(currentTitle);
                    userRef.child(key).child("points").setValue(pointCount);
                    userRef.child(key).child("titles").setValue(userTitles);
                }
                else if (pointCount <= 0 && !checkSame){
                    Toast.makeText(PointsShopActivity.this, "You don't have enough points!", Toast.LENGTH_SHORT).show();
                }
                else if(pointCount > 0 && checkSame){
                    Toast.makeText(PointsShopActivity.this, "You already have this title!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(PointsShopActivity.this, "You already have this title!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = currentSnapshot.getKey();
                boolean checkSame = false;
                String currentTitle = textView7.getText().toString();

                for(int i = 0; i < userTitles.size(); i++){
                    if(currentTitle.equals(userTitles.get(i))){
                        checkSame = true;
                    }
                }

                if(pointCount > 0 && !checkSame){
                    pointCount -= 1;
                    userTitles.add(currentTitle);
                    userRef.child(key).child("points").setValue(pointCount);
                    userRef.child(key).child("titles").setValue(userTitles);
                }
                else if (pointCount <= 0 && !checkSame){
                    Toast.makeText(PointsShopActivity.this, "You don't have enough points!", Toast.LENGTH_SHORT).show();
                }
                else if(pointCount > 0 && checkSame){
                    Toast.makeText(PointsShopActivity.this, "You already have this title!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(PointsShopActivity.this, "You already have this title!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = currentSnapshot.getKey();
                boolean checkSame = false;
                String currentTitle = textView8.getText().toString();

                for(int i = 0; i < userTitles.size(); i++){
                    if(currentTitle.equals(userTitles.get(i))){
                        checkSame = true;
                    }
                }

                if(pointCount > 0 && !checkSame){
                    pointCount -= 1;
                    userTitles.add(currentTitle);
                    userRef.child(key).child("points").setValue(pointCount);
                    userRef.child(key).child("titles").setValue(userTitles);
                }
                else if (pointCount <= 0 && !checkSame){
                    Toast.makeText(PointsShopActivity.this, "You don't have enough points!", Toast.LENGTH_SHORT).show();
                }
                else if(pointCount > 0 && checkSame){
                    Toast.makeText(PointsShopActivity.this, "You already have this title!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(PointsShopActivity.this, "You already have this title!", Toast.LENGTH_SHORT).show();
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