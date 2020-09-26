package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Models.Client2;
import com.example.myapp.Models.Coach;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CoachProfile extends AppCompatActivity {

    //Fields
    String coachID;
    Button viewReviews, addReviewBtn;
    TextView CoachNameTv, CoachEmailTv;
    ImageView AvatarUserProfile;
    String clientID;

    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference CoachesRef, ClientsRef;
    String currentUserID;
    String currentUserName;
    String coachName;
    String coachProfilepic;
    String nutritionistProfilepic;
    String currentUserPic;
    String professional;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_profile);


        //Init Buttons
        viewReviews = (Button) findViewById(R.id.viewWorkouts);
        addReviewBtn = (Button) findViewById(R.id.addReviewBtn);


        //Init text views
        CoachNameTv = (TextView) findViewById(R.id.nutritionistNameTv);
        CoachEmailTv = (TextView) findViewById(R.id.CoachEmailTv);

        //Init image view
        AvatarUserProfile = (ImageView) findViewById(R.id.AvatarUserProfile);

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        currentUserID = user.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ClientsRef = firebaseDatabase.getReference("Clients");

        //Action bar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("My Coach");
        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        ClientsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Client2 client = dataSnapshot1.getValue(Client2.class);
                    if (!(client.getUID() == null)) {
                        if (client.getUID().equals(currentUserID)) {
                            coachID = client.getCoachID().toString().trim();
                            currentUserName = client.getName().toString().trim();
                            currentUserPic = client.getImageAddress().toString().trim();
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        CoachesRef = firebaseDatabase.getReference("Coaches");

        //Query query = CoachesRef.orderByChild("email").equalTo(user.getEmail());
        CoachesRef.addValueEventListener(new ValueEventListener() {

                                             @Override
                                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                 //CoachesRef = firebaseDatabase.getReference("Coaches").child(nutritionistID);

                                                 //check until required data get
                                                 // This method is called once with the initial value and again
                                                 // whenever data at this location is updated.
                                                 for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                     Coach coach = dataSnapshot1.getValue(Coach.class);

                                                     if (!(coach.getUID() == null)) {
                                                         //If the nutritionistID matches the clients nutritionistID
                                                         if (coach.getUID().equals(coachID)) {
                                                             //get coach details
                                                             coachName = coach.getName();
                                                             coachProfilepic = coach.getImageAddress();
                                                             CoachNameTv.setText(coach.getName());
                                                             CoachEmailTv.setText(coach.getEmail());
                                                             String link = coach.getImageAddress();


                                                             try {
                                                                 Picasso.get().load(link).fit().into(AvatarUserProfile);
                                                                 //Toast.makeText(getContext(), link, Toast.LENGTH_LONG).show();

                                                                 Picasso.Builder builder = new Picasso.Builder(getBaseContext());
                                                                 builder.listener(new Picasso.Listener() {


                                                                     @Override
                                                                     public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                                                         exception.printStackTrace();
                                                                     }

                                                                     ;

                                                                 });
                                                             } catch (Exception e) {
                                                                 //if there is any execption while geting the image  then set default
                                                                 Picasso.get().load(R.mipmap.ic_add_image).into(AvatarUserProfile);
                                                             }
                                                         }
                                                     }

                                                     // Log.d(TAG, "Value is: " + value);
                                                 }
                                             }

                                             @Override
                                             public void onCancelled(@NonNull DatabaseError databaseError) {

                                             }


                                         }

        );


        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                professional = "coach";

                //New intent to Create a review
                Intent myIntent = new Intent(CoachProfile.this, Create_Reviews.class);

                //Values to pass to next intent for the creation of the review
                myIntent.putExtra("coachID", coachID);
                myIntent.putExtra("currentUserID", currentUserID);
                myIntent.putExtra("currentUserName", currentUserName);
                myIntent.putExtra("currentUserPic", currentUserPic);
                myIntent.putExtra("nutritionistName", coachName);
                myIntent.putExtra("coachProfilepic", coachProfilepic);
                myIntent.putExtra("professional", professional);
                CoachProfile.this.startActivity(myIntent);


            }
        });


        viewReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                professional = "coach";

                //New intent to Create a review
                Intent myIntent = new Intent(CoachProfile.this, Display_Reviews.class);

                //Values to pass to next intent for the creation of the review
                myIntent.putExtra("coachID", coachID);
                myIntent.putExtra("currentUserID", currentUserID);
                myIntent.putExtra("currentUserName", currentUserName);
                myIntent.putExtra("coachName", coachName);
                myIntent.putExtra("coachProfilepic", coachProfilepic);
                myIntent.putExtra("currentUserPic", currentUserPic);
                myIntent.putExtra("professional", professional);
                CoachProfile.this.startActivity(myIntent);

            }
        });


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }


}
