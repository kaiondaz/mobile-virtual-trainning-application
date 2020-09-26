package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapp.Adapters.ClientsNutritionPlansAdapter;
import com.example.myapp.Models.NutritionPlan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClientNutritionPlans extends AppCompatActivity {

    DatabaseReference ClientsRef,nutritionPlanRef,nutritionPlanRef2;
    RecyclerView recyclerView;
    ArrayList<NutritionPlan> list;
    ClientsNutritionPlansAdapter adapter;
    String nutritionPlanName;
    String nutritionPlanDate;
    String hisUID;

    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    String currentUserID;
    String currentUserName;

    /**
     public CoachClients() {
     // Required empty public constructor
     }
     */

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_nutrition_plans);

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Nutrition Plans");

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
        nutritionPlanRef=firebaseDatabase.getReference("Clients").child(currentUserID).child("Nutrition plans");
        final String id=user.getUid();

        recyclerView =  (RecyclerView) findViewById(R.id.recyclerViewView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ClientNutritionPlans.this));
        list = new ArrayList<NutritionPlan>();


        if (!(hisUID==null)){
            nutritionPlanRef2=firebaseDatabase.getReference("Clients").child(hisUID).child("Nutrition plans");

            nutritionPlanRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())

                    {
                        NutritionPlan nutritionPlan =dataSnapshot1.getValue(NutritionPlan.class);
                        if(!(nutritionPlan==null)){
                            list.add(nutritionPlan);
                        }
                    }

                    recyclerView = findViewById(R.id.recyclerViewView);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ClientNutritionPlans.this));

                    adapter= new ClientsNutritionPlansAdapter(ClientNutritionPlans.this,list,nutritionPlanRef2);
                    recyclerView.setAdapter(adapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ClientNutritionPlans.this,"Can't connect to the database.",Toast.LENGTH_SHORT).show();

                }
            });



        }

        else{

        nutritionPlanRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())

                {
                    NutritionPlan nutritionPlan =dataSnapshot1.getValue(NutritionPlan.class);
                    if(!(nutritionPlan==null)){
                        list.add(nutritionPlan);
                    }
                }

                recyclerView = findViewById(R.id.recyclerViewView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(ClientNutritionPlans.this));

                adapter= new ClientsNutritionPlansAdapter(ClientNutritionPlans.this,list,nutritionPlanRef);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ClientNutritionPlans.this,"Can't connect to the database.",Toast.LENGTH_SHORT).show();

            }
        });


    }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }


}
