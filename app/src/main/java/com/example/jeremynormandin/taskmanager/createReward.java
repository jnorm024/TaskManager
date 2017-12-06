package com.example.jeremynormandin.taskmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class createReward extends AppCompatActivity {

    private DatabaseReference databaseTasksManagement;
    private DatabaseReference rewardsRef;
    private DatabaseReference tasksRef;

    EditText nameText;
    EditText descriptionText;
    Button buttonAddReward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reward);
        getSupportActionBar().setTitle("Create reward");
        //instatiate references to the database
        databaseTasksManagement = FirebaseDatabase.getInstance().getReferenceFromUrl("https://taskmanager-47695.firebaseio.com/");
        rewardsRef = databaseTasksManagement.child("rewards");
        tasksRef = databaseTasksManagement.child("tasks");


        nameText = (EditText) findViewById(R.id.name);
        descriptionText = (EditText) findViewById(R.id.description);
        buttonAddReward = (Button) findViewById(R.id.addReward);
        buttonAddReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //popup giving the choice between adding a reward of cancelling the creation
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(createReward.this);
                builder = new AlertDialog.Builder(createReward.this);
                builder.setTitle("Do you want to add a ressource");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addReward();
                        startActivity(new Intent(createReward.this, New_Ressource.class));

                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addReward();
                        startActivity(new Intent(createReward.this, PrincipalActivity.class));
                    }

                });
                builder.show();
                //finish();
            }
        });

        Button cancelButton= (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(createReward.this, TaskCreation.class));
            }
        });


    }

    /* Method taken from https://stackoverflow.com/questions/6514657/prevent-back-button-from-closing-my-application
    */
    public void onBackPressed() {
        // do nothing. We want to force user to stay in this activity and not drop out.
    }

    /**
     * This method add the reward to the database if the inputs are valid
     */
    private void addReward() {
        String rewardName = nameText.getText().toString().trim();
        String rewardDescription = descriptionText.getText().toString();

        //checking if the value is provided
        if (!TextUtils.isEmpty(rewardName)) {
            //setting editText to blank again
            nameText.setText("");
            descriptionText.setText("");

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Reward
            String id = rewardsRef.push().getKey();

            //creating a new Reward Object
            Reward newReward =  new Reward(id, rewardName, rewardDescription);

            //Saving the reward
            rewardsRef.child(id).setValue(newReward);

            TaskCreation.lastTaskCreated.setAssociatedRewardId(id);
            tasksRef.child(TaskCreation.lastTaskCreated.getTaskId()).setValue(TaskCreation.lastTaskCreated);

            //displaying a success toast
            Toast.makeText(this, "Reward added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a valid name", Toast.LENGTH_LONG).show();
        }
    }


}
