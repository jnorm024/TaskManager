package com.example.jeremynormandin.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class viewReward extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reward);
        getSupportActionBar().setTitle("Rewards List");
        ListView taskList= (ListView) findViewById(R.id.taskList);
        String[] values= new String []{
                "Empty","Empty2"
        };
        final ArrayList<String> list= new ArrayList<String>();
        for (int i=0; i<values.length; ++i){
            list.add(values[i]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        taskList.setAdapter(adapter);
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> list, View v, int pos, long id){
                startActivity(new Intent(viewReward.this, giveRewards.class));
            }

        });

        Button refresh = (Button) findViewById(R.id.refreshButton);
        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO refresh la liste de tâches
                //même méthode que dans principalActivity??
            }
        });
    }
}
