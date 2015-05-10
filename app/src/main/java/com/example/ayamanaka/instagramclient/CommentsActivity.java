package com.example.ayamanaka.instagramclient;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;


public class CommentsActivity extends ActionBarActivity {

    private InstagramPhoto photo;
    private ArrayList<InstagramComment> comments;
    private InstagramCommentsAdapter aComments;
    private InstagramClient instagramClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        photo = (InstagramPhoto) getIntent().getSerializableExtra("photo");

        ImageView ivUserProfilePicture = (ImageView) findViewById(R.id.ivUserProfilePictureComments);
        TextView tvCaption = (TextView) findViewById(R.id.tvCaptionComments);
        TextView tvCreatedTime = (TextView) findViewById(R.id.tvPhotoCreatedTimeComments);

        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(90)
                .oval(false)
                .build();
        Picasso.with(this)
                .load(photo.user.profilePictureUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .transform(transformation)
                .into(ivUserProfilePicture);

        tvCaption.setText(photo.getUsernameAndCaptionForDisplay());

        tvCreatedTime.setText(photo.getCreatedTimeForDisplay());


        // Initialize InstagramClient
        this.instagramClient = new InstagramClient();
        // Setup the listener for this object
        instagramClient.setInstagramClientListener(new InstagramClient.InstagramClientListener() {
            @Override
            public void onFetchComments(ArrayList<InstagramComment> retrievedComments) {
                comments.clear();
                for (InstagramComment comment : retrievedComments) {
                    comments.add(comment);
                }
                aComments.notifyDataSetChanged();
            }
            @Override
            public void onFetchPopularPhotos(ArrayList<InstagramPhoto> popularPhotos) {
            }
        });

        // SEND OUT API REQUEST TO POPULAR PHOTOS
        comments = new ArrayList<>();
        // Create the adapter linking it to the source
        aComments = new InstagramCommentsAdapter(this, this, comments);
        // Find the ListView from the layout
        ListView lvComments = (ListView) findViewById(R.id.lvComments);
        // Set the Adapter binding it to the ListView
        lvComments.setAdapter(aComments);
        // Fetch the popular photos
        fetchPhotoComments(photo.id);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void fetchPhotoComments(String photoId)
    {
        instagramClient.fetchPhotoComments(photoId);
    }
}
