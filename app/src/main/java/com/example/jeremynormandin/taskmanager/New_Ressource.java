package com.example.jeremynormandin.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
        //TODO faire une reference à la database de ressources et ajouter tous les ressources à une liste static de ressources à chaque changement

        Button createButton= (Button) findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO créer la ressource avec les valeurs dans NameText
            }
        });

    }



    /* Method taken from https://stackoverflow.com/questions/6514657/prevent-back-button-from-closing-my-application
  */
    public void onBackPressed() {
        // do nothing. We want to force user to stay in this activity and not drop out.
    }

}
