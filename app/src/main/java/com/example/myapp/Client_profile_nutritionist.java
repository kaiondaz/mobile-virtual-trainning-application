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

import com.example.myapp.Models.Client2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Client_profile_nutritionist extends AppCompatActivity {


    //Fields
    String hisUID;
    Button viewNutritionBtn,addNutritionPlanBtn;
    TextView ClientUsernameTv,ClientEmailTv;
    ImageView AvatarUserProfile;

    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,UsersRef ;
    String currentUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile_nutritionist);

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("My Client");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        //Init Buttons
        viewNutritionBtn= (Button) findViewById(R.id.viewNutritionBtn);
        addNutritionPlanBtn= (Button) findViewById(R.id.addNutritionPlanBtn2);

        //Init text views
        ClientUsernameTv = (TextView) findViewById(R.id.nutritionistNameTv2);
        ClientEmailTv = (TextView) findViewById(R.id.coachEmailTv);

        //Init image view
        AvatarUserProfile = (ImageView) findViewById(R.id.avatarUserProfile);
        Intent intent=getIntent();
        hisUID=intent.getStringExtra("hisUID");
        //hisUID=intent.getStringExtra("NutritionistID");


        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        currentUserID = user.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Clients").child(hisUID);

        //Query query = CoachesRef.orderByChild("email").equalTo(user.getEmail());
        databaseReference.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        //check until required data get
                                                        // This method is called once with the initial value and again
                                                        // whenever data at this location is updated.
                                                        Client2 client = dataSnapshot.getValue(Client2.class);
                                                        //Toast.makeText(getContext(), "SUCCESS"+client.getName(), Toast.LENGTH_SHORT).show();
                                                        ClientUsernameTv.setText(client.getName());
                                                        ClientEmailTv.setText(client.getEmail());
                                                        String link=client.getImageAddress();


                                                        try{
                                                            Picasso.get().load(link).fit().into(AvatarUserProfile);
                                                            //Toast.makeText(getContext(), link, Toast.LENGTH_LONG).show();

                                                            Picasso.Builder builder = new Picasso.Builder(getBaseContext());
                                                            builder.listener(new Picasso.Listener() {


                                                                @Override
                                                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                                                    exception.printStackTrace();
                                                                };

                                                            });
                                                        }

                                                        catch(Exception e) {
                                                            //if there is any execption while geting the image  then set default
                                                            Picasso.get().load(R.mipmap.ic_add_image).into(AvatarUserProfile);
                                                        }

                                                        // Log.d(TAG, "Value is: " + value);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }


                                                }

        );



        if(user.getEmail().contains("@my.nutrition")){
            addNutritionPlanBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(Client_profile_nutritionist.this, Create_Nutrition_Plan.class);
                    // myIntent.putExtra("key", value); //Optional parameters
                    myIntent.putExtra("nutritionistID", hisUID);
                    Client_profile_nutritionist.this.startActivity(myIntent);

                }
            });}


        viewNutritionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Client_profile_nutritionist.this, ClientNutritionPlans.class);
                myIntent.putExtra("hisUID", hisUID);
                Client_profile_nutritionist.this.startActivity(myIntent);

            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }

}
