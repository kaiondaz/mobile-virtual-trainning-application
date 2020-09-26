package com.example.myapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Client extends AppCompatActivity {

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("DashboardActivity");

        //init
        firebaseAuth=FirebaseAuth.getInstance();

    }

    private void checkUserStatus() {

        //get Current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {

            //user is signed in stay here
        } else {

            //User not signed in go to main activity
            startActivity(new Intent(Client.this,Main3Activity.class) );
            finish();

        }




    }


    @Override
    protected void onStart() {
        //check on start of the app
        checkUserStatus();
        super.onStart();
    }










}