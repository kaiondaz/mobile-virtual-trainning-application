package com.example.myapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Register_User_Type extends AppCompatActivity {
    String userType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__user__type);

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Register as");
        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);




    }



    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Client_Button:
                Intent myIntent2 = new Intent(Register_User_Type.this, Registration.class);
                userType="Client";
                myIntent2.putExtra("userType", userType);
                // myIntent.putExtra("key", value); //Optional parameters
                Register_User_Type.this.startActivity(myIntent2);

                break;
            case R.id.Trainer_Button:
                Intent myIntent = new Intent(Register_User_Type.this, Register_User_Type.class);
                userType="Coach";
                myIntent.putExtra("userType", userType);
                // myIntent.putExtra("key", value); //Optional parameters
                Register_User_Type.this.startActivity(myIntent);
                break;

            case R.id.Nutricionist_Button:
                Intent myIntent3 = new Intent(Register_User_Type.this, Register_User_Type.class);
                userType="Nutritionist";
                myIntent3.putExtra("userType", userType);
                // myIntent.putExtra("key", value); //Optional parameters
                Register_User_Type.this.startActivity(myIntent3);

        }



    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }




}
