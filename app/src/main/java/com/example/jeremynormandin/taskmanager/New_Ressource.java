package com.example.jeremynormandin.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class New_Ressource extends AppCompatActivity {

    private DatabaseReference databaseTasksManagement;
    private DatabaseReference ressourcesRef;

    EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseTasksManagement = FirebaseDatabase.getInstance().getReferenceFromUrl("https://taskmanager-47695.firebaseio.com/");
        ressourcesRef = databaseTasksManagement.child("ressources");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__ressource);
        getSupportActionBar().setTitle("Create a new ressource");

        Button cancelButton= (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(New_Ressource.this, PrincipalActivity.class));
            }
        });
        //TODO (DONE) faire une reference à la database de ressources et ajouter tous les ressources à une liste static de ressources à chaque changement

        Button createButton= (Button) findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO (DONE) créer la ressource avec les valeurs dans NameText
                int input = addRessource();
                if(input==0) {
                    startActivity(new Intent(New_Ressource.this, PrincipalActivity.class));
                    finish();
                }
            }
        });

        nameText = (EditText) findViewById(R.id.nameText);

    }

    private int addRessource() {
        String name = nameText.getText().toString().trim();


        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {
            //setting editText to blank again
            nameText.setText("");

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Ressources
            String ressourceId = ressourcesRef.push().getKey();
            String groupName = TaskCreation.lastTaskCreated.getGroup();
            String relatedTaskId = TaskCreation.lastTaskCreated.getTaskId();
            //creating a new Ressources Object
            Ressources newRessource =  new Ressources( name, groupName, relatedTaskId, ressourceId);

            PrincipalActivity.ressources.add(newRessource);
            ressourcesRef.child(ressourceId).setValue(newRessource);

            //displaying a success toast
            Toast.makeText(this, "ressource added", Toast.LENGTH_LONG).show();
            return 0;
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Invalid Inputs", Toast.LENGTH_LONG).show();
            return -1;
        }
    }


    /* Method taken from https://stackoverflow.com/questions/6514657/prevent-back-button-from-closing-my-application
  */
    public void onBackPressed() {
        // do nothing. We want to force user to stay in this activity and not drop out.
    }

}
