package com.example.ayamanaka.instagramclient;

import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;

import java.io.Serializable;

public class InstagramComment implements Serializable {
    private static final long serialVersionUID = 5177222050535318634L;

    public String text;
    public InstagramUser user;
    public String createdTime;

    public String getText()
    {
        return text;
    }

    public InstagramUser getUser()
    {
        return user;
    }

    public String getCreatedTime()
    {
        return createdTime;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setUser(InstagramUser user)
    {
        this.user = user;
    }

    public void setCreatedTime(String createdTime)
    {
        this.createdTime = createdTime;
    }

    public Spanned getCommentForDisplay()
    {
        String boldAuthorUsernameWithComment = "<font color=\"#517fa4\"><b>" + user.username + "</b></font> " + text;
        return Html.fromHtml(boldAuthorUsernameWithComment);
    }

    public CharSequence getCreatedTimeForDisplay()
    {
        long createdTimeInt = Integer.parseInt(this.createdTime);
        CharSequence displayTime = DateUtils.getRelativeTimeSpanString(createdTimeInt * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        String displayTimeString = displayTime.toString();
        String[] displayTimeArray = displayTimeString.split(" ");
        return displayTimeArray[0] + displayTimeArray[1].charAt(0);
    }
}
