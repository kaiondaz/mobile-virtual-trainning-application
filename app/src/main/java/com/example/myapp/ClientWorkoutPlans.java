package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapp.Adapters.ClientsWorkoutPlansAdapter;
import com.example.myapp.Models.Workout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClientWorkoutPlans extends AppCompatActivity {

    DatabaseReference ClientsRef,workoutPlanRef,workoutPlanRef2;
    RecyclerView recyclerView;
    ArrayList<Workout> list;
    ClientsWorkoutPlansAdapter adapter;
    String nutritionPlanName;
    String nutritionPlanDate;
    String hisUID;

    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    String currentUserID;
    String currentUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_workout_plans);

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Workout Plans");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //Getting Client ID from coach's client
        Intent intent=getIntent();
        hisUID=intent.getStringExtra("hisUID");

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        currentUserID = user.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ClientsRef =firebaseDatabase.getReference("Clients");
        workoutPlanRef=firebaseDatabase.getReference("Clients").child(currentUserID).child("Workouts");
        final String id=user.getUid();

        recyclerView =  (RecyclerView) findViewById(R.id.recyclerViewView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(ClientWorkoutPlans.this));
        list = new ArrayList<Workout>();


        if (!(hisUID==null)) {

            workoutPlanRef2=firebaseDatabase.getReference("Clients").child(hisUID).child("Workouts");

            workoutPlanRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Workout workoutPlan = dataSnapshot1.getValue(Workout.class);
                        if (!(workoutPlan == null)) {
                            list.add(workoutPlan);
                        }
                    }

                    recyclerView = findViewById(R.id.recyclerViewView2);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ClientWorkoutPlans.this));

                    adapter = new ClientsWorkoutPlansAdapter(ClientWorkoutPlans.this, list, workoutPlanRef2);
                    recyclerView.setAdapter(adapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ClientWorkoutPlans.this, "Can't connect to the database.", Toast.LENGTH_SHORT).show();

                }
            });

        }

         else{

            workoutPlanRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Workout workoutPlan = dataSnapshot1.getValue(Workout.class);
                        if (!(workoutPlan == null)) {
                            list.add(workoutPlan);
                        }
                    }

                    recyclerView = findViewById(R.id.recyclerViewView2);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ClientWorkoutPlans.this));

                    adapter = new ClientsWorkoutPlansAdapter(ClientWorkoutPlans.this, list, workoutPlanRef);
                    recyclerView.setAdapter(adapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ClientWorkoutPlans.this, "Can't connect to the database.", Toast.LENGTH_SHORT).show();

                }
            });
    }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous activity
    }


}
