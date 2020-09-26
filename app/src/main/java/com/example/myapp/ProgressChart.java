package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.myapp.Models.Client2;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProgressChart extends AppCompatActivity {

    //Fields
    String startingWeight2;

    //Line Chart field
    LineChart mpLineChart;
    String IBM;
    String Weight;
    String strength;

    //Firebase
    DatabaseReference reference;
    FirebaseAuth auth;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_chart);

        //Action bar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Progress chart");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        reference = FirebaseDatabase.getInstance().getReference("Clients");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    //If the starting weight is found in the database display the same on the edit text
                    //box
                    Client2 client = dataSnapshot1.getValue(Client2.class);

                    if (!(client.getUID() == null)) {

                        if (!(client.getStartingWeight() == null)) {

                            startingWeight2 = client.getStartingWeight();

                        }

                    }


                }
            }

            //If no clientsReference is found for the initial weight store initial weight information on database
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });


        //Init Line chart
        mpLineChart = (LineChart) findViewById(R.id.lineChart);
        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "Weight/days");
        LineDataSet lineDataSet2 = new LineDataSet(dataValues2(), "Strength/days");
        LineDataSet lineDataSet3 = new LineDataSet(dataValues3(), "BMI/days");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);
        dataSets.add(lineDataSet3);


        //Set data sets properties
        lineDataSet1.setColor(Color.GREEN);
        lineDataSet3.setColor(Color.RED);
        lineDataSet1.setLineWidth(4f);
        lineDataSet2.setLineWidth(4f);
        lineDataSet3.setLineWidth(4f);


        //init new line data with dataSets
        LineData data = new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.invalidate();

    }

    private ArrayList<Entry> dataValues1() {
        ArrayList<Entry> weightValues = new ArrayList<Entry>();
        weightValues.add(new Entry(0, 20));
        weightValues.add(new Entry(1, 24));
        weightValues.add(new Entry(2, 2));
        weightValues.add(new Entry(3, 10));
        weightValues.add(new Entry(4, 28));

        return weightValues;

    }

    private ArrayList<Entry> dataValues2() {
        ArrayList<Entry> strengthValues = new ArrayList<Entry>();
        strengthValues.add(new Entry(1, 60));
        strengthValues.add(new Entry(2, 62));
        strengthValues.add(new Entry(3, 58));
        strengthValues.add(new Entry(4, 54));
        strengthValues.add(new Entry(5, 52));

        return strengthValues;

    }

    private ArrayList<Entry> dataValues3() {
        ArrayList<Entry> BMIvalues = new ArrayList<Entry>();
        BMIvalues.add(new Entry(1, 80));
        BMIvalues.add(new Entry(2, 81));
        BMIvalues.add(new Entry(3, 75));
        BMIvalues.add(new Entry(4, 70));
        BMIvalues.add(new Entry(5, 65));

        return BMIvalues;

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }


}
