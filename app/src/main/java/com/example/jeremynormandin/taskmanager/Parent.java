package com.example.jeremynormandin.taskmanager;

/**
 * Created by amand on 2017-11-24.
 */

public class Parent extends User {
    private boolean isParent = true;

    public Parent(String userId, String name, String password, boolean isParent) {
        super(userId, name, password, isParent );
    }
    public boolean getIsParent() {return isParent;}


}
