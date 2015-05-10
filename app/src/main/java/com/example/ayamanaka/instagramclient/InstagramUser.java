package com.example.ayamanaka.instagramclient;

import java.io.Serializable;

public class InstagramUser implements Serializable {

    private static final long serialVersionUID = 5177222050535318635L;
    public String id;
    public String username;
    public String fullName;
    public String profilePictureUrl;

    public String getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public String getFullName()
    {
        return fullName;
    }

    public String getProfilePictureUrl()
    {
        return profilePictureUrl;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public void setProfilePictureUrl(String profilePictureUrl)
    {
        this.profilePictureUrl = profilePictureUrl;
    }
}
