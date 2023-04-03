package com.example.siukslesv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;

public class PostDetailsActivity extends AppCompatActivity {
    private TextView mTitleTextView;
    private TextView mVotesTextView;
    private TextView locationTextView;

    private ImageView imagePostView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        // Get the post data from the Intent
        String postTitle = getIntent().getStringExtra("post_title");
        String postVotes = getIntent().getStringExtra("post_votes");
        String postLocation = getIntent().getStringExtra("post_location");
        String imagePost = getIntent().getStringExtra("post_image");

        // Initialize the views in the layout
        mTitleTextView = findViewById(R.id.row_post_title);
        mVotesTextView = findViewById(R.id.votes);
        locationTextView = findViewById(R.id.location);
        imagePostView = findViewById(R.id.row_post_image);

        // Set the views with the post data
        mTitleTextView.setText(postTitle);
        mVotesTextView.setText(postVotes);
        locationTextView.setText(postLocation);
        Picasso.get().load(imagePost).into(imagePostView);
    }
}