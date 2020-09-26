package com.example.myapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapp.Models.NutritionPlan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Create_Nutrition_Plan extends AppCompatActivity {

    //Fields
    String hisUID;

    EditText mealPlan1NameRow1;
    EditText mealPlan2NameRow1;
    EditText mealPlan3NameRow1;
    EditText mealPlanNameEdtx;
    EditText dateEdtx;
    ImageView picRow1 ;

    //Arrays
    String mealPlan1Column[] = new String[5];
    String mealPlan2Column[] = new String[5];
    String mealPlan3Column[] = new String[5];
    String Pics[] = new String[5];
    Button createNutritionPlan;

    //
    String nutritionPlanName;
    String date;


    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference clientRef;
    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__nutrition__plan);

        Intent myIntent = getIntent();
        hisUID = myIntent.getStringExtra("nutritionistID");

        //Init Firebase database
        database = FirebaseDatabase.getInstance();
        clientRef = database.getReference("Clients").child(hisUID).child("Nutrition plans").push();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        //Action bar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create nutrition plan");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //init meal plan 1
        mealPlan1NameRow1 = (EditText) findViewById(R.id.mealPlan1NameRow1);


        //init meal plan 2
        mealPlan2NameRow1 = (EditText) findViewById(R.id.mealPlan2NameRow1);


        //init meal plan 3
        mealPlan3NameRow1 = (EditText) findViewById(R.id.mealPlan3NameRow1);


        //init pics
        picRow1 = (ImageView) findViewById(R.id.picRow1);


        //init Text views
        dateEdtx = findViewById(R.id.date);
        mealPlanNameEdtx = findViewById(R.id.nutritionPlanName);


        //init Button
        createNutritionPlan = findViewById(R.id.createNutritionPlan);

        //Button event
        createNutritionPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Validating entries
                if (dateEdtx.length() == 0) {

                    dateEdtx.setError("Please enter the date of the workout.");
                } else if (mealPlanNameEdtx.length() == 0) {
                    mealPlanNameEdtx.setError("Please set a name for the workout.");
                } else {

                    // get text from edit text
                    String id = user.getUid();
                    getInputTextFields();
                    String nutritionPlanID;

                    if ((mealPlan1NameRow1.length() == 0) || (mealPlan2NameRow1.length() == 0) || (mealPlan3NameRow1.length() == 0)) {
                        mealPlan1NameRow1.setError("");
                        mealPlan2NameRow1.setError("");
                        mealPlan3NameRow1.setError("");

                        Toast.makeText(Create_Nutrition_Plan.this, "At least one row needs to be filled for the nutrition plan.", Toast.LENGTH_LONG).show();
                    }


                            NutritionPlan nutritionPlan = new NutritionPlan();
                            nutritionPlan.setMeal1Name(mealPlan1NameRow1.getText().toString().trim());
                            nutritionPlan.setMeal2Name(mealPlan2NameRow1.getText().toString().trim());
                            nutritionPlan.setMeal3Name(mealPlan3NameRow1.getText().toString().trim());
                            nutritionPlan.setNutritionPlanPic("");
                            nutritionPlan.setNutritionPlanDate(date.toString().trim());
                            nutritionPlan.setNutritionPlanName(nutritionPlanName.toString().trim());
                            clientRef.setValue(nutritionPlan);
                            nutritionPlanID = clientRef.getKey();
                            nutritionPlan.setNutritionPlanID(nutritionPlanID);
                            Toast.makeText(Create_Nutrition_Plan.this, "Nutrition plan created!", Toast.LENGTH_SHORT).show();
                            Intent myIntent2 = new Intent(Create_Nutrition_Plan.this, DashboardActivityNutritionist.class);
                            // myIntent.putExtra("key", value); //Optional parameters
                            Create_Nutrition_Plan.this.startActivity(myIntent2);

                        }


                }

        });
    }


    public void getInputTextFields() {

        //date and workout name
        date = dateEdtx.getText().toString().trim();
        nutritionPlanName = mealPlanNameEdtx.getText().toString().trim();

        //Add to array
        mealPlan1Column[0] = mealPlan1NameRow1.getText().toString().trim();

        //Add to array
        mealPlan2Column[0] = mealPlan2NameRow1.getText().toString().trim();

        //Add to array
        mealPlan3Column[0] = mealPlan3NameRow1.getText().toString().trim();


        //Add to array
        /**
         Pics[0]=picRow1.getText().toString().trim();
         Pics[1]=picRow2.getText().toString().trim();
         Pics[2]=picRow3.getText().toString().trim();
         Pics[3]=picRow4.getText().toString().trim();
         Pics[4]=picRow5.getText().toString().trim();
         */


    }

    // go previous activity
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}


