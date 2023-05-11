package com.example.siukslesv1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;



public class aboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Element adsElement = new Element();
        adsElement.setTitle("About us");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.logo)
                .setDescription("Trashify - making a world a better place by reducing the polution in our country.")
                .addGroup("Follow us on social media!")
                .addEmail("Trashify@gmail.com")
                .addFacebook("Trashify")
                .addTwitter("Trashify")
                .addInstagram("Trashify")
                .create();
        setContentView(aboutPage);

    }
}