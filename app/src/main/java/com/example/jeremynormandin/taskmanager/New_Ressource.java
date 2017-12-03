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

public class New_Ressource extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__ressource);
        getSupportActionBar().setTitle("Create a new ressource");

        Button cancelButton= (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(New_Ressource.this, View_Ressources.class));
            }
        });

        Button createButton= (Button) findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO créer la ressource avec les valeurs dans NameText et spinner groups
            }
        });

        addGroups();

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
