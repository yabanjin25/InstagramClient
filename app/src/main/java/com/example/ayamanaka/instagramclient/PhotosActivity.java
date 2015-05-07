package com.example.ayamanaka.instagramclient;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotosActivity extends ActionBarActivity {

    public static final String CLIENT_ID = "5713017ddc9d43618241418af40320f0";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        // SEND OUT API REQUEST TO POPULAR PHOTOS
        photos = new ArrayList<>();
        // Create the adapter linking it to the source
        aPhotos = new InstagramPhotosAdapter(this, photos);
        // Create the AsyncHttpClient
        client = new AsyncHttpClient();
        // Find the ListView from the layout
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        // Set the Adapter binding it to the ListView
        lvPhotos.setAdapter(aPhotos);
        // Fetch the popular photos
        fetchPopularPhotos();
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

    /**
     * Fetches the popular photos from Instagram and updates the UI with the new photos
     */
    public void fetchPopularPhotos()
    {
        // URL for fetching popular photos
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

        // Trigger the GET request
        client.get(url, null, new JsonHttpResponseHandler() {
            // onSuccess (worked)

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Expecting a JSONObject

                // Iterate each of the photo items and decode the item into a java object
                JSONArray photosJSON = null;
                try {
                    photosJSON = response.getJSONArray("data");     // array of posts
                    for (int i = 0; i < photosJSON.length(); i++) {
                        // get JSONObject at that position
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        // decode the attributes of the JSON into a data model
                        InstagramPhoto photo = new InstagramPhoto();
                        // - Author Name: { "data" => [x] => "user" => "username" }
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        // - Caption: { "data" => [x] => "caption" => "text" }
                        photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        // - Type: { "data" => [x] => "type" } ("image" or "video")
                        //photo.type = photoJSON.getJSONObject("type").getString("text");
                        // - URL: { "data" => [x] => "images" => "standard_resolution" => "url" }
                        photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        // Height
                        photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        // Likes Count
                        photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                        // Add decoded object to the photos
                        photos.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                aPhotos.notifyDataSetChanged();
            }

            // onFailure (fail)
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // DO SOMETHING
            }
        });
    }
}
