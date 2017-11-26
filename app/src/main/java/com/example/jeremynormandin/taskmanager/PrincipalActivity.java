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

import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        getSupportActionBar().setTitle("Home Page");

        tasks = new ArrayList<>();
        databaseTasksManagement = FirebaseDatabase.getInstance().getReferenceFromUrl("https://taskmanager-47695.firebaseio.com/");
        tasksRef = databaseTasksManagement.child("tasks");
        //TODO Ma liste static des tâches ne semble pas se remplir bien qu'on itère à travers tous les tâches. À arranger!!!
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
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
        nameView = (TextView) findViewById(R.id.nameView);
        nameView.setText(loginUser.getName());
        //TODO faire en sorte que la liste de tâche affiche notre tasks list
        ListView taskList= (ListView) findViewById(R.id.taskList);
        String[] values= new String []{
                "Empty"
        };
        ArrayList<String> list= new ArrayList<String>();
        for(String value : values) {
            list.add(value);
        }
        for(Task task : tasks) {
            System.out.println(task.getName());
            list.add(task.getName());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        taskList.setAdapter(adapter);
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> list, View v, int pos, long id){
                startActivity(new Intent(PrincipalActivity.this, TaskDetails.class));
            }

        });


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