package com.example.ayamanaka.instagramclient;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class PhotosActivity extends ActionBarActivity {

    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        // SEND OUT API REQUEST TO POPULAR PHOTOS
        photos = new ArrayList<>();
        // Create the adapter linking it to the source
        aPhotos = new InstagramPhotosAdapter(this, photos);
        // Find the ListView from the layout
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        // Set the Adapter binding it to the ListView
        lvPhotos.setAdapter(aPhotos);
        // Fetch the popular photos

        // Create InstagramClient
        InstagramClient instagramClient = new InstagramClient();
        // Step 4 - Setup the listener for this object
        instagramClient.setInstagramClientListener(new InstagramClient.InstagramClientListener() {
            @Override
            public void onFetchPopularPhotos(ArrayList<InstagramPhoto> popularPhotos) {
                photos.clear();
                for (InstagramPhoto photo : popularPhotos) {
                    photos.add(photo);
                }
                aPhotos.notifyDataSetChanged();
            }
        });

        // Fetch the popular photos
        instagramClient.fetchPopularPhotos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
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
}
