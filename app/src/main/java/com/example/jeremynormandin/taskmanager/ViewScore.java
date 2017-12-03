package com.example.jeremynormandin.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score);
        getSupportActionBar().setTitle("Scores");

        final ArrayList<String> userScores = new ArrayList<>();
        final ListView scoreList = (ListView) findViewById(R.id.scoreList);
        for(User user : LoginActivity.users) {
            userScores.add(user.getName() + " : " + user.getPoints() + " points");
        }
        ArrayAdapter adapter = new ArrayAdapter(ViewScore.this, android.R.layout.simple_list_item_1, userScores);
        scoreList.setAdapter(adapter);

        Button home = (Button) findViewById(R.id.homeFromScore);
        home.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                startActivity(new Intent(ViewScore.this, PrincipalActivity.class));
                finish();
            }
        });
    }

}
