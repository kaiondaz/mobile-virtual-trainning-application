package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class CoachProfileDetails extends AppCompatActivity {

    //views
    //EditText mEmailEt, mPasswordEt;
    EditText nameTv,emailTv,addressTv;
    Button Updatedetails;
    //ProgressDialog pd;


    //Declare an instance of FirebaseAuth
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,databaseReferenceName,databaseReferenceAddress,databaseReferencePhone;
    Client2 client;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    String currentUserID;
    TextView phoneTv;
    ImageView AvatarIv;

    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_profile_details);

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("My details");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //Get values from previous activity
        Intent intent = getIntent();
        userType = intent.getStringExtra("coach");


        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        currentUserID=user.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference = firebaseDatabase.getReference("Coaches").child(currentUserID);
        databaseReferenceName = firebaseDatabase.getReference("Coaches").child(currentUserID).child("name");
        databaseReferenceAddress = firebaseDatabase.getReference("Coaches").child(currentUserID).child("address");
        databaseReferencePhone = firebaseDatabase.getReference("Coaches").child(currentUserID).child("phone");


        //UsersRef=firebaseDatabase.getReference("Users");


        nameTv= findViewById(R.id.NameEdTxt);
        emailTv= findViewById(R.id.EmailTv);
        addressTv=findViewById(R.id.AddressTv);
        Updatedetails=findViewById(R.id.update_details_bttn);
        phoneTv= findViewById(R.id.PriceTv);
        AvatarIv=findViewById(R.id.profilePicTv);

        // Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());

        databaseReference.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        //check until required data get
                                                        // This method is called once with the initial value and again
                                                        // whenever data at this location is updated.
                                                        Coach coach = dataSnapshot.getValue(Coach.class);

                                                        nameTv.setText(coach.getName());
                                                        emailTv.setText(coach.getEmail());
                                                        addressTv.setText(coach.getAddress());
                                                        phoneTv.setText(coach.getPhone());
                                                        String link=coach.getImageAddress();

                                                        Picasso.get().load(link).fit().into(AvatarIv);
                                                        //Toast.makeText(getContext(), link, Toast.LENGTH_LONG).show();

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }


                                                }


        );


        Updatedetails.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view) {

                databaseReference.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                //check until required data get
                                                                // This method is called once with the initial value and again
                                                                // whenever data at this location is updated.
                                                                Client2 client = dataSnapshot.getValue(Client2.class);


                                                                databaseReferenceAddress.setValue(addressTv.getText().toString().trim());
                                                                databaseReferenceName.setValue(nameTv.getText().toString().trim());
                                                                databaseReferencePhone.setValue(phoneTv.getText().toString().trim());


                                                                Toast.makeText(CoachProfileDetails.this, client.getName()+" you have updated your details.", Toast.LENGTH_LONG).show();

                                                                finish();

                                                                // String link=client.getImageAddress();

                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }



                                                        }


                );


            }});

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous activity
    }



}
