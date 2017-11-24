package com.example.taskmanager;

/**
 * Created by amand on 2017-11-24.
 */

public class Task {
    static String name;
    static String details;
    static boolean isBonus;
    static boolean isAssigned;
     //Guillaume quand tu sauras si on peut mettre des Date
    static String dueDate;
    static boolean isAccomplished;
    static boolean rewardClaimed;
    static boolean isRepeated;

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
