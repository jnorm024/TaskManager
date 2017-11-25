package com.example.jeremynormandin.taskmanager;

/**
 * Created by amand on 2017-11-24.
 */

public class Task {
    private String taskId;
    private String name;
    private String details;
    private boolean isBonus;
    private boolean isAssigned;
    private String dueDate;
    private boolean isAccomplished;
    private boolean rewardClaimed;
    private boolean isRepeated;

    public Task(String taskId, String name, String details, String dueDate) {
        this.taskId = taskId;
        this.name = name;
        this.details = details;
        this.dueDate = dueDate;
    }

    public String getTaskId() {return this.taskId;}
    public String getName(){
        return this.name;
    }
    public String getDetails(){
        return this.details;
    }
    public boolean getIsBonus(){
        return this.isBonus;
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
}
