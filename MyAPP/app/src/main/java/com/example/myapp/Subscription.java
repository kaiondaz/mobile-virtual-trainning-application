package com.example.myapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Subscription extends AppCompatActivity {

    private String price;
    RadioGroup radiogroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        radiogroup=findViewById(R.id.radioGroup);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);


        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Subscription");
        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    //redirecting to payment page method after choosing subscription
    public void goToPayment(String price){

        //Payment_Page payment= new Payment_Page(price);
        Toast.makeText(Subscription.this,"redirecting...",Toast.LENGTH_LONG).show();
        Intent intent =new Intent(Subscription.this, Payment_Page.class);
        intent.putExtra("price", price);
        Subscription.this.startActivity(intent);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }



    public void onRadioButtonClicked(View view) {
        radiogroup=findViewById(R.id.radioGroup);
        int radioId=radiogroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioId);

        // Check which radio button was clicked
        switch(radioButton.getId()) {
            case R.id.radioButton:
                price="25";
                goToPayment(price);
                break;
            case R.id.radioButton2:
                price="35";
                goToPayment(price);
                break;
            case R.id.radioButton3:
                price="40";
                goToPayment(price);
                break;
        }


    }

    //Which button is checked
    public void checkButton(View view) {

        int radioId=radiogroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioId);
    }


}
