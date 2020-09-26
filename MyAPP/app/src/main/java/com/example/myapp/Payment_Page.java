package com.example.myapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Payment_Page extends AppCompatActivity {

    private String Price;
    Activity activity;
    String price;
    //TextView to display price
    TextView display_amountTv;


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

    }


   /** public Payment_Page(Activity activity) {
        this.activity=activity;
    }*/



    //public Payment_Page(String price){
      //  Price=price;
        //TextView amount =(TextView) this.activity.findViewById(R.id.display_amount);
        //amount.setText("£ "+Price.trim());
  //}

    public void pay(View v){
        startActivity(new Intent(Payment_Page.this, DashboardActivity.class));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }



}
