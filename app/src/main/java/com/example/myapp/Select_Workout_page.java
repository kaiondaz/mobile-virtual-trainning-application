package com.example.myapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Select_Workout_page extends AppCompatActivity {

    ImageButton Workout1Btn;
    ImageButton Workout2Bt;
    ImageButton Workout3Btn;
    ImageButton Workout4Btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__workout_page);

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Select workout");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.workout1:
                Intent myIntent1 = new Intent(Select_Workout_page.this, Workout_tutorial1.class);
                // myIntent.putExtra("key", value); //Optional parameters
                Select_Workout_page.this.startActivity(myIntent1);

                break;

            case R.id.workout2:
                Intent myIntent2 = new Intent(Select_Workout_page.this, Workout_tutorial1.class);
                // myIntent.putExtra("key", value); //Optional parameters
                Select_Workout_page.this.startActivity(myIntent2);
                break;

            case R.id.workout3:
                Intent myIntent3 = new Intent(Select_Workout_page.this, Workout_tutorial1.class);
                // myIntent.putExtra("key", value); //Optional parameters
                Select_Workout_page.this.startActivity(myIntent3);
                break;

            case R.id.workout4:
                Intent myIntent4 = new Intent(Select_Workout_page.this, Workout_tutorial1.class);
                // myIntent.putExtra("key", value); //Optional parameters
                Select_Workout_page.this.startActivity(myIntent4);
                break;

        }



    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }


}
