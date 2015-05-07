package com.example.ayamanaka.instagramclient;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InstagramClient {

    // Step 1 - This interface defines the type of messages I want to communicate to my owner
    public interface InstagramClientListener {
        public void onFetchPopularPhotos(ArrayList<InstagramPhoto> photos);
    }

    private static final String CLIENT_ID = "5713017ddc9d43618241418af40320f0";

    private InstagramClientListener listener;
    private AsyncHttpClient client;

    // Constructor where listener events are ignored
    public InstagramClient() {
        this.listener = null;
        this.client = new AsyncHttpClient();
    }

    public void fetchPopularPhotos() {
        // URL for fetching popular photos
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

        // Trigger the GET request
        client.get(url, null, new JsonHttpResponseHandler() {
            // onSuccess (worked)

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Expecting a JSONObject
                ArrayList<InstagramPhoto> popularPhotos = new ArrayList<>();

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
                        popularPhotos.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (listener != null) {
                    listener.onFetchPopularPhotos(popularPhotos); // <---- fire listener here
                }
            }

            // onFailure (fail)
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // DO SOMETHING
            }
        });
    }

    // Assign the listener implementing events interface that will receive the events
    public void setInstagramClientListener(InstagramClientListener listener) {
        this.listener = listener;
    }

}
