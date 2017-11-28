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
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class viewReward extends AppCompatActivity {

    Spinner viewRewardFor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reward);
        getSupportActionBar().setTitle("Rewards List");

        viewRewardFor();

        ListView rewardList= (ListView) findViewById(R.id.rewardList);
        String[] values= new String []{
                "Empty","Empty2"
        };
        final ArrayList<String> list= new ArrayList<String>();
        for (int i=0; i<values.length; ++i){
            list.add(values[i]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        rewardList.setAdapter(adapter);
        rewardList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> list, View v, int pos, long id){
                startActivity(new Intent(viewReward.this, giveRewards.class));
            }

        });




    }

    public void viewRewardFor() {
        viewRewardFor = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();

        if(PrincipalActivity.getLoginUser().getIsParent()) {

            for (User user : LoginActivity.users) {
                list.add(user.getName());
                System.out.println(user.getName());
            }
            for (String userName : list) {
                System.out.println(userName);
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        viewRewardFor.setAdapter(dataAdapter);
    }



}
