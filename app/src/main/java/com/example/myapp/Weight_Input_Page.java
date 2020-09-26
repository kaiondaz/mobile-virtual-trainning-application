package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapp.Models.Client;
import com.example.myapp.Models.Client2;
import com.example.myapp.Models.StartingWeighTracker;
import com.example.myapp.Models.Subscription;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Weight_Input_Page extends AppCompatActivity {

    EditText startingWeightEdtTx;
    String startingWeight2;
    EditText currentWeightInputEdtTx;
    Button submitBttn;

    //Firebase
    DatabaseReference reference;
    FirebaseAuth auth;
    Boolean startingWeight = false ;
    String id;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report__input_page);


        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Track weight");
        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        startingWeightEdtTx = (EditText) findViewById(R.id.startingWeightEdtTx2);
        currentWeightInputEdtTx = (EditText) findViewById(R.id.currentWeightInputEdtTx);
        submitBttn = (Button) findViewById(R.id.submitButton);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        id=user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Clients");



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())

                {
                    //If the starting weight is found in the database display the same on the edit text
                    //box
                    Client2 client = dataSnapshot1.getValue( Client2.class);

                    if(!(client.getUID()==null)){

                            if(!(client.getStartingWeight() == null)){

                                startingWeight2 = client.getStartingWeight();
                                startingWeightEdtTx.setText(startingWeight2);

                            }

                        }


                }
            }

            //If no clientsReference is found for the initial weight store initial weight information on database
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });




        //Button event to submit values
        submitBttn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())

                        {
                            //If the starting weight is found in the database display the same on the edit text
                            //box
                            Client2 client = dataSnapshot1.getValue( Client2.class);

                            if(!(client.getUID()==null)){
                                if (client.getUID().equals(id)){
                                    //if the starting weight its not found in the database then store the new weight as initial weight
                                    if(client.getStartingWeight()== null){

                                        storeInitialWeight();

                                    }
                                    if(!(client.getStartingWeight() == null)){

                                        startingWeight2 = client.getStartingWeight();
                                        startingWeightEdtTx.setText(startingWeight2);

                                    }

                                }
                            }

                        }
                    }

                    //If no clientsReference is found for the initial weight store initial weight information on database
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });


            }
        });

    }


    //Method to store the current weight
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void storeWeight (){

        // read the index key
        String weightID = reference.child(id).child("WeightTracker").push().getKey();
        reference.child(id).child("WeightTracker").child(weightID).setValue(currentWeightInputEdtTx.getText().toString().trim());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        reference.child(id).child("WeightTracker").child(weightID).child("startingWeightDate").setValue(date.toString().trim());

    }

    //Method to store the initial weight
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void storeInitialWeight (){

        reference.child(id).child("startingWeight").setValue(currentWeightInputEdtTx.getText().toString().trim());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        reference.child(id).child("startingWeight").child("startingWeightDate").setValue(date.toString().trim());

    }


    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }





    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }


}
