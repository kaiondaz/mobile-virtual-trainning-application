package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Models.Subscription;
import com.example.myapp.Models.Client2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Payment_Page extends AppCompatActivity {

    private String Price;
    Activity activity;
    String price;
    //TextView to display price
    TextView display_amountTv;
    EditText Name_on_cardTxtB,card_numberTxtB,Exp_dateTxtB,sec_CodeTxtB,Zip_Postal_codeTxtB;
    String fromSubscriptionPage2;
    String subscriptionType2;

    //Firebase fields
    FirebaseDatabase database;
    DatabaseReference ref,clientRef;
    Client2 client;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__page);

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Payment");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Intent intent=getIntent();
        price=intent.getStringExtra("price");

        //init amount to pay textView
        display_amountTv=findViewById(R.id.display_amount);

        //set price on the amount TextView
        display_amountTv.setText("£"+price);

        //Init fields
        Name_on_cardTxtB = findViewById(R.id.Name_on_cardTxtB);
        card_numberTxtB = findViewById(R.id.card_numberTxtB);
        Exp_dateTxtB = findViewById(R.id.Exp_dateTxtB);
        sec_CodeTxtB = findViewById(R.id.sec_CodeTxtB);
        Zip_Postal_codeTxtB = findViewById(R.id.Zip_Postal_codeTxtB);

        Intent intent1=new Intent();
        fromSubscriptionPage2=intent1.getStringExtra("fromSubscriptionPage");
        subscriptionType2=intent1.getStringExtra("subscriptionType");
        Toast.makeText(Payment_Page.this,fromSubscriptionPage2,Toast.LENGTH_LONG).show();

        //init
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Clients");
        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String id=firebaseUser.getUid();
        client=new Client2();


            clientRef=database.getReference("Clients").child(id);


    }


    public void pay(View v){
        if (Name_on_cardTxtB.length()==0){

            Name_on_cardTxtB.setError("Please enter your name on the card");
        }

        else if (card_numberTxtB.length()==0){
            card_numberTxtB.setError("Please enter a card number");
        }

        else if (Exp_dateTxtB.length()==0){
            Exp_dateTxtB.setError("Please enter the exp. date on the card");
        }

        else if (sec_CodeTxtB.length()==0){
            sec_CodeTxtB.setError("Please enter the security code on the card");
        }

        else if (Zip_Postal_codeTxtB.length()==0){
            Zip_Postal_codeTxtB.setError("Please enter your card registered zip/postal code");
        }

        else {
            clientRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Subscription subscription=new Subscription();

                    subscription.setType(subscriptionType2);
                    subscription.setPrice(price);

                    clientRef.child("Subscription").setValue(subscription);
                    startActivity(new Intent(Payment_Page.this, DashboardActivityClient.class));


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

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
