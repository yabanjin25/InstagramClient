package com.example.ayamanaka.instagramclient;

import android.text.Html;
import android.text.Spanned;

public class InstagramComment {
    public String text;
    public InstagramUser user;
    public String createdTime;

    public Spanned getCommentForDisplay()
    {
        String boldAuthorUsernameWithComment = "<font color=\"#517fa4\"><b>" + user.username + "</b></font> " + text;
        return Html.fromHtml(boldAuthorUsernameWithComment);
    }
}
