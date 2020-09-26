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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Models.Client2;
import com.example.myapp.Models.Review;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Create_Reviews extends AppCompatActivity {

    //Firebase ID's' for passed values (Coach)
    String coachID;
    String currentUserID;
    String currentUserName;
    String coachName;
    String coachProfilepic;
    String professional;


    //Firebase ID's' for passed values (Nutritionists)
    String nutritionistProfilePic;
    String nutritionistName;
    String nutritionistID;



    //Views fields
    TextView coachNameTv;
    ImageView profilePic;

    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference CoachesReviewsRef, ClientsRef,NutritionistsReviewsRef,coachProfilePicReference;
    private String currentUserPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reviews);

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Add review");
        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //init Views
        coachNameTv=findViewById(R.id.coachNameTv);
        profilePic= findViewById(R.id.profilePic);


        //If the person being reviewed is a coach

        //Get values from previous activity
        Intent intent = getIntent();
        professional = intent.getStringExtra("professional");

        coachID = intent.getStringExtra("coachID");
        currentUserID= intent.getStringExtra("currentUserID");
        currentUserPic = intent.getStringExtra("currentUserPic");
        currentUserName= intent.getStringExtra("currentUserName");
        coachName= intent.getStringExtra("coachName");
        coachProfilepic= intent.getStringExtra("coachProfilepic");


        //Get values from previous activity
        nutritionistID = intent.getStringExtra("nutritionistID");
        currentUserID= intent.getStringExtra("currentUserID");
        currentUserName= intent.getStringExtra("currentUserName");
        nutritionistName= intent.getStringExtra("nutritionistName");
        nutritionistProfilePic= intent.getStringExtra("nutritionistProfilePic");

        //If the person is a coach
        if (professional.equals("coach")){

            //Set views
        coachNameTv.setText(coachName);
        try{
            Picasso.get().load(coachProfilepic).fit().into(profilePic);
            //Toast.makeText(getContext(), link, Toast.LENGTH_LONG).show();

            Picasso.Builder builder = new Picasso.Builder(Create_Reviews.this);
            builder.listener(new Picasso.Listener() {


                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                };

            });
        }

        catch(Exception e) {
            //if there is any execption while geting the image  then set default
            Picasso.get().load(R.mipmap.ic_add_image).into(profilePic);
        }


        firebaseDatabase=FirebaseDatabase.getInstance();

        //Coaches ClientsRef
        CoachesReviewsRef = firebaseDatabase.getReference("Coaches").child(coachID).child("Reviews").push();

        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        Button submitBtn = (Button) findViewById(R.id.submitReviewbtn);
        final TextView ratingDisplayTextView = (TextView) findViewById(R.id.ratingDisplayTxtView);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                ratingDisplayTextView.setText("Your rating is "+ ratingBar.getRating());
                String Rating = ""+ratingBar.getRating();
                Review review =new Review();
                review.setDate(formatter.format(date).toString().trim());
                review.setReviewerID(currentUserID);
                review.setReviewerName(currentUserName);
                review.setReviewerPic(currentUserPic);
                review.setStars(Rating);
                CoachesReviewsRef.setValue(review);
                Toast.makeText(Create_Reviews.this,"Review submitted",Toast.LENGTH_LONG).show();


            }
        });
        }


        //If the person is a nutritionist
        if (professional.equals("nutritionist")){

            //Set views
            coachNameTv.setText(nutritionistName);
            try{


                Picasso.get().load(nutritionistProfilePic).fit().into(profilePic);
                //Toast.makeText(getContext(), link, Toast.LENGTH_LONG).show();

                Picasso.Builder builder = new Picasso.Builder(Create_Reviews.this);
                builder.listener(new Picasso.Listener() {


                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        exception.printStackTrace();
                    };

                });
            }

            catch(Exception e) {
                //if there is any exception while geting the image  then set default
                Picasso.get().load(R.mipmap.ic_add_image).into(profilePic);
            }



            firebaseDatabase=FirebaseDatabase.getInstance();

            //Coaches ClientsRef
            NutritionistsReviewsRef = firebaseDatabase.getReference("Nutritionists").child(nutritionistID).child("Reviews").push();

            final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
            Button submitBtn = (Button) findViewById(R.id.submitReviewbtn);
            final TextView ratingDisplayTextView = (TextView) findViewById(R.id.ratingDisplayTxtView);

            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date(System.currentTimeMillis());
                    ratingDisplayTextView.setText("Your rating is "+ ratingBar.getRating());
                    String Rating = ""+ratingBar.getRating();
                    Review review =new Review();
                    review.setDate(formatter.format(date).toString().trim());
                    review.setReviewerID(currentUserID);
                    review.setReviewerName(currentUserName);
                    review.setReviewerPic(currentUserPic);
                    review.setStars(Rating);

                    //If the person is a coach store review on coaches clientsReference
                    if (professional.equals("coach")){
                        CoachesReviewsRef.setValue(review);
                        Toast.makeText(Create_Reviews.this,"Review submitted",Toast.LENGTH_LONG).show();

                    }

                    //If the person is a nutritionist store review on nutritionists clientsReference
                    if (professional.equals("nutritionist")){
                        NutritionistsReviewsRef.setValue(review);
                        Toast.makeText(Create_Reviews.this,"Review submitted",Toast.LENGTH_LONG).show();
                    }


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
