package com.example.ayamanaka.instagramclient;

import android.content.Context;
import android.content.Intent;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    private final int REQUEST_CODE = 20;
    private final Context context;
    private final List<InstagramPhoto> photos;
    private PhotosActivity activity;

    // What data do we need from the activity
    // Context, Data Source
    public InstagramPhotosAdapter(Context context, PhotosActivity activity, List<InstagramPhoto> photos) {
        super(context, android.R.layout.simple_list_item_1, photos);
        this.context = context;
        this.activity = activity;
        this.photos = photos;
    }
    // What our item looks like
    // Use the template to display each photo


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        // Get the data item for this position
        InstagramPhoto photo = getItem(position);
        // Check if we are using a recycled view, if not we need to inflate
        if (convertView == null) {
            // create a new view from template
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        // Look up the views for populating the data (image, caption)
        TextView tvUsernameMain = (TextView) convertView.findViewById(R.id.tvUsernameMain);
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvCreatedTime = (TextView) convertView.findViewById(R.id.tvCreatedTime);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        TextView tvViewAllComments = (TextView) convertView.findViewById(R.id.tvViewAllComments);

        tvViewAllComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, CommentsActivity.class);
                InstagramPhoto photo = photos.get(pos);
                i.putExtra("photo", photo);
                activity.startActivityForResult(i, REQUEST_CODE);
            }
        });

        TextView tvComment = (TextView) convertView.findViewById(R.id.tvComment);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView ivUserProfilePicture = (ImageView) convertView.findViewById(R.id.ivUserProfilePicture);

        // Insert the model data into each of the view items
        String authorUsername = photo.user.username;
        tvCreatedTime.setText(photo.getCreatedTimeForDisplay());
        tvLikes.setText(photo.getLikesCountForDisplay() + " likes");
        tvViewAllComments.setText("view all " + photo.getCommentsCountForDisplay() + " comments");

        if (photo.comments.size() > 0) {
            tvComment.setText(photo.comments.get(0).getCommentForDisplay());
        } else {
            tvComment.setVisibility(View.GONE);
        }

        tvUsernameMain.setText(authorUsername);
        Spanned boldAuthorUsernameWithCaption = photo.getUsernameAndCaptionForDisplay();
        tvCaption.setText(boldAuthorUsernameWithCaption);
        // Clear out the ImageView if it was recycled (right away)
        ivPhoto.setImageResource(0);
        // Insert the image using picasso (send out async)
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(90)
                .oval(false)
                .build();
        Picasso.with(getContext())
                .load(photo.imageUrl)
                .placeholder(R.drawable.blue_instagram_icon)
                .error(R.drawable.blue_instagram_icon)
                .into(ivPhoto);
        Picasso.with(getContext())
                .load(photo.user.profilePictureUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .transform(transformation)
                .into(ivUserProfilePicture);
        // Return the created item as a view
        return convertView;
    }

}
