package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapp.Models.Report;
import com.example.myapp.Models.Client2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClientReportInputPage extends AppCompatActivity {

    //Edit text fields to get the values from xml page
    EditText weightEdtTx, strengthEdtx, currentIBMEdtx;
    Button addBtn;
    String UID;


    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference CoachesRef, ClientsRef;
    String currentUserID;
    String currentUserName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_report_input_page);

        //Setting the components from xml view
        weightEdtTx = (EditText) findViewById(R.id.weightEdtTx);
        strengthEdtx = (EditText) findViewById(R.id.strengthEdtx);
        currentIBMEdtx = (EditText) findViewById(R.id.currentIBMEdtx);
        addBtn = (Button) findViewById(R.id.addBtn);

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        currentUserID = user.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ClientsRef =firebaseDatabase.getReference("Clients");



        //Button to submit the values and create a new report entry
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                ClientsRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())

                        {
                            Client2 client=dataSnapshot1.getValue(Client2.class);
                            if(!(client==null)){
                            //Toast.makeText(CoachClients.this,"CoachID "+client.getCoachID() ,Toast.LENGTH_LONG).show();
                            if(client.getUID().equals(currentUserID)){

                                Report report = new Report();
                                report.setBMI(currentIBMEdtx.getText().toString().trim());
                                report.setWeight(weightEdtTx.getText().toString().trim());
                                report.setStrentgh(strengthEdtx.getText().toString().trim());

                                ClientsRef.child(currentUserID).child("Reports").push().setValue(report);



                            }
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

    }


}
