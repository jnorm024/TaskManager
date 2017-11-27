package com.example.jeremynormandin.taskmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TaskDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO ajouter les détails de la tâche dans les zones de texte correspondantes
        setContentView(R.layout.activity_task_details);
        getSupportActionBar().setTitle("Task details");



        Button setCompleted = (Button) findViewById(R.id.setAsCompleted);
        //TODO tester si la tâche est assignée à ce user(sinon popup) et si cest le cas set la task a isAccomplished = true
        setCompleted.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                /* Prend la tâche et la set à complétée */
            }
        });
        //TODO tester so le user est parent avant de delete et retirer la tâche de la database si cest le cas
        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                /* if user == parent, on delete la task autrement le button fait juste envoyé un pop-up comme quoi
                   l'utilisateur n'est pas un parent
                 */
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
        }});
    }
}
