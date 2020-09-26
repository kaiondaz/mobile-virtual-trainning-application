package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Fragment_home extends AppCompatActivity {

    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase FirebaseDatabase;
    DatabaseReference Databasereference;

    //Views from xml
    ImageView avatarIv;
    TextView nameTv, emailTv;


    public Fragment_home(){
        //Required empty profile constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        //init Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        FirebaseDatabase= com.google.firebase.database.FirebaseDatabase.getInstance();
        Databasereference=FirebaseDatabase.getReference("Clients");

        //init Views
        avatarIv=findViewById(R.id.AddressTv);
        nameTv=findViewById(R.id.NameEdTxt);
        emailTv=findViewById(R.id.EmailTv);


        Query query =Databasereference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Check until required data get
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    //Get data
                    String name = "" + ds.child("name").getValue();
                    //String address= ""+ ds.child("address").getValue();
                    String email = "" + ds.child("email").getValue();
                    //String image = "" + ds.child("imageAddress").getValue();
                    //System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"+name);

                    //set Data
                    nameTv.setText(name);
                    emailTv.setText(email);

                    try {
                        //If image is received then set into imageView
                       // Picasso.get().load(image).into(avatarIv);
                    } catch (Exception e) {
                        //If there is an exception getting then set to default
                       // Picasso.get().load(R.mipmap.ic_add_image).into(avatarIv);


                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
          });








    }
}
