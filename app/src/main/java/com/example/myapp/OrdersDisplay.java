package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapp.Adapters.AdapterClientsCoach;
import com.example.myapp.Adapters.AdapterOrders;
import com.example.myapp.Models.Client2;
import com.example.myapp.Models.OrderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrdersDisplay extends AppCompatActivity {

    DatabaseReference clientsReference,ordersReference;
    RecyclerView recyclerView;
    ArrayList<OrderModel> list;
    AdapterOrders adapter;
    FirebaseAuth auth;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_display);

        recyclerView =  (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrdersDisplay.this));
        list = new ArrayList<>();


        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("My Orders");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //Firebase auth
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        final String id=user.getUid();
        email = user.getEmail().trim();

        //Firebase database references
        clientsReference = FirebaseDatabase.getInstance().getReference("Clients");
        ordersReference = FirebaseDatabase.getInstance().getReference("Clients").child(id).child("Orders");


        ordersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())

                {
                    OrderModel order =dataSnapshot1.getValue(OrderModel.class);
                    if(!(order==null)){
                       {
                            list.add(order);
                        }}
                }

                if (list.size()==0)
                {
                    Intent intent = new Intent(OrdersDisplay.this, noOrdersToDisplay.class);
                    startActivity(intent);

                }

                else{

                adapter= new AdapterOrders(OrdersDisplay.this,list,email,ordersReference,id);
                recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(OrdersDisplay.this,"Can't connect to the database.",Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }


}
