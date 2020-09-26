package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LOOOOOOOOOOL extends AppCompatActivity {

    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,UsersRef ;
    String currentUserID;


    //Views from xml
    TextView nameTv;
    TextView emailTv;
    ImageView avatarIv;
    String Name;
    View profileView;
    private RecyclerView myProfileInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looooooooool);

        nameTv = findViewById(R.id.NAMETEXTVIEW);
        emailTv = findViewById(R.id.EMAILTEXTVIEW);

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        currentUserID = user.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference = firebaseDatabase.getReference("Clients").child(currentUserID);
        //UsersRef=firebaseDatabase.getReference("Users");


        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //check until required data get
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Client2 client = dataSnapshot.getValue(Client2.class);
                    Toast.makeText(LOOOOOOOOOOL.this, "SUCCESS"+client.getName(), Toast.LENGTH_SHORT).show();
                   nameTv.setText(client.getName());

                    // Log.d(TAG, "Value is: " + value);
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


            // try{
                //       Picasso.get().load(Image).into(avatarIv);
                // }

                //catch(Exception e) {
                //if there is any execption while geting the image  then set default
                //  Picasso.get().load(R.mipmap.ic_add_image).into(avatarIv);
                //}}}

                //  @Override
                //public void onCancelled(@NonNull DatabaseError databaseError) {
                //  Toast.makeText(LOOOOOOOOOOL.this,"NOOOOOT working",Toast.LENGTH_LONG).show();


                //}


            }






        );


    }}
