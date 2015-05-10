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
        public void onFetchComments(ArrayList<InstagramComment> comments);
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
                        InstagramPhoto photo = getInstagramPhotoFromJSONObject(photoJSON);
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

    public void fetchPhotoComments(String photoId) {
        // URL for fetching popular photos
        String url = "https://api.instagram.com/v1/media/" + photoId + "/comments?client_id=" + CLIENT_ID;

        // Trigger the GET request
        client.get(url, null, new JsonHttpResponseHandler() {
            // onSuccess (worked)

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Expecting a JSONObject
                ArrayList<InstagramComment> retrievedComments = new ArrayList<>();

                // Iterate each of the photo items and decode the item into a java object
                JSONArray photosJSON = null;
                try {
                    photosJSON = response.getJSONArray("data");     // array of posts
                    for (int i = 0; i < photosJSON.length(); i++) {
                        // get JSONObject at that position
                        JSONObject commentJSON = photosJSON.getJSONObject(i);
                        // decode the attributes of the JSON into a data model
                        InstagramComment comment = getInstagramCommentFromJSONObject(commentJSON);
                        retrievedComments.add(comment);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (listener != null) {
                    listener.onFetchComments(retrievedComments); // <---- fire listener here
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

    protected InstagramPhoto getInstagramPhotoFromJSONObject(JSONObject photoJSONObject)
    {
        try {
            InstagramPhoto photo = new InstagramPhoto();
            // - Id: { "data" => [x] => "id" }
            photo.id = photoJSONObject.getString("id");
            // - Author Name: { "data" => [x] => "user" }
            photo.user = getInstagramUserFromJSONObject(photoJSONObject.getJSONObject("user"));
            // - Caption: { "data" => [x] => "caption" => "text" }
            photo.caption = getCaptionFromJSONObject(photoJSONObject);
            // - Comments: { "data" => [x] => "comments" => "data" => [x] }
            photo.comments = getInstagramCommentsFromJSONArray(photoJSONObject.getJSONObject("comments").getJSONArray("data"));
            // - Comments: { "data" => [x] => "comments" => "count" }
            photo.commentsCount = photoJSONObject.getJSONObject("comments").getInt("count");
            // - Type: { "data" => [x] => "type" } ("image" or "video")
            photo.type = photoJSONObject.getString("type");
            // - URL: { "data" => [x] => "images" => "standard_resolution" => "url" }
            photo.imageUrl = photoJSONObject.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
            // - Created Time: { "data" => [x] => "created_time"}
            photo.createdTime = photoJSONObject.getString("created_time");
            // Height
            photo.imageHeight = photoJSONObject.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
            // Likes Count
            photo.likesCount = photoJSONObject.getJSONObject("likes").getInt("count");
            // Add decoded object to the photos
            return photo;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected InstagramUser getInstagramUserFromJSONObject(JSONObject userJSONObject)
    {
        try {
            InstagramUser user = new InstagramUser();
            user.id = userJSONObject.getString("id");
            user.username = userJSONObject.getString("username");
            user.fullName = userJSONObject.getString("full_name");
            user.profilePictureUrl = userJSONObject.getString("profile_picture");

            return user;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected ArrayList<InstagramComment> getInstagramCommentsFromJSONArray(JSONArray commentsJSONArray)
    {
        try {
            ArrayList<InstagramComment> commentsArray = new ArrayList<>();

            for (int i = 0; i < commentsJSONArray.length(); i++) {
                JSONObject commentJSONObject = commentsJSONArray.getJSONObject(i);
                InstagramComment instagramComment = getInstagramCommentFromJSONObject(commentJSONObject);
                commentsArray.add(instagramComment);
            }

            return commentsArray;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected InstagramComment getInstagramCommentFromJSONObject(JSONObject jsonObject)
    {
        try {
            InstagramComment comment = new InstagramComment();
            comment.user = getInstagramUserFromJSONObject(jsonObject.getJSONObject("from"));
            comment.text = jsonObject.getString("text");
            comment.createdTime = jsonObject.getString("created_time");
            return comment;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected String getCaptionFromJSONObject(JSONObject jsonObject)
    {
        try {
            JSONObject captionObject = jsonObject.optJSONObject("caption");
            if (captionObject == null) {
                return "";
            } else {
                return captionObject.getString("text");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
