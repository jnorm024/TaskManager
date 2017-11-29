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
    private String dueDate;
    private boolean isAccomplished = false;
    private boolean rewardClaimed = false;
    private boolean isRepeated;

    private String assignedUserId;
    private String associatedRewardId;

    public Task() {}

    public Task(String taskId, String name, String details, String dueDate, boolean isRepeated) {
        this.taskId = taskId;
        this.name = name;
        this.details = details;
        this.dueDate = dueDate;
        this.isRepeated = isRepeated;
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
    public boolean getRewardClaimed(){
        return this.rewardClaimed;
    }
    public boolean getIsRepeated(){
        return this.isRepeated;
    }
    public String getAssignedUserId() {return this.assignedUserId;}
    public String getAssociatedRewardId() {return this.associatedRewardId;}

    public void setAssignedUserId(String id) {
        isAssigned = true;
        assignedUserId = id;
    }
    public void setAssociatedRewardId(String id) {
        associatedRewardId = id;
    }
    public void setAccomplished() {
        isAccomplished = true;
    }
    public void setRewardClaimed() {
        rewardClaimed = true;
    }
}
