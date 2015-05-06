package com.example.ayamanaka.instagramclient;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ayamanaka on 5/5/15.
 */
public class InstagramAPI
{
    private static final String CLIENT_ID = "5713017ddc9d43618241418af40320f0";
    private AsyncHttpClient asyncHttpClient;
    //private ArrayList<InstagramPhoto> popularPhotos;

    public InstagramAPI()
    {
        asyncHttpClient = new AsyncHttpClient();
        //popularPhotos = new ArrayList<>();
    }

    /**public void getUser(String username)
    {
        String url = "https://api.instagram.com/v1/users/" + username + "/?client_id=" + CLIENT_ID;

        // Trigger the GET request
        asyncHttpClient.get(url, null, new JsonHttpResponseHandler() {

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
                        //photos.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // onFailure (fail)

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // DO SOMETHING
            }
        });
    }*/

    public ArrayList<InstagramPhoto> fetchPopularPhotos()
    {
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        //popularPhotos = new ArrayList<>();
        final ArrayList<InstagramPhoto> popularPhotos = new ArrayList<>();
        //Log.i("DEBUG", popularPhotos.toString());

        // Trigger the GET request
        asyncHttpClient.get(url, null, new JsonHttpResponseHandler() {
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

                        popularPhotos.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            // onFailure (fail)
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // DO SOMETHING
            }

        });
        return popularPhotos;
    }

}
