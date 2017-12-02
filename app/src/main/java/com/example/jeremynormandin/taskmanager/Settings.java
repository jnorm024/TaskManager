package com.example.jeremynormandin.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle("Settings");

        Button swtichUser = (Button) findViewById(R.id.switchUser);
        swtichUser.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                startActivity(new Intent(Settings.this, LoginActivity.class));
                finish();
            }
        });

        Button deleteUser = (Button) findViewById(R.id.deleteUser);
        deleteUser.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                // TODO implémentation de suppression de l'utilisateur
                //Il faut une confirmation first pour éviter les suppressions sans le vouloir

            }
        });


        Button home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                startActivity(new Intent(Settings.this, PrincipalActivity.class));
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
