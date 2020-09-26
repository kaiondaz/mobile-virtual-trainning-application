package com.example.myapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class NutritionPlanClientDisplay extends AppCompatActivity {

    String nutritionPlanDate;
    String meal1;
    String meal2;
    String meal3;
    String nutritionPlanName;
    EditText mealPlanNameEdtx;
    EditText dateEdtx;
    private EditText mealPlan1NameRow1;
    private EditText mealPlan2NameRow1;
    private EditText mealPlan3NameRow1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_plan_client_display);



        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Nutrition plan");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //Getting nutrition plan info from selected nutrition plan
        Intent intent=getIntent();
        nutritionPlanDate=intent.getStringExtra("nutritionPlanDate");
        nutritionPlanName=intent.getStringExtra("nutritionPlanName");
        meal1=intent.getStringExtra("Meal1");
        meal2=intent.getStringExtra("Meal2");
        meal3=intent.getStringExtra("Meal3");


        //init Text views
        dateEdtx= findViewById(R.id.date);
        mealPlanNameEdtx= findViewById(R.id.nutritionPlanName);

        //init meal plan 1
        mealPlan1NameRow1 = (EditText) findViewById(R.id.mealPlan1NameRow1);
        //init meal plan 2
        mealPlan2NameRow1= (EditText) findViewById(R.id.mealPlan2NameRow1);
        //init meal plan 3
        mealPlan3NameRow1 = (EditText) findViewById(R.id.mealPlan3NameRow1);


        dateEdtx.setText(nutritionPlanDate);
        mealPlanNameEdtx.setText(nutritionPlanName);
        mealPlan1NameRow1.setText(meal1);
        mealPlan2NameRow1.setText(meal2);
        mealPlan3NameRow1.setText(meal3);




    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }



}
