package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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


public class user_details extends AppCompatActivity {

    //views
    //EditText mEmailEt, mPasswordEt;
    TextView nameTv,emailTv,addressTv;
    Button Updatedetails;
    //ProgressDialog pd;


    //Declare an instance of FirebaseAuth
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    Client2 client;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    String currentUserID;
    TextView phoneTv;
    ImageView AvatarIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("My details");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        currentUserID=user.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference = firebaseDatabase.getReference("Clients").child(currentUserID);
        //UsersRef=firebaseDatabase.getReference("Users");


        nameTv= findViewById(R.id.NameTv);
        emailTv= findViewById(R.id.EmailTv);
        addressTv=findViewById(R.id.homeTv);
        Updatedetails=findViewById(R.id.update_details_bttn);
        phoneTv= findViewById(R.id.phonenumberTv);

        //AvatarIv=findViewById(R.id.avatarImageView2);


        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        databaseReference.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        //check until required data get
                                                        // This method is called once with the initial value and again
                                                        // whenever data at this location is updated.
                                                        Client2 client = dataSnapshot.getValue(Client2.class);
                                                        Toast.makeText(user_details.this, "SUCCESS"+client.getName(), Toast.LENGTH_SHORT).show();
                                                        nameTv.setText(client.getName());
                                                        emailTv.setText(client.getEmail());
                                                        addressTv.setText((client.getAddress()));
                                                        phoneTv.setText(client.getPhone());

                                                        String link=client.getImageAddress();


                                                        //try{
                                                          //  Picasso.get().load(link).fit().into(AvatarIv);
                                                            //Toast.makeText(user_details.this, link, Toast.LENGTH_LONG).show();

                                                            // Picasso.Builder builder = new Picasso.Builder(getContext());
                                                            //builder.listener(new Picasso.Listener() {


                                                            // @Override
                                                            //public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                                            //  exception.printStackTrace();
                                                            //};

                                                            //});
                                                        //}

                                                        //catch(Exception e) {
                                                            //if there is any execption while geting the image  then set default
                                                            //Picasso.get().load(R.mipmap.ic_add_image).into(AvatarIv);
                                                        //}

                                                        // Log.d(TAG, "Value is: " + value);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }



                                                }


        );


        Updatedetails.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view) {

                Intent myIntent = new Intent(user_details.this, Update_details.class);
                // myIntent.putExtra("key", value); //Optional parameters
                user_details.this.startActivity(myIntent);

            }});

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous activity
    }



}
