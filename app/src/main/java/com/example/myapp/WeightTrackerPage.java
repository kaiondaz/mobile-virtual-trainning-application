package com.example.myapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WeightTrackerPage extends AppCompatActivity {
    private Button inputWeightBttn;
    private Button weightChartBttn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_tracker_page);

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Track weight");
        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        inputWeightBttn = findViewById(R.id.inputWeightBttn);
        weightChartBttn = findViewById(R.id.weightChartBttn);



        inputWeightBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(WeightTrackerPage.this, Weight_Input_Page.class);
                // myIntent.putExtra("key", value); //Optional parameters
                WeightTrackerPage.this.startActivity(intent);
            }
        });


        weightChartBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(WeightTrackerPage.this, ProgressChart.class);
                // myIntent.putExtra("key", value); //Optional parameters
                WeightTrackerPage.this.startActivity(intent);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }






}
