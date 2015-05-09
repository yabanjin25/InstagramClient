package com.example.ayamanaka.instagramclient;

import android.text.format.DateUtils;

import java.util.ArrayList;

public class InstagramPhoto {
    public InstagramUser user;
    public String caption;
    public ArrayList<InstagramComment> comments;
    public String type;
    public String imageUrl;
    public String createdTime;
    public int imageHeight;
    public int likesCount;

    public CharSequence getCreatedTimeForDisplay()
    {
        long createdTimeInt = Integer.parseInt(this.createdTime);
        CharSequence displayTime = DateUtils.getRelativeTimeSpanString(createdTimeInt * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        String displayTimeString = displayTime.toString();
        String[] displayTimeArray = displayTimeString.split(" ");
        return displayTimeArray[0] + displayTimeArray[1].charAt(0);
    }
}
