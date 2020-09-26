package com.example.myapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_home4 extends Fragment{


    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    com.google.firebase.database.FirebaseDatabase FirebaseDatabase;
    DatabaseReference Databasereference;

    //Views from xml
    ImageView avatarIv;
    TextView nameTv, emailTv;

    public Fragment_home4() {
        // Required empty public constructor

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        //init Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        FirebaseDatabase= com.google.firebase.database.FirebaseDatabase.getInstance();
        Databasereference=FirebaseDatabase.getReference("Clients");

        //init Views
        avatarIv= view.findViewById(R.id.homeTv);
        nameTv=view.findViewById(R.id.NameTv);
        emailTv= view.findViewById(R.id.EmailTv);


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
        return view;
    }


}
