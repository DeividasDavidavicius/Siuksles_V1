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
        adsElement.setTitle("bbzn");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.logo)
                .setDescription("We hope to reduce the pollution in our world!")
                .addItem(new Element().setTitle("Version 1.0"))
                .addGroup("Connect with us")
                .addEmail("siukslesrenkam@gmail.com")
                .addWebsite("http://edmtdev.com")
                .addFacebook("EDMTDev")
                .addTwitter("My Twitter")
                .create();
        setContentView(aboutPage);

    }
}