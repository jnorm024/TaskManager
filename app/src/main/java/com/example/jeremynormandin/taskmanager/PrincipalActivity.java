package com.example.jeremynormandin.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity implements Serializable  {

    TextView nameView;
    /**
     * ici je fais appelle à ma base de donnée pour les tâches
     */
    private DatabaseReference databaseTasksManagement;
    private DatabaseReference tasksRef;
    /**
     * Ces deux valeurs static permettent d'accéder à l'utilisateur
     * actuellement connecté et à la liste de toutes les tâches à partir de n'importe lequel autre activité
     */
    private static User loginUser;
    static List<Task> tasks;
    static Task selectedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        getSupportActionBar().setTitle("Home Page");

        tasks = new ArrayList<>();
        databaseTasksManagement = FirebaseDatabase.getInstance().getReferenceFromUrl("https://taskmanager-47695.firebaseio.com/");
        tasksRef = databaseTasksManagement.child("tasks");
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
                            System.out.println(task.getName());
                            //adding task to the list
                            tasks.add(task);
                            list.add(task.getName());
                        }
                        ArrayAdapter adapter = new ArrayAdapter(PrincipalActivity.this, android.R.layout.simple_list_item_1, list);
                        taskList.setAdapter(adapter);
                        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                            public void onItemClick(AdapterView<?> taskList, View ListView, int pos, long id){
                                String name = (String) taskList.getItemAtPosition(pos);

                                for (int i = 0; i < list.size(); i++) {
                                    if(taskList.getItemAtPosition(pos).equals(tasks.get(i).getName())){
                                        //TODO trouver la variable qui va dans putExtra et régler startActivity
                                        Task selectedTask = tasks.get(i);
                                        Intent myIntent = new Intent(PrincipalActivity.this, TaskDetails.class);
                                        myIntent.putExtra("task", selectedTask);
                                        startActivity(myIntent);
                                    }
                                }
                                //Task task = new Task(selectedTask.getTaskId(), selectedTask.getName(), selectedTask.getDetails(), selectedTask.getDueDate(), selectedTask.getIsRepeated());


                            }

                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
        nameView = (TextView) findViewById(R.id.nameView);
        nameView.setText(loginUser.getName());

        Button newTask = (Button) findViewById(R.id.addNewTask);
        newTask.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                startActivity(new Intent(PrincipalActivity.this, TaskCreation.class));
            }
        });


        Button toRewards= (Button) findViewById(R.id.toRewards);
        toRewards.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(PrincipalActivity.this, viewReward.class));

            }
        });

    }

    public static void setLoginUser(User user) {
        loginUser = user;
    }

    public static User getLoginUser() {
        return loginUser;
    }

}