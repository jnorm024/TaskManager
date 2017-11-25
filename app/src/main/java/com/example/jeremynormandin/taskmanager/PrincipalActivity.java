package com.example.jeremynormandin.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class PrincipalActivity extends AppCompatActivity {

    private static User loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        getSupportActionBar().setTitle("Home Page");

        ListView taskList= (ListView) findViewById(R.id.taskList);
        String[] values= new String []{
                "Empty"
        };
        final ArrayList<String> list= new ArrayList<String>();
        for (int i=0; i<values.length; ++i){
            list.add(values[i]);
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