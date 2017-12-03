package com.example.jeremynormandin.taskmanager;

/**
 * Created by guillaume on 2017-12-03.
 */

public class Group {
    private String name;
    private String groupId;

    public Group() {}

    public Group(String name, String groupId) {
        this.name = name;
        this.groupId = groupId;
    }

    public String getName() {return name;}
    public String getGroupId() {return groupId;}
}
