package com.example.jeremynormandin.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewUser extends AppCompatActivity {

    private DatabaseReference databaseTasksManagement;
    private DatabaseReference usersRef;

    List<User> users;

    EditText nameText;
    EditText passwordText;
    CheckBox isParent;
    Button buttonAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        nameText = (EditText) findViewById(R.id.nameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        isParent = (CheckBox) findViewById(R.id.isParent);
        buttonAddUser = (Button) findViewById(R.id.signIn);
        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
                startActivity(new Intent(NewUser.this, LoginActivity.class));
                finish();
            }
        });

        users = new ArrayList<>();

        databaseTasksManagement = FirebaseDatabase.getInstance().getReferenceFromUrl("https://taskmanager-47695.firebaseio.com/");
        usersRef = databaseTasksManagement.child("users");
        usersRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //clear previous user list
                        users.clear();
                        //access to all user in the database
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //getting user
                            User user = postSnapshot.getValue(User.class);
                            //adding user to the list
                            users.add(user);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
        Button cancelButton= (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewUser.this, LoginActivity.class));
            }
        });
    }

    private void addUser() {
        //getting the values to save
        String name = nameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        boolean parent = isParent.isChecked();

        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {
            //setting edittext to blank again
            nameText.setText("");
            passwordText.setText("");

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our User
            String id = usersRef.push().getKey();

            //creating a new User Object
            User newUser;
            newUser = new User(id, name, password, parent);



            //Saving the user
            usersRef.child(id).setValue(newUser);
            //add new user to user list
            users.add(newUser);

            //displaying a success toast
            Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a valid name and password", Toast.LENGTH_LONG).show();
        }
    }


    /* Method taken from https://stackoverflow.com/questions/6514657/prevent-back-button-from-closing-my-application
    */
    public void onBackPressed() {
        // do nothing. We want to force user to stay in this activity and not drop out.
    }

}