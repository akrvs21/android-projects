package com.example.yrocery.POJO;

public class User {
    private String Name;
    private String Password;

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public User() {
    }

    public User(String name, String password) {
        Name = name;
        Password = password;
    }
}
