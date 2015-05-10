package com.example.ayamanaka.instagramclient;

import android.content.Context;
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

public class InstagramCommentsAdapter extends ArrayAdapter<InstagramComment> {

    private final int REQUEST_CODE = 20;
    private final Context context;
    private final List<InstagramComment> comments;
    private CommentsActivity activity;

    // What data do we need from the activity
    // Context, Data Source
    public InstagramCommentsAdapter(Context context, CommentsActivity activity, List<InstagramComment> comments) {
        super(context, android.R.layout.simple_list_item_1, comments);
        this.context = context;
        this.activity = activity;
        this.comments = comments;
    }
    // What our item looks like
    // Use the template to display each photo


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        // Get the data item for this position
        InstagramComment comment = getItem(position);
        // Check if we are using a recycled view, if not we need to inflate
        if (convertView == null) {
            // create a new view from template
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
        }

        // Look up the views for populating the data (image, caption)
        ImageView ivCommenterProfilePicture = (ImageView) convertView.findViewById(R.id.ivCommenterProfilePicture);
        TextView tvCommenterComment = (TextView) convertView.findViewById(R.id.tvCommenterComment);
        TextView tvCommentCreatedTime = (TextView) convertView.findViewById(R.id.tvCommentCreatedTime);


        // Clear out the ImageView if it was recycled (right away)
        ivCommenterProfilePicture.setImageResource(0);
        // Insert the image using picasso (send out async)
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(90)
                .oval(false)
                .build();
        Picasso.with(getContext())
                .load(comment.user.profilePictureUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .transform(transformation)
                .into(ivCommenterProfilePicture);

        // Insert the model data into each of the view items
        tvCommenterComment.setText(comment.getCommentForDisplay());
        tvCommentCreatedTime.setText(comment.getCreatedTimeForDisplay());

        return convertView;
    }

}
