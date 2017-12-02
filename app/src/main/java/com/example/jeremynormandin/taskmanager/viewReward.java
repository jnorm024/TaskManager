package com.example.jeremynormandin.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class viewReward extends AppCompatActivity {

    Spinner viewRewardFor;
    Button refresh;

    private DatabaseReference databaseTasksManagement;
    private DatabaseReference rewardsRef;

    static HashMap<String, Reward> rewards;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reward);
        getSupportActionBar().setTitle("Rewards List");

        final Integer spinnerUser = (Integer) getIntent().getSerializableExtra("user");

        viewRewardFor();
        viewRewardFor.setSelection(spinnerUser);

        final ListView rewardList= (ListView) findViewById(R.id.rewardList);
        final ArrayList<String> list= new ArrayList<String>();
        rewards = new HashMap<>();
        databaseTasksManagement = FirebaseDatabase.getInstance().getReferenceFromUrl("https://taskmanager-47695.firebaseio.com/");
        rewardsRef = databaseTasksManagement.child("rewards");
        rewardsRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //clear previous user list
                        rewards.clear();
                        //access to all user in database
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //getting user
                            Reward reward =  postSnapshot.getValue(Reward.class);
                            //adding user to the list
                            rewards.put(reward.getRewardId(), reward);
                        }

                        User selectedUser = getSelectedUser();
                        final ArrayList<Reward> userRewards = getRewardsList(selectedUser);

                        for (Reward reward : userRewards){
                            list.add(reward.getName());
                        }
                        ArrayAdapter adapter = new ArrayAdapter(viewReward.this, android.R.layout.simple_list_item_1, list);
                        rewardList.setAdapter(adapter);
                        rewardList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                            public void onItemClick(AdapterView<?> list, View v, int pos, long id){
                                for(Reward reward : userRewards) {
                                    if(rewardList.getItemAtPosition(pos).equals(reward.getName())) {
                                        Intent myIntent = new Intent(viewReward.this, giveRewards.class);
                                        myIntent.putExtra("reward",reward);
                                        startActivity(myIntent);
                                        finish();
                                    }
                                }

                            }

                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });




        refresh = (Button) findViewById(R.id.refreshButton);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(viewReward.this, viewReward.class);
                int spinnerIndex = viewRewardFor.getSelectedItemPosition();
                myIntent.putExtra("user", spinnerIndex);
                startActivity(myIntent);
                finish();
            }
        });

        Button cancelButton= (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(viewReward.this, PrincipalActivity.class));
            }
        });



    }

    public void viewRewardFor() {
        viewRewardFor = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();

        if(PrincipalActivity.getLoginUser().getIsParent()) {

            for (User user : LoginActivity.users) {
                list.add(user.getName());
                System.out.println(user.getName());
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        viewRewardFor.setAdapter(dataAdapter);
    }
    private User getSelectedUser() {
        for(User user : LoginActivity.users) {
            if(user.getName().equals(viewRewardFor.getSelectedItem().toString())) {
                return user;
            }
        }
        System.err.println("Error : user not found");
        return null;
    }

    private ArrayList<Reward> getRewardsList(User user) {
        ArrayList<Task> userRewardedTasks = new ArrayList<>();
        for(Task task : PrincipalActivity.tasks) {
            if(user.getUserId().equals(task.getAssignedUserId()) && task.getAssociatedRewardId()!= null && task.getIsAccomplished()) {
                userRewardedTasks.add(task);
            }
        }
        ArrayList<Reward> userRewards = new ArrayList<>();
        for(Task task : userRewardedTasks) {
            userRewards.add(rewards.get(task.getAssociatedRewardId()));
        }
        return userRewards;
    }

    /* Method taken from https://stackoverflow.com/questions/6514657/prevent-back-button-from-closing-my-application
    */
    public void onBackPressed() {
        // do nothing. We want to force user to stay in this activity and not drop out.
    }


}
