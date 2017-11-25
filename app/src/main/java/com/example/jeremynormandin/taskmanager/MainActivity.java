package com.example.jeremynormandin.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private void collectUsers(Map<String,Object> users) {

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            this.users.add((User) singleUser);
        }
    }

    public static void addUserToDatabase (String name, String password, boolean isParent) {
        //getting a unique id using push().getKey() method
        //it will create a unique id and we will use it as the Primary Key for our Product
        String id = usersRef.push().getKey();

        //creating an Product Object
        User newUser;
        if(!isParent) newUser = new User(id, name, password);
        else newUser = new Parent(id, name, password);


        //Saving the Product
        usersRef.child(id).setValue(newUser);
    }


    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
