package com.example.jeremynormandin.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ModifyTask extends AppCompatActivity {

    Spinner daySpinner;
    Spinner monthSpinner;
    Spinner yearSpinner;
    Spinner assignedTo;

    String dueDate;

    private DatabaseReference databaseTasksManagement;
    private DatabaseReference tasksRef;
    private DatabaseReference ressourcesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_task);
        getSupportActionBar().setTitle("Modify a task");
        final Task taskToModify = (Task) getIntent().getSerializableExtra("task");

        databaseTasksManagement = FirebaseDatabase.getInstance().getReferenceFromUrl("https://taskmanager-47695.firebaseio.com/");
        tasksRef = databaseTasksManagement.child("tasks");
        ressourcesRef = databaseTasksManagement.child("ressources");

        dueDate= taskToModify.getDueDate();

        assignToUser();
        addDay();
        addMonth();
        addYear();



        TextView taskName = (TextView) findViewById(R.id.nameView);
        taskName.setText(taskToModify.getName());

        TextView taskDesc = (TextView) findViewById(R.id.descView);
        taskDesc.setText(taskToModify.getDetails());



        Button applyChanges= (Button) findViewById(R.id.applyChanges);
        applyChanges.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick (View v){
                //find the task to modify in task list and replace it with the modifications applied
               for (Task task : PrincipalActivity.tasks){
                   if(taskToModify.getTaskId().equals(task.getTaskId())){
                       PrincipalActivity.tasks.remove(task);

                       task.setDueDate(yearSpinner.getSelectedItem().toString(),monthSpinner.getSelectedItem().toString(),daySpinner.getSelectedItem().toString());
                       for(User user: LoginActivity.users){
                           if(assignedTo.getSelectedItem().toString().equals("bonus")){
                               task.setAssignedUserId(null);
                               task.setIsUnassigned();
                           }

                           if(assignedTo.getSelectedItem().toString().equals(user.getName())){
                               task.setAssignedUserId(user.getUserId());
                           }
                       }
                       PrincipalActivity.tasks.add(task);
                       tasksRef.child(task.getTaskId()).setValue(task);
                       break;
                   }

               }
               startActivity(new Intent( ModifyTask.this, PrincipalActivity.class));
               finish();

            }
        });

        Button cancelButton= (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task selectedTask = (Task) getIntent().getSerializableExtra("task");
                Intent myIntent = new Intent(ModifyTask.this, TaskDetails.class);
                myIntent.putExtra("task", selectedTask);
                startActivity(myIntent);
                finish();
            }
        });

        Button changeResButton= (Button) findViewById(R.id.changeRes);
        changeResButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ModifyTask.this, New_Ressource.class);
                myIntent.putExtra("task", taskToModify);
                startActivity(myIntent);
                finish();
            }
        });

        Button deleteRes= (Button) findViewById(R.id.delRes);
        deleteRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int j=0;
                for(int i=0; i<PrincipalActivity.ressourcesList.size(); i++){
                    if(PrincipalActivity.ressourcesList.get(i).getRelatedTaskId().equals(taskToModify.getTaskId())) {
                        ressourcesRef.child(PrincipalActivity.ressourcesList.get(i).getRessourceId()).removeValue();
                        PrincipalActivity.ressourcesList.remove(PrincipalActivity.ressourcesList.get(i));
                        Toast.makeText(ModifyTask.this, "The resource is removed", Toast.LENGTH_SHORT).show();
                        j = 1;
                    }
                }
                if(j==0){Toast.makeText(ModifyTask.this,"This task does not have any resources",Toast.LENGTH_SHORT).show(); }

            }
        });

    }


    /* Method taken from https://stackoverflow.com/questions/6514657/prevent-back-button-from-closing-my-application
  */
    public void onBackPressed() {
        // do nothing. We want to force user to stay in this activity and not drop out.
    }

    /**
     * add all users to the users spinner
     */
    public void assignToUser() {
        assignedTo = (Spinner) findViewById(R.id.usersSpin);
        List<String> list = new ArrayList<String>();
        list.add("bonus");

        for (User user : LoginActivity.users) {
                list.add(user.getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        assignedTo.setAdapter(dataAdapter);
    }

    public void addDay(){
        daySpinner = (Spinner) findViewById(R.id.daySpinner);

        List<String> list= new ArrayList<String>();
        list.add("01");
        list.add("02");
        list.add("03");
        list.add("04");
        list.add("05");
        list.add("06");
        list.add("07");
        list.add("08");
        list.add("09");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        list.add("15");
        list.add("16");
        list.add("17");
        list.add("18");
        list.add("19");
        list.add("20");
        list.add("21");
        list.add("22");
        list.add("23");
        list.add("24");
        list.add("25");
        list.add("26");
        list.add("27");
        list.add("28");
        list.add("29");
        list.add("30");
        list.add("31");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        daySpinner.setAdapter(dataAdapter);

    }

    public void addMonth(){
        monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        List<String> list= new ArrayList<String>();
        list.add("01");
        list.add("02");
        list.add("03");
        list.add("04");
        list.add("05");
        list.add("06");
        list.add("07");
        list.add(("08"));
        list.add("09");
        list.add("10");
        list.add("11");
        list.add("12");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        monthSpinner.setAdapter(dataAdapter);
    }

    public void addYear() {
        yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        List<String> list = new ArrayList<String>();
        list.add("2017");
        list.add("2018");
        list.add("2019");
        list.add("2020");
        list.add("2021");
        list.add("2022");
        list.add("2023");
        list.add(("2024"));
        list.add("2025");
        list.add("2026");
        list.add("2027");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        yearSpinner.setAdapter(dataAdapter);
    }
}
