package com.example.jeremynormandin.taskmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class NewUser extends AppCompatActivity {

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

            MainActivity.addUserToDatabase(name,password,parent);
            //setting edittext to blank again
            nameText.setText("");
            passwordText.setText("");

            //displaying a success toast
            Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a valid name and password", Toast.LENGTH_LONG).show();
        }
    }

}
