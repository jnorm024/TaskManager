package com.example.jeremynormandin.taskmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.jeremynormandin.taskmanager.R.layout.activity_task_details;

public class TaskDetails extends AppCompatActivity {

    private DatabaseReference databaseTasksManagement;
    private DatabaseReference tasksRef;
    private DatabaseReference rewardsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Task selectedTask = (Task) getIntent().getSerializableExtra("task");
        setContentView(activity_task_details);

        databaseTasksManagement = FirebaseDatabase.getInstance().getReferenceFromUrl("https://taskmanager-47695.firebaseio.com/");
        tasksRef = databaseTasksManagement.child("tasks");
        rewardsRef = databaseTasksManagement.child("rewards");

        TextView taskName = (TextView) findViewById(R.id.taskName);
        taskName.setText(selectedTask.getName());

        TextView taskDetails = (TextView) findViewById(R.id.taskDetails);
        taskDetails.setText(selectedTask.getDetails());

        TextView assignedTo = (TextView) findViewById(R.id.assignedTo);
        System.out.println(selectedTask.getAssignedUserId()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(selectedTask.getIsAssigned());
        if(!selectedTask.getIsAssigned()) {
             assignedTo.setText("Bonus");
        } else {
            for(User user : LoginActivity.users) {
                System.out.println(selectedTask.getAssignedUserId()+" vs "+user.getUserId());
                if(selectedTask.getAssignedUserId().equals(user.getUserId())) {
                    System.out.println(user.getName()+" user name found !!!!!!!!!!!!!!!!!!");
                    assignedTo.setText(user.getName());
                }
            }
        }

        TextView dueDate = (TextView) findViewById(R.id.dueDate);
        if(!selectedTask.getIsRepeated()) dueDate.setText(selectedTask.getDueDate());
        else dueDate.setText("repetitive");
        getSupportActionBar().setTitle("Task details");


        Button setCompleted = (Button) findViewById(R.id.setAsCompleted);
        setCompleted.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                /* Prend la tâche et la set à complétée */
                if(!PrincipalActivity.getLoginUser().getUserId().equals(selectedTask.getAssignedUserId()) && selectedTask.getIsAssigned()){
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(TaskDetails.this);
                    builder.setTitle("Can't proceed");
                    builder.setMessage("This task is not assigned to you.");
                    builder.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }   else{
                    selectedTask.setAccomplished();
                    selectedTask.setAssignedUserId(PrincipalActivity.getLoginUser().getUserId());
                    tasksRef.child(selectedTask.getTaskId()).setValue(selectedTask);
                    Toast.makeText(TaskDetails.this,"This task is set as accomplished",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(TaskDetails.this, PrincipalActivity.class));
                    finish();
                }
            }
        });
        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                /* if user == parent, on delete la task autrement le button fait juste envoyé un pop-up comme quoi
                   l'utilisateur n'est pas un parent
                 */
                if(!PrincipalActivity.getLoginUser().getIsParent()) {
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(TaskDetails.this);
                    builder.setTitle("Can't proceed");
                    builder.setMessage("Only a parent can delete a task");
                    builder.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }else{
                    tasksRef.child(selectedTask.getTaskId()).removeValue();
                    if(selectedTask.getAssociatedRewardId()!=null) {
                        rewardsRef.child(selectedTask.getAssociatedRewardId()).removeValue();
                    }
                    Toast.makeText(TaskDetails.this,"This task is removed",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(TaskDetails.this, PrincipalActivity.class));
                    finish();
                }
        }});
    }
}
