package com.example.myapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class WorkoutPlanClientDisplay extends AppCompatActivity {

    private String workoutPlanDate;
    private String workoutPlanName;
    private String exerciseName;
    private String reps;
    private String sets;
    private EditText workoutPlanNameEdtx;
    private EditText dateEdtx;
    private EditText exerciseNameRow1;
    private EditText repsRow1;
    private EditText setsRow1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_plan_client_display);

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Workout plan");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        //Getting nutrition plan info from selected nutrition plan
        Intent intent=getIntent();
        workoutPlanDate=intent.getStringExtra("workoutPlanDate");
        workoutPlanName=intent.getStringExtra("workoutPlanName");
        exerciseName=intent.getStringExtra("exerciseName");
        reps=intent.getStringExtra("reps");
        sets=intent.getStringExtra("sets");


        //init Text views
        dateEdtx= findViewById(R.id.date);
        workoutPlanNameEdtx = findViewById(R.id.nutritionPlanName);

        //init meal plan 1
        exerciseNameRow1 = (EditText) findViewById(R.id.exerciseNameRow1);
        //init meal plan 2
        repsRow1 = (EditText) findViewById(R.id.repsRow1);
        //init meal plan 3
        setsRow1 = (EditText) findViewById(R.id.setsRow1);


        dateEdtx.setText(workoutPlanDate);
        workoutPlanNameEdtx.setText(workoutPlanName);
        exerciseNameRow1.setText(exerciseName);
        repsRow1.setText(reps);
        setsRow1.setText(sets);







    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }


}
