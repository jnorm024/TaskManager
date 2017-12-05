package com.example.jeremynormandin.taskmanager;

import java.io.Serializable;

/**
 * Created by amand on 2017-11-24.
 */

public class Task implements Serializable {
    private String taskId;
    private String name;
    private String details;
    private boolean isAssigned = false;
    private boolean hasReward = false;
    private boolean hasRessource = false;
    private String dueDate;
    private boolean isAccomplished = false;
    private boolean isRepeated;
    private int points;
    private String group;

    private String assignedUserId;
    private String associatedRewardId;

    public Task() {}

    public Task(String taskId, String name, String details, String dueDate, boolean isRepeated, int points, String group) {
        this.taskId = taskId;
        this.name = name;
        this.details = details;
        this.dueDate = dueDate;
        this.isRepeated = isRepeated;
        this.points = points;
        this.group = group;
    }

    public String getTaskId() {return this.taskId;}
    public String getName(){
        return this.name;
    }
    public String getDetails(){
        return this.details;
    }
    public boolean getIsAssigned(){
        return this.isAssigned;
    }
    public String getDueDate(){
        return this.dueDate;
    }
    public boolean getIsAccomplished(){
        return this.isAccomplished;
    }
    public boolean getIsRepeated(){
        return this.isRepeated;
    }
    public String getAssignedUserId() {return this.assignedUserId;}
    public String getAssociatedRewardId() {return this.associatedRewardId;}
    public boolean gethasReward() {return this.hasReward;}
    public boolean getHasRessource() {return hasRessource;}

    public void setAssignedUserId(String id) {
        isAssigned = true;
        assignedUserId = id;
    }

    public void setIsUnassigned(){
        isAssigned = false;
    }
    public void setAssociatedRewardId(String id) {
        hasReward = true;
        associatedRewardId = id;
    }
    public void setDueDate(String year, String month, String day){
        dueDate= year+month+day;
    }

    public void setAccomplished() {
        isAccomplished = true;
    }
    public void reset() { isAccomplished = false; }

    public int getPoints() {
        return this.points;
    }

    public String getGroup() { return this.group; }

    public void setHasRessource(boolean hasRessource) {this.hasRessource = hasRessource;}
}




