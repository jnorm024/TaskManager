package com.example.jeremynormandin.taskmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Settings extends AppCompatActivity {

    private DatabaseReference databaseTasksManagement;
    private DatabaseReference usersRef;
    private DatabaseReference tasksRef;
    private DatabaseReference rewardsRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle("Settings");

        Button swtichUser = (Button) findViewById(R.id.switchUser);
        swtichUser.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                startActivity(new Intent(Settings.this, LoginActivity.class));
                finish();
            }
        });

        Button deleteUser = (Button) findViewById(R.id.deleteUser);
        deleteUser.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                //Il faut une confirmation first pour éviter les suppressions sans le vouloir
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(Settings.this);
                builder = new AlertDialog.Builder(Settings.this);
                builder.setTitle("You are about to delete this user.");
                builder.setMessage("Press OK to proceed");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        databaseTasksManagement = FirebaseDatabase.getInstance().getReferenceFromUrl("https://taskmanager-47695.firebaseio.com/");
                        tasksRef = databaseTasksManagement.child("tasks");
                        rewardsRef = databaseTasksManagement.child("rewards");
                        usersRef = databaseTasksManagement.child("users");
                        for(Task task : PrincipalActivity.tasks) {
                            if(PrincipalActivity.getLoginUser().getUserId().equals(task.getAssignedUserId())) {
                                System.out.println("has a task !!!!!!!!!!!!"+task.getName());
                                if(task.gethasReward()) {
                                    rewardsRef.child(task.getAssociatedRewardId()).removeValue();
                                }
                                //TODO même code que dans ModifyTask
                                tasksRef.child(task.getTaskId()).removeValue();
                            }
                        }

                        usersRef.child(PrincipalActivity.getLoginUser().getUserId()).removeValue();
                        LoginActivity.users.remove(PrincipalActivity.getLoginUser());

                        //Toast.makeText(TaskDetails.this,"This task is removed",Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(TaskDetails.this, PrincipalActivity.class));
                        //finish();
                        startActivity(new Intent(Settings.this, LoginActivity.class));
                        Toast.makeText(Settings.this,"User removed",Toast.LENGTH_LONG).show();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }

                });
                builder.show();

            }
        });


        Button home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                startActivity(new Intent(Settings.this, PrincipalActivity.class));
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
