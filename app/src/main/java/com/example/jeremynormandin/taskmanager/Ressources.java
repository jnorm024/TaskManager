package com.example.jeremynormandin.taskmanager;

/**
 * Created by amand on 2017-12-03.
 */

public class Ressources {

    private String name;
    private String relatedTaskId;

    public Ressources(String name, String groupName, String relatedTaskId) {
        this.name = name;
        this.relatedTaskId = relatedTaskId;
    }

    public String getName(){
        return this.name;
    }
    public String getRelatedTaskId(){
        return this.relatedTaskId;
    }
}
