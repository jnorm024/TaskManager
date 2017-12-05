package com.example.jeremynormandin.taskmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ManageRessources extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_ressources);
        getSupportActionBar().setTitle("Manage ressources");
        final Task taskToViewRes = (Task) getIntent().getSerializableExtra("task");
        final ListView resList= (ListView) findViewById(R.id.resList);
        final ArrayList<String> list= new ArrayList<String>();

        //TODO remplir resList avec la liste de ressource la taskToViewRes --JN

        /*for(int i=0; i<taskToViewRes.getResList().size(); i++) {
            list.add(taskToViewRes.getResList().get(i).getName());
        }*/


        // à retirer une fois la liste remplie avec les ressources
        list.add("empty");
        list.add("empty2");

        //permet de retirer un item de la liste lorsque l'on clique dessus
        final ArrayAdapter adapter = new ArrayAdapter(ManageRessources.this, android.R.layout.simple_list_item_1, list);
        resList.setAdapter(adapter);
        resList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> resList, View ListView, int pos, long id) {
                //TODO retirer la ressource correspondant de la task, voir ModifyTask pour exemple --JN

                Object toRemove = adapter.getItem(pos);
                adapter.remove(toRemove);

            }
        });



        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(ManageRessources.this);
        builder.setTitle("Delete a ressource");
        builder.setMessage("To delete a ressource from the task, simply click on it.");
        builder.setNeutralButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

        Button addResButton= (Button) findViewById(R.id.addRes);
        addResButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task taskToViewRes = (Task) getIntent().getSerializableExtra("task");
                Intent myIntent = new Intent(ManageRessources.this, New_Ressource.class);
                myIntent.putExtra("task", taskToViewRes);
                startActivity(myIntent);
                finish();
            }
        });


        Button cancelButton= (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task taskToModify = (Task) getIntent().getSerializableExtra("task");
                Intent myIntent = new Intent(ManageRessources.this, ModifyTask.class);
                myIntent.putExtra("task", taskToModify);
                startActivity(myIntent);
                finish();
            }
        });
    }

    /* Method taken from https://stackoverflow.com/questions/6514657/prevent-back-button-from-closing-my-application
 */
    public void onBackPressed() {
        // do nothing. We want to force user to stay in this activity and not drop out.
    }


}
