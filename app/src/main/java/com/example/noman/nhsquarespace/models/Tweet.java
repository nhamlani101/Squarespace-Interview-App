package com.example.noman.nhsquarespace.models;

/**
 * Created by Noman on 11/5/2015.
 */
public class Tweet {

    private String username;
    private String tweetText;
    private String profileImageUrl;
    private long id;


    public Tweet(String user, String text, String profileImage, long SID) {
        this.username = user;
        this.tweetText = text;
        this.profileImageUrl = profileImage;
        this.id = SID;
    }

    public String getUsername() {
        return username;
    }

    public String getTweetText() {
        return tweetText;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public long getId() {
        return id;
    }
}
