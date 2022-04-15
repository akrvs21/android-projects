package com.example.recyclerview;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserWrapper {
    @SerializedName("data") // @SerializedName is Gson annotation, so when serializing and deserializing  an object it maps specified name(in parentheses) with variable name
    ArrayList<User> users; // List of users, where each item is the type of User
    int total;
    int page;
    int limit;

    public UserWrapper(ArrayList<User> users, int total, int page, int limit) {
        this.users = users;
        this.total = total;
        this.page = page;
        this.limit = limit;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public int getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }
}
