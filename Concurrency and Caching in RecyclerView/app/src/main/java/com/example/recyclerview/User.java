package com.example.recyclerview;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    String userId;
    String title;
    String firstName;
    String lastName;
    @SerializedName("picture")
    String userImage;

    public User(String userId, String title, String firstName, String lastName, String userImage) {
        this.userId = userId;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userImage = userImage;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserImage() {
        return userImage;
    }
}
