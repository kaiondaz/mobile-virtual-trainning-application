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

import com.example.myapp.Models.Workout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Create_Workouts extends AppCompatActivity {

    //Fields
    String hisUID;

    EditText exerciseNameRow1,exerciseNameRow2,exerciseNameRow3,exerciseNameRow4,exerciseNameRow5;
    EditText RepetitionsRow1, RepetitionsRow2, RepetitionsRow3, RepetitionsRow4, RepetitionsRow5;
    EditText setsRow1, setsRow2, setsRow3, setsRow4, setsRow5;
    EditText workoutNameEdtx;
    EditText dateEdtx;
    ImageView picRow1, picRow2, picRow3, picRow4, picRow5;

    //Arrays
    String ExercisesNames[]=new String[5];
    String Repetitions[]=new String[5];
    String Sets[]=new String[5];
    String Pics[]=new String[5];
    Button createWorkout;

    //
    String workoutName;
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
        setContentView(R.layout.activity_create__workoust);

        Intent myIntent=getIntent();
        hisUID=myIntent.getStringExtra("nutritionistID");

        //Init Firebase database
        database = FirebaseDatabase.getInstance();
        clientRef = database.getReference("Clients").child(hisUID).child("Workouts").push();
        firebaseAuth= FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Create workout");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //init exercise names
        exerciseNameRow1 = (EditText) findViewById(R.id.mealPlan1NameRow1);
        exerciseNameRow2 = (EditText) findViewById(R.id.mealPlan1NameRow2);
        exerciseNameRow3 = (EditText) findViewById(R.id.mealPlan1NameRow3);
        exerciseNameRow4 = (EditText) findViewById(R.id.mealPlan1NameRow4);
        exerciseNameRow5 = (EditText) findViewById(R.id.mealPlan1NameRow5);




        //init repetitions

        RepetitionsRow1= (EditText) findViewById(R.id.mealPlan2NameRow1);
        RepetitionsRow2= (EditText) findViewById(R.id.mealPlan2NameRow2);
        RepetitionsRow3= (EditText) findViewById(R.id.mealPlan2NameRow3);
        RepetitionsRow4= (EditText) findViewById(R.id.mealPlan2NameRow4);
        RepetitionsRow5= (EditText) findViewById(R.id.mealPlan2NameRow5);




        //init sets
        setsRow1 = (EditText) findViewById(R.id.mealPlan3NameRow1);
        setsRow2 = (EditText) findViewById(R.id.mealPlan3NameRow2);
        setsRow3 = (EditText) findViewById(R.id.mealPlan3NameRow3);
        setsRow4 = (EditText) findViewById(R.id.mealPlan3NameRow4);
        setsRow5 = (EditText) findViewById(R.id.mealPlan3NameRow5);


        //init sets
        picRow1 = (ImageView) findViewById(R.id.picRow1);
        picRow2 = (ImageView) findViewById(R.id.picRow2);
        picRow3 = (ImageView) findViewById(R.id.picRow3);
        picRow4 = (ImageView) findViewById(R.id.picRow4);
        picRow5 = (ImageView) findViewById(R.id.picRow5);


        //init Text views
        dateEdtx= findViewById(R.id.date);
        workoutNameEdtx= findViewById(R.id.nutritionPlanName);


        //init Button
        createWorkout = findViewById(R.id.createNutritionPlan);

        //Button event
        createWorkout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //Validating entries
                if (dateEdtx.length()==0){

                    dateEdtx.setError("Please enter the date of the workout.");
                }

                else if (workoutNameEdtx.length()==0){
                    workoutNameEdtx.setError("Please set a name for the workout.");
                }

                else{

                // get text from edit text
                String id=user.getUid();
                getInputTextFields();

                    if ((exerciseNameRow1.length()==0) || (RepetitionsRow1.length()==0) || (setsRow1.length()==0)){
                        exerciseNameRow1.setError("");
                        RepetitionsRow1.setError("");
                        setsRow1.setError("");

                        Toast.makeText(Create_Workouts.this,"At least one row needs to be filled for the wokout",Toast.LENGTH_LONG).show();
                    }

                    else{


                String workoutid;

                for(int i=0;i<ExercisesNames.length;i++){
                    if ((!ExercisesNames[i].isEmpty()) && (!Repetitions[i].isEmpty()) && (!Sets[i].isEmpty())){

                        Workout workout = new Workout();
                        workout.setExerciseName(ExercisesNames[i].toString().trim());
                        workout.setRepetitions(Repetitions[i].toString().trim());
                        workout.setSets(Sets[i].toString().trim());
                        workout.setExercisePic("");
                        workout.setWorkoutDate(date.toString().trim());
                        workout.setWorkoutName(workoutName.toString().trim());
                        clientRef.setValue(workout);
                        workoutid=clientRef.getKey();;
                        workout.setExerciseID(workoutid);
                        Toast.makeText(Create_Workouts.this,"Workout created!",Toast.LENGTH_SHORT).show();
                        Intent myIntent2 = new Intent(Create_Workouts.this, DashboardActivityCoach.class);
                        // myIntent.putExtra("key", value); //Optional parameters
                        Create_Workouts.this.startActivity(myIntent2);

                    }
                }}

            }}
        });
    }


    public void getInputTextFields(){

        //date and workout name
        date= dateEdtx.getText().toString().trim();
        workoutName=workoutNameEdtx.getText().toString().trim();

        //Add to array
        ExercisesNames[0]=exerciseNameRow1.getText().toString().trim();
        ExercisesNames[1]=exerciseNameRow2.getText().toString().trim();
        ExercisesNames[2]=exerciseNameRow3.getText().toString().trim();
        ExercisesNames[3]=exerciseNameRow4.getText().toString().trim();
        ExercisesNames[4]=exerciseNameRow5.getText().toString().trim();

        //Add to array
        Repetitions[0]=RepetitionsRow1.getText().toString().trim();
        Repetitions[1]=RepetitionsRow2.getText().toString().trim();
        Repetitions[2]=RepetitionsRow3.getText().toString().trim();
        Repetitions[3]=RepetitionsRow4.getText().toString().trim();
        Repetitions[4]=RepetitionsRow5.getText().toString().trim();

        //Add to array
        Sets[0]=setsRow1.getText().toString().trim();
        Sets[1]=setsRow2.getText().toString().trim();
        Sets[2]=setsRow3.getText().toString().trim();
        Sets[3]=setsRow4.getText().toString().trim();
        Sets[4]=setsRow5.getText().toString().trim();



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


