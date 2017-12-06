package com.example.jeremynormandin.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class giveRewards extends AppCompatActivity {

    TextView rewardName;
    TextView rewardDesc;
    Button giveButton;
    Button cancelButton;

    private DatabaseReference databaseTasksManagement;
    private DatabaseReference rewardsRef;
    private DatabaseReference tasksRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_rewards);
        getSupportActionBar().setTitle("Give a reward");
        //Instantiate the database
        databaseTasksManagement = FirebaseDatabase.getInstance().getReferenceFromUrl("https://taskmanager-47695.firebaseio.com/");
        tasksRef = databaseTasksManagement.child("tasks");
        rewardsRef = databaseTasksManagement.child("rewards");
        //getting the reward selected in the reward list from viewReward
        final Reward reward = (Reward) getIntent().getSerializableExtra("reward");

        rewardName = (TextView) findViewById(R.id.rewardName);
        rewardName.setText(reward.getName());

        rewardDesc = (TextView) findViewById(R.id.rewardDesc);
        rewardDesc.setText(reward.getDescription());

        giveButton = (Button) findViewById(R.id.giveButton);
        giveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //This loop search for the task associated with this reward
                for(Task task : PrincipalActivity.tasks) {
                    if(reward.getRewardId().equals(task.getAssociatedRewardId())) {
                        //Once the task is found, the reward is removed
                        // the task is reset if it is repetitive and deleted otherwise
                        if(!task.getIsRepeated()) {
                            tasksRef.child(task.getTaskId()).removeValue();
                            rewardsRef.child(reward.getRewardId()).removeValue();
                            viewReward.rewards.remove(reward.getRewardId());
                        } else {
                            PrincipalActivity.tasks.remove(task);
                            task.reset();
                            PrincipalActivity.tasks.add(task);
                            tasksRef.child(task.getTaskId()).setValue(task);

                        }
                        break;
                    }
                }
                Toast.makeText(giveRewards.this, "Don't forget to give the reward in real life!", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(giveRewards.this, viewReward.class);
                myIntent.putExtra("user",0);
                startActivity(myIntent);
                finish();
            }
        });

        cancelButton= (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(giveRewards.this, viewReward.class);
                myIntent.putExtra("user",0);
                startActivity(myIntent);
                finish();
            }
        });

    }

    /* Method taken from https://stackoverflow.com/questions/6514657/prevent-back-button-from-closing-my-application
    */
    public void onBackPressed() {
        // do nothing. We want to force user to stay in this activity and not drop out.
    }
}
