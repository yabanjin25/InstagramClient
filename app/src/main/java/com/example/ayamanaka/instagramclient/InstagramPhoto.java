package com.example.ayamanaka.instagramclient;

import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class InstagramPhoto implements Serializable {

    private static final long serialVersionUID = 5177222050535318633L;
    public String id;
    public InstagramUser user;
    public String caption;
    public ArrayList<InstagramComment> comments;
    public int commentsCount;
    public String type;
    public String imageUrl;
    public String createdTime;
    public int imageHeight;
    public int likesCount;

    public String getId()
    {
        return id;
    }

    public InstagramUser getUser()
    {
        return user;
    }

    public String getCaption()
    {
        return caption;
    }

    public ArrayList<InstagramComment> getComments()
    {
        return comments;
    }

    public int getCommentsCount()
    {
        return commentsCount;
    }

    public String getType()
    {
        return type;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public String getCreatedTime()
    {
        return createdTime;
    }

    public int getImageHeight()
    {
        return imageHeight;
    }

    public int getLikesCount()
    {
        return likesCount;
    }


    public void setId(String id)
    {
        this.id = id;
    }

    public void setUser(InstagramUser user)
    {
        this.user = user;
    }

    public void setCaption(String caption)
    {
        this.caption = caption;
    }

    public void setComments(ArrayList<InstagramComment> comments)
    {
        this.comments = comments;
    }

    public void setCommentsCount(int commentsCount)
    {
        this.commentsCount = commentsCount;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public void setCreatedTime(String createdTime)
    {
        this.createdTime = createdTime;
    }

    public void setImageHeight(int imageHeight)
    {
        this.imageHeight = imageHeight;
    }

    public void setLikesCount(int likesCount)
    {
        this.likesCount = likesCount;
    }


    public CharSequence getCreatedTimeForDisplay()
    {
        long createdTimeInt = Integer.parseInt(this.createdTime);
        CharSequence displayTime = DateUtils.getRelativeTimeSpanString(createdTimeInt * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        String displayTimeString = displayTime.toString();
        String[] displayTimeArray = displayTimeString.split(" ");
        return displayTimeArray[0] + displayTimeArray[1].charAt(0);
    }

    public String getLikesCountForDisplay()
    {
        return NumberFormat.getNumberInstance(Locale.US).format(likesCount);
    }

    public String getCommentsCountForDisplay()
    {
        return NumberFormat.getNumberInstance(Locale.US).format(commentsCount);
    }

    public Spanned getUsernameAndCaptionForDisplay()
    {
        String boldAuthorUsernameWithComment = "<font color=\"#517fa4\"><b>" + user.username + "</b></font> " + caption;
        return Html.fromHtml(boldAuthorUsernameWithComment);
    }
}
