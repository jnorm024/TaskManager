package com.example.jeremynormandin.taskmanager;

/**
 * Created by amand on 2017-12-03.
 */

public class Ressources {

    private String name;
    private String relatedTaskId;
    private String ressourceId;

    public Ressources() {}

    public Ressources(String name, String groupName, String relatedTaskId, String ressourceId) {
        this.name = name;
        this.relatedTaskId = relatedTaskId;
        this.ressourceId = ressourceId;
    }

    public String getName(){
        return this.name;
    }
    public String getRelatedTaskId(){
        return this.relatedTaskId;
    }
    public String getRessourceId() {return this.ressourceId;}
}
