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

public class AddResToTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_res_to_task);
        final Task taskToViewRes = (Task) getIntent().getSerializableExtra("task");
        final ListView resList= (ListView) findViewById(R.id.resList);
        final ArrayList<String> list= new ArrayList<String>();

        //TODO remplir la liste avec toutes les ressources associées au group de la task -- JN
        /* for(int i=0; i<taskToViewRes.getGroup.getAllRes().size(); i++){
            list.add(taskToViewRes.getGroup.getAllRes().get(i).getName());


        }
         */

        final ArrayAdapter adapter = new ArrayAdapter(AddResToTask.this, android.R.layout.simple_list_item_1, list);
        resList.setAdapter(adapter);
        resList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> resList, View ListView, int pos, long id) {
                //TODO ajouter la ressource à la liste de la task --JN


            }
        });

        Button cancelButton= (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task taskToViewRes = (Task) getIntent().getSerializableExtra("task");
                Intent myIntent = new Intent(AddResToTask.this, ManageRessources.class);
                myIntent.putExtra("task", taskToViewRes);
                startActivity(myIntent);
                finish();
            }
        });

    }
}
