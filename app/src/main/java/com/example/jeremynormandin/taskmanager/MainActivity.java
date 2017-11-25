package com.example.jeremynormandin.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    static DatabaseReference databaseTasksManagement;
    static DatabaseReference usersRef;
    static DatabaseReference tasksRef;
    static DatabaseReference rewardsRef;

    static List<User> users;
    static List<Task> tasks;
    static List<Reward> rewards;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*
        users = new ArrayList<>();

        databaseTasksManagement = FirebaseDatabase.getInstance().getReferenceFromUrl("https://taskmanager-47695.firebaseio.com/");
        usersRef = databaseTasksManagement.child("users");
        usersRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        collectUsers((Map<String,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
        //tasksRef = databaseTasksManagement.child("tasks");
        //
        //
        //Ref = databaseTasksManagement.child("rewards");



*/
        Button newTask = (Button) findViewById(R.id.addNewTask);
        newTask.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                startActivity(new Intent(MainActivity.this, TaskCreation.class));
            }
        });

        Button toRewards= (Button) findViewById(R.id.toRewards);
        toRewards.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, viewReward.class));

            }
        });

    }

}
