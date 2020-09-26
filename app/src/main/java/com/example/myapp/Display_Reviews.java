package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapp.Adapters.AdapterReviews;
import com.example.myapp.Models.Client2;
import com.example.myapp.Models.Review;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Display_Reviews extends AppCompatActivity {

    //Firebase ID's' for passed values
    String coachID;
    String currentUserID;
    String currentUserName;
    String coachName;
    String coachProfilepic;
    String currentUserPic;
    String Professional;

    DatabaseReference referenceCoachReviews, referenceCurrentUser, referenceNutritionistReviews;
    RecyclerView recyclerView;
    ArrayList<Review> list;
    AdapterReviews adapter;
    FirebaseAuth auth;
    String professional;
    String nutritionistID;
    String nutritionistName;
    String nutritionistProfilePic;

    /**
     public CoachClients() {
     // Required empty public constructor
     }
     */

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_clients);

        recyclerView =  (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(Display_Reviews.this));
        list = new ArrayList<Review>();


        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Reviews");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        final String id=user.getUid();

        //Get values from previous activity
        Intent intent = getIntent();
        professional = intent.getStringExtra("professional");

        coachID = intent.getStringExtra("coachID");
        currentUserID= intent.getStringExtra("currentUserID");
        currentUserName= intent.getStringExtra("currentUserName");
        coachName= intent.getStringExtra("coachName");
        coachProfilepic= intent.getStringExtra("coachProfilepic");
        currentUserPic = intent.getStringExtra("currentUserPic");


        //Get values from previous activity
        nutritionistID = intent.getStringExtra("nutritionistID");
        currentUserID= intent.getStringExtra("currentUserID");
        currentUserName= intent.getStringExtra("currentUserName");
        nutritionistName= intent.getStringExtra("nutritionistName");
        nutritionistProfilePic= intent.getStringExtra("nutritionistProfilePic");

        //init firebase references
        if (professional.equals("coach")){
            referenceCoachReviews = FirebaseDatabase.getInstance().getReference("Coaches").child(coachID).child("Reviews");
        }

        if (professional.equals("nutritionist")) {
            referenceNutritionistReviews = FirebaseDatabase.getInstance().getReference("Nutritionists").child(nutritionistID).child("Reviews");
        }

        referenceCurrentUser = FirebaseDatabase.getInstance().getReference("Clients").child(currentUserID);


        referenceCurrentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Client2 modelClient= dataSnapshot.getValue(Client2.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //If the person is as a coach
        if (professional.equals("coach")){
        referenceCoachReviews.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())

                {
                    Review review =dataSnapshot1.getValue(Review.class);

                        list.add(review);
                }

                adapter= new AdapterReviews(Display_Reviews.this,list,currentUserPic);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Display_Reviews.this,"Unable to connect to database. ",Toast.LENGTH_SHORT).show();

            }
        });
        }



        //If the person is as a coach
        if (professional.equals("nutritionist")){
            referenceNutritionistReviews.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())

                    {
                        Review review =dataSnapshot1.getValue(Review.class);

                        list.add(review);
                    }

                    adapter= new AdapterReviews(Display_Reviews.this,list,currentUserPic);
                    recyclerView.setAdapter(adapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Display_Reviews.this,"Unable to connect to database. ",Toast.LENGTH_SHORT).show();

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
