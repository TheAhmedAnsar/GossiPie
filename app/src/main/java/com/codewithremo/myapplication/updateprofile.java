package com.codewithremo.myapplication;

public class updateprofile {


    public  String username , userUID;

// Empty Constructor

    public updateprofile() {
    }

    // Constructor
    public updateprofile(String username, String userUID) {
        this.username = username;
        this.userUID = userUID;
    }

    // Getter and Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }
}

