package com.example.jeremynormandin.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class View_Ressources extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__ressources);
        getSupportActionBar().setTitle("Ressources");

        addGroups();

        Button homeButton= (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(View_Ressources.this, PrincipalActivity.class));
            }
        });


        //TODO (DONE) delete ces deux boutons useless!!!



        Button refreshButton= (Button) findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    public void addGroups(){
        Spinner groupsSpinner = (Spinner) findViewById(R.id.groups);

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
