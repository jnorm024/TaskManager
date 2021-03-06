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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This activity represent the schedule in the class diagram
 * It shows all tasks depending on the user or group selected
 */
public class PrincipalActivity extends AppCompatActivity implements Serializable  {

    Spinner taskView;
    Spinner groupView;
    /**
     * ici je fais appelle à ma base de données pour les tâches
     */
    private DatabaseReference databaseTasksManagement;
    private DatabaseReference tasksRef;
    private DatabaseReference ressourcesRef;
    private DatabaseReference usersRef;
    /**
     * Ces attributs statiques permettent d'accéder à l'utilisateur
     * actuellement connecté et à la liste de toutes les tâches à partir de n'importe lequel autre activité
     */
    private static User loginUser;
    static List<Task> tasks;
    static List<Ressources> ressourcesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        getSupportActionBar().setTitle("Home - "+ loginUser.getName());
        setTaskViewSpinner();
        setGroupViewSpinner();
        //if a group or user was selected in the spinner before lauching activity, set the spinner with the selection
        if(getIntent().hasExtra("group") && getIntent().hasExtra("user")) {
            final Integer spinnerGroup = (Integer) getIntent().getSerializableExtra("group");
            final Integer spinnerUser = (Integer) getIntent().getSerializableExtra("user");
            taskView.setSelection(spinnerUser);
            groupView.setSelection(spinnerGroup);
        }

        ressourcesList = new ArrayList<>();
        tasks = new ArrayList<>();
        //this list the task to be show in the UI list
        final ArrayList<Task> listedTask = new ArrayList<>();
        //instantiate the database
        databaseTasksManagement = FirebaseDatabase.getInstance().getReferenceFromUrl("https://taskmanager-47695.firebaseio.com/");
        tasksRef = databaseTasksManagement.child("tasks");
        usersRef = databaseTasksManagement.child("users");
        ressourcesRef = databaseTasksManagement.child("ressources");
        ressourcesRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //clear previous ressources list
                        ressourcesList.clear();
                        //access to all ressources in database
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //getting ressource
                            Ressources ressource = postSnapshot.getValue(Ressources.class);
                            //adding ressource to the list
                            ressourcesList.add(ressource);
                        }
                    }

                        @Override
                        public void onCancelled (DatabaseError databaseError){
                            //handle databaseError
                        }
                    }
        );
        final ArrayList<String> list= new ArrayList<String>();
        final ListView taskList= (ListView) findViewById(R.id.taskList);
        tasksRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //clear previous task list
                        tasks.clear();
                        //access to all task in database
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //getting task
                            Task task =  postSnapshot.getValue(Task.class);

                            tasks.add(task);

                            if(!task.getIsAccomplished()) {
                                listedTask.add(task);
                                list.add(task.getName());
                            }
                        }
                        //if a specific user or group is selected, remove all tasks not related to it from the list to be shown
                        if(!taskView.getSelectedItem().toString().equals("all users")) {
                            User user = null;
                            for(User u : LoginActivity.users) {
                                if(u.getName().equals(taskView.getSelectedItem().toString())) {
                                    user = u;
                                }
                            }
                            for (Task task : listedTask) {
                                if (!user.getUserId().equals(task.getAssignedUserId())) {
                                    list.remove(task.getName());
                                }
                            }
                        }
                        if(!groupView.getSelectedItem().toString().equals("all groups")) {
                            for (Task task : listedTask) {
                                if (!task.getGroup().equals(groupView.getSelectedItem().toString())) {
                                    list.remove(task.getName());
                                }
                            }
                        }
                        ArrayAdapter adapter = new ArrayAdapter(PrincipalActivity.this, android.R.layout.simple_list_item_1, list);
                        taskList.setAdapter(adapter);
                        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                            public void onItemClick(AdapterView<?> taskList, View ListView, int pos, long id){
                                String name = (String) taskList.getItemAtPosition(pos);

                                for (int i = 0; i < list.size(); i++) {
                                    for(Task task : listedTask) {
                                        if(taskList.getItemAtPosition(pos).equals(task.getName())){
                                            Task selectedTask = task;
                                            Intent myIntent = new Intent(PrincipalActivity.this, TaskDetails.class);
                                            myIntent.putExtra("task", selectedTask);
                                            startActivity(myIntent);
                                            finish();
                                        }
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

        Button newTask = (Button) findViewById(R.id.addNewTask);
        newTask.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                startActivity(new Intent(PrincipalActivity.this, TaskCreation.class));
                finish();
            }
        });


        Button toRewards= (Button) findViewById(R.id.toRewards);
        toRewards.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(loginUser.getIsParent()) {
                    Intent myIntent = new Intent(PrincipalActivity.this, viewReward.class);
                    myIntent.putExtra("user", 0);
                    startActivity(myIntent);
                    finish();
                } else {
                    Toast.makeText(PrincipalActivity.this, "Only parents have access to this", Toast.LENGTH_LONG).show();
                }

            }
        });

        Button settings = (Button) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                startActivity(new Intent(PrincipalActivity.this, Settings.class));
                finish();
            }
        });

        Button ressources = (Button) findViewById(R.id.Ressources);
        ressources.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                startActivity(new Intent(PrincipalActivity.this, View_Ressources.class));
                finish();
            }
        });

        Button viewScores = (Button) findViewById(R.id.viewScores);
        viewScores.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                startActivity(new Intent(PrincipalActivity.this, ViewScore.class));
                finish();
            }
        });

        Button refresh = (Button) findViewById(R.id.button2);
        refresh.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                Intent myIntent = new Intent(PrincipalActivity.this, PrincipalActivity.class);
                myIntent.putExtra("user",taskView.getSelectedItemPosition());
                myIntent.putExtra("group",groupView.getSelectedItemPosition());
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

    public static void setLoginUser(User user) {
        loginUser = user;
    }

    public static User getLoginUser() {
        return loginUser;
    }

    /**
     * Set the spinner with all username
     */
    private void setTaskViewSpinner() {
        taskView = (Spinner) findViewById(R.id.taskView);
        List<String> list = new ArrayList<String>();
        list.add("all users");
        for (User user : LoginActivity.users) {
            list.add(user.getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        taskView.setAdapter(dataAdapter);
    }

    private void setGroupViewSpinner() {
        groupView = (Spinner) findViewById(R.id.groupView);
        List<String> list = new ArrayList<String>();
        list.add("all groups");
        list.add("Outdoor work");
        list.add("Household");
        list.add("Personnal");
        list.add("Shopping");
        list.add("Others");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        groupView.setAdapter(dataAdapter);
    }

}