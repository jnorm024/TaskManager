package com.example.jeremynormandin.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TaskCreation extends AppCompatActivity {

    /**
     * Cette tâche statique me permet de sauvegarder la derniere tâche créée de manière
     * à ce que je puisse il accéder pour ajouter une récompense dans createReward
     */
    static Task lastTaskCreated;

    private DatabaseReference databaseTasksManagement;
    private DatabaseReference tasksRef;

    EditText taskName;
    EditText taskDescription;
    Spinner assignedTo;
    Spinner daySpinner;
    Spinner monthSpinner;
    Spinner yearSpinner;
    CheckBox repetitive;
    Button addRewardButton;
    Button createTaskButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creation);
        getSupportActionBar().setTitle("New task creation");

        databaseTasksManagement = FirebaseDatabase.getInstance().getReferenceFromUrl("https://taskmanager-47695.firebaseio.com/");
        tasksRef = databaseTasksManagement.child("tasks");

        taskName = (EditText) findViewById(R.id.taskName);
        taskDescription = (EditText) findViewById(R.id.taskDescription);
        repetitive = (CheckBox) findViewById(R.id.repetitiveTask);


        addRewardButton = (Button) findViewById(R.id.addRewardButton);
        addRewardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addTask();
                startActivity(new Intent(TaskCreation.this, createReward.class));

            }
        });
        createTaskButton = (Button) findViewById(R.id.createTaskButton);
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addTask();
                startActivity(new Intent(TaskCreation.this, PrincipalActivity.class));

            }
        });

        assignToUser();
        addDay();
        addMonth();
        addYear();


    }
    /*
    Le code pour remplir les spinners est basé sur l'algorithm trouvé sur
    http://www.c-sharpcorner.com/UploadFile/e14021/spinner-in-android-using-android-studio/

     */
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

    public void assignToUser() {
        assignedTo = (Spinner) findViewById(R.id.userSpinner);
        List<String> list = new ArrayList<String>();
        list.add("bonus");
        if(PrincipalActivity.getLoginUser().getIsParent()) {

            for (User user : LoginActivity.users) {
                list.add(user.getName());
                System.out.println(user.getName());
            }
            for (String userName : list) {
                System.out.println(userName);
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        assignedTo.setAdapter(dataAdapter);
    }

    private void addTask() {
        String name = taskName.getText().toString().trim();
        String description = taskDescription.getText().toString();
        boolean repeat = repetitive.isChecked();
        String date = yearSpinner.getSelectedItem().toString()+monthSpinner.getSelectedItem().toString()+daySpinner.getSelectedItem().toString();

        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {
            //setting editText to blank again
            taskName.setText("");
            taskDescription.setText("");

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Reward
            String id = tasksRef.push().getKey();

            //creating a new Reward Object
            Task newTask =  new Task(id, name, description, date, repeat);
            /**
             * Si le user est un parent je compare le choix qu'il a fait dans la liste déroulante,
             * Si ce choix correspond à un usager et non bonus, alors j'assigne le id de l'usager reliè
             * à la tâche
             */
            if(PrincipalActivity.getLoginUser().getIsParent()) {
                boolean found = true;
                for(User user : LoginActivity.users) {
                    if(user.getName().length()==name.length()){
                        for (int i = 0; i < name.length(); i++) {
                            if (name.charAt(i) != user.getName().charAt(i)) found = false;
                        }
                        if(found == true) {
                            newTask.setAssignedUserId(user.getUserId());
                            break;
                        }
                    }
                }
            }
            lastTaskCreated = newTask;
            //add new task to the static task list in principalActivity
            PrincipalActivity.tasks.add(newTask);

            //Saving the reward
            tasksRef.child(id).setValue(newTask);

            //displaying a success toast
            Toast.makeText(this, "Task created", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a valid name", Toast.LENGTH_LONG).show();
        }
    }
}
