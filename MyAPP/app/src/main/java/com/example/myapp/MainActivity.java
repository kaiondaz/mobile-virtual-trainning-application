package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // firebaseAuth = FirebaseAuth.getInstance();
        //firebaseAuth.signOut();
    }




    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Login:
                    Intent myIntent2 = new Intent(MainActivity.this, Login.class);
                    // myIntent.putExtra("key", value); //Optional parameters
                    MainActivity.this.startActivity(myIntent2);

                    break;
                case R.id.Register:
                    Intent myIntent = new Intent(MainActivity.this, Register_User_Type.class);
                    // myIntent.putExtra("key", value); //Optional parameters
                    MainActivity.this.startActivity(myIntent);
                    break;

            }



    }
}