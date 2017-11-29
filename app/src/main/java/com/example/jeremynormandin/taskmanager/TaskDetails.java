package com.example.jeremynormandin.taskmanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.jeremynormandin.taskmanager.R.layout.activity_task_details;

public class TaskDetails extends AppCompatActivity {


    /* Les textView finissant en View ne sont pas à changer, ils servent seulement à permettre à l'usager de savoir ce que chaque info est.




     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Task selectedTask = (Task) getIntent().getSerializableExtra("task");
        setContentView(activity_task_details);

        TextView taskName = (TextView) findViewById(R.id.taskName);
        taskName.setText(selectedTask.getName());

        TextView taskDetails = (TextView) findViewById(R.id.taskDetails);
        taskDetails.setText(selectedTask.getDetails());

        TextView assignedTo = (TextView) findViewById(R.id.assignedTo);
        if(selectedTask.getAssignedUserId()==null) {
            assignedTo.setText("Bonus");
        } else {
            for(User user : LoginActivity.users) {
                if(selectedTask.getAssignedUserId().equals(user.getUserId())) {
                    assignedTo.setText(user.getName());
                }
            }
        }

        TextView dueDate = (TextView) findViewById(R.id.dueDate);
        dueDate.setText(selectedTask.getDueDate());

        getSupportActionBar().setTitle("Task details");


        Button setCompleted = (Button) findViewById(R.id.setAsCompleted);
        //TODO tester si la tâche est assignée à ce user(sinon popup) et si cest le cas set la task a isAccomplished = true
        setCompleted.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                /* Prend la tâche et la set à complétée */
                //if(PrincipalActivity.getLoginUser().getUserId() != Task.R.getAssignedUserId();){
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
              //  }else{
                //    Task.setAccomplished();
              //  }
            }
        });
        //TODO deleter la tache
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
                    //delete task
                }
        }});
    }
}
