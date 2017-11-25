package com.example.jeremynormandin.taskmanager;

/**
 * Created by amand on 2017-11-24.
 */

public class Reward {
    private String rewardId;
    private String name;
    private String description;

    public Reward(String rewardId, String name, String description) {
        this.rewardId = rewardId;
        this.name = name;
        this.description = description;
    }

    public String getRewardId() {return this.rewardId;}
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
}
