package com.example.siukslesv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class settingsActivity extends AppCompatActivity {

    private ImageButton backButton;
    private ImageButton contactUsButton;
    private ImageButton aboutUsButton;
    private androidx.appcompat.widget.AppCompatButton logoutButton;
    private androidx.appcompat.widget.SwitchCompat themeBtn;

    FirebaseAuth auth;
    Button buttonLogout;
    FirebaseUser user;
    Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backButton = (ImageButton) findViewById(R.id.homePage1);
        //contactUsButton = (ImageButton) findViewById(R.id.contactUs);
        aboutUsButton = (ImageButton) findViewById(R.id.aboutUs);
        logoutButton = (androidx.appcompat.widget.AppCompatButton) findViewById(R.id.logout);
        themeBtn = (androidx.appcompat.widget.SwitchCompat) findViewById(R.id.switchBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });
        /*
        contactUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContactUsActivity();
            }
        })
        */
        aboutUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAboutUsActivity();
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(view);
            }
        });
        themeBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(themeBtn.isChecked()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
    }
    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void openContactUsActivity(){
        Intent intent = new Intent(this, contactUsActivity.class);
        startActivity(intent);
    }
    public void openAboutUsActivity(){
        Intent intent = new Intent(this, aboutUsActivity.class);
        startActivity(intent);
    }
    public void logout(View view)
    {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }
}