package com.example.jeremynormandin.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class View_Ressources extends AppCompatActivity {
    Spinner groupsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__ressources);
        getSupportActionBar().setTitle("Ressources");

        addGroups();
        if(getIntent().hasExtra("group")) {
            final Integer spinnerGroup = (Integer) getIntent().getSerializableExtra("group");
            groupsSpinner.setSelection(spinnerGroup);
        }

        Button homeButton= (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(View_Ressources.this, PrincipalActivity.class));
            }
        });


        Button refreshButton= (Button) findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(View_Ressources.this, View_Ressources.class);
                myIntent.putExtra("group", groupsSpinner.getSelectedItemPosition());
                startActivity(myIntent);
                finish();
            }
        });

        ArrayList<String> ressourcesList = new ArrayList<>();
        for(Task task : PrincipalActivity.tasks) {
            if(groupsSpinner.getSelectedItem().toString().equals(task.getGroup())) {
                for(Ressources r : PrincipalActivity.ressourcesList) {
                    if(r.getRelatedTaskId().equals(task.getTaskId())) {
                        ressourcesList.add(r.getName());
                    }
                }
            }
        }
        final ListView resList= (ListView) findViewById(R.id.resList);
        ArrayAdapter adapter = new ArrayAdapter(View_Ressources.this, android.R.layout.simple_list_item_1, ressourcesList);
        resList.setAdapter(adapter);

    }

    public void addGroups(){
        groupsSpinner = (Spinner) findViewById(R.id.groups);

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


    /* Method taken from https://stackoverflow.com/questions/6514657/prevent-back-button-from-closing-my-application
   */
    public void onBackPressed() {
        // do nothing. We want to force user to stay in this activity and not drop out.
    }
}
