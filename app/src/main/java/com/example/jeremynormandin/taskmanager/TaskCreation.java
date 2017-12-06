package com.example.jeremynormandin.taskmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
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
     * à ce que je puisse il accéder pour ajouter une récompense dans CreateReward
     */
    static Task lastTaskCreated;

    private DatabaseReference databaseTasksManagement;
    private DatabaseReference tasksRef;

    EditText taskName;
    EditText taskDescription;
    EditText points;
    Spinner assignedTo;
    Spinner daySpinner;
    Spinner monthSpinner;
    Spinner yearSpinner;
    CheckBox repetitive;
    Button addRewardButton;
    Button createTaskButton;
    Button addRessourcesButton;
    Spinner groupsSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creation);
        getSupportActionBar().setTitle("New task creation");
        addGroups();

        databaseTasksManagement = FirebaseDatabase.getInstance().getReferenceFromUrl("https://taskmanager-47695.firebaseio.com/");
        tasksRef = databaseTasksManagement.child("tasks");

        taskName = (EditText) findViewById(R.id.taskName);
        taskDescription = (EditText) findViewById(R.id.taskDescription);
        points = (EditText) findViewById(R.id.points);
        repetitive = (CheckBox) findViewById(R.id.repetitiveTask);
        groupsSpinner = (Spinner) findViewById(R.id.groupSpinner);

        //TODO (DONE) ajouter le groupe sélectionné à la tâche
        addRewardButton = (Button) findViewById(R.id.addRewardButton);
        addRewardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int input = addTask();
                if(input==0) {
                    startActivity(new Intent(TaskCreation.this, CreateReward.class));
                    finish();
                }

            }
        });
        addRessourcesButton = (Button) findViewById(R.id.addRessources);
        addRessourcesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int input = addTask();
                if(input==0) {
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(TaskCreation.this);
                    builder.setTitle("Do you want to create a reward first");
                    builder.setMessage("If you click no you will not be able to crate a reward for this activity");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            addTask();
                            startActivity(new Intent(TaskCreation.this, CreateReward.class));

                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            addTask();
                            startActivity(new Intent(TaskCreation.this, New_Ressource.class));
                        }

                    });
                    builder.show();
                }
            }
        });
        createTaskButton = (Button) findViewById(R.id.createTaskButton);
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int input = addTask();
                if(input==0) {
                    startActivity(new Intent(TaskCreation.this, PrincipalActivity.class));
                    finish();
                }

            }
        });



                Button cancelButton= (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                //System.out.println(user.getName());
            }
            for (String userName : list) {
                System.out.println(userName);
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        assignedTo.setAdapter(dataAdapter);
    }

    private int addTask() {
        String name = taskName.getText().toString().trim();
        String description = taskDescription.getText().toString();
        boolean repeat = repetitive.isChecked();
        String date = yearSpinner.getSelectedItem().toString()+monthSpinner.getSelectedItem().toString()+daySpinner.getSelectedItem().toString();
        String userName = assignedTo.getSelectedItem().toString();
        String group = groupsSpinner.getSelectedItem().toString();
        int taskPoints = -1;
        try {
            taskPoints = Integer.parseInt(points.getText().toString().trim());
        } catch(Exception e) {
            System.out.println("invalid input at points editText !");
        }

        //checking if the value is provided
        if (!TextUtils.isEmpty(name) && taskPoints!=-1) {
            //setting editText to blank again
            taskName.setText("");
            taskDescription.setText("");

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Reward
            String id = tasksRef.push().getKey();

            //creating a new Reward Object
            Task newTask =  new Task(id, name, description, date, repeat, taskPoints, group);
            /**
             * Si le user est un parent je compare le choix qu'il a fait dans la liste déroulante,
             * Si ce choix correspond à un usager et non bonus, alors j'assigne le id de l'usager relié
             * à la tâche
             */
            if(PrincipalActivity.getLoginUser().getIsParent()) {
                for(User user : LoginActivity.users) {
                    if(user.getName().equals(userName)){
                            newTask.setAssignedUserId(user.getUserId());
                            break;
                    }
                }
            }
            lastTaskCreated = newTask;
            //add new task to the static task list in principalActivity
            PrincipalActivity.tasks.add(newTask);

            //Saving the reward
            tasksRef.child(id).setValue(newTask);

            //displaying a success toast
            Toast.makeText(this, "Task created", Toast.LENGTH_SHORT).show();
            return 0;
        } else {
            //if the value is not given displaying a toast
            Toast toast = Toast.makeText(this, "Invalid Inputs", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 2);
            toast.show();
            return -1;
        }
    }

    /* Method taken from https://stackoverflow.com/questions/6514657/prevent-back-button-from-closing-my-application
    */
    public void onBackPressed() {
        // do nothing. We want to force user to stay in this activity and not drop out.
    }
    public void addGroups(){
        groupsSpinner = (Spinner) findViewById(R.id.groupSpinner);

        List<String> list= new ArrayList<String>();
        list.add("Outdoor work");
        list.add("Household");
        list.add("Personnal");
        list.add("Shopping");
        list.add("Others");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        groupsSpinner.setAdapter(dataAdapter);

    }
}
