package com.example.jeremynormandin.taskmanager;

/**
 * Created by amand on 2017-11-23.
 */

public class User {
    private String userId;
    private String name;
    private String password;
    private boolean isParent = false;

    public User() {}

    public User(String userId, String name, String password){
        this.userId = userId;
        this.name = name;
        this.password = password;
    }

    public String getUserId() {return this.userId;}
    public String getName(){
        return this.name;
    }
    public String getPassword(){
        return this.password;
    }
    public boolean getIsParent() {return this.isParent;}
}
